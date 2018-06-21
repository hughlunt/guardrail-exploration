#!/usr/bin/env bash

set -eux

setup_git() {
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis CI"
}

add_release_tag() {
  if [ "$TRAVIS_PULL_REQUEST" = "false" ] && [ `dirname "$TRAVIS_BRANCH"` = "release" ]
  then
      release_tag=`basename "$TRAVIS_BRANCH"`
      git checkout $TRAVIS_BRANCH
      if git tag --list | grep "$release_tag"
      then
          git tag -d $release_tag
          git push origin :refs/tags/$release_tag
      fi
      git tag -a $release_tag -m "Release $release_tag"
  else
      echo "Tagging ONLY occurs for release branches"
  fi
  git push --tags
}

setup_git
add_release_tag
