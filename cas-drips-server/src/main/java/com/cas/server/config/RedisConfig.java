package com.cas.server.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018\11\20 0020.
 *  缓存令牌和用户信息
 */
@Configuration
public class RedisConfig {

    private Logger logger= LoggerFactory.getLogger(RedisConfig.class);

    @Value("#{'${redis.cacheNames}'.split(',')}")
    private List<String> cacheNames;
    @Value("${spring.redis.host:localhost}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database:0}")
    private int db;
    @Value("${spring.redis.timeout:0}")
    private int timeout;
    @Value("${spring.redis.pool.max-active:8}")
    private int max_active;
    @Value("${spring.redis.pool.max-wait:-1}")
    private int max_wait;
    @Value("${spring.redis.pool.max-idle:8}")
    private int max_idle;
    @Value("${spring.redis.pool.min-idle:0}")
    private int min_idle;

    private JedisPool jedisPool;


    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<Object,Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        Map<String, Long> map = new HashMap<String, Long>();
        if(cacheNames!=null&&!cacheNames.isEmpty()) {
            for(String cacheName : cacheNames) {
                if(StringUtils.isNotBlank(cacheName)) {
                    String[] str = cacheName.split(":");
                    // 添加指定缓存过期时间，key：缓存名称，value：过期时间（秒）
                    map.put(str[0], Long.parseLong(str[1]));
                }
            }
        }
        cacheManager.setExpires(map);
        System.out.println("Redis CacheManage init finished !");
        return cacheManager;
    }


    @Bean(name = "jedisConnectionFactory")
    @ConditionalOnMissingBean(name = "jedisConnectionFactory")
    public JedisConnectionFactory createJedisConnection(){
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setTimeout(timeout); // 设置连接超时时间
        jedisConnectionFactory.setDatabase(db);
        jedisConnectionFactory.setPassword(password);
        JedisPoolConfig config =jedisConnectionFactory.getPoolConfig();
        config.setMaxWaitMillis(max_wait);
        config.setTestOnBorrow(false);
        config.setMaxTotal(max_active);
        config.setMaxIdle(max_idle);
        config.setMinIdle(min_idle);
        System.out.println("Redis jedisConnectionFactory init finished !");
        jedisPool=new JedisPool(config,host,port);
        return jedisConnectionFactory;
    }


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        //解决键、值序列化问题
        RedisTemplate<Object,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        System.out.println("Redis redisTemplate init finished !");
        return redisTemplate;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }
}
