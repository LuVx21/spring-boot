package org.luvx.nio;

import java.io.IOException;
import java.nio.file.*;

/**
 * @ClassName: org.luvx.io
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/17 17:45
 * https://cwind.iteye.com/blog/2179068
 */
public class WatchServiceCase {

    static String url = "E:\\logs";

    public static void main(String[] args) throws InterruptedException, IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        final Path path = Paths.get(url);
        final WatchKey watchKey = path.register(watchService,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
        boolean fileNotChanged = true;
        int count = 0;

        while (fileNotChanged) {
            final WatchKey wk = watchService.take();

            System.out.println("Loop count: " + count);
            for (WatchEvent<?> event : wk.pollEvents()) {
                final Path changed = (Path) event.context();
                System.out.println(changed + ", " + event.kind());
                if (changed.endsWith("sample1.txt")) {
                    System.out.println("Sample file has changed");
                }
            }

            // reset the key
            boolean valid = wk.reset();
            if (!valid) {
                System.out.println("Key has been unregisterede");
            }
            count++;
        }

    }
}
