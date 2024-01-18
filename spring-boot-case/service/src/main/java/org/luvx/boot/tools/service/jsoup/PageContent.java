package org.luvx.boot.tools.service.jsoup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PageContent {
    private String       url;
    private String       title;
    private String       pubDate;
    private Set<String>  categorySet;
    private List<String> content;
}
