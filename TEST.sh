#!/bin/bash

result=0

test () {
    arr=($1)
    ./RUN.sh ${arr[*]}
    status=$?
    if [[ $status -ne $2 ]]; then
        echo TESTING FAIL [$1] $status "!=" $2
        ((result+=1))
    else
        echo TESTING OK [$1] $status "==" $2
    fi
    return $status
}

./BUILD.sh

test "" 0
test "-h" 0

test "-login XXX -pass XXX" 1
test "-login jdoe -pass XXX" 2
test "-login jdoe -pass sup3rpaZZ" 0

test "-login jdoe -pass sup3rpaZZ -role read -res a" 0
test "-login jdoe -pass sup3rpaZZ -role read -res a.b" 0
test "-login jdoe -pass sup3rpaZZ -role xxx -res a.b" 3
test "-login jdoe -pass sup3rpaZZ -role read -res XXX" 4
test "-login jdoe -pass sup3rpaZZ -role write -res a" 4
test "-login jdoe -pass sup3rpaZZ -role write -res a.bc" 4

test "-login jdoe -pass sup3rpaZZ -role read -res a -ds 2015-05-01 -de 2015-05-02 -vol 100" 0
test "-login jdoe -pass sup3rpaZZ -role read -res a -ds XXX -de XXX -vol XXX" 5
test "-login jdoe -pass sup3rpaZZ -role read -res a -ds 2015-05-01 -de 2015-05-02 -vol XXX" 5

echo
if [[ $result -gt 0 ]]; then
    echo $result tests failed
    exit 1
else
    echo ALL TESTS PASSED
    exit 0
fi

