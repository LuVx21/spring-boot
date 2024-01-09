package org.luvx.boot.neo.entity.dept;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("dept")
@Data
@Builder
public class Dept {
    @Id
    @GeneratedValue
    private Long   id;
    @Property(name = "deptName")
    private String deptName;
    @Relationship(type = "BU", direction = OUTGOING)
    private Dept   parent;
}