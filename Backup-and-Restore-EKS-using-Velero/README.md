# Backup-and-Restore-EKS-using-Velero

# Install jq:
    yum install jq -y
# Provide AWS_REGION=<Desired-Region-name>
    
    export AWS_REGION=us-east-1
# Create an S3 bucket to backup cluster:
  If you are running this workshop in a region other than us-east-1, use the command below to create S3 bucket. 
  Regions outside of us-east-1 require the appropriate LocationConstraint to be specified in order to create the bucket in the desired region.
    
    export VELERO_BUCKET=$(aws s3api create-bucket \
    --bucket eksworkshop-backup-$(date +%s)-$RANDOM \
    --region $AWS_REGION \
    --create-bucket-configuration LocationConstraint=$AWS_REGION \
    --| jq -r '.Location' \
    --| cut -d'/' -f3 \
    --| cut -d'.' -f1)
  For us-east-1, use the command below to create S3 bucket.
    
    export VELERO_BUCKET=$(aws s3api create-bucket \
    --bucket eksworkshop-backup-$(date +%s)-$RANDOM \
    --region $AWS_REGION \
    --| jq -r '.Location' \
    --| tr -d /)  
# Now, let’s save the VELERO_BUCKET environment variable into the bash_profile
    echo "export VELERO_BUCKET=${VELERO_BUCKET}" | tee -a ~/.bash_profile
# Create an IAM role Velero:
    aws iam create-user --user-name velero
# Attach policies to give velero the necessary permissions:
    cat > velero-policy.json <<EOF
    {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Effect": "Allow",
                "Action": [
                    "ec2:DescribeVolumes",
                    "ec2:DescribeSnapshots",
                    "ec2:CreateTags",
                    "ec2:CreateVolume",
                    "ec2:CreateSnapshot",
                    "ec2:DeleteSnapshot"
                ],
                "Resource": "*"
            },
            {
                "Effect": "Allow",
                "Action": [
                    "s3:GetObject",
                    "s3:DeleteObject",
                    "s3:PutObject",
                    "s3:AbortMultipartUpload",
                    "s3:ListMultipartUploadParts"
                ],
                "Resource": [
                    "arn:aws:s3:::${VELERO_BUCKET}/*"
                ]
            },
            {
                "Effect": "Allow",
                "Action": [
                    "s3:ListBucket"
                ],
                "Resource": [
                    "arn:aws:s3:::${VELERO_BUCKET}"
                ]
            }
        ]
      }
    EOF
# Attach policy to velero IAM User:
     aws iam put-user-policy \
    --user-name velero \
    --policy-name velero \
    --policy-document file://velero-policy.json
# Create an access key for the user: 
    aws iam create-access-key --user-name velero > velero-access-key.json 
# Verify the access key created:
    cat velero-access-key.json  
# Now, let’s set the VELERO_ACCESS_KEY_ID and VELERO_SECRET_ACCESS_KEY environment variables and save them to bash_profile.
    export VELERO_ACCESS_KEY_ID=$(cat velero-access-key.json | jq -r '.AccessKey.AccessKeyId')
    export VELERO_SECRET_ACCESS_KEY=$(cat velero-access-key.json | jq -r '.AccessKey.SecretAccessKey')
    echo "export VELERO_ACCESS_KEY_ID=${VELERO_ACCESS_KEY_ID}" | tee -a ~/.bash_profile
    echo "export VELERO_SECRET_ACCESS_KEY=${VELERO_SECRET_ACCESS_KEY}" | tee -a ~/.bash_profile
# Create a credentials file (velero-credentials) specfic to velero user in your local directory (~/environment). We will need this file when we install velero on EKS
    cat > velero-credentials <<EOF
    [default]
    aws_access_key_id=$VELERO_ACCESS_KEY_ID
    aws_secret_access_key=$VELERO_SECRET_ACCESS_KEY
    EOF
# INSTALL VELERO:
    wget https://github.com/vmware-tanzu/velero/releases/download/v1.3.2/velero-v1.3.2-linux-amd64.tar.gz
    tar -xvf velero-v1.3.2-linux-amd64.tar.gz
    mv velero-v1.3.2-linux-amd64/velero /usr/bin
    velero version
# Install Velero on EKS:
    velero install \
    --provider aws \
    --plugins velero/velero-plugin-for-aws:v1.0.1 \
    --bucket $VELERO_BUCKET \
    --backup-location-config region=$AWS_REGION \
    --snapshot-location-config region=$AWS_REGION \
    --secret-file ./velero-credentials
# Inspect the resources created
    kubectl get all -n velero
# Deploy Nodejs Application in nodejsdeploy namespace
    kubectl apply -f namespace.yml
    kubectl apply -f deployment.yml
    kubectl apply -f service.yml
  Check Deployment and service
    
    kubectl get all -n nodejsdeploy
# BACKUP AND RESTORE
  Let’s backup the "nodejsdeploy" namespace using velero

    velero backup create nodejsdeploy-backup --include-namespaces nodejsdeploy
  
  Check the status of backup
    
    velero backup describe nodejsdeploy-backup
  
  Let’s delete the "nodejsdeploy" namespace to simulate a disaster
  
    kubectl delete namespace nodejsdeploy
  
  Check Deployment and service is deleted or not
      
    kubectl get all -n nodejsdeploy
      
  Restore nodejsdeploy namespace

    velero restore create --from-backup nodejsdeploy-backup

  You can check the restore status using the command below:
  
    velero restore get
    
  Verify if deployments, replicasets, services and pods are restored:
  
      kubectl get all -n nodejsdeploy
