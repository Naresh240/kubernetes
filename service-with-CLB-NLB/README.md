# External-DNS-Nginx

# Pre-requisites:
    - Install GIT
    - EKS Cluster
# Install GIT:
     yum install git -y
# EKS Cluster Setup:
  [EKS Cluster Setup](https://github.com/Naresh240/eks-cluster-setup/blob/main/README.md)
# Deploy all the Manifest files
    kubectl apply -f deployment.yml
    kubectl apply -f service-CLB.yml
# Check deployments, pods and services
    kubectl get deploy
    kubectl get pods
    kubectl get svc
