import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class SimpleAdderTester extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "SimpleAdder"

  it should "correctly add two numbers" in {
    test(new SimpleAdder) { dut =>
      // 测试用例 1: 5 + 3 = 8
      dut.io.a.poke(5.U)
      dut.io.b.poke(3.U)
      dut.clock.step()
      dut.io.sum.expect(8.U)
      println(s"Test 1: 5 + 3 = ${dut.io.sum.peek().litValue}")

      // 测试用例 2: 10 + 20 = 30
      dut.io.a.poke(10.U)
      dut.io.b.poke(20.U)
      dut.clock.step()
      dut.io.sum.expect(30.U)
      println(s"Test 2: 10 + 20 = ${dut.io.sum.peek().litValue}")

      // 测试用例 3: 0 + 0 = 0
      dut.io.a.poke(0.U)
      dut.io.b.poke(0.U)
      dut.clock.step()
      dut.io.sum.expect(0.U)
      println(s"Test 3: 0 + 0 = ${dut.io.sum.peek().litValue}")

      // 测试用例 4: 255 + 1 (溢出测试)
      dut.io.a.poke(255.U)
      dut.io.b.poke(1.U)
      dut.clock.step()
      println(s"Test 4: 255 + 1 = ${dut.io.sum.peek().litValue} (溢出后为 0)")
    }
  }
}
