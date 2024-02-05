package com.beaconfire.compositeservice.service.remote;

import com.beaconfire.compositeservice.dto.HistoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("history-service")
public interface RemoteHistoryService {
    @GetMapping("history-service/history/all/{userid}")
    List<HistoryResponse> getHistoryByUserId(@PathVariable("userid") Long userId);

}
