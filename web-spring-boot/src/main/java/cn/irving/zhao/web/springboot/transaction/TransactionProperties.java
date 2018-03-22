package cn.irving.zhao.web.springboot.transaction;

import lombok.Getter;
import lombok.Setter;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import java.util.List;
import java.util.Map;

/**
 * @author zhaojn1
 * @version TransactionProperties.java, v 0.1 2018/2/22 zhaojn1
 * @project userProfile
 */
@ConfigurationProperties(prefix = "project.transaction")
@Getter
@Setter
public class TransactionProperties {

    private Boolean enabled;

    private String pointcutExpress;

    private List<TransactionAttributes> transactionAttributes;

    @Getter
    @Setter
    public static class TransactionAttributes {
        private String method;
        private Propagation propagation;
        private Isolation isolation;
        private int timeout;
        private Class<? extends Throwable>[] rollBackFor;
        private Class<? extends Throwable>[] noRollbackFor;
    }
}
