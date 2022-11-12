package org.luvx.tools.service.retrofit.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.util.concurrent.Uninterruptibles;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.luvx.coding.common.util.JSONPathUtils;
import org.luvx.tools.service.retrofit.GeekTimeApi;
import org.luvx.tools.service.retrofit.GeekTimeApi.ArticleBody;
import org.luvx.tools.service.retrofit.GeekTimeApi.ArticlesBody;
import org.luvx.tools.service.retrofit.GeekTimeApi.IntroBody;
import org.springframework.stereotype.Service;

import io.vavr.Tuple2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class GeekTimeService {
    private static final boolean                               online = Boolean.TRUE;
    private static final Map<Long, String>                     course = Maps.newHashMap();
    private static final Map<Long, List<Tuple2<Long, String>>> map    = Maps.newHashMap();

    public static       String root    =
            "Mac OS X".equals(System.getProperty("os.name")) ? "/Users/renxie/" : "D:\\code\\";
    public static final String geek    = root + "OneDrive/Code/geek-time/";
    public static final String docDir  = geek + "doc/";
    public static final String jsonDir = geek + "json/";

    public static final String INDEX_JSON = "README.json";
    public static final String INDEX_MD   = "README.md";

    private final OkHttpClient client = new OkHttpClient();

    @Resource
    private GeekTimeApi geekTimeApi;

    public void downloadCourse(Collection<Long> courseIds) throws IOException {
        for (Long courseId : courseIds) {
            List<Tuple2<Long, String>> articles = courseIndex(courseId);
            for (Tuple2<Long, String> article : articles) {
                downloadArticle(courseId, article._1());
            }
            System.out.println(courseId + "_" + course.get(courseId) + " -> 结束");
        }
    }

    /**
     * 目录 id 获取文章id和题目
     *
     * @param courseId 课程id
     * @return 文章id-文章标题
     */
    private List<Tuple2<Long, String>> courseIndex(Long courseId) throws IOException {
        JSONObject json;
        if (online) {
            IntroBody body1 = new IntroBody();
            body1.setCid(courseId + "");
            Map<String, Object> intro = geekTimeApi.intro(body1);
            long id = NumberUtils.toLong(JSONPathUtils.get(intro, "$.data.id").toString());

            ArticlesBody body2 = new ArticlesBody();
            body2.setCid(id);
            Map<String, Object> response = geekTimeApi.articles(body2);
            json = JSON.parseObject(JSON.toJSONString(response));
        } else {
            File file = new File(jsonDir + courseId + "_" + course.get(courseId));
            if (!file.exists() || !file.isDirectory()) {
                System.out.println(file.getPath() + " -> 没有本地课程目录");
                return Collections.emptyList();
            }

            File[] indexArray = file.listFiles((dir, name) -> INDEX_JSON.equals(name));
            File index = indexArray[0];
            json = JSONObject.parseObject(read(index.getPath()));
        }

        JSONArray array = json.getJSONObject("data").getJSONArray("list");
        List<Tuple2<Long, String>> result = array.stream()
                .map(oo -> {
                    Long k = ((JSONObject) oo).getLong("id");
                    String v = ((JSONObject) oo).getString("article_title");
                    return new Tuple2<>(k, v);
                })
                .collect(Collectors.toList());

        String courseDirName = courseId + "_" + course.get(courseId);
        write(json.toJSONString(), jsonDir + courseDirName + File.separator + INDEX_JSON);
        String prefix = "←←[课程目录](../../README.md)\n\n\n\n";
        String indexContent = result.stream()
                .map(p -> "* [" + p._2() + "](./" + p._1() + ".md)")
                .collect(Collectors.joining("\n"));
        write(prefix + indexContent, docDir + courseDirName + File.separator + INDEX_MD);

        map.put(courseId, result);
        return result;
    }

    /**
     * @return 需要更新的文章 id
     */
    public List<Long> getUpdateArticleIds(Long courseId) throws IOException {
        List<Tuple2<Long, String>> all = courseIndex(courseId);
        return all.stream()
                .map(Tuple2::_1)
                .filter(articleId -> {
                    String mdFile = docDir + courseId + "_" + course.get(courseId) + File.separator + articleId + ".md";
                    return !new File(mdFile).exists();
                })
                .collect(Collectors.toList());
    }

    /**
     * 文章 id 得到文章内容
     *
     * @param courseId 课程id
     * @param articleId 文章id
     */
    public void downloadArticle(Long courseId, Long articleId) throws IOException {
        JSONObject json = getArticleJson(courseId, articleId);
        JSONObject data = json.getJSONObject("data");

        String article_title = data.getString("article_title");
        // String article_cover = data.getString("article_cover");
        String mp3Url = data.getString("audio_download_url");
        String articleContent = data.getString("article_content");

        String preNext = getPrefixOfMd(courseId, articleId);
        StringBuilder content = new StringBuilder(preNext);
        content.append(StringUtils.isEmpty(preNext) ? "" : "\n\n")
                .append("<h2>").append(article_title).append("</h2>")
                .append("\n\n");
        // if (article_cover != null) {
        //     content.append("<img src=\"" + article_cover + "\">")
        //     .append("\n\n");
        // }
        if (mp3Url != null) {
            content.append("<audio controls=\"controls\"><source src=\"").append(mp3Url)
                    .append("\" type=\"audio/mpeg\"></audio>").append("\n\n");
        }
        content.append(articleContent)
                .append("\n\n")
                .append(preNext);

        String courseDirName = courseId + "_" + course.get(courseId);
        write(json.toJSONString(), jsonDir + courseDirName + File.separator + articleId + ".json");
        write(content.toString(), docDir + courseDirName + File.separator + articleId + ".md");
        write(content.toString(), root + courseDirName + File.separator + article_title + ".html");

        /// 保存音频文件
        if (false) {
            /// String name = mp3Url.substring(mp3Url.lastIndexOf("/") + 1);
            /// 和文档同名
            String name = articleId + ".mp3";
            mp3Download(mp3Url, docDir + courseDirName + File.separator + "video/" + name);
        }

        System.out.println(courseId + ":" + article_title + " -> OK");
        if (online) {
            Uninterruptibles.sleepUninterruptibly(6, TimeUnit.SECONDS);
        }
    }

    private JSONObject getArticleJson(Long courseId, Long articleId) throws IOException {
        JSONObject json;
        if (online) {
            ArticleBody body1 = new ArticleBody();
            body1.setId(articleId + "");
            Map<String, Object> response = geekTimeApi.article(body1);
            json = JSON.parseObject(JSON.toJSONString(response));
        } else {
            String path = jsonDir + courseId + "_" + course.get(courseId) + File.separator + articleId + ".json";
            json = JSONObject.parseObject(read(path));
        }
        return json;
    }

    private String getPrefixOfMd(Long courseId, Long articleId) {
        StringBuilder content = new StringBuilder();
        // 文章内部增加跳转到前后篇以及目录
        List<Tuple2<Long, String>> pairs = map.get(courseId);
        for (int i = 0; i < pairs.size(); i++) {
            Tuple2<Long, String> pair = pairs.get(i);
            if (!pair._1().equals(articleId)) {
                continue;
            }

            if (i >= 1) {
                content.append("[上一篇](./").append(pairs.get(i - 1)._1()).append(".md)");
            }
            if (content.length() == 0) {
                content.append("           ");
            }
            content.append("               [目录](./README.md)");
            if (i + 1 < pairs.size()) {
                content.append("               [下一篇](./").append(pairs.get(i + 1)._1()).append(".md)");
            }
            break;
        }
        return content.toString().replaceAll(" ", "&nbsp;");
    }

    private String getPrefixOfHtml(Long courseId, Long articleId) {
        StringBuilder content = new StringBuilder();
        // 文章内部增加跳转到前后篇以及目录
        List<Tuple2<Long, String>> pairs = map.get(courseId);
        for (int i = 0; i < pairs.size(); i++) {
            Tuple2<Long, String> pair = pairs.get(i);
            if (!pair._1().equals(articleId)) {
                continue;
            }

            if (i >= 1) {
                content.append("<a href=\"./").append(pairs.get(i - 1)._1()).append(".html\">上一篇</a>");
            }
            if (content.length() == 0) {
                content.append("           ");
            }
            content.append("               [目录](./README.md)");
            if (i + 1 < pairs.size()) {
                content.append("               [下一篇](./").append(pairs.get(i + 1)._1()).append(".md)");
            }
            break;
        }
        return content.toString().replaceAll(" ", "&nbsp;");
    }

    private void mp3Download(String url, String path) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();

        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Files.write(response.body().bytes(), file);
    }

    /**
     * 所有课程
     */
    public void allCourse() throws IOException {
        if (false) {
            JSONArray array;
            int i = 0;
            do {
                ImmutableMap<String, Object> param = ImmutableMap.of(
                        "desc", true,
                        "expire", 1,
                        "last_learn", 0,
                        "learn_status", 0,
                        "prev", i,
                        "size", 200,
                        "sort", 1,
                        "type", "c1",
                        "with_learn_count", 1
                );

                Map<String, Object> response = geekTimeApi.product(param);
                JSONObject o = JSON.parseObject(JSON.toJSONString(response));
                array = o.getJSONObject("data").getJSONArray("products");
                array.forEach(oo -> {
                    JSONObject ooo = ((JSONObject) oo);
                    course.put(ooo.getLong("id"), ooo.getString("title"));
                });
                i++;
            } while (array.size() != 0);
            System.out.println(course);
        } else {
            String json = read(geek + "t.csv");
            String[] lines = json.split("\n");

            IntStream.range(1, lines.length).forEach(i -> {
                String[] split = lines[i].split(",");
                course.put(Long.valueOf(split[0]), split[1]);
            });
        }
    }

    public static String read(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException(path);
        }
        return Files.asCharSource(file, Charsets.UTF_8).read();
    }

    public static void write(String content, String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.asCharSink(file, Charsets.UTF_8).write(content);
    }

    public static void makeMainIndex() {
        File doc = new File(docDir);
        File[] files = doc.listFiles();
        // Arrays.sort(files, Comparator.comparing(f -> f.getName().substring(10)));
        Arrays.sort(files, Comparator.comparing(f -> f.getName().substring(0, 9)));
        String s = "| `{0}` | [{1}](./doc/{2}/README.md)";
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (!f.isDirectory()) {
                continue;
            }
            if (Objects.requireNonNull(f.listFiles()).length == 0) {
                continue;
            }
            String fileName = f.getName(), prefix = fileName.substring(0, 9), postfix = fileName.substring(10);
            String format = MessageFormat.format(s, prefix, postfix, fileName);

            if ((i + 1) % 3 == 0) {
                System.out.println(format + "|");
            } else {
                System.out.print(format);
            }
        }
    }

    public static void main(String[] args) {
        makeMainIndex();
    }
}
