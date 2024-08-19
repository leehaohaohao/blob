package com.lihao.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {
    @Scheduled(fixedRate = 1000000)
    public void noteScan(){

    }
}
