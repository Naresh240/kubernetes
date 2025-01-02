# Minikube Cluster creation steps with Docker driver

## kubectl (install)

```bash
curl -LO https://dl.k8s.io/release/v1.32.0/bin/linux/amd64/kubectl
chmod +x kubectl
mv kubectl /usr/bin/
kubectl version --client=true
```

## Install docker:

```bash
yum install docker -y
service docker start
```

## Minikube setup:

```bash
curl -Lo minikube https://github.com/kubernetes/minikube/releases/download/v1.34.0/minikube-linux-amd64
chmod +x minikube
mv minikube /usr/bin/

## Add non-root user and Switch to non-root user
useradd minikube
usermod -aG docker minikube
su - minikube

## Start minikube with docker driver
minikube start --driver=docker
```
