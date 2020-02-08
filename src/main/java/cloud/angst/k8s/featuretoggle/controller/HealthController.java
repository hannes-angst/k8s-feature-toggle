package cloud.angst.k8s.featuretoggle.controller;

import cloud.angst.k8s.featuretoggle.config.k8s.FeatureToggle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController(value = "HealthController")
public class HealthController {
    private final FeatureToggle toggle;

    @GetMapping(path = "/health")
    public ResponseEntity<String> health() {
        if (toggle.isHealthy()) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Feature Toggle is down");
        }
    }
}
