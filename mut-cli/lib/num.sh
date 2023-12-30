#! /bin/bash
ls /home/Fuzzaroo/pool
MUT_NUM=$(ls /home/Fuzzaroo/pool | wc -l)
echo "[LOG]Generate $MUT_NUM mutations"
