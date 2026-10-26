# Prometheus and Grafana with Helm on EKS

This setup deploys Prometheus and Grafana in the `monitoring` namespace using the official Helm charts and the repository values files.

## Prerequisites

- An EKS cluster is running
- `kubectl` is connected to the cluster
- Helm is installed
- The EBS CSI driver is already configured if persistent volumes are required for Prometheus/Grafana storage

## 1. Create the namespace

```bash
kubectl create namespace monitoring
```

## 2. Add Helm repositories

```bash
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update
```

## 3. Install Prometheus

Use the repository values file from the monitoring folder:

```bash
helm upgrade --install prometheus prometheus-community/prometheus \
  --namespace monitoring \
  --values monitoring/prometheous-values.yaml
```

Important note:
- The Prometheus service created by the Helm chart is `prometheus-server`
- It is exposed inside the cluster as `http://prometheus-server.monitoring.svc.cluster.local:80`

## 4. Port-forward Prometheus locally

```bash
kubectl port-forward -n monitoring svc/prometheus-server 19090:80
```

Then open:

```text
http://localhost:19090
```

## 5. Create Grafana admin secret

```bash
kubectl create secret generic grafana-admin \
  --namespace monitoring \
  --from-literal=admin-user=admin \
  --from-literal=admin-password='Admin#123'
```

## 6. Install Grafana

```bash
helm upgrade --install grafana grafana-community/grafana \
  --namespace monitoring \
  --values monitoring/grafana-values.yaml
```

The Grafana service created by the chart is:

```text
grafana
```

## 7. Port-forward Grafana locally

```bash
kubectl port-forward -n monitoring svc/grafana 3000:80
```

Then open:

```text
http://localhost:3000
```

Login with:

```text
Username: admin
Password: Admin#123
```

## 8. Datasource configuration

The Grafana values file configures the default datasource as:

```text
http://prometheus-server.monitoring.svc.cluster.local:80
```

This is the correct internal Kubernetes service URL for Prometheus.

## 9. Dashboard notes

The dashboard JSON files in the Grafana values are added as custom dashboards under the `default` provider path. These dashboards are intended to work with the metrics exposed by the EKS monitoring stack such as:

- kube_node_status_condition
- kube_node_info
- node_cpu_seconds_total
- node_memory_MemAvailable_bytes
- kube_pod_info

## 10. Verify services

```bash
kubectl get svc -n monitoring
kubectl get pods -n monitoring
```

Expected services include:

- `grafana`
- `prometheus-server`
- `prometheus-alertmanager`
- `prometheus-kube-state-metrics`
- `prometheus-prometheus-node-exporter`

## Useful commands

```bash
kubectl get all -n monitoring
kubectl logs -n monitoring deploy/prometheus-server
kubectl logs -n monitoring deploy/grafana
```

## Troubleshooting

If Grafana or Prometheus is not reachable:

- confirm the namespace is `monitoring`
- confirm the service names are correct
- confirm the port-forward target matches the actual service
- verify the datasource URL uses the in-cluster service name and not localhost

## Summary

This Helm-based deployment gives you a working Prometheus + Grafana stack for EKS monitoring. The key fix in this setup was using the actual Helm-generated service names and matching the datasource URL to the cluster-internal Prometheus service.
