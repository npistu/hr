package hu.webuni.hr.npistu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "hr")
@Component
public class HrConfigurationProperties {
    private JWTConfig jwtConfig;
    private List<Smart> smart;

    public JWTConfig getJwtConfig() {
        return jwtConfig;
    }

    public void setJwtConfig(JWTConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public List<Smart> getSmart() {
        return smart;
    }

    public void setSmart(List<Smart> smart) {
        this.smart = smart;
    }

    public static class JWTConfig {
        private String issuer;
        private String secret;
        private long duration;

        public String getIssuer() {
            return issuer;
        }

        public void setIssuer(String issuer) {
            this.issuer = issuer;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }
    }

    public static class Smart {
        private double limit;
        private int percent;

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }

        public double getLimit() {
            return limit;
        }

        public void setLimit(double limit) {
            this.limit = limit;
        }
    }
}
