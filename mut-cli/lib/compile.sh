#！ /bin/bash

echo -e "\e[36m[STAGE] compiler mutator\e[0m"

POOL_DIR=/home/Fuzzaroo/pool

for FN in $(ls "$POOL_DIR")
do
    MUT_DIR="$POOL_DIR/$FN"
    for SRC_FILE in $(find "$MUT_DIR" | grep ".java")
    do
        echo "javac -d $MUT_DIR $SRC_FILE"
        javac -d "$MUT_DIR" "$SRC_FILE"
    done
done