package org.luvx.pattern.Bridge;

abstract class Image {

    protected OS os;

    public void setOs(OS os) {
        this.os = os;
    }

    public abstract void parseFile(String fileName);

}