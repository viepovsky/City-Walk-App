@echo off

set DOCKER_USERNAME=viepovsky
set IMAGE_NAME=city-walk
set TAG=latest

docker build . -t %DOCKER_USERNAME%/%IMAGE_NAME%:%TAG%

docker push %DOCKER_USERNAME%/%IMAGE_NAME%:%TAG%