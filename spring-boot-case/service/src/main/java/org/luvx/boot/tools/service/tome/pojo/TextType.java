package org.luvx.boot.tools.service.tome.pojo;

import lombok.Data;

@Data
public class TextType extends Message{
    private String  msgtype = "text";
    private Content text;

    public TextType(Content text) {
        this.text = text;
    }
}