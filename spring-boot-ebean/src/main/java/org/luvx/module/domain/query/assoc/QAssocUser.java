package org.luvx.module.domain.query.assoc;

import io.ebean.typequery.PInteger;
import io.ebean.typequery.PLocalDateTime;
import io.ebean.typequery.PLong;
import io.ebean.typequery.PString;
import io.ebean.typequery.TQAssocBean;
import io.ebean.typequery.TQProperty;
import io.ebean.typequery.TypeQueryBean;
import javax.annotation.Generated;
import org.luvx.module.domain.User;
import org.luvx.module.domain.query.QUser;

/**
 * Association query bean for AssocUser.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@Generated("io.ebean.querybean.generator")
@TypeQueryBean("v1")
public class QAssocUser<R> extends TQAssocBean<User,R> {

  public PLocalDateTime<R> insertTime;
  public PString<R> insertUser;
  public PLocalDateTime<R> updateTime;
  public PString<R> updateUser;
  public PLong<R> version;
  public PLong<R> userId;
  public PString<R> userName;
  public PString<R> password;
  public PInteger<R> age;

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  @SafeVarargs @SuppressWarnings("varargs")
  public final R fetch(TQProperty<QUser>... properties) {
    return fetchProperties(properties);
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  @SafeVarargs @SuppressWarnings("varargs")
  public final R fetchQuery(TQProperty<QUser>... properties) {
    return fetchQueryProperties(properties);
  }

  /**
   * Eagerly fetch this association using L2 cache.
   */
  @SafeVarargs @SuppressWarnings("varargs")
  public final R fetchCache(TQProperty<QUser>... properties) {
    return fetchCacheProperties(properties);
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  @SafeVarargs @SuppressWarnings("varargs")
  public final R fetchLazy(TQProperty<QUser>... properties) {
    return fetchLazyProperties(properties);
  }

  public QAssocUser(String name, R root) {
    super(name, root);
  }

  public QAssocUser(String name, R root, String prefix) {
    super(name, root, prefix);
  }
}
