package hu.webuni.hr.npistu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "hr")
@Component
public class HrConfigurationProperties {
    private List<Smart> smart;

    public List<Smart> getSmart() {
        return smart;
    }

    public void setSmart(List<Smart> smart) {
        this.smart = smart;
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
