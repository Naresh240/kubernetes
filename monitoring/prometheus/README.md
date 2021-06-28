# prometheus-kubernetes

## To create Namespace
    kubectl apply -f namespace.yaml
## To create cluster Role and Role-binding
    kubectl apply -f clusterRole.yaml
## To create Configmap
    kubectl apply -f config-map.yaml
## To create Deployment
    kubectl apply -f deployment.yaml
## To create Service
    kubectl apply -f service.yaml
## Check deployments, pods and services using below commands
    kubectl get deploy -n monitoring
    kubectl get pods -n monitoring
    kubectl get svc -n monitoring
## Allow Nodeport port number "30001" with in security group
## Goto UI and open prometheus page
    <IP-address>:30001
