package com.github.bartgora.kafka.producer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class KafkaProducerApplication

fun main(args: Array<String>) {
    SpringApplication.run(KafkaProducerApplication::class.java, *args)
}


