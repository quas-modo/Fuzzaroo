#! /bin/bash

echo -e "\e[36m[STAGE] execute mutants, calculate score"
EXECUTION_PATH=edu.nju.mutest.DemoMutantExecution
TESTSUITE_PATH=/home/Fuzzaroo/testsuite
POOL_DIR=/home/Fuzzaroo/pool
TESTSUITE_FQN="$1"

java -cp /home/Fuzzaroo/target/classes:/home/Fuzzaroo/lib/javaparser-core-3.25.5.jar:/home/Fuzzaroo/lib/commons-io-2.11.0.jar "$EXECUTION_PATH" "$TESTSUITE_PATH" "$POOL_DIR" "$TESTSUITE_FQN"