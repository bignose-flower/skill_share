#!/bin/bash

docker rm -f mysql frontend backend
docker rmi -f docker_vue3_mysql docker_vue3_frontend docker_vue3_backend