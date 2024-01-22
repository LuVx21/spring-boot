package org.luvx.boot.tools.common;

import org.luvx.boot.common.app.AppInfo;
import org.luvx.coding.common.consts.Systems;
import org.luvx.coding.common.id.SnowflakeIdWorker;
import org.luvx.coding.common.net.NetUtils;

import static java.util.Optional.ofNullable;

public class CCC {
    public static final SnowflakeIdWorker defaultIdWorker = new SnowflakeIdWorker(0, 0);

    public static String exposeDomain() {
        String ip = ofNullable(Systems.appRunInHost())
                .orElseGet(() -> NetUtils.getHostInfo().get("ip"));
        // String ip = "192.168.2.131";
        Integer port = ofNullable(Systems.appRunInPort())
                .flatMap(o -> AppInfo.instance().map(AppInfo::getPort))
                .orElse(8080);
        return STR."\{ip}:\{port}";
    }
}
