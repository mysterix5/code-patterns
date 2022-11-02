package com.github.mysterix5.template.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userstatistics")
public class UserStatisticsController {

    public final UserStatisticsService userStatisticsService;

    @GetMapping("/noreg")
    public int getNoOfRegisteredUsers(){
        return userStatisticsService.getNoOfRegisteredUsers();
    }
}
