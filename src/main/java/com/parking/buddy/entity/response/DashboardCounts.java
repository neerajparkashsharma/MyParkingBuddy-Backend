package com.parking.buddy.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardCounts {

    private Long totalBookings;
    private Long totalBookingsToday;
    private Long totalBookingsThisMonth;
    private Long totalBookingsThisYear;
    private Long totalBookingsLastYear;
    private Long totalBookingsLastMonth;
    private Long totalBookingsLastWeek;
    private Long totalParkingSpaces;
    private Long totalParkingSpacesAvailableToday;

    private Long totalUsers;
    private Long totalUsersLastWeek;

    private Long totalCustomersLastWeek;

    private Long totalHostsLastWeek;


    private Long totalParkingSpacesLastWeek;

    private Long totalCustomers;

    private Long totalHosts;

    private List<ChartData> chartData;


}
