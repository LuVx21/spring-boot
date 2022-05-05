package org.luvx.tome.pojo;

import lombok.Data;

@Data
public class TextType extends Message{
    private String  msgtype = "text";
    private Content text;

    public TextType(Content text) {
        this.text = text;
    }
}