package cn.irving.zhao.web.springboot.transaction;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaojn1
 * @version TransactionConfig.java, v 0.1 2018/2/22 zhaojn1
 * @project userProfile
 */
@EnableConfigurationProperties(TransactionProperties.class)
@Configuration
public class TransactionConfig {

    public TransactionConfig(TransactionProperties transactionProperties) {
        this.transactionProperties = transactionProperties;
    }

    private TransactionProperties transactionProperties;


}
