package com.beaconfire.historyservice.repository;

import com.beaconfire.historyservice.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserIdOrderByViewDateDesc(Long userId);

    List<History> findByUserId(Long userId);
}
