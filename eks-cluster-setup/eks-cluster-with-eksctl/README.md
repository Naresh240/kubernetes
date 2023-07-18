# EKS clustercreation using eksctl:

# Step1: Take EC2 Instance with t2.micro instance type
# Step2: Create IAM Role with Admin policy for eks-cluster and attach to ec2-instance
# Step3: Install kubectl
	curl -o kubectl https://s3.us-west-2.amazonaws.com/amazon-eks/1.22.6/2022-03-09/bin/linux/amd64/kubectl
	chmod +x ./kubectl
	cp kubectl /usr/bin
	kubectl version --short --client

# Step4: Install eksctl:
    curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
    sudo mv /tmp/eksctl /usr/bin
    eksctl version

# Step5: Cluster creation:
    eksctl create cluster --name=eksdemo \
                      --region=us-east-1 \
                      --zones=us-east-1a,us-east-1b \
		      --version 1.24 \
		      --without-nodegroup 

# Step6: Create node-group:
    eksctl create nodegroup --cluster=eksdemo \
                       --region=us-east-1 \
                       --name=eksdemo-ng-public \
                       --node-type=t2.small \
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
# Step7: Add Iam-Oidc-Providers:
    eksctl utils associate-iam-oidc-provider \
        --region us-east-1 \
        --cluster eksdemo \
	--approve
 
# CleanUP
Delete node-group:
			   
    eksctl delete nodegroup --cluster=eksdemo \
                       --region=us-east-1 \
		       	--name=eksdemo-ng-public
Delete Cluster:
				   
    eksctl delete cluster --name=eksdemo \
                      --region=us-east-1					   			   
