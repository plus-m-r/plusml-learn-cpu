# Chisel 快速入门指南 🎓

## 📖 什么是 Chisel?

**Chisel** (Constructing Hardware In a Scala Embedded Language) 是一种开源的硬件描述语言 (HDL),基于 Scala 编程语言。它由加州大学伯克利分校开发,用于设计数字电路和系统。

### Chisel 的优势

✅ **高级抽象**: 使用 Scala 的强大功能进行硬件设计  
✅ **参数化设计**: 轻松创建可重用的模块  
✅ **类型安全**: 编译时检查,减少错误  
✅ **现代工具链**: 生成 SystemVerilog/Verilog 代码  
✅ **活跃社区**: Berkeley、Google 等公司支持  

---

## 🔧 环境配置

### 必需软件

| 软件 | 最低版本 | 推荐版本 |
|------|---------|---------|
| Java | 11+ | 21 LTS |
| sbt | 1.5+ | 1.12+ |
| Verilator | 4.0+ | 5.0+ |
| GTKWave | 3.3+ | 3.3.116+ |

### 安装步骤 (Ubuntu/WSL)

```bash
# 1. 安装 Java
sudo apt update
sudo apt install openjdk-21-jdk

# 2. 安装 sbt
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | \
  sudo tee /etc/apt/sources.list.d/sbt.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | \
  sudo apt-key add
sudo apt update
sudo apt install sbt

# 3. 安装 Verilator
sudo apt install verilator

# 4. 安装 GTKWave (可选,用于查看波形)
sudo apt install gtkwave
```

### 验证安装

```bash
java -version      # 应显示 Java 21
sbt --version      # 应显示 sbt 1.12+
verilator --version # 应显示 Verilator 5.0+
gtkwave --version  # 应显示 GTKWave 3.3+
```

---

## 💡 Chisel 核心概念

### 1. Module (模块)

Module 是 Chisel 中的基本构建块,类似于 Verilog 中的 `module`。

```scala
import chisel3._

class MyModule extends Module {
  val io = IO(new Bundle {
    // 定义输入输出接口
  })
  
  // 实现逻辑
}
```

### 2. IO (输入输出)

IO 定义模块的外部接口,使用 `Bundle` 组织信号。

```scala
val io = IO(new Bundle {
  val input  = Input(UInt(8.W))   // 8位输入
  val output = Output(UInt(8.W))  // 8位输出
})
```

### 3. 数据类型

| 类型 | 说明 | 示例 |
|------|------|------|
| `UInt` | 无符号整数 | `UInt(8.W)` - 8位无符号数 |
| `SInt` | 有符号整数 | `SInt(16.W)` - 16位有符号数 |
| `Bool` | 布尔值 | `Bool()` - 1位信号 |
| `Bundle` | 结构体 | 组合多个信号 |
| `Vec` | 数组 | `Vec(4, UInt(8.W))` - 4个8位数 |

### 4. 连接运算符

- `:=` : 组合逻辑连接
- `<>` : 双向连接(常用于 Bundle)

```scala
io.output := io.input + 1.U  // 组合逻辑
```

### 5. 时序逻辑

使用 `Reg` 创建寄存器(触发器):

```scala
val counter = RegInit(0.U(8.W))  // 初始化为0的8位寄存器
counter := counter + 1.U        // 每个时钟周期加1
```

---

## 🚀 第一个 Chisel 程序

### SimpleAdder - 8位加法器

```scala
import chisel3._

class SimpleAdder extends Module {
  val io = IO(new Bundle {
    val a   = Input(UInt(8.W))
    val b   = Input(UInt(8.W))
    val sum = Output(UInt(8.W))
  })

  io.sum := io.a + io.b
}
```

### 生成 SystemVerilog 代码

```scala
object SimpleAdderDriver extends App {
  emitVerilog(new SimpleAdder, Array("--target-dir", "generated"))
}
```

运行命令:
```bash
sbt "runMain SimpleAdderDriver"
```

生成的文件:
- `generated/SimpleAdder.sv` - SystemVerilog 代码
- `generated/SimpleAdder.fir` - FIRRTL 中间表示

---

## 🧪 测试方法

### 使用 chiseltest

```scala
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class SimpleAdderTester extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "SimpleAdder"

  it should "add two numbers correctly" in {
    test(new SimpleAdder) { dut =>
      dut.io.a.poke(5.U)
      dut.io.b.poke(3.U)
      dut.clock.step()
      dut.io.sum.expect(8.U)
    }
  }
}
```

运行测试:
```bash
sbt test
```

### 生成波形文件

```scala
test(new SimpleAdder).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
  // 测试代码
}
```

查看波形:
```bash
gtkwave test_run_dir/*/SimpleAdder.vcd
```

---

## 📊 组合逻辑 vs 时序逻辑

### 组合逻辑 (Combinational Logic)

- 输出仅取决于当前输入
- 没有状态存储
- 示例: 加法器、多路选择器

```scala
io.output := io.input1 + io.input2
```

### 时序逻辑 (Sequential Logic)

- 输出取决于输入和内部状态
- 使用时钟同步
- 示例: 计数器、状态机

```scala
val reg = RegInit(0.U(8.W))
reg := reg + 1.U
```

---

## 🎯 常见设计模式

### 1. 条件逻辑

```scala
when(io.condition) {
  io.output := io.input1
}.otherwise {
  io.output := io.input2
}
```

### 2. 多路选择器

```scala
io.output := Mux(io.sel, io.input1, io.input2)
```

### 3. 有限状态机 (FSM)

```scala
val state = RegInit(sIdle)
switch(state) {
  is(sIdle) {
    when(io.start) { state := sRunning }
  }
  is(sRunning) {
    when(io.done) { state := sIdle }
  }
}
```

---

## 🔍 调试技巧

### 1. 打印语句

```scala
printf(p"Clock cycle: $cycle, Value: ${io.value}\n")
```

### 2. 断言

```scala
assert(io.output >= 0.U, "Output should be non-negative")
```

### 3. 波形查看

- 生成 VCD 文件
- 使用 GTKWave 打开
- 观察信号变化

### 4. Peek/Poke 测试

```scala
dut.io.input.poke(42.U)   // 设置输入值
dut.clock.step()           // 推进一个时钟周期
dut.io.output.expect(43.U) // 验证输出值
```

---

## 📚 学习资源

### 官方文档
- [Chisel 官网](https://www.chisel-lang.org/)
- [Chisel 文档](https://www.chisel-lang.org/docs/)
- [API 参考](https://www.chisel-lang.org/api/latest/)

### 教程
- [Chisel Tutorial](https://github.com/freechipsproject/chisel-tutorial)
- [Berkeley CS250](https://cs250.seas.harvard.edu/)

### 书籍
- 《Digital Design and Computer Architecture》

### 社区
- [Gitter 聊天室](https://gitter.im/freechipsproject/chisel3)
- [Stack Overflow](https://stackoverflow.com/questions/tagged/chisel)

---

## ⚠️ 常见错误

### 1. 宽度不匹配

```scala
// 错误: 8位 + 8位 = 9位,但输出只有8位
io.sum := io.a + io.b  // 会溢出!

// 正确: 考虑进位
val result = io.a +& io.b  // 9位结果
io.sum := result(7, 0)     // 取低8位
```

### 2. 忘记时钟步进

```scala
// 错误: 时序逻辑需要时钟步进
dut.io.input.poke(5.U)
dut.io.output.expect(6.U)  // 可能失败!

// 正确: 先步进时钟
dut.io.input.poke(5.U)
dut.clock.step()
dut.io.output.expect(6.U)
```

### 3. 组合逻辑环

```scala
// 错误: 创建组合逻辑环
io.a := io.b
io.b := io.a

// 避免: 确保数据流是单向的
```

---

## 🎓 下一步学习

1. ✅ 完成 SimpleAdder 实验
2. ⬜ 学习更多组合逻辑组件
3. ⬜ 掌握时序逻辑设计
4. ⬜ 实现 ALU
5. ⬜ 构建完整 CPU

---

**祝你学习愉快!** 🚀

*最后更新: 2026年5月20日*
