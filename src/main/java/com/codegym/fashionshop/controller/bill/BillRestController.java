package com.codegym.fashionshop.controller.bill;

import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.entities.Pricing;
import com.codegym.fashionshop.entities.ProductType;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.bill.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin("*")
public class BillRestController {
    @Autowired
    private IBillService billService;

    @GetMapping
    public ResponseEntity<List<Bill>> getAllProduct() {
        List<Bill> bills = billService.findAll();
        if (bills.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin màu sắc");
        }
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<Object> createBill(@Validated @RequestBody Bill bill, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            // Trả về phản hồi JSON chứa thông điệp lỗi
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        for (BillItem billItem: bill.getBillItemList()) {
            billItem.setBill(bill);
        }
        billService.save(bill);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    @PostMapping("/checkBillCode")
    public ResponseEntity<Map<String, Boolean>> checkBillCode(@RequestBody Map<String, String> request) {
        String billCode = request.get("code");
        System.out.println("Received pricing code: " + billCode);
        boolean isUnique = billService.isBillCodeUnique(billCode);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the daily sales revenue for a specific date.
     *
     * @param date The date for which to retrieve the daily sales revenue.
     * @return A ResponseEntity containing the daily sales revenue.
     * @author ThanhTT
     */
    @GetMapping("/revenue/daily")
    public ResponseEntity<Double> getDailySalesRevenue(@RequestParam("date") LocalDate date){
        Double dailyRevenue = billService.getDailySalesRevenue(date);
        return new ResponseEntity<>(dailyRevenue, HttpStatus.OK);
    }
    /**
     * Retrieves the monthly sales revenue for a specific month.
     *
     * @param yearMonth The YearMonth object for which to retrieve the monthly sales revenue.
     * @return A ResponseEntity containing the monthly sales revenue.
     * @author ThanhTT
     */
    @GetMapping("/revenue/monthly")
    public ResponseEntity<Double> getMonthlySalesRevenue(@RequestParam("month")YearMonth yearMonth){
        Double monthlyRevenue = billService.getMonthlySalesRevenue(yearMonth);
        return new ResponseEntity<>(monthlyRevenue, HttpStatus.OK);
    }
    /**
     * Retrieves the daily sales revenue for each day in a specific month.
     *
     * @param yearMonth The YearMonth object for which to retrieve the daily sales revenue.
     * @return A ResponseEntity containing a map of the day and the corresponding daily sales revenue.
     * @author ThanhTT
     */
    @GetMapping("/revenue/daily/month")
    public ResponseEntity<Map<Integer, Double>> getDailySalesRevenueForMonth(@RequestParam("month")YearMonth yearMonth) {
        Map<Integer, Double> dailyRevenue = billService.getDailySalesRevenueForMonth(yearMonth);
        return new ResponseEntity<>(dailyRevenue, HttpStatus.OK);
    }

}
