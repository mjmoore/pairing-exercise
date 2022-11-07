package io.billie.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {

    @Bean
    fun mapper(
        timeModule: JavaTimeModule
    ): ObjectMapper = jacksonObjectMapper()
        .registerModule(timeModule)
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)

    @Bean
    fun timeModule(dateDeserializer: LocalDateDeserializer): JavaTimeModule = JavaTimeModule()
        .apply { addDeserializer(LocalDate::class.java, dateDeserializer) }

    @Bean
    fun dateDeserializer(): LocalDateDeserializer = LocalDateDeserializer(
        DateTimeFormatter.ofPattern("dd/MM/yyyy")
    )

}
