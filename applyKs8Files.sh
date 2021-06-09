kubectl apply -f kubernetes/k8s-namespace-create.yml && \
kubectl apply -f kubernetes/k8s-ingress.yml && \
kubectl apply -f kubernetes/k8s-service.yml && \
kubectl apply -f kubernetes/k8s-hpa.yml && \
kubectl apply -f kubernetes/k8s-deployments.yml
