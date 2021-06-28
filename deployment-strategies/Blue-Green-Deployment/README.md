# Blue Green Deployment NodejsApp

# Pre-requisites:
    - Install Git
    - Install npm
    - Install Docker
    - EKS-Cluster
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
# Install Git:
    yum install git -y
# Install npm:
    sudo yum install -y gcc-c++ make
    curl -sL https://rpm.nodesource.com/setup_13.x | sudo -E bash -
    sudo yum install -y nodejs
# Install Docker:
    yum install docker -y
    service docker start
# Clone code from github:
    git clone https://github.com/VamsiTechTuts/kubernetes.git
    cd kubernetes/nodejs-k8s
# Build Maven Artifact:
    npm install
# Build Docker image for Springboot Application
    docker build -t naresh240/nodejs-k8s:v1 .
# Docker login
    docker login
# Push docker image to dockerhub
    docker push naresh240/nodejs-k8s:v1
# Deploy nodejs Application using below commands:
    kubectl apply -f deployment-blue.yml
    kubectl apply -f service-blue.yml
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

    kubectl apply -f deployment-green.yml
    kubectl apply -f service-green.yml
    
 Update ingress file by editing service name
 
  ![image](https://user-images.githubusercontent.com/58024415/95006146-b9beb200-061e-11eb-9fbe-cfdeca61e59c.png)

Run ingress again

    kubectl apply -f ingress.yml
    
# Goto Web UI and check whether we are getting upgraded output or not:
  https://nodejs.cloudtechmasters.ml/
![image](https://user-images.githubusercontent.com/58024415/95006177-0a360f80-061f-11eb-9b58-02376f1cd9e4.png)
# Remove older version of deployment and service:
    kubectl delete deploy nodejs-deployment-blue
    kubectl delete svc nodejs-service-blue
