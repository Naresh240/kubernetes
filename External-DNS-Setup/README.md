# External-DNS-Setup-Kubernetes
# Pre-Requisites
    - EKS-Cluster Setup
    - Application Load Balancer
# EKS-Cluster Setup:
  [EKS Cluster Setup](https://github.com/Naresh240/eks-cluster-setup/blob/main/README.md)
# ALB-Ingress-Controller Setup:
  [ALB Ingress Controller](https://github.com/Naresh240/ALB-Ingress-Controller-Setup/blob/main/README.md)
# External DNS Setup
  Create policy for External-DNS using below JSON content:
  
    {
      "Version": "2012-10-17",
      "Statement": [
        {
          "Effect": "Allow",
          "Action": [
            "route53:ChangeResourceRecordSets"
          ],
          "Resource": [
            "arn:aws:route53:::hostedzone/*"
          ]
        },
        {
          "Effect": "Allow",
          "Action": [
            "route53:ListHostedZones",
            "route53:ListResourceRecordSets"
          ],
          "Resource": [
            "*"
          ]
        }
      ]
    }
 # Create Iam Role and attach Policy and service account:
     eksctl create iamserviceaccount \
        --name external-dns \
        --region us-east-1 \
        --namespace default \
        --cluster eksdemo \
        --attach-policy-arn arn:aws:iam::119159500181:policy/External-DNS \
        --approve \
        --override-existing-serviceaccounts
 # Verify the Service Account:
    kubectl get sa external-dns
 # Deploy External-dns yaml file
    kubectl apply -f external-dns.yml
