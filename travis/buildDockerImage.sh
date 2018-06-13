#!/usr/bin/env bash
set -eux

sbt dist

docker build -t sitebudgets .

docker tag sitebudgets https://mdsol.jfrog.io/mdsol/payments-docker/sitebudgets:latest

docker push https://mdsol.jfrog.io/mdsol/payments-docker/sitebudgets:latest

