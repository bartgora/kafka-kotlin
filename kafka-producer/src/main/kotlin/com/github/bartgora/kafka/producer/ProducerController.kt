package com.github.bartgora.kafka.producer

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerController(private val producer: RecordProducer) {
    @PostMapping("/send")
    fun send(@RequestBody request: Request){
         producer.send(request.key, request.value)
    }

    data class Request(val key: String, val value: String)
}
