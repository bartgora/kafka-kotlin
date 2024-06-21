package com.github.bartlomiejgora.kafka.consumer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KafkaConsumerApplication

    fun main(args: Array<String>) {
        SpringApplication.run(KafkaConsumerApplication::class.java, *args)
        val consumer = RecordConsumer()
        consumer.consume()
    }

