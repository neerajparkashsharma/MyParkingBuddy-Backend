package com.parking.buddy.controller;


import com.parking.buddy.entity.response.DashboardCounts;
import com.parking.buddy.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/dashboard")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboardCounts")
    public DashboardCounts getDashboardCounts() {
        return dashboardService.getDashboardCounts();
    }
}
