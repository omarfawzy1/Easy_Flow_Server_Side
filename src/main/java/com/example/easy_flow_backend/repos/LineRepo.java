package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.dto.Views.LiveWithStationsView;
import com.example.easy_flow_backend.entity.Line;
import com.example.easy_flow_backend.dto.Views.LineView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepo extends JpaRepository<Line, String> {
    <T> List<T> findBy(Class<T> type);
    LineView findProjectedById(String id);

    boolean existsByNameIgnoreCase(String name);
    Line findByName(String name);
    @Query( "SELECT line.name " +
            "From Line line " +
            "WHERE line.owner.id = :ownerId")
    List<Object> getOwnerDetails(@Param("ownerId") String id);
    @Query( "SELECT line " +
            "From Line line " +
            "WHERE line.name = :name")
    LiveWithStationsView getLineDetails(@Param("name") String name);

    void deleteByName(String name);
}
