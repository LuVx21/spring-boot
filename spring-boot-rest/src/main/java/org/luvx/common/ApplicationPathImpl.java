package org.luvx.common;

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @ClassName: org.luvx
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/8/27 16:45
 */
@Component
@ApplicationPath(WebConst.REST_API_PREFIX)
public class ApplicationPathImpl extends Application {
}