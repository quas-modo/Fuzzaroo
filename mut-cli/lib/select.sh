#! /bin/bash

echo -e "\e[36m[STAGE] choose mutator\e[0m"

# 参数处理
ENGINE_PATH=edu.nju.mutest.MutationEngine
MUTATION_TARGET=/home/Fuzzaroo/src/main/java/edu/nju/mutest/example/AORExample.java # 这个参数会被替换了，不重要
POOL_DIR=/home/Fuzzaroo/pool
MUTATION_TYPE="$1"
TESTSUITE_TYPE="$2"

java -cp /home/Fuzzaroo/target/classes:/home/Fuzzaroo/lib/javaparser-core-3.25.5.jar:/home/Fuzzaroo/lib/commons-io-2.11.0.jar "$ENGINE_PATH"  "$MUTATION_TARGET" "$POOL_DIR" "$MUTATION_TYPE" "$TESTSUITE_TYPE"