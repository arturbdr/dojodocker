# dojodocker


### Using Minikube to read LOCAL images:
1. Set the environment variables with ```eval $(minikube docker-env)```
2. Build the image with the Docker daemon of Minikube (eg ``` docker build -t my-image .```)
3. Set the image in the pod spec like the build tag (eg my-image)
4. Set the imagePullPolicy to Never, otherwise Kubernetes will try to download the image.
5. Important note: You have to run eval $(minikube docker-env) on each terminal you want to use, since it only sets the environment variables for the current shell session.


### Using https for ingress:
```openssl req -x509 -newkey rsa:4096 -sha256 -nodes -keyout tls.key -out tls.crt -subj "/CN=my.local.machine" -days 365
	kubectl -n kube-system create secret tls my-local-machine --cert=tls.crt --key=tls.key```

