package com.beaconfire.historyservice.service;

import com.beaconfire.historyservice.domain.History;
import com.beaconfire.historyservice.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<History> findHistoriesByUserId(Long userId) {
        return historyRepository.findByUserIdOrderByViewDateDesc(userId);
    }


    public void create(History history) {
        historyRepository.save(history);
    }

}
