#!/bin/bash

for i in ./Problems/*.tsp; do
	name="$(basename $i .tsp)"
	java -jar ./TSP/TSP.jar solve $name
done


for i in ./Problems/*.tsp; do
	name="$(basename $i .tsp)"
	problem="./Problems/$name.tsp"
	solution="./Solutions/$name.opt.tour"
	python3 tourCheckv3.py "$problem" $solution  $(cat "./Solutions/$name.claim")
done