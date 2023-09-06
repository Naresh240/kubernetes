# cluster-autoscaler

## Pre-Requisites

```shell
EKS-Cluster Setup
```

## Clone the cluster autoscaler code

```shell
git clone https://github.com/Naresh240/kubernetes.git
cd AutoScaling/cluster-autoscaler
```

## Install service-account

```shell
kubectl apply -f service-account.yml
```

## Create IRSA for cluster autoscaler to communicate Auto scaling service in AWS

1. Create policy with below json file
   ```json
	   {
	  "Version": "2012-10-17",
	  "Statement": [
	    {
	      "Sid": "VisualEditor0",
	      "Effect": "Allow",
	      "Action": [
	        "autoscaling:DescribeAutoScalingInstances",
	        "autoscaling:SetDesiredCapacity",
	        "autoscaling:DescribeAutoScalingGroups",
	        "autoscaling:DescribeTags",
	        "autoscaling:DescribeLaunchConfigurations",
	        "ec2:DescribeLaunchTemplateVersions",
	        "ec2:DescribeInstanceTypes",
	        "autoscaling:TerminateInstanceInAutoScalingGroup"
	      ],
	      "Resource": "*"
	    }
	  ]
	}
   ```

   ```shell
   aws iam create-policy \
    --policy-name AutoScalerIAMPolicy \
    --policy-document file://cluster-autoscaler.json
   ```

2. Create IRSA

   ```shell
   eksctl create iamserviceaccount \
  --cluster=eksdemo \
  --region us-east-1 \
  --namespace=kube-system \
  --name=cluster-autoscaler \
  --role-name AutoscalerAole \
  --attach-policy-arn=arn:aws:iam::066289642577:policy/AutoScalerIAMPolicy \
  --approve
  --override-existing-serviceaccounts
   ```

## Run Cluster Autoscaler yaml file

```shell
kubectl apply -f cluster-autoscaler-deployment.yml
```

## Check cluster autoscaler pod

```shell
kubectl get pod -n kube-system
```

## Deploy "ngix" application

```shell
kubectl apply -f deployment.yml
```

## Cluster Scale UP: Scale our application to 30 pods

```shell
kubectl scale --replicas=30 deploy ca-demo-deployment
```

## Terminal - 2: Verify nodes

```shell
kubectl get nodes -o wide
```

## Cluster Scale DOWN: Scale our application to 1 pod
  Terminal - 1: Keep monitoring cluster autoscaler logs
	  
    kubectl -n kube-system logs -f deployment.apps/cluster-autoscaler
  
  Terminal - 2: Scale down the demo application to 1 pod
	  
    kubectl scale --replicas=1 deploy ca-demo-deployment 
	
  Terminal - 2: Verify nodes
	
    kubectl get nodes -o wide    
