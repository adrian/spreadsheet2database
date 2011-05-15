#!/bin/sh

# figure out the dir we're in so we can build a classpath
PRGDIR=`dirname $0`
BASEDIR=$PRGDIR/..

java -jar $BASEDIR/lib/${project.build.finalName}-${project.version}.jar $@