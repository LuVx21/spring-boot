package org.luvx.fund.util;

import static java.util.Collections.singletonList;

import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownUtils {
    private static final List<Extension> extensions = singletonList(TablesExtension.create());
    private static final HtmlRenderer renderer;

    static {
        renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
    }

    /**
     * 直接将markdown语义的文本转为html格式输出
     *
     * @param content markdown语义文本
     */
    public static String markdown2Html1(String content) {
        Node document = Parser.builder()
                .extensions(extensions)
                .build().parse(content);
        return renderer.render(document);
    }
}
