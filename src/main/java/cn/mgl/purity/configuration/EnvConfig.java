package cn.mgl.purity.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "env")
@Data
public class EnvConfig {
    private String uploadFilePath;
    private String uploadFolder;

    private String exportFilePath;
    private String exportFolder;
}
