#!/usr/bin/env bash
set -eux

sbt dist

docker build -t sitebudgets .

