package org.luvx.common.base;

import io.ebean.EbeanServer;
import io.ebean.Finder;

public abstract class BaseFinder<I, T> extends Finder<I, T> {

    protected final EbeanServer server;

    public BaseFinder(Class<T> type, EbeanServer server) {
        super(type);
        this.server = server;
    }

    @Override
    public EbeanServer db() {
        return server;
    }
}
