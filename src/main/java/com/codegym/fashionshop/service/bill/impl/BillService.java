package com.codegym.fashionshop.service.bill.impl;

import com.codegym.fashionshop.dto.SoldPricings;
import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.entities.Customer;
import com.codegym.fashionshop.entities.Promotion;
import com.codegym.fashionshop.repository.bill.IBillRepository;
import com.codegym.fashionshop.repository.customer.ICustomerRepository;
import com.codegym.fashionshop.service.bill.IBillItemService;
import com.codegym.fashionshop.service.bill.IBillService;
import com.codegym.fashionshop.service.customer.ICustomerService;
import com.codegym.fashionshop.service.promotion.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BillService implements IBillService {
    @Autowired
    private IBillRepository repository;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IPromotionService promotionService;
    @Autowired
    private IBillItemService billItemService;

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

    @Override
    public List<Bill> findBillsByCustomer_CustomerId(Long customerId) {
        return repository.findBillsByCustomer_CustomerId(customerId);

    }

    public boolean isBillCodeUnique(String billCode) {
        return !repository.existsByBillCode(billCode);
    }

    public Double calculateTotalBillForCustomer(Long customerId) {
        Customer customer = customerService.findById(customerId);
        List<Bill> bills = repository.findBillsByCustomer_CustomerId(customerId);
        double totalAmount = 0.0;

        for (Bill bill : bills) {
            double billAmount = 0.0;
            for (BillItem item : bill.getBillItemList()) {
                billAmount += item.getPricing().getPrice() * item.getQuantity();
            }

            // Áp dụng mã khuyến mãi của hóa đơn nếu có
            if (bill.getPromotionCode() != null && !bill.getPromotionCode().isEmpty()) {
                Promotion promotion = promotionService.findByPromotionCode(bill.getPromotionCode());
                if (promotion != null) {
                        double discount = promotion.getDiscount();
                    if (discount > 0 && discount < 1) {
                        // Tính theo phần trăm trên tổng bill
                        billAmount -= billAmount * discount;
                    } else if (discount >= 1) {
                        // Lấy tổng bill trừ ra
                        billAmount -= discount;
                    }
                }
            }

            // Áp dụng giảm giá theo loại khách hàng
            if (customer.getCustomerType() != null) {
                billAmount -= billAmount * customer.getCustomerType().getDiscount();
            }

            totalAmount += billAmount;
        }

        return totalAmount;
    }


    /**
     * {@inheritDoc}
     *
     * @author ThanhTT
     */
    @Override
    public Double getDailySalesRevenue(LocalDate date) {
        return repository.getDailySalesRevenue(date);
    }

    /**
     * {@inheritDoc}
     *
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
     *
     * @author ThanhTT
     */
    @Override
    public Map<Integer, Double> getDailySalesRevenueForMonth(YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int monthValue = yearMonth.getMonthValue();
        Map<Integer, Double> dailyRevenueMap = new HashMap<>();
        List<Object[]> results = repository.getDailySalesRevenueForMonth(year, monthValue);
        for (Object[] result : results) {
            Integer day = (Integer) result[0];
            Double dailyRevenue = (Double) result[1];
            dailyRevenueMap.put(day, dailyRevenue);
        }
        return dailyRevenueMap;
    }
    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public List<SoldPricings> getDailySoldPricings(LocalDate date) {
        List<Object[]> results = repository.getDailySoldPricings(date);
        return results.stream().map(this::mapToSoldPricings).collect(Collectors.toList());
    }
    /**
     * {@inheritDoc}
     * @author ThanhTT
     */
    @Override
    public List<SoldPricings> getDailySoldPricings(int year, int month) {
        List<Object[]> results = repository.getMonthlySoldPricings(year, month);
        return results.stream().map(this::mapToSoldPricings).collect(Collectors.toList());
    }
    private SoldPricings mapToSoldPricings(Object[] object) {
        return SoldPricings.builder()
                .pricingCode((String) object[0])
                .pricingName((String) object[1])
                .totalQuantity( ( (BigDecimal) object[2] ).intValue() )
                .price((double) object[3])
                .build();
    }
}
