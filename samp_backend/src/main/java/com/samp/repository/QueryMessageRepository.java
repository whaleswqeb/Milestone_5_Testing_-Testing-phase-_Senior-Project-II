package com.samp.repository;

import com.samp.entity.QueryMessage;
import com.samp.entity.StudentQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryMessageRepository extends JpaRepository<QueryMessage, Long> {
    List<QueryMessage> findByQueryOrderByCreatedAtAsc(StudentQuery query);
}
