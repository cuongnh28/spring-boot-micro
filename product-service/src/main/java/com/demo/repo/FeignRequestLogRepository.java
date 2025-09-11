package com.demo.repo;

import com.demo.model.FeignRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeignRequestLogRepository extends JpaRepository<FeignRequestLog, Long> {
}
