package org.luvx.boot.tools.service.retrofit;

import jakarta.annotation.Resource;

import org.luvx.boot.tools.service.api.WeiboApi;
import org.springframework.stereotype.Service;

@Service
public class WeiboService {
    @Resource
    private WeiboApi weiboApi;

    public void hotBand() {
        String s = weiboApi.hotBand();
    }
}
