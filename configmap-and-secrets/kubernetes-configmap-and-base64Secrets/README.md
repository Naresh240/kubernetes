# kubernetes-configmap-and-secret-explained

# Encode PASSWORD of mysql using following command
    echo -n "admin123" | base64
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
