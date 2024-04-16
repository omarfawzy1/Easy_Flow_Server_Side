package com.easy_flow_server.repository;

import com.easy_flow_server.entity.GraphEdge;
import com.easy_flow_server.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphEdgeRepo extends JpaRepository<GraphEdge, String> {

    List<GraphEdge> findAllByLine(Line line);
}
