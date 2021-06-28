# kubernetes-configmap-and-secret-explained

# We need to Install External Secrets in our existing claster
    helm repo add external-secrets https://external-secrets.github.io/kubernetes-external-secrets/
    helm install external-secrets external-secrets/kubernetes-external-secrets
  
  ````Remove External Secrets Service Account````
    
    kubectl delete sa external-secrets-kubernetes-external-secrets
  
  ````Create a policy in IAM with below policy````
  
	{
		"Version": "2012-10-17",
		"Statement": [
			{
				"Sid": "VisualEditor0",
				"Effect": "Allow",
				"Action": [
					"secretsmanager:DescribeSecret",
					"secretsmanager:PutSecretValue",
					"secretsmanager:CreateSecret",
					"secretsmanager:DeleteSecret",
					"secretsmanager:CancelRotateSecret",
					"secretsmanager:ListSecretVersionIds",
					"secretsmanager:UpdateSecret",
					"secretsmanager:GetRandomPassword",
					"secretsmanager:GetResourcePolicy",
					"secretsmanager:GetSecretValue",
					"secretsmanager:StopReplicationToReplica",
					"secretsmanager:ReplicateSecretToRegions",
					"secretsmanager:RestoreSecret",
					"secretsmanager:RotateSecret",
					"secretsmanager:UpdateSecretVersionStage",
					"secretsmanager:RemoveRegionsFromReplication"
				],
				"Resource": "*"
			}
		]
	}
    
  ````Create IAM Role with Policy```` (Role Name should be ````external-secrets-kubernetes-external-secrets````)
	
    eksctl create iamserviceaccount \
		--region us-east-1 \
		--name external-secrets-kubernetes-external-secrets \
		--namespace default \
		--cluster eksdemo \
		--attach-policy-arn arn:aws:iam::601279438670:policy/external-secrets \
		--override-existing-serviceaccounts \
		--approve    
# Restart External secret POD and check pod
    kubectl delete pod <external-secrets pod>
    kubectl get pod
# Create a secrets in AWS Console
  ![image](https://user-images.githubusercontent.com/58024415/121695890-23198d80-cae9-11eb-8d80-61c851210981.png)

  ````Below are credentials:````
  
  ![image](https://user-images.githubusercontent.com/58024415/121695936-2ca2f580-cae9-11eb-80b5-ceaf81ee7a15.png)

# Deploying mysql in kubernetes cluster
    kubectl apply -f pv.yml
    kubectl apply -f pvc.yml
    kubectl apply -f configmaps.yml
    kubectl apply -f secrets.yml
    kubectl apply -f deployment.yml
# Connect to mysql pod
    kubectl exec -it <pod-name> -- /bin/bash
# Connect to mysql
    mysql -u naresh -p
  Provide password for mysql
# Connect to mysqldb
    use mysqldb
# Create "employee" table in mysqldb
    create table employee( empno varchar(40), ename varchar(40));
# Insert data into "employee" table
    insert into employee(empno, ename) values("101","Naresh");
# Check table data
    select * from employee;
