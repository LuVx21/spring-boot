package org.luvx.boot.mars;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
// @SpringBootTest(classes = {TestConfiguration.class})
@SpringBootTest(classes = {MarsApp.class})
public class BaseAppTests {
}
