package com.github.bartgora.kafka.producer

import org.apache.kafka.clients.producer.*
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.stereotype.Service
import java.util.*

@Service
class RecordProducer {
    private val properties = Properties()

    init {
        properties["bootstrap.servers"] = "localhost:9092"
        properties["client.id"] = "producer1"
        properties["acks"] = "1"
        properties["key.serializer"] = StringSerializer::class.java
        properties["value.serializer"] = StringSerializer::class.java
        properties["partitions"] = "2"
        properties["batch.size"] = 1024
        properties[ProducerConfig.PARTITIONER_CLASS_CONFIG] = RoundRobinPartitioner::class.java
    }

    fun send(requests: List<KafkaRequest>) {
        val producer: Producer<String?, String?> = KafkaProducer<String?, String?>(properties)
        producer.use {
            for (request in requests) {
                val producerRecord = ProducerRecord(TOPIC, request.key, request.value)
                producer.send(producerRecord) { recordMetadata: RecordMetadata, e: Exception? ->
                    handleResponse(recordMetadata, e)
                }
            }

        }

    }

    private fun handleResponse(recordMetadata: RecordMetadata, e: Exception? ) {
        e?.printStackTrace()
        println("topic: " + recordMetadata.topic() + " Partition: "
                + recordMetadata.partition() + " Offset: " + recordMetadata.offset()
                + " timestamp: " + recordMetadata.timestamp())
    }

    companion object {
        private const val TOPIC = "fun-with-kafka-topic"
    }
}

data class KafkaRequest(val key: String?, val value: String?)
