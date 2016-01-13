java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -h

java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login XXX -pass XXX
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass XXX
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ

java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ -role READ -res a
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ -role READ -res a.b
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ -role XXX -res a.b
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ -role READ -res XXX
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ -role WRITE -res a
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ -role WRITE -res a.bc

java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol 100
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 01-01-2015 -de 2015-12-31 -vol 100
java -classpath "commons-cli-1.3.1.jar;AAA.jar" main1 -login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol XXX

pause
