#!/bin/bash -v
rm -rf "workAP"
mkdir -p workAP/classes 
find . -name "*.java" | xargs javac -cp "lib/*" -d workAP/classes -sourcepath src -verbose

cp -r src/account/ workAP/classes/
cp -r src/auth/ workAP/classes/
cp -r src/role/ workAP/classes/
cp -r src/work/ workAP/classes/

mkdir -p "workAP/lib"
cp lib/* workAP/lib/

jar -cfe out/aaa.jar main1 -C workAP/classes/ .
