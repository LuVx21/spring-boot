package org.luvx.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesUtils {
    @Value("${org.luvx.title}")
    private String title;
    @Value("${org.luvx.description}")
    private String description;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
