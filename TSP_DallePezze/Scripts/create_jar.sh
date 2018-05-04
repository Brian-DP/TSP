#!/bin/bash

cd ../TSPSolver

jar cfm TSPSolver.jar ./META-INF/MANIFEST.MF TSPSolver.class ./Structure ./IO ./Algorithm

echo "JAR created"
