package org.luvx.module.domain.finder;

import io.ebean.Finder;
import org.luvx.module.domain.User;

public class UserFinder extends Finder<Long, User> {
    /**
     * Construct using the default EbeanServer.
     */
    public UserFinder() {
        super(User.class);
    }
}
