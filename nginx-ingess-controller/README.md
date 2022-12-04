# spring-boot-hello

## Pre-requisites
```bash
 Install GIT
 EKS Cluster setup
 Create Hosted zone with domain name with in Route53
 Need to have cerified DNS with primary.key & certificate.crt
 Create CNAME record set for DNS name with in Hosted Zone
 ```

## Check Hosted zone with domain name with in ROute53

![image](https://user-images.githubusercontent.com/58024415/205474363-0925a1fb-492d-41e2-a873-21f4f077300e.png)

## I purchased doamin from Godaddy site and added Nameservers here:

![image](https://user-images.githubusercontent.com/58024415/205474401-8cd3372e-9729-4f1e-8c01-d822515fe990.png)

## Cretificate ```openssl``` cretificate
[openssl-create](https://github.com/Naresh240/ssl-and-tls-secret-kubernetes/blob/master/README.md)

## Deploy Nginx-Ingress-Controller using below commands
```bash
cd nginx-ingress-controller
kubectl apply -f .
```

***Note:*** Create "A" record set with nginx ingress controler loadbalancer

## Deploy nginx on EKS-Cluster

```bash
cd ..
kubectl apply -f deployement.yml
kubectl apply -f service.yml
kubectl apply -f ingress.yaml
```

## Check Deployments

```bash
kubectl get deployments
kubectl get pods
kubectl get svc
kubectl get ingress
```

## Check output for the application using dns name:

```bash
https://nginx.awsdevopstrainer.com
```

## Check in browser:

![image](https://user-images.githubusercontent.com/58024415/205474571-61e8a3d8-349e-45d8-ab34-a43ae156ca40.png)
