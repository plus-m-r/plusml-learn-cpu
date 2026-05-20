# Chisel CPU 设计项目

## 📖 项目简介

这是一个基于 Chisel (Constructing Hardware In a Scala Embedded Language) 的 CPU 设计学习项目。Chisel 是一种开源的硬件描述语言 (HDL),基于 Scala,用于描述数字电路和系统。

## 🛠️ 环境要求

### 已安装的工具
- **Java**: OpenJDK 21.0.10
- **sbt**: 1.12.0 (Scala Build Tool)
- **Verilator**: 5.020 (硬件仿真器)
- **GTKWave**: 3.3.116 (波形查看器)

### 依赖库
- **Chisel**: 6.5.0
- **chiseltest**: 6.0.0
- **Scala**: 2.13.12

## 📁 项目结构

```
chisel-test/
├── build.sbt                          # sbt 构建配置
├── src/
│   ├── main/scala/
│   │   └── SimpleAdder.scala         # 主模块:8位加法器
│   └── test/scala/
│       ├── SimpleAdderTester.scala   # 基础测试
│       └── WaveformTester.scala      # 波形生成测试
├── generated/
│   └── SimpleAdder.sv                # 生成的 SystemVerilog 代码
├── test_run_dir/                      # 测试运行目录(包含 VCD 波形文件)
├── target/                            # 编译输出目录
└── README.md                          # 本文件
```

## 🚀 快速开始

### 1. 生成硬件描述代码

```bash
cd chisel-test
sbt "runMain SimpleAdderDriver"
```

这会在 `generated/` 目录下生成 SystemVerilog 代码。

### 2. 运行测试

```bash
# 运行所有测试
sbt test

# 运行特定测试
sbt "testOnly SimpleAdderTester"
sbt "testOnly WaveformTester"
```

### 3. 查看波形

```bash
# GTKWave 已安装,直接打开波形文件
gtkwave test_run_dir/*/SimpleAdder.vcd
```

在 GTKWave 中:
1. 左侧面板展开 `SimpleAdder` 模块
2. 选择信号:`io_a`, `io_b`, `io_sum`, `clock`
3. 拖拽到右侧波形显示区域
4. 观察信号随时钟周期的变化

## 💡 示例说明

### SimpleAdder - 8位加法器

这是一个简单的组合逻辑电路,实现两个 8 位数的加法运算。

**接口定义:**
- `io.a`: 输入 A (8位)
- `io.b`: 输入 B (8位)
- `io.sum`: 输出和 (8位)

**功能:**
```scala
io.sum := io.a + io.b
```

**测试结果示例:**
```
✓ Test 1:   0 +   0 =   0
✓ Test 2:   1 +   2 =   3
✓ Test 3:   5 +  10 =  15
✓ Test 4: 100 +  50 = 150
✓ Test 5: 255 +   1 =   0 (溢出)
✓ Test 6: 128 + 127 = 255
✓ Test 7:  64 +  64 = 128
```

## 🔧 常用命令

### 编译和运行
```bash
# 清理项目
sbt clean

# 编译项目
sbt compile

# 生成 Verilog/SystemVerilog
sbt run

# 运行主程序
sbt "runMain SimpleAdderDriver"
```

### 测试
```bash
# 运行所有测试
sbt test

# 运行单个测试类
sbt "testOnly <TestClassName>"

# 运行单个测试用例
sbt "testOnly * -- -z \"test name\""
```

### 仿真
```bash
# 使用 Verilator 编译生成的 SV 代码
verilator --cc generated/SimpleAdder.sv --exe --top-module SimpleAdder

# 编译 C++ 仿真模型
make -C obj_dir -f VSimpleAdder.mk

# 运行仿真
./obj_dir/VSimpleAdder
```

## 📊 查看效果的方法

### 方法 1: 控制台输出
运行测试时,测试用例会打印计算结果到控制台。

### 方法 2: SystemVerilog 代码
查看 `generated/SimpleAdder.sv` 文件,这是 Chisel 编译后生成的硬件描述代码。

### 方法 3: 波形图 (VCD)
测试会生成 VCD (Value Change Dump) 格式的波形文件,可以使用 GTKWave 查看信号的时序变化。

### 方法 4: Verilator 仿真
使用 Verilator 将 SystemVerilog 编译为 C++ 模型,进行更高效的仿真。

## 🎓 学习路线

### 阶段 1: 基础组件
- ✅ 组合逻辑:加法器、比较器、多路选择器
- ⬜ 时序逻辑:寄存器、计数器、移位寄存器
- ⬜ 存储器:RAM、ROM、寄存器文件

### 阶段 2: CPU 核心组件
- ⬜ ALU (算术逻辑单元)
- ⬜ 指令译码器
- ⬜ 控制单元
- ⬜ 程序计数器 (PC)

### 阶段 3: 完整 CPU
- ⬜ 单周期 CPU
- ⬜ 流水线 CPU
- ⬜ RISC-V 指令集支持

### 阶段 4: 高级特性
- ⬜ 缓存系统
- ⬜ 分支预测
- ⬜ 中断处理

## 📚 学习资源

### 官方文档
- [Chisel 官方网站](https://www.chisel-lang.org/)
- [Chisel 文档](https://www.chisel-lang.org/docs/)
- [Chisel API 参考](https://www.chisel-lang.org/api/latest/)

### 教程
- [Chisel 教程仓库](https://github.com/freechipsproject/chisel-tutorial)
- [Berkeley CS250 课程](https://cs250.seas.harvard.edu/)
- [RISC-V 规范](https://riscv.org/specifications/)

### 书籍
- 《Digital Design and Computer Architecture》- David Money Harris
- 《Computer Organization and Design RISC-V Edition》- David A. Patterson

### 社区
- [Chisel Gitter 聊天室](https://gitter.im/freechipsproject/chisel3)
- [Stack Overflow - Chisel 标签](https://stackoverflow.com/questions/tagged/chisel)

## 🐛 常见问题

### Q1: sbt 下载依赖很慢?
**A:** 配置国内镜像源,在 `~/.sbt/repositories` 中添加:
```
[repositories]
  local
  aliyun: https://maven.aliyun.com/repository/public
  typesafe: https://repo.typesafe.com/typesafe/ivy-releases/, [organization]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext]
```

### Q2: 如何修改设计?
**A:** 编辑 `src/main/scala/SimpleAdder.scala`,然后运行 `sbt run` 重新生成硬件描述。

### Q3: 如何添加新测试?
**A:** 在 `src/test/scala/` 目录下创建新的 Scala 测试文件,继承 `AnyFlatSpec with ChiselScalatestTester`。

### Q4: 波形文件在哪里?
**A:** 每次测试都会在 `test_run_dir/` 下生成新的子目录,其中包含 `.vcd` 波形文件。

### Q5: 如何清理项目?
**A:** 运行 `sbt clean` 清理编译产物,或删除 `target/` 和 `test_run_dir/` 目录。

### Q6: 遇到编译错误怎么办?
**A:** 
1. 检查 Scala 语法是否正确
2. 确保导入了必要的 Chisel 包
3. 运行 `sbt clean` 后重新编译
4. 查看详细错误信息:`sbt -debug compile`

## 📝 开发规范

### 代码风格
- 使用 camelCase 命名变量和方法
- 使用 PascalCase 命名类和对象
- 模块接口使用 `IO()` 包裹
- 添加必要的注释说明设计意图

### 测试规范
- 每个模块都应该有对应的测试
- 测试应覆盖边界情况 (如溢出、零值等)
- 使用有意义的测试名称
- 添加断言验证预期行为

### 提交规范
- 提交前确保所有测试通过
- 提交消息清晰描述改动
- 重要改动更新本文档

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request!

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交改动 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

## 👨‍💻 作者

CPU 设计学习者

## 🙏 致谢

- Chisel 开发团队
- Berkeley 大学
- RISC-V International

---

**祝你学习愉快!🚀**

*最后更新: 2026年5月20日*
