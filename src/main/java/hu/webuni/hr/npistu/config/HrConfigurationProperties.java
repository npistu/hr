package hu.webuni.hr.npistu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "hr")
@Component
@Getter
@Setter
public class HrConfigurationProperties {
    private JWTConfig jwtConfig;
    private List<Smart> smart;

    @Getter
    @Setter
    public static class JWTConfig {
        private String issuer;
        private long duration;
        private Algorithm algorithm;

        @Getter
        @Setter
        public static class Algorithm {
            private String type;
            private String secret;
        }
    }

    @Getter
    @Setter
    public static class Smart {
        private double limit;
        private int percent;
    }
}
