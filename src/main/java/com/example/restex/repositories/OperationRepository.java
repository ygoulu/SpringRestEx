package com.example.restex.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.restex.entities.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

  Page<Operation> findAll(Pageable pageable);

  @Query("SELECT h FROM Operation h WHERE h.id = (SELECT MAX(id) FROM Operation)")
  Operation findLastHistory();
}