package org.luvx.boot.mq.pulsar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.pulsar.annotation.EnablePulsar;

@Slf4j
@EnablePulsar
@SpringBootApplication
public class PulsarApp {
    public static void main(String[] args) {
        SpringApplication.run(PulsarApp.class, args);
    }
}
