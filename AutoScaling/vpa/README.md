# Vertical-Pod_Autoscaler
  A set of components that automatically adjust the amount of CPU and memory requested by pods running in the Kubernetes Cluster. Current state - beta.
# Pre-Requisites
    - Install Docker
    - EKS Cluster
    - Deploy Metrics
    - Deploy VPA
# EKS Cluster Setup:
  [EKS Cluster Setup](https://github.com/Naresh240/eks-cluster-setup/blob/main/README.md)
# Build and Push Docker Image
    docker build -t naresh240/application-cpu:latest .
    docker login
    docker push naresh240/application-cpu:latest
# Deploy Metrics in Cluster
    kubectl apply -f ./metrics-server
# Install Vertical Pod Autoscaler
    git clone https://github.com/kubernetes/autoscaler.git
    cd autoscaler
    git checkout vpa-release-0.8
    cd vertical-pod-autoscaler
    ./hack/vpa-up.sh
# Deploy Application
    kubectl apply -f deployment.yaml
# Deploy VPA rule for application
    kubectl apply -f vpa.yml
# Check VPA Recommendations
    kubectl describe vpa application-cpu
# Check Pod CPU and memory
    kubectl top pods
# Increase traffic on load
  Step1: Deploy pod to increase traffic
    
    kubectl apply -f traffic-generator.yaml
   
  Step2: Connect to traffic-generator pod
  
    kubectl get pods
    kubectl exec -it traffic-generator sh
  
  Step3: Give below commands to increase load
      
    apk add --no-cache wrk
    wrk -c 5 -t 5 -d 99999 -H "Connection: Closed" http://application-cpu
# Check pods using "--watch"
    kubectl get pods --watch
# Change update policy
    Change policy from "Off" to "Auto"
# Check pod CPU and Memory Again
    kubectl top pods
