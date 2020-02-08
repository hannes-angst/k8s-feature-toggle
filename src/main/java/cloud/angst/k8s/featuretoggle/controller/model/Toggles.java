package cloud.angst.k8s.featuretoggle.controller.model;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class Toggles extends LinkedHashMap<String, String> {
    public Toggles(@Nullable Map<String, String> toggles) {
        super(toggles == null ? Map.of() : toggles);
    }
}
