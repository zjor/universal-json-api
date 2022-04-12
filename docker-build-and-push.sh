#!/bin/bash

DOCKER_USER=zjor
IMAGE_NAME=ujapi
TAG=latest

IMAGE_NAME=${DOCKER_USER}/${IMAGE_NAME}:${TAG}

docker build -t ${IMAGE_NAME} .
docker tag ${IMAGE_NAME} ${IMAGE_NAME}
docker push ${IMAGE_NAME}