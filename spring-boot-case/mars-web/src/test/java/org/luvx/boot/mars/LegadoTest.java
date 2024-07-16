package org.luvx.boot.mars;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.google.common.io.BaseEncoding;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.luvx.coding.common.more.MorePrints;
import org.springframework.util.Base64Utils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.luvx.coding.common.consts.Properties.DIR_USER_HOME;

@Slf4j
class LegadoTest {

    @Test
    void mergeBookSource() throws IOException {
        File merged = new File(DIR_USER_HOME + "/code/yd/merged.json");
        CharSink charSink = Files.asCharSink(merged, UTF_8, FileWriteMode.APPEND);
        String read = Files.asCharSource(merged, UTF_8).read();
        Set<String> urlSet = JSON.parseArray(read).stream()
                .map(o -> (JSONObject) o)
                .map(item -> item.getString("bookSourceUrl"))
                .map(url -> UriComponentsBuilder.fromHttpUrl(url).build())
                .map(UriComponents::getHost)
                .collect(Collectors.toSet());

        List<String> list = List.of(
                DIR_USER_HOME + "/code/yd/书源/必须导入精品/破冰最新合集.txt",
                DIR_USER_HOME + "/code/yd/书源/必须导入精品/namofree书源合集.txt",
                DIR_USER_HOME + "/code/yd/书源/必须导入精品/漫画源最新.txt",
                DIR_USER_HOME + "/code/yd/书源/必须导入精品/听书源合集.txt"
        );
        for (String s : list) {
            String json = Files.asCharSource(new File(s), UTF_8).read();
            JSONArray array = JSON.parseArray(json);
            for (Object o : array) {
                JSONObject item = (JSONObject) o;
                String bookSourceUrl = item.getString("bookSourceUrl");
                String host = bookSourceUrl;
                try {
                    UriComponents uri = UriComponentsBuilder.fromHttpUrl(bookSourceUrl).build();
                    host = uri.getHost();
                } catch (Exception e) {
                }
                if (urlSet.contains(host)) {
                    continue;
                }
                log.info(host);

                String jsonString = item.toJSONString(JSONWriter.Feature.PrettyFormat);
                charSink.write("," + jsonString);
                urlSet.add(host);
            }
        }
    }
}