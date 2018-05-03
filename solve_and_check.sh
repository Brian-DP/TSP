#!/bin/bash

cd TSP/out/production/TSP/

for i in ../../../../Problems/*.tsp; do
	name="$(basename $i .tsp)"
	java Main solve $name
done

cd ../../../../

for i in ./Problems/*.tsp; do
	name="$(basename $i .tsp)"
	problem="./Problems/$name.tsp"
	solution="./Solutions/$name.opt.tour"
	python3 tourCheckv3.py "$problem" $solution  $(cat "./Solutions/$name.claim")
done