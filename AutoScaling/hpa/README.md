# HPA NodejsApp

# Pre-requisites:
    - Install GIT
    - EKS Cluster
# EKS Cluster Setup:
  [EKS Cluster Setup](https://github.com/Naresh240/eks-cluster-setup/blob/main/README.md)
# Install GIT:
    yum install git -y
# Install npm:
    sudo yum install -y gcc-c++ make
    curl -sL https://rpm.nodesource.com/setup_13.x | sudo -E bash -
    sudo yum install -y nodejs
# Install Docker:
    yum install docker -y
    service docker start
# Clone code from github:
    git clone https://github.com/Naresh240/HPA-NodejsApp
    cd HPA-NodejsApp
# Build Maven Artifact:
    npm install
# Build Docker image for Springboot Application
    docker build -t naresh240/nodejs-hpa-k8s:v1 .
# Docker login
    docker login
# Push docker image to dockerhub
    docker push naresh240/nodejs-hpa-k8s:v1:v1
# Deploy nodejs Application using below commands:
    kubectl apply -f deployment.yml
    kubectl apply -f service.yml
# Check all inside kubernetes:
    kubectl get all
![image](https://user-images.githubusercontent.com/58024415/95016236-5610a500-066f-11eb-9779-2c538af6bbd4.png)
# If you want see horizontal pod autoscaling we need metrics
    kubectl apply -f metrics-server
# Run HPA for our Nodejs application
    kubectl apply -f hpa.yml
# Check all inside kubernetes:
    kubectl get all  
![image](https://user-images.githubusercontent.com/58024415/95016285-bc95c300-066f-11eb-92db-d7ce4ea32bde.png)
# Connect to pod and increase CPU Utilization using below commands
    kubectl get pods
    kubectl exec -it nodejs-deployment-76c9dffdb9-4m68x -- /bin/bash
    for i in 1 2 3 4; do while : ; do : ; done & done
# Now see pods will increase slowly
    kubectl get all
![image](https://user-images.githubusercontent.com/58024415/95016364-36c64780-0670-11eb-8a5a-3dfbb9c438f5.png)
# Again connect to pod and kill for loop for reducing CPU Utilization using below command
    for i in 1 2 3 4; do kill %$i; done
It will reduce pods again, because CPU utilization reduces
