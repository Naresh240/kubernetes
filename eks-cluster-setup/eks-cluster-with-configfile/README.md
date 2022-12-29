# eks-cluster-with-configfile

## Step1: Take EC2 Instance with t2.micro instance type
## Step2: Create IAM Role with Admin policy for eks-cluster and attach to ec2-instance
## Step3: Install kubectl

```bash
curl -o kubectl https://s3.us-west-2.amazonaws.com/amazon-eks/1.22.6/2022-03-09/bin/linux/amd64/kubectl
chmod +x ./kubectl
cp kubectl /usr/bin
kubectl version --short --client
```

## Step4: Install eksctl:

```bash
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/bin
eksctl version
```

## Step5: Run custer.yml file

```bash
eksctl create cluster -f cluster.yml
```
