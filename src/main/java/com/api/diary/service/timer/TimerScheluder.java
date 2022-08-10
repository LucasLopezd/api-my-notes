package com.api.diary.service.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TimerScheluder {
    
    private final TimerService timerService;

    @Scheduled(cron = "@hourly") 
    public void refreshDataBase(){
        timerService.refreshDataBase();
        System.out.println("DATA BASE UPDATED....");
    }
    
}
