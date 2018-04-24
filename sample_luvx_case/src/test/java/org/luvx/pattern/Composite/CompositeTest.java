package org.luvx.pattern.Composite;

import org.junit.Test;

/**
 * 组合模式
 */
public class CompositeTest {

    @Test
    public void run01() {
        FileType file1, file2, file3, file4, dir1, dir2, dir3;

        dir1 = new Directory("文件");
        dir2 = new Directory("图像文件");
        dir3 = new Directory("文本文件");

        file1 = new Image("笑.jpg");
        file2 = new Image("狗在笑.gif");
        file3 = new Text("小说.txt");
        file4 = new Text("文档.doc");

        dir2.add(file1);
        dir2.add(file2);
        dir3.add(file3);
        dir3.add(file4);
        dir1.add(dir2);
        dir1.add(dir3);

        dir1.operation();
    }
}