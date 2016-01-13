#!/bin/bash
# add -x for debug output
# to suppress log add > /dev/null
java -cp "lib/*:out/aaa.jar" com.andr.Main $@

