# kubernetes-updating-nodegroup

# Pre-Requisites
    -  EKS-cluster
# Step:1
  Need to create cluster with one node group. Here I created nodegroup with the name of "eksdemo-ng-public1".
  
  ![image](https://user-images.githubusercontent.com/58024415/116839252-40586380-abef-11eb-9684-e4c5f6718c07.png)
# Step:2 
  Deploy application using below command
    
    kubectl apply -f deployment.yml
  ![image](https://user-images.githubusercontent.com/58024415/116839510-49960000-abf0-11eb-9629-e7866946e84e.png)
# Step:3
  Need to create new node group using below command
    
    eksctl create nodegroup --cluster=eksdemo \
                   --region=us-west-1 \
                   --name=eksdemo-ng-public2 \
                   --node-type=t2.small \
                   --nodes=2 \
                   --nodes-min=2 \
                   --nodes-max=4 \
                   --node-volume-size=10 \
                   --ssh-access \
                   --ssh-public-key=Naresh-california \
                   --managed \
                   --asg-access \
                   --external-dns-access \
                   --full-ecr-access \
                   --appmesh-access \
                   --alb-ingress-access	
  ![image](https://user-images.githubusercontent.com/58024415/116839228-261e8580-abef-11eb-9bb7-78d18c481cc3.png)            
# Step:4
  Need to "Cordon" nodes of Old nodegroup, so that new deployment will not be happen on Old nodegroup

# Step:5
  Deploy nodejs application using below command
    
    kubectl apply -f nodejs-deployment.yml
  ![image](https://user-images.githubusercontent.com/58024415/116839559-6cc0af80-abf0-11eb-86b7-aa99bb63978c.png)
# Step:6
  Draining nodes of old nodegroup
   
    kubectl drain ip-192-168-28-250.us-west-1.compute.internal --ignore-daemonsets --delete-local-data
  ![image](https://user-images.githubusercontent.com/58024415/116839765-2750b200-abf1-11eb-9760-0500919b7ed2.png)
    
    kubectl drain ip-192-168-32-74.us-west-1.compute.internal --ignore-daemonsets --delete-local-data
  ![image](https://user-images.githubusercontent.com/58024415/116839919-a940db00-abf1-11eb-9a35-ff381a6d6b1f.png)
# Step:7
  Check pods, where they are running
    
    kubectl get pods -o wide
  ![image](https://user-images.githubusercontent.com/58024415/116839984-e016f100-abf1-11eb-8fdf-25388116c958.png)
# Step:8
  Delete old nodegroup using below command and check after 2min whether nodegroup exists or not
    
    eksctl delete nodegroup --cluster=eksdemo --region=us-west-1 --name=eksdemo-ng-public1
  ![image](https://user-images.githubusercontent.com/58024415/116840071-35530280-abf2-11eb-882a-c989ae4608b2.png)    
