package com.github.bartgora.kafka.producer

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerController(private val producer: RecordProducer) {
    @PostMapping("/send")
    fun send(@RequestBody request: List<Request>) {
        producer.send(request.map { KafkaRequest(it.key, it.value) })
    }

    data class Request(val key: String, val value: String)
}
