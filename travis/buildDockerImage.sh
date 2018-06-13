#!/usr/bin/env bash
set -eux

sbt dist

docker build -t sitebudgets .

docker tag sitebudgets mdsol-payments-docker.jfrog.io/sitebudgets

docker push mdsol-payments-docker.jfrog.io/sitebudgets