# Host Bbased Routing

1. Create hosted zone in Route53 with same of domain

![image](https://github.com/Naresh240/springboot-hello/assets/156821153/9d10d237-c6eb-452f-ae1c-8c3876bef9fb)

2. Create certificate for domain under ACM Service

![image](https://github.com/Naresh240/springboot-hello/assets/156821153/0b27bf31-d753-47b9-96f4-f9561b805d64)

3. Run below commands

```bash
kubectl apply -f nginx.yaml
kubectl apply -f springboot.yaml
kubectl apply -f ingress.yaml
```

4. Create records under Hosted zone

![image](https://github.com/Naresh240/springboot-hello/assets/156821153/86c10514-a399-43c7-bed7-6086aa93137c)

5. Check applications output

>https://nginx.devopstechtrainer.com/

![image](https://github.com/Naresh240/springboot-hello/assets/156821153/fea4d64d-4ad5-4fd6-bc90-818d35a42c4c)

>https://springboot.devopstechtrainer.com/

![image](https://github.com/Naresh240/springboot-hello/assets/156821153/ede2a7cf-1628-49d8-94cf-b60445f45dd8)
