package org.luvx.boot.tools.dao.mongo.weibo;

import org.luvx.boot.tools.dao.mongo.rss.PageContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotBandRepository extends MongoRepository<HotBand, Long> {
}
