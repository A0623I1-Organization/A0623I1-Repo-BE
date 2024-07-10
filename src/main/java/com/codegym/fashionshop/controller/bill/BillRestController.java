package com.codegym.fashionshop.controller.bill;

import com.codegym.fashionshop.entities.AppUser;
import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.repository.authenticate.IUserRepository;
import com.codegym.fashionshop.service.authenticate.IAppUserService;
import com.codegym.fashionshop.service.bill.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * REST controller for managing bill-related operations.
 * Author: HoaNTT
 */
@RestController
@RequestMapping("/api/auth/bills")
@CrossOrigin("*")
public class BillRestController {

    @Autowired
    private IBillService billService;
    @Autowired
    private IAppUserService userService;

    /**
     * GET endpoint to retrieve all bills.
     *
     * @return a ResponseEntity containing a list of bills and HTTP status OK (200) if successful
     * @throws HttpExceptions.NotFoundException if no bills are found
     */
    @GetMapping
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billService.findAll();
        if (bills.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin hóa đơn");
        }
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    /**
     * POST endpoint to create a new bill.
     *
     * @param bill          the bill object to be created (validated using @Validated)
     * @param bindingResult captures and exposes errors from binding/validation process
     * @return a ResponseEntity containing the created bill and HTTP status CREATED (201) if successful
     * @throws HttpExceptions.BadRequestException if there are validation errors in the request body
     */
    @PostMapping("")
    public ResponseEntity<Object> createBill(@Validated @RequestBody Bill bill, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            throw new HttpExceptions.BadRequestException("Validation errors: " + errors.toString());
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = userService.findByUsername(username);
        System.out.println(username);
        for (BillItem billItem : bill.getBillItemList()) {
            billItem.setBill(bill);
            System.out.println("billItem = " + billItem.getQuantity());
        }
        int pointsToAdd = calculatePoints(bill);
        System.out.println("bill = " + bill.getCustomer().getCustomerName());
        System.out.println("mâ = " + pointsToAdd);
        bill.setAppUser(user);
        billService.createBillAndUpdatePoints(bill, pointsToAdd);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    /**
     * POST endpoint to generate and check a unique bill code.
     *
     * @return a ResponseEntity containing a map with the generated bill code and HTTP status OK (200) if successful
     */
    @PostMapping("/generateAndCheckBillCode")
    public ResponseEntity<Map<String, String>> generateAndCheckBillCode() {
        String billCode = generateUniqueBillCode();
        Map<String, String> response = new HashMap<>();
        response.put("code", billCode);
        return ResponseEntity.ok(response);
    }

    /**
     * <<<<<<< HEAD
     * Generates a unique bill code.
     *
     * @return a unique bill code prefixed with "HD" (e.g., HD000001)
     */
    private String generateUniqueBillCode() {
        String billCode;
        Random random = new Random();
        do {
            billCode = "HD" + String.format("%06d", random.nextInt(1000000));
        } while (!billService.isBillCodeUnique(billCode));
        return billCode;
    }

    /**
     * Calculates points to be added based on the bill.
     *
     * @param bill the bill object
     * @return the calculated points
     */
    private int calculatePoints(Bill bill) {
        double sum = 0;
        for (BillItem billItem : bill.getBillItemList()) {
            sum += billItem.getPricing().getPrice() * billItem.getQuantity();
        }
        return (int) (sum / 100000);
        /* Retrieves the daily sales revenue for a specific date.
         *
         * @param date The date for which to retrieve the daily sales revenue.
         * @return A ResponseEntity containing the daily sales revenue.
         * @author ThanhTT
         */
    }
        @GetMapping("/revenue/daily")
        public ResponseEntity<Double> getDailySalesRevenue (@RequestParam("date") LocalDate date){
            Double dailyRevenue = billService.getDailySalesRevenue(date);
            if (dailyRevenue == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
        public ResponseEntity<Double> getMonthlySalesRevenue (@RequestParam("month") YearMonth yearMonth){
            Double monthlyRevenue = billService.getMonthlySalesRevenue(yearMonth);
            if (monthlyRevenue == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
        public ResponseEntity<Map<Integer, Double>> getDailySalesRevenueForMonth (@RequestParam("month") YearMonth
        yearMonth){
            Map<Integer, Double> dailyRevenue = billService.getDailySalesRevenueForMonth(yearMonth);
            if (dailyRevenue.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(dailyRevenue, HttpStatus.OK);

        }
}
