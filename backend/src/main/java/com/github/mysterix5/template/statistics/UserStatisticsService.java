package com.github.mysterix5.template.statistics;

import com.github.mysterix5.template.model.StatisticsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatisticsService {
    public final UserStatisticsRepository userStatisticsRepository;
    public int getNoOfRegisteredUsers() {
        return userStatisticsRepository.findByName("users").orElseGet(()->new StatisticsEntity("users", -1)).getAmount();
    }

    @EventListener
    public void onRegister(final RegisterEvent registerEvent){
        var userStatistics = userStatisticsRepository.findByName("users").orElseGet(()->new StatisticsEntity("users", 0));
        userStatistics.increase();
        userStatisticsRepository.save(userStatistics);
    }
}
