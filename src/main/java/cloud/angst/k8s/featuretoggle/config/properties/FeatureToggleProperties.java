package cloud.angst.k8s.featuretoggle.config.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ConfigurationProperties("feature-toggles")
public class FeatureToggleProperties {
    @Builder.Default
    private String namespace = "default";
    @Builder.Default
    private String configMap = "feature-toggles";
}
