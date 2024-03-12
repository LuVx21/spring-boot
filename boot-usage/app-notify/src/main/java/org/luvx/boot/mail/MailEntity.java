package org.luvx.boot.mail;

import lombok.Data;

import java.util.List;

/**
 * @desc:
 */
@Data
public class MailEntity {
    private MailName       sender;
    private List<MailName> toList;
    private List<MailName> ccList;
    private List<MailName> bccList;
    private String         title;
    private String         content;

    @Data
    public static class MailName {
        private String mail;
        private String name;
    }
}
