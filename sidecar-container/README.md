# Pre-Requisites
    - Install Git
    - Install Docker
    - Install EKS-Cluster

# Install Git:
    yum install git -y
# Install Docker:
    yum install docker -y
    service docker start
# Clone code from github:
    git clone https://github.com/naresh240/sidecar-kubernetes-python-app.git
    cd sidecar-kubernetes-python-app
# Build Docker image for python Application
    docker build -t naresh240/sidecar-python:latest .
# Docker login
    docker login
# Push docker image to dockerhub
    docker push naresh240/sidecar-python:latest
# Deploy application
    kubectl apply -f deployment.yml
    kubectl apply -f service.yml
# Check running deployments, pods, services using below commands
    kubectl get deploy
    kubectl get pods
    kubectl get svc
# Check logs of two containers using below commands
    kubectl logs python-app-7c88bfb446-99rwm -c redis
    kubectl logs python-app-7c88bfb446-99rwm -c python-app
![image](https://user-images.githubusercontent.com/68885738/93906416-cc311580-fd19-11ea-8988-65e138d80db1.png)
# Go to UI and check with loadbalancer
    http://afad699c3f0c34c48b180a9d4e834ebf-806369083.us-east-1.elb.amazonaws.com/
![image](https://user-images.githubusercontent.com/68885738/93906182-84aa8980-fd19-11ea-87c0-08c0facc510b.png)
