package com.mycompany.storekafkastreams.config;

import org.springframework.cloud.stream.annotation.StreamMessageConverter;
import org.springframework.cloud.stream.schema.avro.AvroSchemaMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.MimeType;

//@Configuration
public class MessageConverterConfig {

//    @Bean
//    @StreamMessageConverter
//    MessageConverter newsMessageConverter() {
//        return new AvroSchemaMessageConverter(MimeType.valueOf("application/*+avro"));
//    }

}
