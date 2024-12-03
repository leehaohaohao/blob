#!/bin/bash
# 定义变量
IMAGE_NAME="blob"
CONTAINER_NAME="blob"
HOST_UPLOAD_DIR="/tools/blob/static"
CONTAINER_UPLOAD_DIR="/app/blob/static"
HOST_PORT_HTTP=9090
CONTAINER_PORT_HTTP=9090
HOST_PORT_WS=9091  # 新增 WebSocket 的端口
CONTAINER_PORT_WS=9091
# 停止并删除已有的容器（如果存在）
docker stop $CONTAINER_NAME
docker rm $CONTAINER_NAME
# 构建 Docker 镜像（如果需要）
docker build -t $IMAGE_NAME .
# 启动 Docker 容器，并挂载宿主机的上传目录，同时映射多个端口
docker run -d \
  -p $HOST_PORT_HTTP:$CONTAINER_PORT_HTTP \
  -p $HOST_PORT_WS:$CONTAINER_PORT_WS \
  --name $CONTAINER_NAME \
  -v $HOST_UPLOAD_DIR:$CONTAINER_UPLOAD_DIR \
  $IMAGE_NAME
# 输出容器的运行状态
docker ps
