# spring-boot-hello

# Pre-requisites:
  - Install Java
  - Install GIT
  - Install Maven
  - Install Docker
  - Install Jenkins
  - Minikube SetUp
# Minukube Set-UP
  [minikube](https://github.com/Naresh240/minikube-setup/blob/main/README.md)
# Clone code from github:
    git clone https://github.com/Naresh240/minikube-springbootCICD.git
    cd minikube-springbootCICD
# Build Maven Artifact:
    mvn clean install
# Build Docker image for Springboot Application:
    docker build -t naresh240/springboot-k8s:latest .
# Docker login:
    docker login
# Push docker image to dockerhub:
    docker push naresh240/springboot-k8s:latest
# Deploy Springboot Application on EKS-Cluster:
    kubectl apply -f deployement.yml
# If you use Jenkins, we can deploy with Jenkins pipelines also
  1. Add sudo permissions for jenkins user
      
    visudo
    ----------------------------------------------------------
    jenkins ALL=(ALL)       NOPASSWD: ALL
    ----------------------------------------------------------
  2. Add below plugins in jenkins
  
    docker
    docker pipeline
    Kubernetes Continuous Deploy
  3. Create credentials for docker and kubernetes config file
  4. Create jenkins job and click on build
