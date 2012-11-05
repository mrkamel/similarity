#!/bin/sh

/usr/bin/java -classpath jetty/*:lire/*:bin Similarity 2>&1 | /usr/bin/tee -a log/similarity.log

