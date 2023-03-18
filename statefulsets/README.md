# Statefulsets

## We need to run files as below sequence
```bash
kubectl apply -f storage-class.yaml
kubectl apply -f pv.yaml
kubectl apply -f pvc.yaml
kubectl apply -f service.yaml
kubectl apply -f statefulset.yaml
```
