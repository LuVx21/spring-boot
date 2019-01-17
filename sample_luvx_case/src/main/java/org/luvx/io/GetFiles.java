package org.luvx.io;

import java.io.File;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 递归获取一个目录下的所有文件个数及总大小
 */
public class GetFiles {

    public static void getFiles(String path) {
        File file = new File(path);
        if (!file.isDirectory()) {
            System.out.println("输入的是文件!");
            return;
        }

        int count = 0, size = 0;
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                getFiles(f.getPath());
            } else {
                count++;
                size += f.length();
            }
        }

        System.out.println("当前路径为:" + path);
        System.out.println("当前目录下文件数:" + count);
        System.out.println(MessageFormat.format("当前目录文件大小:{0}K", size / 1024));
    }

    public static void getFilesByLoop(String path) {
        File file = new File(path);
        if (!file.isDirectory()) {
            System.out.println("输入的是文件!");
            return;
        }

        Queue<File> queue = new LinkedList();
        queue.offer(file);

        while (!queue.isEmpty()) {
            File f = queue.poll();
            System.out.println("当前路径为:" + f.getPath());
            int count = 0, size = 0;
            File[] files1 = f.listFiles();
            for (File ff : files1) {
                if (ff.isDirectory()) {
                    queue.offer(ff);
                } else {
                    count++;
                    size += ff.length();
                }
            }
            System.out.println("当前目录下文件数:" + count);
            System.out.println(MessageFormat.format("当前目录文件大小:{0}K", size / 1024));
        }
    }


    public static void main(String[] args) {
        getFiles("/Users/renxie/code/luvx_trial/sample_luvx_case/src/main/java/org/luvx/algorithm/");
        System.out.println("---------");
        getFilesByLoop("/Users/renxie/code/luvx_trial/sample_luvx_case/src/main/java/org/luvx/algorithm/");
    }
}
