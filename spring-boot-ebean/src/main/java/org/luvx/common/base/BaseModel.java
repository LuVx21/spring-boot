package org.luvx.common.base;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.annotation.WhoCreated;
import io.ebean.annotation.WhoModified;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseModel extends Model {
    // @Id
    // Long          id;
    @WhenCreated
    LocalDateTime insertTime;
    @WhoCreated
    String        insertUser;
    @WhenModified
    LocalDateTime updateTime;
    @WhoModified
    String        updateUser;
    @Version
    Long          version;
}
