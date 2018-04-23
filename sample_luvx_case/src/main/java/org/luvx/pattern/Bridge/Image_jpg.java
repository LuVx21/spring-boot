package org.luvx.pattern.Bridge;


class Image_jpg extends Image {

    public void parseFile(String fileName) {
        os.display();
        System.out.println("解析JPG格式图片......文件名为:" + fileName);
    }

}