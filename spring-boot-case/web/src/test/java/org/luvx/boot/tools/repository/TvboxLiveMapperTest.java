package org.luvx.boot.tools.repository;

import com.github.phantomthief.util.MoreFunctions;
import com.google.common.base.Splitter;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.BaseAppTests;
import org.luvx.boot.tools.dao.entity.TvboxLive;
import org.luvx.boot.tools.dao.mapper.TvboxLiveMapper;
import org.luvx.coding.common.net.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.io.File;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.luvx.coding.common.consts.Common.SPLITTER_COMMA;
import static org.luvx.coding.common.consts.Properties.DIR_USER_HOME;

@Slf4j
class TvboxLiveMapperTest extends BaseAppTests {

    @Autowired
    private TvboxLiveMapper mapper;

    @Test
    void tvboxInsert() throws Exception {
        Stream.of(
                        ""
                )
                .filter(StringUtils::isNotBlank)
                .forEachOrdered(path -> {
                    List<String> lines;
                    if (path.startsWith("http")) {
                        HttpRequest request = HttpRequest.newBuilder()
                                .GET()
                                .uri(URI.create(path))
                                .build();
                        lines = MoreFunctions.catching(() -> {
                            String text = HttpUtils.httpClientSupplier.get().send(request, HttpResponse.BodyHandlers.ofString()).body();
                            return CharSource.wrap(text).readLines();
                        });
                    } else {
                        CharSource charSource = Files.asCharSource(new File(DIR_USER_HOME + "/code/yd/tvbox/" + path), UTF_8);
                        lines = MoreFunctions.catching(charSource::readLines);
                    }

                    log.info("-----------{}-----------", path);
                    if (path.endsWith(".m3u")
                            || lines.contains("#EXTM3U")
                            || lines.contains("#EXTINF")) {
                        m3u(lines);
                    } else {
                        txt(lines);
                    }
                });
    }

    private void txt(List<String> lines) {
        String groupName = "";
        for (String line : lines) {
            if (StringUtils.isBlank(line)
                    || "##".equals(line)
            ) {
                continue;
            }
            if (line.contains("#genre#")) {
                groupName = line.strip();
                continue;
            }

            List<String> strings = SPLITTER_COMMA.splitToList(line);
            String name = strings.get(0).strip(), url = strings.get(1).strip();
            TvboxLive tvboxLive = new TvboxLive(groupName, name, url);
            try {
                mapper.insert(tvboxLive);
            } catch (DuplicateKeyException ignore) {
            }
        }
    }

    private void m3u(List<String> lines) {
        Splitter splitter_space = Splitter.on("\" ");
        Splitter.MapSplitter mapSplitter = splitter_space.withKeyValueSeparator("=");

        String groupName = "", name = "";
        for (String line : lines) {
            if (StringUtils.isBlank(line)
                    || "#EXTM3U".equals(line)
            ) {
                continue;
            }
            if (line.startsWith("#EXTINF:")) {
                String str = "group-title";
                String substring = line.substring(line.indexOf(str)).strip();
                List<String> strings = SPLITTER_COMMA.splitToList(substring);
                String s0 = strings.get(0).strip();
                List<String> aa = splitter_space.splitToList(s0);
                for (String s : aa) {
                    if (s.contains("group-title")) {
                        s0 = s;
                    }
                }
                Map<String, String> split = mapSplitter.split(s0);
                groupName = split.get("group-title").replace("\"", "").strip();
                name = strings.get(strings.size() - 1).strip();

                continue;
            }

            TvboxLive tvboxLive = new TvboxLive(groupName, name, line.strip());
            try {
                mapper.insert(tvboxLive);
            } catch (DuplicateKeyException ignore) {
            }
        }
    }

    @Test
    void saveFile() {
    }
}