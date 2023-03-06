package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Graph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GraphRepo extends JpaRepository<Graph, String> {
    Graph findByOwnerIdAndLineId(String owner_id, String line_id);

    List<Graph> findAllByOwnerId(String owner_id);

}
