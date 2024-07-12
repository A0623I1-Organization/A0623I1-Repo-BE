package com.codegym.fashionshop.service.bill.impl;

import com.codegym.fashionshop.dto.DailyRevenueDTO;
import com.codegym.fashionshop.dto.SoldPricingsDTO;
import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.repository.bill.IBillRepository;
import com.codegym.fashionshop.service.bill.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BillService implements IBillService {
    @Autowired
    private IBillRepository repository;
    @Transactional
    @Override
    public void createBillAndUpdatePoints(Bill bill, int pointsToAdd) {
        repository.save(bill);
        repository.updateAccumulatedPoints(bill.getCustomer().getCustomerId(), pointsToAdd);
    }

    @Override
    public List<Bill> findAll() {
        return repository.findAll();
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
    public List<DailyRevenueDTO> getDailySalesRevenueForMonth(YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int monthValue = yearMonth.getMonthValue();
        List<DailyRevenueDTO> revenueDTOList = new ArrayList<>();
        List<Object[]> results = repository.getDailySalesRevenueForMonth(year, monthValue);
        for(Object[] result: results) {
            int day = (Integer) result[0];
            double revenue = (Double) result[1];
            DailyRevenueDTO dailyRevenue = new DailyRevenueDTO(day, revenue);
            revenueDTOList.add(dailyRevenue);
        }
        return revenueDTOList;
    }
    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public List<SoldPricingsDTO> getDailySoldPricings(LocalDate date) {
        List<Object[]> results = repository.getDailySoldPricings(date);
        return results.stream().map(this::mapToSoldPricings).collect(Collectors.toList());
    }
    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public List<SoldPricingsDTO> getDailySoldPricings(int year, int month) {
        List<Object[]> results = repository.getMonthlySoldPricings(year, month);
        return results.stream().map(this::mapToSoldPricings).collect(Collectors.toList());
    }
    private SoldPricingsDTO mapToSoldPricings(Object[] object) {
        return SoldPricingsDTO.builder()
                .pricingCode((String) object[0])
                .pricingName((String) object[1])
                .totalQuantity( ( (BigDecimal) object[2] ).intValue() )
                .price((double) object[3])
                .build();
    }
}
