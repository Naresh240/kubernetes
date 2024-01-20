# Path Based Routing

1. Create hosted zone in Route53 with same of domain

![image](https://github.com/Naresh240/springboot-hello/assets/156821153/9d10d237-c6eb-452f-ae1c-8c3876bef9fb)

2. Create certificate for domain under ACM Service

![image](https://github.com/Naresh240/springboot-hello/assets/156821153/0b27bf31-d753-47b9-96f4-f9561b805d64)

3. Run below commands

```bash
kubectl apply -f springboot.yaml
kubectl apply -f ingress.yaml
```

