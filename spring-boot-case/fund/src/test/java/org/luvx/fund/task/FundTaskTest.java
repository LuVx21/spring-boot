package org.luvx.fund.task;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;
import javax.script.ScriptEngine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.SneakyThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FundTaskTest {
    @Resource
    FundTask service;

    @Test
    void contextLoads() {
        service.readRequest("009147");
    }

    @Test
    @SneakyThrows
    void readFile() {
        Path path = Paths.get(FundTaskTest.class.getClassLoader().getResource("post.js").toURI());
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            engine.eval(reader);
        }
    }
}