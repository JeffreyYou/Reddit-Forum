package com.beaconfire.historyservice.service;

import com.beaconfire.historyservice.domain.History;
import com.beaconfire.historyservice.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<History> findMostRecentHistoriesByUserId(Long userId) {
        return historyRepository.findByUserIdOrderByViewDateDesc(userId);
    }

    public List<History> findAllHistoriesByUserId(Long userId) {
        return historyRepository.findByUserId(userId);
    }



    //get posts by userid, check post status, delete (?) non-"published" records

    //create new record

}
