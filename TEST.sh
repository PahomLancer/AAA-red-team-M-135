#!/bin/sh

RES=0

function test {
	ARR=($1)
    bash ./RUN.sh ${ARR[*]}
    STAT=$?
	if [ $STAT -eq $2 ]; then
		echo OK
	else
		echo $STAT instead of $2
		((RES+=1))
	fi
}

test "" 0

test "-h" 0

test "-login XXX -pass XXX" 1


test "-login jdoe  -pass XXX" 2


test "-login jdoe  -pass sup3rpaZZ" 0

test "-login jdoe  -pass sup3rpaZZ -role READ -res a" 0

test "-login jdoe  -pass sup3rpaZZ -role READ -res a.b" 0

test "-login jdoe  -pass sup3rpaZZ -role XXX -res a.b" 3

test "-login jdoe  -pass sup3rpaZZ -role READ -res XXX" 4

test "-login jdoe  -pass sup3rpaZZ -role WRITE -res a" 4

test "-login jdoe  -pass sup3rpaZZ -role WRITE -res a.bc" 4

test "-login jdoe  -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol 100" 0

test "-login jdoe  -pass sup3rpaZZ -role READ -res a.b -ds 01-01-2015 -de 2015-12-31 -vol 100" 5

test "-login jdoe  -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol XXX" 5

if [ $RES -ne 0 ]; then
	echo $RES TESTS FAILED
	exit 1
else
	echo EVERYTHING IS OK
	exit 0
fi