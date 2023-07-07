package com.easy_flow_server.repos;

import com.easy_flow_server.dto.Views.LiveWithStationsView;
import com.easy_flow_server.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepo extends JpaRepository<Line, String> {
    <T> List<T> findBy(Class<T> type);

    <T> List<T> findAllByOwnerId(String ownerId, Class<T> type);

    <T> T findById(String id, Class<T> type);

    <T> T findByName(String name, Class<T> type);

    boolean existsByName(String name);

    Line findByName(String name);

    @Query("SELECT line.name " +
            "From Line line " +
            "WHERE line.owner.id = :ownerId")
    List<Object> getOwnerDetails(@Param("ownerId") String id);

    @Query("SELECT line " +
            "From Line line " +
            "WHERE line.name = :name")
    LiveWithStationsView getLineDetails(@Param("name") String name);

    void deleteByName(String name);

    void removeByName(String name);
}
