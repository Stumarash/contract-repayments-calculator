#!/bin/bash

# Exit on error and undefined variable usage
set -euo pipefail

# Constants
APP_NAME="contract-repayments-calculator"
IMAGE_NAME="$APP_NAME:latest"
NAMESPACE="default"
HELM_CHART_DIR="./helm-charts"
DEPLOYMENT_TIMEOUT="5m"
REQUIRED_MINIKUBE_VERSION="v1.34.0"

# Logging functions
log() {
    echo -e "\033[1;32m[$(date +'%Y-%m-%d %H:%M:%S')] \$@\033[0m"
}

error_exit() {
    echo -e "\033[1;31m[$(date +'%Y-%m-%d %H:%M:%S')] Error: \$@\033[0m" >&2
    exit 1
}

cleanup() {
    log "Cleaning up failed Helm release..."
    helm uninstall "$APP_NAME" --namespace "$NAMESPACE" || log "No Helm release to clean up."
}

trap cleanup ERR # Cleanup on any error

# Check Minikube version
log "Checking Minikube version..."
CURRENT_MINIKUBE_VERSION=$(minikube version --short | awk '{print $3}')

if [[ "$CURRENT_MINIKUBE_VERSION" != "$REQUIRED_MINIKUBE_VERSION" ]]; then
    log "Expected Minikube version: $REQUIRED_MINIKUBE_VERSION -> Actual: $CURRENT_MINIKUBE_VERSION"
    log "Recreating Minikube cluster to ensure compatibility..."
    minikube delete || log "No existing Minikube cluster to delete."
    minikube start || error_exit "Failed to start Minikube."
else
    log "Minikube version is compatible."
fi

# Ensure Minikube is running
if ! minikube status | grep -q "host: Running"; then
    log "Starting Minikube..."
    minikube start || error_exit "Failed to start Minikube."
else
    log "Minikube is already running."
fi

# Enable ingress addon
log "Enabling Minikube ingress addon..."
minikube addons enable ingress || error_exit "Failed to enable ingress addon."

# Set Docker environment to Minikube
log "Configuring Docker environment for Minikube..."
eval "$(minikube docker-env)" || error_exit "Failed to configure Docker environment."

# Build Docker image
log "Building Docker image '$IMAGE_NAME'..."
docker build -t "$IMAGE_NAME" . || error_exit "Failed to build Docker image."

# Verify Docker image build
if ! docker images --format '{{.Repository}}:{{.Tag}}' | grep -q "^$IMAGE_NAME$"; then
    error_exit "Docker image '$IMAGE_NAME' not found after build."
fi

log "Docker image '$IMAGE_NAME' built successfully."

# Deploy using Helm
log "Deploying application with Helm..."
helm upgrade --install "$APP_NAME" "$HELM_CHART_DIR" \
    --namespace "$NAMESPACE" \
    --create-namespace \
    --wait \
    --timeout "$DEPLOYMENT_TIMEOUT" || error_exit "Helm deployment failed."

# Check deployment status
log "Verifying deployment status..."
if ! kubectl get pods -n "$NAMESPACE" | grep -q "$APP_NAME"; then
    error_exit "Deployment verification failed. Pod for '$APP_NAME' not found."
fi

log "Deployment completed successfully!"
