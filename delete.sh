#!/bin/bash

docker rm -f mysql frontend backend
docker rmi -f skill_share_mysql skill_share_frontend skill_share_backend