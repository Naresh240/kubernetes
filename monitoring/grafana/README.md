## grafana-kubernetes

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
## Allow Nodeport port number "30002" with in security group
## Go to UI and open Grafana page
    <IP-Address>:30002
