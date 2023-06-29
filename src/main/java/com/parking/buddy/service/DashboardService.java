package com.parking.buddy.service;


import com.parking.buddy.entity.BookingDates;
import com.parking.buddy.entity.Parking;
import com.parking.buddy.entity.ParkingBookingRecords;
import com.parking.buddy.entity.Role;
import com.parking.buddy.entity.response.ChartData;
import com.parking.buddy.entity.response.DashboardCounts;
import com.parking.buddy.entity.response.DataPoint;
import com.parking.buddy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {


    @Autowired
    private ParkingRepository parkingRepo;
    @Autowired
    private ParkingRecordsRepository parkingRecordsRepository;


    @Autowired
    private ParkingBookingRecordsRepository parkingBookingRecordsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingDatesRespository bookingDatesRespository;


    public DashboardCounts getDashboardCounts() {
        DashboardCounts dashboardCounts = new DashboardCounts();
        dashboardCounts.setTotalParkingSpaces(Long.parseLong(String.valueOf(parkingRepo.findAll().size())));
        dashboardCounts.setTotalBookings(Long.parseLong(String.valueOf(parkingBookingRecordsRepository.findAll().size())));
        dashboardCounts.setTotalUsers(Long.parseLong(String.valueOf(userRepository.findAll().size())));
        dashboardCounts.setTotalCustomers(Long.parseLong(String.valueOf(userRepository.findByRole(new Role(2L, "Customer")).size())));
        dashboardCounts.setTotalHosts(Long.parseLong(String.valueOf(userRepository.findByRole(new Role(1L, "Host")).size())));

        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime lastWeek = nowTime.minusWeeks(1);

        ZonedDateTime startZonedDateTime = lastWeek.atZone(ZoneId.systemDefault());
        ZonedDateTime endZonedDateTime = nowTime.atZone(ZoneId.systemDefault());

        Date start = Date.from(startZonedDateTime.toInstant());
        Date end = Date.from(endZonedDateTime.toInstant());


        Long totalUsersLastWeek = userRepository.findAll().
                stream().filter(user -> user.getCreatedDate().after(start) && user.getCreatedDate().before(end))
                .count();

        Long totalCustomersLastWeek = userRepository.findAll().
                stream().filter(user -> user.getCreatedDate().after(start) && user.getCreatedDate().before(end))
                .filter(user -> user.getRole().getId() == 2L)
                .count();


        Long totalHostsLastWeek = userRepository.findAll().
                stream().filter(user -> user.getCreatedDate().after(start) && user.getCreatedDate().before(end))
                .filter(user -> user.getRole().getId() == 1L)
                .count();


        Long totalParkingSpacesLastWeek = parkingRepo.findAll().
                stream().filter(parking -> parking.getCreateDate().after(start) && parking.getCreateDate().before(end))
                .count();



        dashboardCounts.setTotalUsersLastWeek(totalUsersLastWeek);
        dashboardCounts.setTotalCustomersLastWeek(totalCustomersLastWeek);
        dashboardCounts.setTotalHostsLastWeek(totalHostsLastWeek);
        dashboardCounts.setTotalParkingSpacesLastWeek(totalParkingSpacesLastWeek);


        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = LocalDate.now().format(formatter);

        Long totalBookingsToday = bookingDatesRespository.findAll()
                .stream()
                .map(x -> x.getBookingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter))
                .filter(date -> date.equals(todayDate))
                .count();

        dashboardCounts.setTotalBookingsToday(totalBookingsToday);

        int thisYear = now.getYear();

        Long totalBookingsThisYear = bookingDatesRespository.findAll()
                .stream()
                .map(x -> x.getBookingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear())
                .filter(year -> year == thisYear)
                .count();


        dashboardCounts.setTotalBookingsThisYear(totalBookingsThisYear);

        LocalDate earlier = now.minusMonths(1);


        int lastMonth = earlier.getMonthValue();

        Long totalBookingsLastMonth = bookingDatesRespository.findAll()
                .stream()
                .map(x -> x.getBookingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue())
                .filter(month -> month == lastMonth)
                .count();


        Long totalBookingsLastWeek = bookingDatesRespository.findAll()
                .stream()
                .map(x -> x.getBookingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .filter(date -> date.isAfter(lastWeek.toLocalDate()) && date.isBefore(now))
                .count();


        Long totalBookingsLastYear = bookingDatesRespository.findAll()
                .stream()
                .map(x -> x.getBookingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear())
                .filter(year -> year == thisYear - 1)
                .count();

        dashboardCounts.setTotalBookingsLastYear(totalBookingsLastYear);
        dashboardCounts.setTotalBookingsLastMonth(totalBookingsLastMonth);
        dashboardCounts.setTotalBookingsLastWeek(totalBookingsLastWeek);
        YearMonth currentMonth = YearMonth.from(now);

        Long totalBookingsThisMonth = bookingDatesRespository.findAll()
                .stream()
                .map(x -> x.getBookingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .filter(date -> YearMonth.from(date).equals(currentMonth))
                .count();


        dashboardCounts.setTotalBookingsThisMonth(totalBookingsThisMonth);



        List<Parking> parkingList = parkingRepo.findAll();
        List<ParkingBookingRecords> parkingBookingRecords = parkingBookingRecordsRepository.findAll();

         int availableParkingCount = parkingList.size();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        for (ParkingBookingRecords pbr : parkingBookingRecords) {
            for (BookingDates bd : bookingDatesRespository.findAll()) {
                Date bookingDate = bd.getBookingDate();
                String formattedBookingDate = dateFormatter.format(bookingDate);
                String formattedTodayDate = dateFormatter.format(new Date());

                if (pbr.getId() == bd.getParkingBookingId() && formattedBookingDate.equals(formattedTodayDate)) {
                    availableParkingCount--;
                }
            }
        }

        dashboardCounts.setTotalParkingSpacesAvailableToday(Long.parseLong(String.valueOf(availableParkingCount)));



        Map<String, Map<String, Integer>> yearlyCounts = new TreeMap<>();
        int currentYear = LocalDate.now().getYear();
        int numYears = 5;

        for (int i = 0; i < numYears; i++) {
            int year = currentYear - i;
            Map<String, Integer> categoryCounts = new TreeMap<>();

            int userCount = calculateYearlyCountForCategory(year, "Users");
            int parkingCount = calculateYearlyCountForCategory(year, "Parkings");
            int bookingCount = calculateYearlyCountForCategory(year, "Bookings");

            categoryCounts.put("Users", userCount);
            categoryCounts.put("Parkings", parkingCount);
            categoryCounts.put("Bookings", bookingCount);

            yearlyCounts.put(String.valueOf(year), categoryCounts);
        }

        List<ChartData> chartDataList = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : yearlyCounts.entrySet()) {
            String year = entry.getKey();
            Map<String, Integer> categoryCounts = entry.getValue();
            List<DataPoint> dataPoints = new ArrayList<>();

            for (Map.Entry<String, Integer> categoryEntry : categoryCounts.entrySet()) {
                String category = categoryEntry.getKey();
                int count = categoryEntry.getValue();
                dataPoints.add(new DataPoint(category, count));
            }

            chartDataList.add(new ChartData(year, dataPoints));
        }
        dashboardCounts.setChartData(chartDataList);





    return dashboardCounts;
    }
    private int calculateYearlyCountForCategory(int year, String category) {

        switch (category) {
            case "Users":
                return userRepository.findAll()
                        .stream()
                        .map(x -> x.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear())
                        .filter(x -> x == year)
                        .collect(Collectors.toList())
                        .size();
            case "Parkings":
                return parkingRepo.findAll()
                        .stream()
                        .map(x -> x.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear())
                        .filter(x -> x == year)
                        .collect(Collectors.toList())
                        .size();
            case "Bookings":
                return bookingDatesRespository.findAll()
                        .stream()
                        .map(x -> x.getBookingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear())
                        .filter(x -> x == year)
                        .collect(Collectors.toList())
                        .size();
            default:
                return 0;
        }
    }
}