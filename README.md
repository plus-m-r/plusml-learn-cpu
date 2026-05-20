# CPU 设计学习项目 🚀

## 📖 项目简介

这是一个从零开始学习 CPU 设计的完整项目。使用现代硬件描述语言 Chisel (基于 Scala) 来设计和实现 CPU,从基础组合逻辑到完整的 RISC-V 处理器。

本项目旨在通过实践深入理解计算机组成原理和数字电路设计,适合对 CPU 设计感兴趣的初学者和进阶学习者。

## 🎯 学习目标

- ✅ 掌握硬件描述语言 Chisel
- ✅ 理解数字电路基础(组合逻辑、时序逻辑)
- ✅ 学习 CPU 核心组件设计(ALU、寄存器文件、控制单元等)
- ✅ 实现 RISC-V 指令集架构
- ✅ 构建单周期和流水线 CPU
- ✅ 掌握硬件仿真和验证技术

## 📁 项目结构

```
学习制作CPU/
├── README.md                    # 项目总览(本文件)
├── docs/                        # 📚 学习文档目录
│   ├── README.md                # 文档索引
│   ├── chisel-basics/           # Chisel 基础知识
│   │   └── README.md            # Chisel 快速入门指南
│   ├── tutorials/               # 实践教程
│   │   ├── README.md            # 教程索引
│   │   └── 查看效果指南.md      # 如何查看设计效果
│   └── references/              # 参考资料
│       └── README.md            # 学习资源列表
└── chisel-test/                 # Chisel 实验项目
    ├── README.md                # Chisel 项目详细说明
    ├── build.sbt                # sbt 构建配置
    ├── src/
    │   ├── main/scala/          # 源代码
    │   │   └── SimpleAdder.scala
    │   └── test/scala/          # 测试代码
    │       ├── SimpleAdderTester.scala
    │       └── WaveformTester.scala
    ├── generated/               # 生成的 SystemVerilog 代码
    ├── test_run_dir/            # 测试运行输出(包含波形文件)
    └── target/                  # 编译产物
```

## 🛠️ 环境配置

### 必需工具

| 工具 | 版本 | 用途 |
|------|------|------|
| Java | 21+ | Scala 运行时环境 |
| sbt | 1.12+ | Scala 构建工具 |
| Verilator | 5.0+ | 硬件仿真器 |
| GTKWave | 3.3+ | 波形查看器(可选) |

### 快速安装 (Ubuntu/WSL)

```bash
# 安装 Java
sudo apt install openjdk-21-jdk

# 安装 sbt
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo apt-key add
sudo apt update
sudo apt install sbt

# 安装 Verilator
sudo apt install verilator

# 安装 GTKWave
sudo apt install gtkwave
```

### 验证安装

```bash
java -version
sbt --version
verilator --version
gtkwave --version
```

## 🚀 快速开始

### 📚 学习文档导航

在开始之前,建议先浏览学习文档:

- **[Chisel 快速入门](docs/chisel-basics/README.md)** - 了解 Chisel 基础概念和语法
- **[查看效果指南](docs/tutorials/查看效果指南.md)** - 学习如何测试和验证设计
- **[参考资料汇总](docs/references/README.md)** - 获取更多学习资源

### 1. 进入 Chisel 项目目录

```bash
cd chisel-test
```

### 2. 生成硬件描述代码

```bash
# 生成 SystemVerilog 代码
sbt "runMain SimpleAdderDriver"
```

生成的代码位于 `generated/` 目录。

### 3. 运行测试

```bash
# 运行所有测试
sbt test

# 运行特定测试
sbt "testOnly SimpleAdderTester"
sbt "testOnly WaveformTester"
```

### 4. 查看波形

```bash
# 打开 VCD 波形文件
gtkwave test_run_dir/*/SimpleAdder.vcd
```

在 GTKWave 中:
1. 左侧展开 `SimpleAdder` 模块
2. 选择信号:`io_a`, `io_b`, `io_sum`, `clock`
3. 拖拽到右侧波形区域查看

详细使用说明请查看 [chisel-test/README.md](chisel-test/README.md)

## 📚 学习路线

### 阶段 1: Chisel 基础 (当前)
- ✅ 环境搭建
- ✅ 第一个 Chisel 模块(加法器)
- ⬜ 组合逻辑:多路选择器、比较器、编码器
- ⬜ 时序逻辑:寄存器、计数器、移位寄存器

### 阶段 2: 基础组件
- ⬜ ALU (算术逻辑单元)
- ⬜ 寄存器文件 (Register File)
- ⬜ 存储器 (RAM/ROM)
- ⬜ 状态机 (FSM)

### 阶段 3: CPU 核心
- ⬜ 指令译码器
- ⬜ 控制单元
- ⬜ 程序计数器 (PC)
- ⬜ 单周期 CPU

### 阶段 4: RISC-V 实现
- ⬜ RV32I 基础指令集
- ⬜ 加载/存储指令
- ⬜ 分支和跳转
- ⬜ 异常处理

### 阶段 5: 高级优化
- ⬜ 流水线 CPU
- ⬜ 数据冒险处理
- ⬜ 控制冒险处理
- ⬜ 缓存系统

## 💡 示例:8位加法器

当前项目实现了简单的 8 位加法器:

```scala
import chisel3._

class SimpleAdder extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(8.W))
    val b = Input(UInt(8.W))
    val sum = Output(UInt(8.W))
  })

  io.sum := io.a + io.b
}
```

**测试结果:**
```
✓ Test 1:   0 +   0 =   0
✓ Test 2:   1 +   2 =   3
✓ Test 3:   5 +  10 =  15
✓ Test 4: 100 +  50 = 150
✓ Test 5: 255 +   1 =   0 (溢出)
```

## 🔧 常用命令

### Chisel 项目

```bash
cd chisel-test

# 清理
sbt clean

# 编译
sbt compile

# 生成 Verilog/SystemVerilog
sbt run

# 运行测试
sbt test

# 运行特定测试
sbt "testOnly <TestClassName>"
```

### Git 操作

```bash
# 添加修改
git add .

# 提交
git commit -m "描述你的修改"

# 推送到远程
git push origin main
```

## 📖 学习资源

### 官方文档
- [Chisel 官方网站](https://www.chisel-lang.org/)
- [Chisel 文档](https://www.chisel-lang.org/docs/)
- [Chisel API 参考](https://www.chisel-lang.org/api/latest/)
- [RISC-V 规范](https://riscv.org/specifications/)

### 教程和课程
- [Chisel 教程仓库](https://github.com/freechipsproject/chisel-tutorial)
- [Berkeley CS250](https://cs250.seas.harvard.edu/)
- [nand2tetris](https://www.nand2tetris.org/)

### 书籍推荐
- 《Digital Design and Computer Architecture》- David Money Harris
- 《Computer Organization and Design RISC-V Edition》- David A. Patterson
- 《See MIPS Run》- Dominic Sweetman

### 社区
- [Chisel Gitter](https://gitter.im/freechipsproject/chisel3)
- [Stack Overflow - Chisel](https://stackoverflow.com/questions/tagged/chisel)
- [Reddit - r/FPGA](https://www.reddit.com/r/FPGA/)

## 🐛 常见问题

### Q: sbt 下载依赖很慢?
**A:** 配置阿里云镜像,在 `~/.sbt/repositories` 中添加:
```
[repositories]
  local
  aliyun: https://maven.aliyun.com/repository/public
```

### Q: 如何调试 Chisel 代码?
**A:** 
1. 使用 `println` 打印中间值
2. 生成 VCD 波形文件用 GTKWave 查看
3. 使用 `chiseltest` 进行单元测试

### Q: 生成的 Verilog 代码在哪里?
**A:** 运行 `sbt run` 后,代码会生成在 `generated/` 目录。

### Q: 如何学习更多 Chisel 特性?
**A:** 参考官方教程,从简单模块开始,逐步增加复杂度。

更多问题请查看 [chisel-test/README.md](chisel-test/README.md)

## 📝 开发规范

### 代码风格
- 变量和方法使用 camelCase
- 类和对象使用 PascalCase
- 模块接口使用 `IO()` 包裹
- 添加必要的注释

### 测试规范
- 每个模块都要有测试
- 覆盖边界情况
- 使用有意义的测试名称

### Git 提交规范
- 提交前确保测试通过
- 提交消息清晰描述改动
- 重要改动更新文档

## 🤝 贡献指南

欢迎 Fork 和提交 Pull Request!

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/YourFeature`)
3. 提交改动 (`git commit -m 'Add YourFeature'`)
4. 推送到分支 (`git push origin feature/YourFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 MIT 许可证

## 👨‍💻 关于作者

CPU 设计学习者,记录从零开始构建 CPU 的学习历程。

## 🙏 致谢

- Chisel 开发团队和 Berkeley 大学
- RISC-V International
- 所有开源硬件描述语言贡献者

---

**🌟 Star 本项目,一起学习 CPU 设计!**

*最后更新: 2026年5月20日*
