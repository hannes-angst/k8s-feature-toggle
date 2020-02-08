package cloud.angst.k8s.featuretoggle.service;

import cloud.angst.k8s.featuretoggle.config.k8s.FeatureToggle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ToggleChangingBehaviour {

    private final FeatureToggle toggles;

    @Scheduled(fixedDelay = 5000)
    public void hello() {
        log.info("Toggle state: {}", toggles.isOn("my-toggle"));
    }
}
