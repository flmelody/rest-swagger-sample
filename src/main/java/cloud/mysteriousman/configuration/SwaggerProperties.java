package cloud.mysteriousman.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author God
 */
@Data
@ConfigurationProperties(prefix = SwaggerProperties.DEFAULT_PREFIX)
public class SwaggerProperties implements Serializable {
    public static final String DEFAULT_PREFIX = "swagger";
    private static final long serialVersionUID = -5056498214009548686L;
    private Boolean enable = false;
    private String title = "Swagger-在线文档";
    private String basePackage = "cloud.mysteriousman.controller";
    private String version = "snapshot";
}
