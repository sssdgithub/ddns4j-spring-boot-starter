package top.sssd.ddns4j.autoconfigure.condition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;
import top.sssd.ddns4j.autoconfigure.DDns4jProperties;

import java.util.Objects;

/**
 * @author sssd
 * @careate 2023-10-23-16:28
 */
public class EasyModeCondition implements Condition {

    @Autowired
    private DDns4jProperties dDns4jProperties;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return dDns4jProperties.getEnabled().equals(Boolean.TRUE)
                && Objects.nonNull(dDns4jProperties.getEasyMode().getServiceProvider())
                && StringUtils.hasText(dDns4jProperties.getEasyMode().getServiceProviderSecret())
                && StringUtils.hasText(dDns4jProperties.getEasyMode().getServiceProviderId())
                && StringUtils.hasText(dDns4jProperties.getEasyMode().getDomain());
    }
}
