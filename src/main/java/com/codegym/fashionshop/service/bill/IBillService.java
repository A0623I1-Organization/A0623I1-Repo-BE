package com.codegym.fashionshop.service.bill;

import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.repository.bill.IBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

public interface IBillService extends IGeneralService<Bill> {
    boolean isBillCodeUnique(String billCode);
    Double getDailySalesRevenue(LocalDate date);
    Double getMonthlySalesRevenue(YearMonth yearMonth);
    Map<Integer, Double> getDailySalesRevenueForMonth(YearMonth yearMonth);
}
