package com.example.restex.repositories;

import com.example.restex.entities.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

  @Query("SELECT h FROM History h LEFT JOIN FETCH h.operation")
  Page<History> findAllWithOperation(Pageable pageable);
}