package cloud.angst.k8s.featuretoggle.config.k8s;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;

public interface FeatureToggle {

  /**
   * Returns <ttt>true</ttt> if the toggle is present and hold a value that can be mapped to logical
   * true, else <ttt>false</ttt>.
   *
   * @param featureToggle the feature toggle name
   * @return true, if toggle is on, else false
   */
  boolean isOn(@NotNull String featureToggle);


  /**
   * Returns <ttt>true</ttt> if the toggle is present and hold a value that can be mapped to logical
   * false, else <ttt>false</ttt>.
   *
   * @param featureToggle the feature toggle name
   * @return true, if toggle is off, else false
   */
  boolean isOff(@NotNull String featureToggle);

  /**
   * Returns the value of a feature toggle key.
   *
   * @param featureToggle the feature toggle name
   * @return value of a feature toggle
   */
  @NotNull
  String valueOf(@NotNull String featureToggle, @NotNull String defaultValue);

  /**
   * Detached copy of the current configuration
   */
  @NotNull
  @Unmodifiable
  Map<String, String> getToggles();

  /**
   * Signals if feature toggle is healthy
   */
  boolean isHealthy();

}
