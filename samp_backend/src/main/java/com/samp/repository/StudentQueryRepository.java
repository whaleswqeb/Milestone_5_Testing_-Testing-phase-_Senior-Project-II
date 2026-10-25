package com.samp.repository;

import com.samp.entity.StudentQuery;
import com.samp.entity.User;
import com.samp.enums.QueryCategory;
import com.samp.enums.QueryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentQueryRepository extends JpaRepository<StudentQuery, Long> {

    List<StudentQuery> findByStudentOrderByCreatedAtDesc(User student);

    List<StudentQuery> findByAssignedToOrderByCreatedAtDesc(User assignedTo);

    List<StudentQuery> findByStatus(QueryStatus status);

    List<StudentQuery> findByCategory(QueryCategory category);

    @Query("SELECT q FROM StudentQuery q ORDER BY q.createdAt DESC")
    List<StudentQuery> findAllOrderByCreatedAtDesc();

    long countByStudentAndStatus(User student, QueryStatus status);
}
