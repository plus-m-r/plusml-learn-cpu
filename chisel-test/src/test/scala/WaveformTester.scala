import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class WaveformTester extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "SimpleAdder with Waveform"

  it should "generate waveform for debugging" in {
    test(new SimpleAdder).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      // 创建一系列测试数据,生成波形
      val testCases = Seq(
        (0, 0),
        (1, 2),
        (5, 10),
        (100, 50),
        (255, 1),
        (128, 127),
        (64, 64)
      )

      println("=" * 50)
      println("SimpleAdder 测试结果:")
      println("=" * 50)
      
      testCases.zipWithIndex.foreach { case ((a, b), idx) =>
        dut.io.a.poke(a.U)
        dut.io.b.poke(b.U)
        dut.clock.step()
        
        val sum = dut.io.sum.peek().litValue
        val expected = (a + b) & 0xFF  // 8位溢出处理
        val status = if (sum == expected) "✓" else "✗"
        
        println(f"$status Test ${idx + 1}: $a%3d + $b%3d = $sum%3d (期望: $expected%3d)")
      }
      
      println("=" * 50)
      println("VCD 波形文件已生成: test_run_dir/SimpleAdder/*.vcd")
      println("可以使用 GTKWave 或其他波形查看器打开")
    }
  }
}
