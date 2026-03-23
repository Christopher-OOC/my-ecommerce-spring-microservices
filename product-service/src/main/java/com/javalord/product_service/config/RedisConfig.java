package com.javalord.product_service.config;

import org.javalord.common.ProductResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.*;
import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                )
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new JacksonJsonRedisSerializer<>(ProductResponse.class))
                );

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(ProductResponse.class));
//
////        ObjectMapper objectMapper = new ObjectMapper();
////        objectMapper.registeredModules();
////
////        GenericJacksonJsonRedisSerializer serializer =
////                new GenericJacksonJsonRedisSerializer(objectMapper);
////
////
////        redisTemplate.setValueSerializer(serializer);
//
//        return redisTemplate;
//    }

}
