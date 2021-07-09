# Pre-Requisites
    EKS Cluster
# Create NameSpace ````devops````
    kubectl create ns devops
# Create deployment
    kubectl apply -f deployment.yml
# Get User details
    aws sts get-caller-identity
# Create user in AWS and get AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
    vi rbacuser_creds.sh
  provide details and save file
# Get ConfigMaps details
    kubectl get configmaps aws-auth -n kube-system
  ![image](https://user-images.githubusercontent.com/58024415/125119059-2a46b200-e10e-11eb-8524-63a9f4b5cf52.png)
# Add User info to eks-cluster with in ConfigMaps file
    kubectl edit configmaps aws-auth -n kube-system
    --------------------------------------------------------
    mapUsers: |
      - userarn: arn:aws:iam::601279438670:user/naresh
        username: naresh
    --------------------------------------------------------        
# Role Binding In RBAC:
A role in Kubernetes RBAC defines what you will do to a group of resources. It contains a group of rules which define a set of permission.

Here’s an example Role within the “default” namespace that can be used to grant read access to pods:

  ![image](https://user-images.githubusercontent.com/58024415/124542906-ea4baa80-de41-11eb-8272-ec0d480f0c95.png)
  
    kubectl apply -f rbacuser-role.yaml
# Cluster Role Binding In RBAC
  ![image](https://user-images.githubusercontent.com/58024415/124542959-02232e80-de42-11eb-8829-c94c8dbb05cb.png)
  
    kubectl apply -f rbacuser-role-binding.yaml
# Run shell script ````rbacuser_creds.sh````
    . rbacuser_creds.sh
# Now check User
    aws sts get-caller-identity
# Check pods of other namespace
    kubectl get pods
  It will through some error
  
  ![image](https://user-images.githubusercontent.com/58024415/124939886-1e33f500-e027-11eb-9149-c480762cd30c.png)

# Check pod details with in namespace with new user ````naresh````
    kubectl get pods -n devops
 
  ![image](https://user-images.githubusercontent.com/58024415/124939928-27bd5d00-e027-11eb-9912-82a30c10afb5.png)
