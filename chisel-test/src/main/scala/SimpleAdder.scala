// 导入 Chisel3 库,提供硬件描述语言的核心功能
import chisel3._

/**
 * SimpleAdder - 8位加法器模块
 * 
 * 这是一个简单的组合逻辑电路,实现两个8位无符号整数的加法运算。
 * 作为 Chisel 学习的入门示例,展示了:
 * - 如何定义模块(Module)
 * - 如何声明输入输出接口(IO)
 * - 如何实现组合逻辑
 */
class SimpleAdder extends Module {
  // 定义模块的输入输出接口
  // IO() 是 Chisel 中用于声明模块接口的函数
  // Bundle 类似于结构体,用于组织多个信号
  val io = IO(new Bundle {
    // Input: 输入端口,UInt(8.W) 表示8位无符号整数
    val a = Input(UInt(8.W))    // 第一个加数 (8位)
    val b = Input(UInt(8.W))    // 第二个加数 (8位)
    
    // Output: 输出端口
    val sum = Output(UInt(8.W)) // 加法结果 (8位)
  })

  // 组合逻辑:将输入 a 和 b 相加,结果赋值给输出 sum
  // := 是 Chisel 中的连接运算符,表示硬件连接关系
  // 注意:由于 sum 也是8位,当 a+b > 255 时会发生溢出(高位被截断)
  io.sum := io.a + io.b
}

/**
 * SimpleAdderDriver - 驱动程序对象
 * 
 * 这是一个 Scala object,用于生成 SystemVerilog 代码。
 * 通过运行这个对象,Chisel 会将硬件描述编译为目标硬件描述语言。
 */
object SimpleAdderDriver extends App {
  // emitVerilog: Chisel 提供的函数,用于生成 SystemVerilog 代码
  // 参数1: new SimpleAdder - 创建 SimpleAdder 模块实例
  // 参数2: Array("--target-dir", "generated") - 指定输出目录为 "generated"
  // 
  // 执行后会生成:
  // - generated/SimpleAdder.sv (SystemVerilog 代码)
  // - generated/SimpleAdder.fir (FIRRTL 中间表示)
  emitVerilog(new SimpleAdder, Array("--target-dir", "generated"))
  
  // 打印提示信息,告知用户代码生成位置
  println("Verilog 代码已生成到 generated/ 目录")
}
