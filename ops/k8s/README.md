# Helm chart maintenance

1. Create namespace `kubectl create namespace app-ujapi`
2. Create secret with `MONGO_URI` env variable `kubectl create secret generic mongo-uri --from-literal=MONGO_URI=<value> -n app-ujapi` 
3. Deploy with Helm
```
helm upgrade --namespace app-ujapi --install ujapi --set version=latest ./ops/k8s/ujapi
```