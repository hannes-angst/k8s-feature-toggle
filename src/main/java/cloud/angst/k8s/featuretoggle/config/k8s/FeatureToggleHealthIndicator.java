package cloud.angst.k8s.featuretoggle.config.k8s;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

@RequiredArgsConstructor
public class FeatureToggleHealthIndicator implements HealthIndicator {
    private final FeatureToggle toggle;

    @Override
    public Health health() {
        if(toggle.isHealthy()) {
            return Health.up().build();
        } else {
            return Health.down().build();
        }
    }
}
