# Minikube Cluster creation steps

# kubectl (install)
	curl -o kubectl https://amazon-eks.s3-us-west-2.amazonaws.com/1.14.6/2019-08-22/bin/linux/amd64/kubectl
	chmod +x ./kubectl
	mv kubectl /usr/bin
	kubectl version --short --client

# Install docker:
	yum install docker -y
	service docker start

# Minikube setup:
	curl -Lo minikube https://github.com/kubernetes/minikube/releases/download/v1.25.2/minikube-linux-amd64
	chmod +x minikube
	sudo mv minikube /usr/local/bin/
	yum install conntrack -y
	export PATH=/usr/local/bin:$PATH
	minikube start --driver=none
