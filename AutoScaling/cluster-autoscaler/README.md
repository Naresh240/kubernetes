# cluster-autoscaler

# Pre-Requisites
    - EKS-Cluster Setup
# Clone the cluster autoscaler code
    git clone https://github.com/Naresh240/cluster-autoscaler.git
    cd cluster-autoscaler
# Install cluster autoscaler
    kubectl apply -f service-account.yml
    kubectl apply -f cluster-autoscaler-deployment.yml
# Check cluster autoscaler pod 
    kubectl get pod -n kube-system
# Deploy "ngix" application
    kubectl apply -f deployment.yml
# Cluster Scale UP: Scale our application to 30 pods
    kubectl scale --replicas=30 deploy ca-demo-deployment
# Terminal - 2: Verify nodes
    kubectl get nodes -o wide
# Cluster Scale DOWN: Scale our application to 1 pod
  Terminal - 1: Keep monitoring cluster autoscaler logs
	  
    kubectl -n kube-system logs -f deployment.apps/cluster-autoscaler
  
  Terminal - 2: Scale down the demo application to 1 pod
	  
    kubectl scale --replicas=1 deploy ca-demo-deployment 
	
  Terminal - 2: Verify nodes
	
    kubectl get nodes -o wide    
