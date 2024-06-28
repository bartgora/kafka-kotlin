package com.github.bartgora.kafka.producer

import org.apache.kafka.clients.producer.*
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.stereotype.Service
import java.util.*

@Service
class RecordProducer {
    private val properties = Properties()

    init {
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        properties[ProducerConfig.CLIENT_ID_CONFIG] = "producer1"
        properties[ProducerConfig.ACKS_CONFIG] = "1"
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

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
