package com.github.bartgora.kafka.consumer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

class RecordConsumer {
    private val props = Properties()

    init {
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ConsumerConfig.GROUP_ID_CONFIG] = "ConsumerGroup1"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
    }

    fun consume() {
        println("Consume")
        val consumer = KafkaConsumer<String, String>(props)
        consumer.subscribe(setOf(TOPIC))
        while (true) {
            println("Reading..")
            val records = consumer.poll(Duration.of(30, ChronoUnit.SECONDS))
            records.forEach { record: ConsumerRecord<String, String> ->
                println(
                    "Partition read:${record.partition()} Key: ${record.key()} Value:${record.value()} Offset: ${record.offset()}"
                )
            }
        }
    }

    companion object {
        private const val TOPIC = "fun-with-kafka-topic"
    }
}
