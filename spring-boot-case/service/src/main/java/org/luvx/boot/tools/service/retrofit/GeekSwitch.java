package org.luvx.boot.tools.service.retrofit;

import org.luvx.coding.common.OnOffSwitch;

public interface GeekSwitch {
    boolean     online     = true;
    /**
     * 保存到mongo
     */
    OnOffSwitch sync2Mongo = OnOffSwitch.of(true);
    /**
     * 保存音频文件
     */
    OnOffSwitch saveMap3   = OnOffSwitch.of(false);
}
