# Helm chart maintenance

1. Create namespace `kubectl create namespace app-ujapi`
2. Create secret with `MONGO_URI` env variable `kubectl create secret generic mongo-uri --from-literal=MONGO_URI=<value> -n app-ujapi` 
