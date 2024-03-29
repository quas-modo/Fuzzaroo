# Fuzzaroo开发日志

## 小组分工

| 姓名   | 开发负责内容                                       |
| ------ | -------------------------------------------------- |
| 陈欣怡 | abs、aor突变算子以及对应测试套件，cli实现          |
| 王骏佳 | lcr突变算子以及对应测试套件                        |
| 耿瑞林 | uoi，air突变算子以及对应测试套件，复杂测试套件设计 |
| 刘博悦 | ror突变算子以及对应测试套件                        |

## 进度安排

### 11.20-11.25

主要目标：代码预备

| 任务                                     | 人员分配                       |
| ---------------------------------------- | ------------------------------ |
| 阅读突变测试demo，理解核心代码和突变流程 | 陈欣怡、耿瑞林、刘博悦、王骏佳 |
| 构建基础架构                             | 陈欣怡                         |
| 运行整体流程                             | 耿瑞林                         |

### 11.26-12.3

主要目标：实现基本功能，也即5种基本突变算子

| 任务                                  | 人员分配 |
| ------------------------------------- | -------- |
| ABS - Absolute Value Insertion        | 陈欣怡   |
| AOR - Arithmetic Operator Replacement | 陈欣怡   |
| LCR - Logical Connector Replacement   | 王骏佳   |
| ROR - Relational Operator Replacement | 刘博悦   |
| UOI - Unary Operator Insertion        | 耿瑞林   |

### 12.4-12.10

主要目标：针对五种基本算子设计基本测试套件，设计复杂测试套件，开始编写CLI

| 任务                       | 人员分配 |
| -------------------------- | -------- |
| ABS测试套件                | 陈欣怡   |
| AOR测试套件                | 陈欣怡   |
| LCR测试套件                | 王骏佳   |
| ROR测试套件                | 刘博悦   |
| UOI测试套件                | 耿瑞林   |
| Complex测试套件            | 耿瑞林   |
| CLI雏形（用shell脚本实现） | 陈欣怡   |

### 12.11-12.17

主要目标：完善项目文档，完善CLI

| 任务                    | 人员分配                       |
| ----------------------- | ------------------------------ |
| 项目文档完善            | 陈欣怡、耿瑞林、刘博悦、王骏佳 |
| CLI完善（用nodejs实现） | 陈欣怡                         |

### 12.18-12.24

主要目标：完善项目文档

| 任务         | 人员分配                       |
| ------------ | ------------------------------ |
| 项目文档完善 | 陈欣怡、耿瑞林、刘博悦、王骏佳 |



## 所遇难题与解决方法

### 多平台同步问题

在开发团队中，团队成员的主力设备为2台windows和2台macos，在之后编写cli的过程中，又引入了linux-ubuntu服务器。而在开发中涉及shell脚本、bash脚本、javac编译的地方都存在各平台命令、符号不同步的地方。比如，contateClassPath这个函数，Unix/Linux系统的路径分隔符是冒号（:），Windows的路径分隔符是分号（;）。

### 单operator中适配变异语法不报错问题

在变异代码的过程中，将类似于`return a == b`的语句变异为`return !a == b`是非法的，所以要对其进行标准化的处理。处理方法是加上括号使之成为类似于`return !(a == b)`的形式。

**解决方法：**将`！`变异符单独处理，并解析AST抽象语法树中的括号栈，使其适配不同情况下的逻辑变异。需要分析其括号栈的层数以及逻辑变异符号具体的位置。





