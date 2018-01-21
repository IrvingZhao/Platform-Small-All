package cn.irving.zhao.util.base.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>redis工具包，使用后需手动释放</p>
 * <p>配置文件：classpath:/conf/redis.properties</p>
 * <p>基础配置：</p>
 * <ul>
 * <li>redis.{name}.host - 服务器地址</li>
 * <li>redis.{name}.port - 服务器端口，可不设置</li>
 * <li>redis.{name}.password - 服务器密码，可不设置</li>
 * <li>redis.{name}.weight - 服务权重，可不设置 </li>
 * <li>redis.{name}.timeout - 超时，可不设置</li>
 * </ul>
 * <p>连接池配置：</p>
 * <ul>
 * <li>pool.maxTotal - 最大连接数，默认为 8 </li>
 * <li>pool.maxWait - 最大等待时间，单位毫秒，小于0，则等待不定时间，默认为 -1 </li>
 * <li>pool.minIdle - 最小空闲数量，默认为 0 </li>
 * </ul>
 */
public class RedisUtil {
    private static ShardedJedisPool shardedJedisPool;
    private static final Pattern KEY_PATTERN = Pattern.compile("redis\\.(.*)\\.(.*)");
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    static {
        InputStream stream = RedisUtil.class.getResourceAsStream("/config/redis.properties");
        if (stream != null) {
            loadPropertiesStream(stream);
        }
    }

    public static void loadProperties() {
        InputStream stream = RedisUtil.class.getResourceAsStream("/conf/redis.properties");
        if (stream != null) {
            loadPropertiesStream(stream);
        }
    }

    public static void loadPropertiesStream(InputStream stream) {
        try {
            if (shardedJedisPool != null && !shardedJedisPool.isClosed()) {
                shardedJedisPool.close();
            }
            Properties properties = new Properties();
            properties.load(stream);
            Map<String, Map<String, String>> tempConfig = new HashMap<>();
            properties.keySet().stream().map(String::valueOf).map(KEY_PATTERN::matcher).filter(Matcher::matches).forEach((item) -> {
                Map<String, String> itemConfig;
                String key = item.group(1);
                String attr = item.group(2);
                String value = properties.getProperty(item.group());
                if ((itemConfig = tempConfig.get(key)) == null) {
                    itemConfig = new HashMap<>();
                    tempConfig.put(key, itemConfig);
                }
                itemConfig.put(attr, value);
            });
            Integer maxTotal = Integer.parseInt(properties.getProperty("pool.maxTotal", "8"));
            Integer maxWait = Integer.parseInt(properties.getProperty("pool.maxWait", "-1"));
            Integer minIdle = Integer.parseInt(properties.getProperty("pool.minIdle", "0"));

            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(maxTotal);
            poolConfig.setMaxWaitMillis(maxWait);
            poolConfig.setMinIdle(minIdle);
            List<JedisShardInfo> shardInfo = new ArrayList<>();
            tempConfig.forEach((key, value) -> {
                String host = value.get("host");
                Integer port = Integer.parseInt(value.getOrDefault("port", "6379"));
                String password = value.get("password");
                Integer weight = Integer.parseInt(value.getOrDefault("weight", "1"));
                Integer timeout = Integer.parseInt(value.getOrDefault("timeout", "2000"));
                JedisShardInfo item = new JedisShardInfo(host, key, port, timeout, weight);
                if (password != null && password.length() > 0) {
                    item.setPassword(password);
                }
                shardInfo.add(item);
            });
            shardedJedisPool = new ShardedJedisPool(poolConfig, shardInfo);
        } catch (IOException | NumberFormatException e) {
            LOGGER.error("redis配置加载异常", e);
            e.printStackTrace();
        }
    }

    public static ShardedJedis getResources() {
        return shardedJedisPool.getResource();
    }

    public static ShardedJedisPipeline getJedisPipeline(){
        return shardedJedisPool.getResource().pipelined();
    }
}
