# eks-cluster-with-configfile

# Step1: Take EC2 Instance with t2.xlarge instance type
# Step2: Create IAM Role with Admin policy for eks-cluster and attach to ec2-instance
# Step3: Install kubectl
	curl -o kubectl https://amazon-eks.s3-us-west-2.amazonaws.com/1.14.6/2019-08-22/bin/linux/amd64/kubectl
	chmod +x ./kubectl
	mkdir -p $HOME/bin
	cp ./kubectl $HOME/bin/kubectl
	export PATH=$HOME/bin:$PATH
	echo 'export PATH=$HOME/bin:$PATH' >> ~/.bashrc
	source $HOME/.bashrc
	kubectl version --short --client

# Step4: Install eksctl:
    curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
    sudo mv /tmp/eksctl /usr/bin
    eksctl version
# Step5: Run custer.yml file
    eksctl create cluster -f cluster.yml
