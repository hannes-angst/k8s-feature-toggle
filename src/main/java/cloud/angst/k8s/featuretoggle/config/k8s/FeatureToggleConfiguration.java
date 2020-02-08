package cloud.angst.k8s.featuretoggle.config.k8s;

import cloud.angst.k8s.featuretoggle.config.properties.FeatureToggleProperties;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.kubernetes.ConditionalOnKubernetesEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnKubernetesEnabled
public class FeatureToggleConfiguration {

    @Bean
    public FeatureToggle featureToggles(KubernetesClient client, FeatureToggleProperties properties) {
        final Map<String, String> toggles = new LinkedHashMap<>();
        final String namespace = properties.getNamespace();
        final String configMap = properties.getConfigMap();


        final var result = new FeatureToggleImpl(toggles);


        client.configMaps().inNamespace(namespace).withName(configMap).watch(new Watcher<>() {
            @Override
            public void eventReceived(Action action, ConfigMap resource) {
                log.info("Received update for configMap '{}'.", configMap);
                toggles.entrySet().removeIf(entry -> !resource.getData().containsKey(entry.getKey()));
                toggles.putAll(resource.getData());
                result.signalOkay();
            }

            @Override
            public void onClose(KubernetesClientException cause) {
                log.error("Connection to cluster API got closed.", cause);
                result.signalFailure();
            }
        });
        return result;
    }
}
