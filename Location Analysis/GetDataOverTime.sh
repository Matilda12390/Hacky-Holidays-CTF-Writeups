#!/bin/bash

x=0

./a.out > out.csv

while [ $x -le 300 ]
do
	./a.out >> out.csv
	echo $x
	x=$(( $x + 1 ))
	sleep 1
done
