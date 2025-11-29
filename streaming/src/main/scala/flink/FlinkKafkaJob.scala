package flink

import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.streaming.api.scala._

object FlinkKafkaJob {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val source = KafkaSource.builder[String]()
      .setBootstrapServers("kafka-broker:29092")
      .setTopics("test_topic")
      .setGroupId("flink-test-group")
      .setStartingOffsets(OffsetsInitializer.earliest())
      .setValueOnlyDeserializer(new SimpleStringSchema())
      .build()

    val stream = env.fromSource(
      source,
      WatermarkStrategy.noWatermarks(),
      "Kafka Source"
    )

    stream.print()

    env.execute("Read Kafka Example")
  }
}

