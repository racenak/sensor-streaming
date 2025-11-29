package flink

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FlinkKafkaJobTest extends AnyFlatSpec with Matchers {
  "FlinkKafkaJob" should "be defined" in {
    FlinkKafkaJob should not be null
  }
}

