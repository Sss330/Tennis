FROM ubuntu:latest
LABEL authors="podvo"

ENTRYPOINT ["top", "-b"]