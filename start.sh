#!/bin/sh

/usr/bin/java -Xmx400m -classpath jetty/*:lire/*:bin Similarity 2>&1 | /usr/bin/tee -a log/similarity.log

