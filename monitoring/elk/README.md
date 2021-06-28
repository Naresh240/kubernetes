# kubernetes-elk-monitoring
    kubectl apply -f elastic-search/
    kubectl apply -f logstash/
    kubectl apply -f filebeat/
    kubectl apply -f metricsbeat/
    kubectl apply -f kibana/
# If you want to delete logs from elastic search on schedule basis    
    kubectl apply -f curator-elasticSearch.yml
