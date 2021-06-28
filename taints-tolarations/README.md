# Blue Green Deployment NodejsApp

# Pre-requisites:
    - Install GIT
    - EKS Cluster
# EKS Cluster Setup:
  [EKS Cluster Setup](https://github.com/Naresh240/eks-cluster-setup/blob/main/README.md)
# Install GIT:
    yum install git -y
# Install Docker:
    yum install docker -y
    service docker start
# Clone code from github:
    git clone https://github.com/Naresh240/RollingUpdate-Rollback-NodejsApp
    cd RollingUpdate-Rollback-NodejsApp
# Build Docker image for Springboot Application
    docker build -t naresh240/nodejs-k8s:v1 .
# Docker login
    docker login
# Push docker image to dockerhub
    docker push naresh240/nodejs-k8s:v1
# Check where our nginx pods are running
    kubectl get nodes
  ![image](https://user-images.githubusercontent.com/58024415/95653219-adea5880-0b14-11eb-9072-d57604801168.png)
# Deploy nginx without taints and tolarations Application using below commands:
    kubectl apply -f nginx-deployment.yml
  Pods are running on both the nodes, Please check below

    kubectl get pods -o wide
  ![image](https://user-images.githubusercontent.com/58024415/95653649-c6a83d80-0b17-11eb-931f-da78e28652ca.png)
# Create taint on single node (having key=value:NoSchedule)
    kubectl taint nodes ip-192-168-28-158.ec2.internal key=value:NoSchedule
# Check Node description
    kubectl describe node ip-192-168-28-158.ec2.internal
  ![image](https://user-images.githubusercontent.com/58024415/95653357-d030a600-0b15-11eb-9543-c7d4f051953f.png)
# Deploy Nodejs application with tolations (having Effect: NoSchedule)
    kubectl apply -f nodejs-deployment-NoSchedule.yml
  ![image](https://user-images.githubusercontent.com/58024415/95653674-feaf8080-0b17-11eb-9356-aacff42bc61c.png)

Pods are running on both the nodes, if you use Effect: NoSchedule
# Create taint on other node (having key=value:NoExecute)
    kubectl taint nodes ip-192-168-47-188.ec2.internal key=value:NoExecute
# Check where pods running
    kubectl get pods -o wide
  ![image](https://user-images.githubusercontent.com/58024415/95653476-aa57d100-0b16-11eb-808f-9d1b5e11c5da.png)
# Deploy Nodejs application with tolations (having Effect: NoExecute)
    kubectl apply -f kubectl apply -f nodejs-deployment-NoExecute.yml
  Pods will run on node which having Effect: NoExecute and also terminate older pods, because deployment name is same.
  
  ![image](https://user-images.githubusercontent.com/58024415/95653535-07538700-0b17-11eb-9530-7ec030dc8185.png)
# CleanUp:
Deployments:

    kubectl delete -f nodejs-deployment-NoExecute.yml
    kubectl delete -f nginx-deployment.yml

Taints:    

    kubectl taint nodes ip-192-168-47-188.ec2.internal key:NoExecute-
    kubectl taint nodes ip-192-168-28-158.ec2.internal key:NoSchedule-
