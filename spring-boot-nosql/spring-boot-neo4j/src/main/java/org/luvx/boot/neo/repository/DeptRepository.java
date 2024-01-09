package org.luvx.boot.neo.repository;

import org.luvx.boot.neo.entity.dept.Dept;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends Neo4jRepository<Dept, Long> {
}