# Fuzzaroo项目文档
Mutation test tool, NJUSE software testing homework
要求：介绍项目的设计方案（架构、流程、类层次设计）和使用方法等。描述应清晰、详实，按照总-分结构介绍。
## 突变算子ROR
ROR(Relational Operator Replacement)，包括运算符 (< <= > >= = !=)。

在这一部分，我们首先用程序RORMutator生成变异体到pool池中。该程序思路是：

当遇到属于集合{< <= > >= = !=}的运算符时，即将其替换成其他关系运算符或者falseOp或trueOp。每个程序只有一处改动，即我们生成了|程序中RO数量|*(|RO类型数|-1+2)个变异体。

接着，我们编写testsuite用来测试。我们首先编写了单一的RORTestSuite用于初步的简单测试，得到了24/42=57.14%的杀伤率，成功验证了我们测试套件和变异算子（变异体）的有效性和良好的结合效果。

此外，我们还编写了复杂的测试套件ComplexTestSuite，用来确认我们全体变异算子在应对复杂情况时的有效性。