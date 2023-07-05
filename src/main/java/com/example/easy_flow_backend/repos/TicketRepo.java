package com.example.easy_flow_backend.repos;

import com.example.easy_flow_backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {
    List<Ticket> findAllByOwnerId(String owner_id);

    List<Ticket> findAllByOwnerIdAndLineId(String owner_id, String line_id);

    <T> T findById(String id, Class<T> type);

    <T> List<T> findAllByOwnerName(String ownerName, Class<T> type);

    <T> List<T> findByLineName(String name, Class<T> type);
}
