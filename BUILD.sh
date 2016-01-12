#!/bin/sh
mkdir -p bin
cd src
javac -classpath ".:../libs/*" -sourcepath EnumConstants -d ../bin EnumConstants/*.java
javac -classpath ".:../libs/*" -sourcepath . -d ../bin *.java
cd ..
jar -cfe bin/aaa.jar MainClass -C bin .
rm -rf bin/*.class
rm -rf bin/EnumConstants/*.class
rm -rf bin/EnumConstants