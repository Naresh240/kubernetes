# Blue Green Deployment NodejsApp

# Pre-requisites:
    - Install GIT
    - EKS Cluster
    - Install ALB-Ingress-Controller
    - Request a Cerficate using Certificate Manager
    - Create Hosted Zone with our Domain Name
    - External DNS Setup
# EKS Cluster Setup:
  [EKS Cluster Setup](https://github.com/Naresh240/eks-cluster-setup/blob/main/README.md)
# ALB Ingress Controller Setup:
  [ALB Ingress Controller](https://github.com/Naresh240/ALB-Ingress-Controller-Setup/blob/main/README.md)
# Create Hosted Zone with our Domain Name
![image](https://user-images.githubusercontent.com/58024415/94990966-7e2fd380-059d-11eb-8285-a82353f38c1a.png)
# Request a Cerficate using Certificate Manager
![image](https://user-images.githubusercontent.com/58024415/94990930-301ad000-059d-11eb-9c5d-8ee47d494f82.png)
# External DNS Setup:
  [External DNS](https://github.com/Naresh240/External-DNS-Setup-Kubernetes/tree/main)
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
    git clone https://github.com/Naresh240/RollingUpdate-Rollback-NodejsApp
    cd RollingUpdate-Rollback-NodejsApp
# Build Maven Artifact:
    npm install
# Build Docker image for Springboot Application
    docker build -t naresh240/nodejs-k8s:v1 .
# Docker login
    docker login
# Push docker image to dockerhub
    docker push naresh240/nodejs-k8s:v1
# Deploy nodejs Application using below commands:
    kubectl apply -f deployment.yml
    kubectl apply -f service.yml
# Check pods and services:
    kubectl get pods
    kubectl get svc
# Run ingress for checking output with DNS name
    kubectl apply -f ingress.yml
# Check Load Balancer of ALB ingress controller attached to ingress or not
    kubectl get ingress
# Go to UI and check our external dns, which showing output application with HTTPS
  https://nodejs.cloudtechmasters.ml/
  
![image](https://user-images.githubusercontent.com/58024415/95006082-dc040000-061d-11eb-8fd6-da6c80216c54.png)
# Upgrading for nodejs Application:
Edit our our application and Build docker image with new tag:
    
    docker build -t naresh240/nodejs-k8s:v2 .

Push Docker image to docker hub with tag v2:

    docker push naresh240/nodejs-k8s:v2

upgrade nodejs application with tag v2:
    
    kubectl rollout history deployment nodejs-deployment
    
Check rollout history for revision "1"
    
    kubectl rollout history deployment nodejs-deployment --revision=1
    
Upgrade new image using below command
    
    kubectl set image deployment nodejs-deployment nodejs-deployment=naresh240/nodejs-k8s:v2
    
# Goto Web UI and check updated version output
![image](https://user-images.githubusercontent.com/58024415/95006858-854ef400-0626-11eb-8250-9a5d4a559e11.png)

Check rollout history for revision "2"

    kubectl rollout history deployment nodejs-deployment --revision=2
  
 Rollout to previous version using below command 
    
    kubectl rollout undo deployment nodejs-deployment --to-revision=1
    
# Goto Web UI and check again:
  https://nodejs.cloudtechmasters.ml/
  
![image](https://user-images.githubusercontent.com/58024415/95006993-fa6ef900-0627-11eb-8269-66299b56f504.png)

