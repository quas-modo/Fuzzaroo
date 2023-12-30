#! /bin/bash
echo "[LOG]The $MUT_NUM mutation diff"
ORIGIN_DIR=/home/Fuzzaroo/src/main/java
POOL_DIR=/home/Fuzzaroo/pool
EXECUTION_DIR=edu/nju/mutest/example

str="$1"
# 检查str是否等于"example"
if [ "$str" = "complex" ]; then
    # 如果是，只将首字母转换为大写
    EXAMPLE="${str^}"
else
    # 否则，将全部字母转换为大写
    EXAMPLE="${str^^}"
fi
NUM="$2"

ORIGIN_PATH=$ORIGIN_DIR/$EXECUTION_DIR/$EXAMPLE"Example.java"
EXECUTION_PATH=$POOL_DIR/"mut-"$NUM/$EXECUTION_DIR/$EXAMPLE"Example.java"

echo $ORIGIN_PATH
echo $EXECUTION_PATH
diff $ORIGIN_PATH $EXECUTION_PATH


