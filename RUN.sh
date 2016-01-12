#!/bin/bash
# add -x for debug output
java -cp "lib/*:out/aaa.jar" com.blzr.Main $@ > /dev/null
