package org.luvx.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String uploadDir;
}
