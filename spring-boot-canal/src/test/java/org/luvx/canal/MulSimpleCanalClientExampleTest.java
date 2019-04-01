package org.luvx.canal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: org.luvx.canal
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/4 19:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MulSimpleCanalClientExampleTest {

    @Test
    public void getBinlogTest() {
        MulLoopCanalClientExample example = new MulLoopCanalClientExample();
        example.getBinlog();
    }
}