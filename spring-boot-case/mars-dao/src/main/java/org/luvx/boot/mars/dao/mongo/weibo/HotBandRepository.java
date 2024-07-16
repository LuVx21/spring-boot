package org.luvx.boot.mars.dao.mongo.weibo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotBandRepository extends MongoRepository<HotBand, Long> {
}
