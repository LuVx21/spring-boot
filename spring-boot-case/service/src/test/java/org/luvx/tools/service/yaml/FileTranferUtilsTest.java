package org.luvx.tools.service.yaml;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.luvx.coding.common.more.MorePrints;

import java.util.Map;
import java.util.stream.Collectors;

class FileTranferUtilsTest {
    static final String fileSample = "/Users/renxie/work/doc/nacos/%s/%s/%s.yaml";

    static final Map<EnvConfig, EnvConfig> map;

    static {
        // String group = "CSS_GROUP", config = "stereo-css-add-data-source-server";
        String group = "stereo-server", config = "stereo-http-server";
        // String group = "stereo-server", config = "stereo-task-server";
        map = Map.of(
                // new EnvConfig("tx-dev", group, config)
                // new EnvConfig("tx-fat", group, config)
                // new EnvConfig("tx-fat2", group, config)
                new EnvConfig("ali-uat", group, config)
                , new EnvConfig("ali-prod", group, config)
                // , new EnvConfig("ali-prod-b", group, config)
                // new EnvConfig("ali-prod-b", group, config)
        );
    }

    @Test
    public void main() {
        map.forEach((_old, _new) -> {
            Map<String, String> oldList = FileTranferUtils.yaml2Prop(_old.filePath());
            Map<String, String> newList = FileTranferUtils.yaml2Prop(_new.filePath());
            SetView<String> oldHas = Sets.difference(oldList.keySet(), newList.keySet());
            SetView<String> newHas = Sets.difference(newList.keySet(), oldList.keySet());
            MorePrints.println(
                    oldHas.stream().sorted().collect(Collectors.joining("\n")),
                    "\n-------\n",
                    newHas.stream().sorted().collect(Collectors.joining("\n"))
            );
        });
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    private static class EnvConfig {
        String env;
        String group;
        String config;

        public String filePath() {
            return String.format(fileSample, env, group, config);
        }
    }
}