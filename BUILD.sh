#!/bin/bash -v
rm -rf "out"
mkdir -p "out/classes"
find . -name "*.java" | xargs javac -cp "lib/*" -d out/classes -sourcepath src -verbose

mkdir -p "out/lib"
cp lib/* out/lib/

jar -cfe out/aaa.jar com.andr.Main -C out/classes/ .
