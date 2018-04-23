package org.luvx.pattern.Bridge;

import org.junit.Test;

public class BridgeTest {

    @Test
    public void showImage() {
        Image image = new Image_jpg();
        OS_Linux linux = new OS_Linux();
        image.setOs(linux);
        image.parseFile("girl.jpg");

        OS_Mac mac = new OS_Mac();
        image.setOs(mac);
        image.parseFile("girl.jpg");
    }
}
