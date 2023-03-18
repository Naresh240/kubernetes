# Daemonsets

## Setup Elasticsearch and Kibana
1. Create repo file for elasticsearch and kibana under /etc/yum.repos.d

```bash
[elasticsearch]
name=Elasticsearch repository for 8.x packages
baseurl=https://artifacts.elastic.co/packages/8.x/yum
gpgcheck=1
gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch
enabled=1
autorefresh=1
type=rpm-md
```

2. Install elasticsearch

```bash
yum install elasticsearch -y
```

3. Edit /etc/elasticsearch/elasticsearch.yml file to expose outside

```bash
network.host: 0.0.0.0
xpack.security.enabled: false
```

4. Start elasticseach service

```bash
systemctl start elasticsearch
```

5. Install kibana

```bash
yum install kibana -y
```

6. Edit /etc/kibana/kibana.yml file to expose to outside and connects to elastic search

```bash
server.port: 5601
server.host: "0.0.0.0"
elasticsearch.hosts: ["http://localhost:9200"]
```

7. Start kibana service

```bash
systemctl start kibana
```

## Run filebeat and metricsbeats to collect logs from each server

```Note:``` Update filebeat.yaml and metricsbeat.yaml files with elastic server ip

```bash
kubectl apply -f filebeat.yaml
kubectl apply -f metricsbeat.yaml
```