# kubernetes-dashboard

Dashboard is a web-based Kubernetes user interface. You can use Dashboard to deploy containerized applications to a Kubernetes cluster, troubleshoot your containerized application, and manage the cluster resources. You can use Dashboard to get an overview of applications running on your cluster, as well as for creating or modifying individual Kubernetes resources (such as Deployments, Jobs, DaemonSets, etc). For example, you can scale a Deployment, initiate a rolling update, restart a pod or deploy new applications using a deploy wizard.

Dashboard also provides information on the state of Kubernetes resources in your cluster and on any errors that may have occurred.

# Deploying the Dashboard UI
  The Dashboard UI is not deployed by default. To deploy it, run the following command:
  
    kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0/aio/deploy/recommended.yaml

# Check Resources of kubernetes-dashboard
    
Need to Edit service type from ClusterIP to NodePort in "kubernetes-dashboard" service

    kubectl edit svc kubernetes-dashboard -n kubernetes-dashboard
    
Note: Add Nodeport portnumber in security group of cluster nodes

# Accessing the Dashboard UI:
Step1: Creating sample user
  we will find out how to create a new user using Service Account mechanism of Kubernetes, grant this user admin permissions and login to Dashboard using bearer token tied to this user.
  
    cat <<EOF | kubectl apply -f -
    apiVersion: v1
    kind: ServiceAccount
    metadata:
      name: admin-user
      namespace: kubernetes-dashboard
    EOF
Step2: Creating a ClusterRoleBinding
  In most cases after provisioning cluster using kops, kubeadm or any other popular tool, the ClusterRole cluster-admin already exists in the cluster. We can use it and create only ClusterRoleBinding for our ServiceAccount. If it does not exist then you need to create this role first and grant required privileges manually.
  
	cat <<EOF | kubectl apply -f -
	apiVersion: rbac.authorization.k8s.io/v1
	kind: ClusterRoleBinding
	metadata:
	  name: admin-user
	roleRef:
	  kind: ClusterRole
	  name: cluster-admin
	  apiGroup: rbac.authorization.k8s.io	  
	subjects:
	- kind: ServiceAccount
	  name: admin-user
	  namespace: kubernetes-dashboard
	EOF
    
# Getting a Bearer Token
    kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')
 
 Now copy the token and paste it into Enter token field on the login screen.

# Goto UI and give <IP-Address>:<nodeport>
   https://18.207.197.103:30001/
 
  ![image](https://user-images.githubusercontent.com/58024415/101757031-ad624400-3afc-11eb-818a-da0fba33a04a.png)

# Check Nodes of Cluster:
  
  ![image](https://user-images.githubusercontent.com/58024415/101759134-39756b00-3aff-11eb-88a1-1b8566e6ae7a.png)
