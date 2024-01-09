package org.luvx.uncrud.tools;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GenerMybatisTool {

    private static final String path = Paths.get("./src/main/java").toAbsolutePath().normalize()
            + "/org/luvx/uncrud/tools/mbg_configuration.xml";

    public static void main(String[] args) {
        generateMbgConfiguration();
    }

    private static void generateMbgConfiguration() {
        List<String> warnings = new ArrayList<>();
        File configFile = new File(path);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DefaultShellCallback callback = new DefaultShellCallback(true);

        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("生成Mybatis配置成功！");
    }
}
