package com.faxe.user.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.faxe.user.dao.RedisDao;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis缓存配置类
 *
 * 注：spring boot 2.0.x以上版本的使用方式
 *
 * @author ZhouXiang
 * @create 2018-07-08 14:46
 **/
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 默认，只能连接本地，连接其他网络添加
     * jedisConnectionFactory.setHostName(host);
     * jedisConnectionFactory.setPort(port);
     */
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    //缓存管理器
    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory).build();
        return cacheManager;
    }
    /**
     * 实例化 RedisTemplate 对象
     *
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);//注入redis数据源
        template.setKeySerializer(new StringRedisSerializer());//设置key的序列化方式
        //template.setValueSerializer(new RedisObjectSerializer());//设置value的序列化方式一
        template.setValueSerializer(jackRedisSerializer());//设置value的序列化方式二 推荐这种方式，这种方式不会乱码
        template.afterPropertiesSet();
        return template;
    }

    public Jackson2JsonRedisSerializer jackRedisSerializer() {
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        return jackson2JsonRedisSerializer;
    }
}
