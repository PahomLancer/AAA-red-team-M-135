#!/bin/bash -v
rm -rf "workAP"
mkdir -p workAP/classes 
find . -name "*.java" | xargs javac -cp "lib/*" -d workAP/classes -sourcepath src -verbose

cp -r resources/ workAP/classes/

mkdir -p "workAP/lib"
cp lib/* workAP/lib/

jar -cfe out/aaa.jar main1 -C workAP/classes/ .