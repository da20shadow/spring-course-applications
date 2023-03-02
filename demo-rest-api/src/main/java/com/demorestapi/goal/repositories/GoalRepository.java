package com.demorestapi.goal.repositories;

import com.demorestapi.goal.models.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends PagingAndSortingRepository<GoalEntity,Long>,
        JpaRepository<GoalEntity,Long> {
}
