package org.luvx.pattern.Bridge;

class OS_Linux implements OS {

    @Override
    public void display() {
        System.out.println("display picture in Linux......");
    }
}