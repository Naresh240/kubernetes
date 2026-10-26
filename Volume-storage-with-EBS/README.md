# EBS Storage with EKS using EBS CSI Driver

This guide shows how to configure persistent storage in EKS using the AWS EBS CSI driver and the Kubernetes manifests already available in this repository.

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

## 4. Apply the StorageClass manifest

This repo already contains the StorageClass definition for EBS:

```bash
kubectl apply -f storage-class.yaml
```

You can inspect the file here:

```bash
cat storage-class.yaml
```

## 5. Apply the PVC manifest

The repo already contains a PVC manifest:

```bash
kubectl apply -f pvc.yaml
```

Check the status:

```bash
kubectl get pvc
kubectl get pv
```

## 6. Apply the pod manifest

This repo already contains a sample pod that mounts the PVC:

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

## 9. Example verification flow

```bash
kubectl get pods
kubectl get pvc
kubectl exec -it app -- sh
ls -l /data
cat /data/hello.txt
```

If the pod is running and the file exists, the EBS-backed volume is working correctly.

## Summary

This setup uses the AWS EBS CSI Driver to dynamically provision storage backed by Amazon EBS volumes in an EKS cluster. The StorageClass decides the volume type and mount behavior, and the PVC lets workloads consume persistent storage reliably.

The repository already contains the working manifests for this flow, so you only need to create the cluster, install the EBS CSI driver, and apply the YAMLs in this folder.
