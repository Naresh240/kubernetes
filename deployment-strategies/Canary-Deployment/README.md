# Canary Deployment NodejsApp

# Pre-requisites:
    - Install GIT
    - EKS Cluster
    - Install ALB-Ingress-Controller
    - Install Istio
    - Request a Cerficate using Certificate Manager
    - Create Hosted Zone with our Domain Name
    - External DNS Setup
# EKS Cluster Setup:
  [EKS Cluster Setup](https://github.com/Naresh240/eks-cluster-setup/blob/main/README.md)
# ALB Ingress Controller Setup:
  [ALB Ingress Controller](https://github.com/Naresh240/ALB-Ingress-Controller-Setup/blob/main/README.md)
# Istio Setup:
  [Istio](https://github.com/Naresh240/Istio-Installation.git)
# Create Hosted Zone with our Domain Name
![image](https://user-images.githubusercontent.com/58024415/94990966-7e2fd380-059d-11eb-8285-a82353f38c1a.png)
# Request a Cerficate using Certificate Manager
![image](https://user-images.githubusercontent.com/58024415/94990930-301ad000-059d-11eb-9c5d-8ee47d494f82.png)
# External DNS Setup:
  [External DNS](https://github.com/Naresh240/External-DNS-Setup-Kubernetes/tree/main)
# Install GIT:
    yum install git -y
# Install Docker:
    yum install docker -y
    service docker start
# Clone code from github:
    git clone https://github.com/Naresh240/Canary-Deployment-NodejsApp.git
    cd Canary-Deployment-NodejsApp
# Build Maven Artifact:
    npm install
# Build Docker image for Springboot Application
    docker build -t naresh240/node-web-app:v1 .
# Docker login
    docker login
# Push docker image to dockerhub
    docker push naresh240/node-web-app:v1
# Upgrading for nodejs Application:
Edit our our application and Build docker image with new tag:
    
    docker build -t naresh240/node-web-app:v2 .

Push Docker image to docker hub with tag v2:

    docker push naresh240/node-web-app:v2
# Deploy nodejs Application using below commands:
    kubectl apply -f deployment-v1.yml
    kubectl apply -f deployment-v2.yml
    kubectl apply -f service.yml
# Check Deployments, Pods and Services:
    kubectl get deploy
    kubectl get pods
    kubectl get svc
# Create an Istio Gateway, VirtualService and default destination rules
    kubectl apply -f istio-rules.yml
# Check Istio Gateway
    kubectl get gateway -n istio-system
    kubectl get virtualservice -n istio-system
    kubectl get destinationRule -n istio-system
# Run ingress for checking output with DNS name
    kubectl apply -f ingress.yml
# Check Load Balancer of ALB ingress controller attached to ingress or not
    kubectl get ingress
# Two records creates automatically in Route53 because of External DNS
![image](https://user-images.githubusercontent.com/68885738/95646444-31408580-0ae6-11eb-84c6-5181a322dd45.png)
# Go to UI and check our external dns, which showing output application with HTTPS
  https://nodejs.cloudtechmasters.ml/
  
![image](https://user-images.githubusercontent.com/58024415/95006082-dc040000-061d-11eb-8fd6-da6c80216c54.png)  
# Check application number of times you can get to know how our applicaiton will work based on Destination rules:
  https://nodejs.cloudtechmasters.ml/
  
![image](https://user-images.githubusercontent.com/58024415/95006177-0a360f80-061f-11eb-9b58-02376f1cd9e4.png)
