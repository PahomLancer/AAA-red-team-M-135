#!/bin/bash -v
rm -rf "aaa"
mkdir -p aaa/classes 
mkdir -p aaa/db 
find . -name "*.java" | xargs javac -cp "lib/*" -d aaa/classes -sourcepath src -verbose

cp -r resources aaa/
cp -r src/db/ aaa/
cp -r resources/ aaa/classes/
#cp -r resources/ aaa/

mkdir -p "aaa/lib"
cp lib/* aaa/lib/

jar -cfe aaa/aaa.jar Main -C aaa/classes/ .
