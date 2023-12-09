#!/bin/bash

mvn compile
# ./cli.sh /pool aor

# 检测java环境变量，应为jdk-11
javac --version

# 参数处理
POOL_DIR="$1"
MUTATION_TYPE="$2"
TESTSUITE_FQN="$3"
MUTATION_TARGET=/home/Fuzzaroo/src/main/java/edu/nju/mutest/example/"$4"

# 变异算子选择（变异测试中使用的变异算子是可配置可选择的）
echo ""
echo "[STAGE] choose mutation operator"
ENGINE_PATH=edu.nju.mutest.MutationEngine
EXECUTION_PATH=edu.nju.mutest.DemoMutantExecution
TESTSUITE_PATH=/home/Fuzzaroo/testsuite

java -cp ./target/classes:./lib/javaparser-core-3.25.5.jar:./lib/commons-io-2.11.0.jar "$ENGINE_PATH"  "$MUTATION_TARGET" "$POOL_DIR" "$MUTATION_TYPE"


# 变异生成
echo ""
echo "[STAGE] generate mutants"
for FN in $(ls "$POOL_DIR")
do
    MUT_DIR="$POOL_DIR/$FN"
    for SRC_FILE in $(find "$MUT_DIR" | grep ".java")
    do
        echo "javac -d $MUT_DIR $SRC_FILE"
        javac -d "$MUT_DIR" "$SRC_FILE"
    done
done

# 变异执行
echo ""
echo "[STAGE] execute mutants, calculate score"
java -cp ./target/classes:./lib/javaparser-core-3.25.5.jar:./lib/commons-io-2.11.0.jar "$EXECUTION_PATH" "$TESTSUITE_PATH" "$POOL_DIR" "$TESTSUITE_FQN"


