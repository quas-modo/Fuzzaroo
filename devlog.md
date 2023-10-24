# Fuzzaroo开发日志

要求：记录开发过程中的进度安排、任务分配、遇到的难题和解决的过程等。注意描述的逻辑性

## 进度安排
### 阅读demo并构建基础架构
时间：10.23-10.29
### 五种变异算子的实现
时间： 10.30-11.5
参考文件(p25开始)：https://www.cs.cornell.edu/courses/cs5154/2021sp/resources/MutationTesting.pdf

参考助教列出的五种经典变异算子诠释

1. ABS - Absolute Value Insertion
   
   每一个算数表达式或者子表达式都可被`abs()` `negAbs()` `failOnZero`替换 
2. AOR - Arithmetic Operator Replacement

   加减乘除模符号相互替换，如将`a = m * (o + p)`替换成`a = m + (o + p)`
3. LCR - Logical Connector Replacement
   
    逻辑运算符相互替换，如& | ^ && ||
4. ROR - Relational Operator Replacement

   对于每一个关系表达式（< <= > >= = !=)，都将其替换成其他关系运算符或者falseOp或者trueOp
5. UOI - Unary Operator Insertion
   
   将单运算符（+ - ! ~ ++ --)等，加在每一个正确类型的表达式前

## 任务分配
## 所遇难题与解决办法