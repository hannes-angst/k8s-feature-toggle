package cloud.angst.k8s.featuretoggle.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(@NotNull PathMatchConfigurer matcher) {
        matcher.setUseRegisteredSuffixPatternMatch(false);
    }

    /**
     * Adapt the client's error message, in terms of removing internal information.
     */
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(
                    WebRequest webRequest, boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, false);
                errorAttributes.remove("timestamp");
                errorAttributes.remove("exception");
                errorAttributes.remove("path");
                errorAttributes.remove("trace");
                return errorAttributes;
            }
        };
    }
}
