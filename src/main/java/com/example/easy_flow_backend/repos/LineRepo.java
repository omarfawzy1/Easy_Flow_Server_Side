package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.dto.Views.LineView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepo extends JpaRepository<Line, String> {

    List<LineView> findAllProjectedBy();

    LineView findProjectedById(String id);

    boolean existsByNameIgnoreCase(String name);
    Line findByName(String name);
}
