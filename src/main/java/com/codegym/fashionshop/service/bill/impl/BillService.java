package com.codegym.fashionshop.service.bill.impl;

import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.repository.bill.IBillRepository;
import com.codegym.fashionshop.service.bill.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BillService implements IBillService {
@Autowired
private IBillRepository repository;
    @Override
    public List<Bill> findAll() {
        return repository.findAll();
    }

    @Override
    public Bill findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void save(Bill bill) {
        repository.save(bill);
    }


    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean isBillCodeUnique(String billCode) {
        return !repository.existsByBillCode(billCode);
    }
    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public Double getDailySalesRevenue(LocalDate date) {
        return repository.getDailySalesRevenue(date);
    }
    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public Double getMonthlySalesRevenue(YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int monthValue = yearMonth.getMonthValue();
        return repository.getMonthlySalesRevenue(year, monthValue);
    }
    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public Map<Integer, Double> getDailySalesRevenueForMonth(YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int monthValue = yearMonth.getMonthValue();
        Map<Integer, Double> dailyRevenueMap = new HashMap<>();
        List<Object[]> results = repository.getDailySalesRevenueForMonth(year, monthValue);
        for(Object[] result: results) {
            Integer day = (Integer) result[0];
            Double dailyRevenue = (Double) result[1];
            dailyRevenueMap.put(day,dailyRevenue);
        }
        return dailyRevenueMap;
    }

}
