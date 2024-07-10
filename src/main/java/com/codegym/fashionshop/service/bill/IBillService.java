package com.codegym.fashionshop.service.bill;

import com.codegym.fashionshop.dto.SoldPricings;
import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.repository.bill.IBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

import java.util.List;

public interface IBillService  {
    boolean isBillCodeUnique(String billCode);
    void createBillAndUpdatePoints(Bill bill, int pointsToAdd);
    List<Bill> findAll();
    List<Bill>findBillsByCustomer_CustomerId(Long customerId);
    public Double calculateTotalBillForCustomer(Long customerId);

    /**
     * Retrieves the daily sales revenue for a specific date.
     *
     * @param date The date for which to retrieve the daily sales revenue.
     * @return The daily sales revenue.
     * @author ThanhTT
     */
    Double getDailySalesRevenue(LocalDate date);
    /**
     * Retrieves the monthly sales revenue for a specific year and month.
     *
     * @param yearMonth The YearMonth object for which to retrieve the monthly sales revenue.
     * @return The monthly sales revenue.
     * @author ThanhTT
     */
    Double getMonthlySalesRevenue(YearMonth yearMonth);
    /**
     * Retrieves the daily sales revenue for each day in a specific month.
     *
     * @param yearMonth The YearMonth object for which to retrieve the daily sales revenue.
     * @return A map containing the day as the key and the corresponding daily sales revenue as the value.
     * @author ThanhTT
     */
    Map<Integer, Double> getDailySalesRevenueForMonth(YearMonth yearMonth);
    void createBillAndUpdatePoints(Bill bill, int pointsToAdd);
    List<Bill> findAll();
    /**
     * Fetches daily sold pricings as SoldPricings objects for a given date.
     *
     * @param date the date for which to fetch sold pricings
     * @return a list of SoldPricings objects
     * @author ThanhTT
     */
    List<SoldPricings> getDailySoldPricings(@Param("date") LocalDate date);
    /**
     * Fetches monthly sold pricings as SoldPricings objects for a given year and month.
     *
     * @param year the year for which to fetch sold pricings
     * @param month the month for which to fetch sold pricings
     * @return a list of SoldPricings objects
     * @author ThanhTT
     */
    List<SoldPricings> getDailySoldPricings(@Param("year") int year, @Param("month") int month);
}
