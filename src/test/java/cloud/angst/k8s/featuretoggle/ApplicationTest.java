package cloud.angst.k8s.featuretoggle;

import cloud.angst.k8s.featuretoggle.cloud.angst.k8s.featuretoggle.MockFeatureToggleConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {MockFeatureToggleConfiguration.class})
class ApplicationTest {
    @Test
    public void contextLoad() {

    }
}
