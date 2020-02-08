package cloud.angst.k8s.featuretoggle.config.k8s;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
class FeatureToggleImpl implements FeatureToggle {

  private static final String NOT_SET = "";

  @NotNull
  private final Map<String, String> toggles;
  private boolean healthy = true;

  @Override
  public boolean isOn(@NotNull String featureToggle) {
    String value = valueOf(featureToggle, NOT_SET);
    if (Objects.equals(value, NOT_SET)) {
      return false;
    }
    return parseTrue(value);
  }

  @Override
  public boolean isOff(@NotNull String featureToggle) {
    String value = valueOf(featureToggle, NOT_SET);
    if (Objects.equals(value, NOT_SET)) {
      return false;
    }
    return parseFalse(value);
  }

  private boolean parseTrue(@NotNull String value) {
    return value.equalsIgnoreCase("true") ||
            value.equalsIgnoreCase("y") ||
            value.equalsIgnoreCase("x") ||
            value.equals("1");
  }

  private boolean parseFalse(@NotNull String value) {
    return value.equalsIgnoreCase("false") ||
            value.equalsIgnoreCase("n") ||
            value.equalsIgnoreCase("-") ||
            value.equals("0");
  }

  @NotNull
  @Override
  public String valueOf(@NotNull String featureToggle, @NotNull String defaultValue) {
    String flag = toggles.get(featureToggle);
    if (flag == null) {
      return defaultValue;
    }
    return flag;
  }

  @NotNull
  @Override
  public Map<String, String> getToggles() {
    return new LinkedHashMap<>(toggles);
  }

  public void signalOkay() {
    healthy = true;
  }

  public void signalFailure() {
    healthy = false;
  }

  @Override
  public boolean isHealthy() {
    return healthy;
  }
}
