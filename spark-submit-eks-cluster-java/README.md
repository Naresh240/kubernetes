# spark-submit-eks-cluster-java
./spark-submit --master local --deploy-mode client --jars /root/spark-submit-eks-cluster-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
  --driver-class-path /root/spark-submit-eks-cluster-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
  --conf spark.executor.extraClassPath=/root/spark-submit-eks-cluster-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
  --class com.cloudtechmasters.App /root/spark-submit-eks-cluster-0.0.1-SNAPSHOT-jar-with-dependencies.jar
