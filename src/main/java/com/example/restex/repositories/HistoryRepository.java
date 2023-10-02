package com.example.restex.repositories;

import com.example.restex.entities.History;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

  @Query("SELECT h FROM History h JOIN FETCH h.operation")
  List<History> findAllWithOperation();
}