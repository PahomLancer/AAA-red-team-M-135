#!/bin/bash 
mkdir -p workAP 
javac -classpath ".:../libs/*" -sourcepath EnumConstants -d ../workAP EnumConstants/*.java
javac -classpath ".:../libs/*" -sourcepath . -d ../workAP *.java
jar -cfe AAA.jar main1 -C workAP .
