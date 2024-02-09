#!/bin/bash

docker rm -f mysql frontend backend
docker rmi -f skill_share-mysql skill_share-frontend skill_share-backend
