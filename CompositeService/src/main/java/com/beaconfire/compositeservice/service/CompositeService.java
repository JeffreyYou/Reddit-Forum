package com.beaconfire.compositeservice.service;

import com.beaconfire.compositeservice.dto.HistoryResponse;
import com.beaconfire.compositeservice.dto.PostResponse;
import com.beaconfire.compositeservice.service.remote.RemoteHistoryService;
import com.beaconfire.compositeservice.service.remote.RemotePostAndReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompositeService {

    private RemoteHistoryService historyService;

    private RemotePostAndReplyService postAndReplyService;

    @Autowired
    public CompositeService(RemoteHistoryService historyService,
                            RemotePostAndReplyService postAndReplyService){
        this.historyService = historyService;
        this.postAndReplyService=postAndReplyService;
    }

    //cache ?
    public List<PostResponse> getHistoryByUserId(Long userId){
        List<HistoryResponse> historyList = historyService.getHistoryByUserId(userId);
        List<PostResponse> postResponses =historyList.stream().map(x->postAndReplyService.getPostById(x.getPostId()))
                .filter(x->x.getStatus().equals("Published"))
                .collect(Collectors.toList());
        return postResponses;
    }
}
