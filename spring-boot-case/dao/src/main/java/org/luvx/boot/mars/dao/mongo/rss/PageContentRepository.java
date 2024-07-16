package org.luvx.boot.mars.dao.mongo.rss;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageContentRepository extends MongoRepository<PageContent, Long> {
}
