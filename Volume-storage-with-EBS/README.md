# EBS Storage with EKS using EBS CSI Driver

This guide shows how to create an Amazon EKS cluster, install the EBS CSI driver, create a StorageClass, and use a PersistentVolumeClaim (PVC) in Kubernetes.

## 1. Create the EKS cluster

```bash
eksctl create cluster \
  --name=eksdemo \
  --region=us-east-1 \
  --zones=us-east-1a,us-east-1b \
  --version=1.34 \
  --without-nodegroup
```

Create a nodegroup:

```bash
eksctl create nodegroup \
  --cluster=eksdemo \
  --region=us-east-1 \
  --name=eksdemo-ng \
  --node-type=t3.small \
  --nodes=3 \
  --nodes-min=3 \
  --nodes-max=4 \
  --node-volume-size=20 \
  --ssh-access \
  --ssh-public-key=awsdevops9am \
  --managed \
  --asg-access \
  --external-dns-access \
  --full-ecr-access \
  --appmesh-access \
  --alb-ingress-access
```

## 2. Enable IAM OIDC and create the EBS CSI service account

```bash
eksctl utils associate-iam-oidc-provider \
  --region us-east-1 \
  --cluster eksdemo \
  --approve
```

```bash
eksctl create iamserviceaccount \
  --cluster eksdemo \
  --namespace kube-system \
  --name ebs-csi-controller-sa \
  --override-existing-serviceaccounts \
  --attach-policy-arn arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy \
  --approve \
  --region us-east-1
```

## 3. Install the EBS CSI driver addon

```bash
aws eks create-addon \
  --cluster-name eksdemo \
  --addon-name aws-ebs-csi-driver \
  --service-account-role-arn arn:aws:iam::<ACCOUNT_ID>:role/eksctl-eksdemo-addon-iamserviceaccount-kube-s-Role1-nDrEZYpK9OqN
```

Replace `<ACCOUNT_ID>` with your AWS account ID and confirm the role ARN matches the IAM service account created by `eksctl`.

## 4. Create StorageClass

The repository already contains a StorageClass manifest:

```yaml
---
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: aws-ebs
provisioner: ebs.csi.aws.com
volumeBindingMode: WaitForFirstConsumer
parameters:
  type: gp3
  fstype: ext4
  encrypted: "true"
reclaimPolicy: Delete
allowVolumeExpansion: true
```

Apply it:

```bash
kubectl apply -f storage-class.yaml
```

## 5. Create a PersistentVolumeClaim (PVC)

Example PVC:

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: ebs-pvc
spec:
  accessModes:
    - ReadWriteOnce
  storageClassName: aws-ebs
  resources:
    requests:
      storage: 5Gi
```

Apply it:

```bash
kubectl apply -f pvc.yaml
```

Check the status:

```bash
kubectl get pvc
kubectl get pv
```

## 6. Use the PVC in a pod

Example pod:

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: app
spec:
  containers:
    - name: app
      image: busybox
      command: ["sh", "-c", "while true; do echo hello > /data/hello.txt; sleep 30; done"]
      volumeMounts:
        - name: ebs-volume
          mountPath: /data
  volumes:
    - name: ebs-volume
      persistentVolumeClaim:
        claimName: ebs-pvc
```

Apply it:

```bash
kubectl apply -f pod.yaml
```

## 7. Verify the storage is working

```bash
kubectl get pods
kubectl exec -it app -- sh
ls -l /data
cat /data/hello.txt
```

You should see the data written to the mounted EBS volume.

## 8. Useful troubleshooting commands

```bash
kubectl get storageclass
kubectl describe pvc ebs-pvc
kubectl describe pv
kubectl get events -n default --sort-by=.metadata.creationTimestamp
```

## Summary

This setup uses the AWS EBS CSI Driver to dynamically provision storage backed by Amazon EBS volumes in an EKS cluster. The StorageClass decides the volume type and mount behavior, and the PVC lets workloads consume persistent storage reliably.
