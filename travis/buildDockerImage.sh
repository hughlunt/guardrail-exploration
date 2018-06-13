#!/usr/bin/env bash
set -eux

sbt dist

docker build -t sitebudgets .

docker tag sitebudgets mdsol.jfrog.io/mdsol/payments-docker/sitebudgets:latest

docker push mdsol.jfrog.io/mdsol/payments-docker/sitebudgets:latest