package cloud.angst.k8s.featuretoggle.controller;

import cloud.angst.k8s.featuretoggle.config.k8s.FeatureToggle;
import cloud.angst.k8s.featuretoggle.config.swagger.ExposedInterface;
import cloud.angst.k8s.featuretoggle.controller.model.Toggles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController(value = "ToggleController")
public class ToggleController {
    private final FeatureToggle toggles;

    @ExposedInterface
    @GetMapping(path = "/toggles")
    public ResponseEntity<Toggles> toggles() {
        return ResponseEntity.ok(new Toggles(toggles.getToggles()));
    }
}
