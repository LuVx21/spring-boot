package org.luvx.boot.tools.service.tome.pojo;

import lombok.Data;

@Data
public class MarkdownType extends Message {
    private String  msgtype = "markdown";
    private Content markdown;

    public MarkdownType(Content markdown) {
        this.markdown = markdown;
    }
}