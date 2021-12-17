package cloud.angst.k8s.featuretoggle.cloud.angst.k8s.featuretoggle;

import cloud.angst.k8s.featuretoggle.config.k8s.FeatureToggle;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockFeatureToggleConfiguration {
    @Bean
    public FeatureToggle featureToggles() {
        return Mockito.mock(FeatureToggle.class);
    }
}
