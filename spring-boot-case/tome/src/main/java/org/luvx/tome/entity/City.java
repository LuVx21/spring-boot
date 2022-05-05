package org.luvx.tome.entity;

import lombok.Data;

@Data
public class City {
    private long   cityId;
    private String counname;
    private String ianatimezone;
    private String name;
    private String pname;
    private String secondaryname;
    private String timezone;

    public String location() {
        return pname + "-" + secondaryname + "-" + name + "\n";
    }
}