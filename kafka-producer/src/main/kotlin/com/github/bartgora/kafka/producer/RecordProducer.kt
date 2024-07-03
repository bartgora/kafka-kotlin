package com.github.bartgora.kafka.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.stereotype.Service
import java.util.*

@Service
class RecordProducer {
    private val properties = Properties()

    init {
        properties["bootstrap.servers"] = "localhost:9092, localhost:9093"
        properties["client.id"] = "producer1"
        properties["acks"] = "1"
        properties["key.serializer"] = StringSerializer::class.java
        properties["value.serializer"] = StringSerializer::class.java
    }

    fun send(key: String?, value: String?): String? {
        val producer: Producer<String?, String?> = KafkaProducer<String?, String?>(properties)
        val response = Response()
        val producerRecord = ProducerRecord(TOPIC, key, value)
        producer.send(producerRecord) { recordMetadata: RecordMetadata, e: Exception? ->
            handleResponse(recordMetadata, e, response)
        }
        producer.close()
        return response.value
    }

    private fun handleResponse(recordMetadata: RecordMetadata, e: Exception?, response: Response) {
        e?.printStackTrace()
        response.value = ("topic: " + recordMetadata.topic() + " Partition: "
                + recordMetadata.partition() + " Offset: " + recordMetadata.offset()
                + " timestamp: " + recordMetadata.timestamp())
    }

    internal inner class Response {
        var value: String? = null
    }

    companion object {
        private const val TOPIC = "fun-with-kafka-topic"
    }
}
