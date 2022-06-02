package org.luvx.es.repository;

import org.luvx.es.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author: Ren, Xie
 */
public interface UserRepository extends ElasticsearchRepository<User, Long> {

}
