import chisel3._

class SimpleAdder extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(8.W))
    val b = Input(UInt(8.W))
    val sum = Output(UInt(8.W))
  })

  io.sum := io.a + io.b
}

object SimpleAdderDriver extends App {
  // 生成 Verilog 代码
  emitVerilog(new SimpleAdder, Array("--target-dir", "generated"))
  println("Verilog 代码已生成到 generated/ 目录")
}
