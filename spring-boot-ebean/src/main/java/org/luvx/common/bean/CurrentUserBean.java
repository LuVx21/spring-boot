package org.luvx.common.bean;

import io.ebean.config.CurrentUserProvider;
import org.springframework.stereotype.Component;

/**
 * Provides the current user to EbeanServer.
 */
@Component
public class CurrentUserBean implements CurrentUserProvider {
    @Override
    public Object currentUser() {
        return "test";
    }
}
