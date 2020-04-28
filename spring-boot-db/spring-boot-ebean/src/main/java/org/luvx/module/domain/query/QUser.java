package org.luvx.module.domain.query;

import io.ebean.Database;
import io.ebean.FetchGroup;
import io.ebean.Query;
import io.ebean.typequery.PInteger;
import io.ebean.typequery.PLocalDateTime;
import io.ebean.typequery.PLong;
import io.ebean.typequery.PString;
import io.ebean.typequery.TQRootBean;
import io.ebean.typequery.TypeQueryBean;
import javax.annotation.Generated;
import org.luvx.module.domain.User;

/**
 * Query bean for User.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@Generated("io.ebean.querybean.generator")
@TypeQueryBean("v1")
public class QUser extends TQRootBean<User,QUser> {

  private static final QUser _alias = new QUser(true);

  /**
   * Return the shared 'Alias' instance used to provide properties to 
   * <code>select()</code> and <code>fetch()</code> 
   */
  public static QUser alias() {
    return _alias;
  }

  public PLocalDateTime<QUser> insertTime;
  public PString<QUser> insertUser;
  public PLocalDateTime<QUser> updateTime;
  public PString<QUser> updateUser;
  public PLong<QUser> version;
  public PLong<QUser> userId;
  public PString<QUser> userName;
  public PString<QUser> password;
  public PInteger<QUser> age;


  /**
   * Return a query bean used to build a FetchGroup.
   */
  public static QUser forFetchGroup() {
    return new QUser(FetchGroup.queryFor(User.class));
  }


  /**
   * Construct with a given Database.
   */
  public QUser(Database server) {
    super(User.class, server);
  }

  /**
   * Construct using the default Database.
   */
  public QUser() {
    super(User.class);
  }

  /**
   * Construct for Alias.
   */
  private QUser(boolean dummy) {
    super(dummy);
  }

  /**
   * Private constructor for FetchGroup building.
   */
  private QUser(Query<User> fetchGroupQuery) {
    super(fetchGroupQuery);
  }

  /**
   * Provides static properties to use in <em> select() and fetch() </em>
   * clauses of a query. Typically referenced via static imports. 
   */
  public static class Alias {
    public static PLocalDateTime<QUser> insertTime = _alias.insertTime;
    public static PString<QUser> insertUser = _alias.insertUser;
    public static PLocalDateTime<QUser> updateTime = _alias.updateTime;
    public static PString<QUser> updateUser = _alias.updateUser;
    public static PLong<QUser> version = _alias.version;
    public static PLong<QUser> userId = _alias.userId;
    public static PString<QUser> userName = _alias.userName;
    public static PString<QUser> password = _alias.password;
    public static PInteger<QUser> age = _alias.age;
  }
}
