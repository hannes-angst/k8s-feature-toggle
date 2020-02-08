package cloud.angst.k8s.featuretoggle.config.properties;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Data
@ConfigurationProperties("info")
public class AppInfoProperties {
  private String name;
  private Map<String, Object> app;
  private Map<String, Object> build;

  @NotNull
  public String getDescription() {
    if (isNotEmpty(app)) {
      Object desc = app.get("description");
      if (desc instanceof String && isNotBlank((String) desc)) {
        return (String) desc;
      }
    }
    return "";
  }

  public String getApiVersion() {
    if (isNotEmpty(app)) {
      Object version = app.get("apiVersion");
      if (version instanceof String && isNotBlank((String) version)) {
        return (String) version;
      }
    }
    return "";
  }

  @NotNull
  public String getVersion() {
    if (isNotEmpty(build)) {
      Object version = build.get("version");
      if (version instanceof String && isNotBlank((String) version)) {
        return (String) version;
      }
    }
    return "";
  }

  private static boolean isNotEmpty(Map<String, Object> app) {
    return app != null && !app.isEmpty();
  }
}
