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

4. Create records under Hosted zone

![image](https://github.com/Naresh240/springboot-hello/assets/58024415/3add4d87-d649-4c5d-90d0-39585b22c7a4)

5. Check applications output

>https://www.devopstechtrainer.com/

![image](https://github.com/Naresh240/springboot-hello/assets/58024415/5dbd1866-6945-4d8f-80dc-20d38ea6bf58)

>https://www.devopstechtrainer.com/hello

![image](https://github.com/Naresh240/springboot-hello/assets/58024415/2f9e5327-d83f-4b7b-ad72-588d8f0e7ff3)
