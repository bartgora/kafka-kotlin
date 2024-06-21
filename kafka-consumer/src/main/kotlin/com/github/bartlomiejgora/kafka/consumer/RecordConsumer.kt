package com.github.bartlomiejgora.kafka.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.function.Consumer

class RecordConsumer {
    private val props = Properties()

    init {
        props["bootstrap.servers"] = "localhost:9092"
        props["group.id"] = "CountryCounter"
        props["key.deserializer"] = StringDeserializer::class.java
        props["value.deserializer"] = StringDeserializer::class.java
    }

    fun consume() {
        println("Consume")
        val consumer = KafkaConsumer<String, String>(props)
        consumer.subscribe(setOf(TOPIC))
        while (true) {
            println("Reading..")
            val records = consumer.poll(Duration.of(30, ChronoUnit.SECONDS))
            records.forEach(Consumer { record: ConsumerRecord<String, String> -> println("Partition read:" + record.partition() + " Key: " + record.key() + " Value:" + record.value() + " Offset: " + record.offset()) })
        }
    }

    companion object {
        private const val TOPIC = "fun-with-kafka-topic"
    }
}