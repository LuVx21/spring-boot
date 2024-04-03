package org.luvx.boot.mybatis.mapper.common.query;

/**
 * @ClassName: org.luvx.query
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/5/27 17:01
 */
public interface Query<T> {
    Order ASC  = Order.ASC;
    Order DESC = Order.DESC;

    Column RAND_COLUMN         = Column.RAND;
    Column ID_COLUMN           = Column.ID;
    Column GMT_CREATE_COLUMN   = Column.GMT_CREATE;
    Column GMT_MODIFIED_COLUMN = Column.GMT_MODIFIED;

    int DELETED     = 1;
    int NOT_DELETED = 0;

    T toBean();

    enum Order {
        ASC, DESC
    }

    enum Column {
        RAND("rand()"), ID, GMT_CREATE, GMT_MODIFIED;
        private String value;

        Column() {
        }

        Column(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value == null ? super.toString() : value;
        }
    }
}
