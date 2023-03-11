# ALB Install Ingress Controller:

> :note: Please create eks cluster with version 1.24 only
## cluster setup
```bash
# eks master
eksctl create cluster --name=eksdemo \
                  --region=us-east-1 \
		  --version=1.24 \
                  --zones=us-east-1a,us-east-1b \
                  --without-nodegroup 

# eks nodegroup
eksctl create nodegroup --cluster=eksdemo \
                   --region=us-east-1 \
                   --name=eksdemo-ng-public \
                   --node-type=t2.large \
                   --nodes=2 \
                   --nodes-min=2 \
                   --nodes-max=4 \
                   --node-volume-size=10 \
                   --ssh-access \
                   --ssh-public-key=awsdevops \
                   --managed \
                   --asg-access \
                   --external-dns-access \
                   --full-ecr-access \
                   --appmesh-access \
                   --alb-ingress-access
# OIDC provider
eksctl utils associate-iam-oidc-provider \
    --region us-east-1 \
    --cluster eksdemo \
    --approve
```

## Create policy using below json file:

1. Download policy using below command

```bash
curl -O https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/v2.4.7/docs/install/iam_policy.json

```

2. Create an IAM policy using the policy downloaded

```bash
aws iam create-policy \
    --policy-name AWSLoadBalancerControllerIAMPolicy \
    --policy-document file://iam_policy.json
```

## Create Role and attach policy to a Role

```bash
eksctl create iamserviceaccount \
  --cluster=eksdemo \
  --region us-east-1 \
  --namespace=kube-system \
  --name=aws-load-balancer-controller \
  --role-name AmazonEKSLoadBalancerControllerRole \
  --attach-policy-arn=arn:aws:iam::111122223333:policy/AWSLoadBalancerControllerIAMPolicy \
  --approve
```

# Deploy ALB Ingress Controller

1. Install cert-manager using one of the following method

```bash
kubectl apply \
    --validate=false \
    -f https://github.com/jetstack/cert-manager/releases/download/v1.5.4/cert-manager.yaml
```

2. Download the controller specification file

```bash
curl -Lo v2_4_7_full.yaml https://github.com/kubernetes-sigs/aws-load-balancer-controller/releases/download/v2.4.7/v2_4_7_full.yaml
```

3. If you downloaded the v2_4_7_full.yaml file, run the following command to remove the ServiceAccount section in the manifest

```bash
sed -i.bak -e '561,569d' ./v2_4_7_full.yaml
```

4. Replace your-cluster-name in the Deployment spec section using below command

```bash
sed -i.bak -e 's|your-cluster-name|eksdemo|' ./v2_4_7_full.yaml
```

5. Apply file to deploy ALB Ingress Controller

```bash
kubectl apply -f v2_4_7_full.yaml
```

## Verify Deployment

```bash
kubectl get deploy -n kube-system
```

## Verify our ALB Ingress Controller is running

```bash
kubectl get pods -n kube-system
```
