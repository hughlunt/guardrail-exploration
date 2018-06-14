#!/usr/bin/env bash
set -eux

sbt dist

docker build -t sitebudgets .

docker login -u="payments-admin" -p="AP8VmfDrdXmMzoBGP4rGDTFUbry" mdsol-payments-docker.jfrog.io

docker tag sitebudgets mdsol-payments-docker.jfrog.io/sitebudgets

docker push mdsol-payments-docker.jfrog.io/sitebudgets