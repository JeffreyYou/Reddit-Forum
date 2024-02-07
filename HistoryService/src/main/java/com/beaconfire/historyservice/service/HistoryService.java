package com.beaconfire.historyservice.service;

import com.beaconfire.historyservice.domain.History;
import com.beaconfire.historyservice.repository.HistoryRepository;
import org.hibernate.boot.model.source.spi.HibernateTypeSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

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
        Optional<History> historyOptional = historyRepository.findByUserIdAndPostId(history.getUserId(), history.getPostId());
        if (historyOptional.isPresent()){
            History history1 = historyOptional.get();
            history1.setViewDate(history.getViewDate());
            historyRepository.save(history1);
        } else {
            historyRepository.save(history);
        }
    }

}
