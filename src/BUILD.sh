#!/bin/bash 
mkdir -p workAP 
javac -classpath ".:../libs/*" -sourcepath EnumConstants -d ../bin EnumConstants/*.java
javac -classpath ".:../libs/*" -sourcepath . -d ../bin *.java
jar -cfe AAA.jar main1 -C workAP .
