package com.codegym.fashionshop.service.bill;

import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.repository.bill.IBillRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IBillService  {
    boolean isBillCodeUnique(String billCode);
    void createBillAndUpdatePoints(Bill bill, int pointsToAdd);
    List<Bill> findAll();

}
