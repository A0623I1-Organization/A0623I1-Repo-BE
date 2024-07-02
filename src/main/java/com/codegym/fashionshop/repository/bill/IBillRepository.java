package com.codegym.fashionshop.repository.bill;

import com.codegym.fashionshop.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public interface IBillRepository extends JpaRepository<Bill,Long> {
    boolean existsByBillCode(String billCode);
    @Query(value = "select sum(bi.quantity * p.price) as daily_revenue from bill_items as bi inner join bills as b on bi.bill_id = b.bill_id inner join pricings\n" +
            "as p on bi.pricing_id = p.pricing_id where date(b.date_create) = :date", nativeQuery = true)
    Long getDailySalesRevenue(@Param("date") LocalDate date);
    @Query(value = "select sum(bi.quantity * p.price) as monthly_revenue from bill_items as bi " +
            "inner join bills as b on bi.bill_id = b.bill_id " +
            "inner join pricings as p on bi.pricing_id = p.pricing_id " +
            "where year(b.date_create) = :year and month(b.date_create) = :month",
            nativeQuery = true)
    Long getMonthlySalesRevenue(@Param("year") int year, @Param("month") int month);

}
