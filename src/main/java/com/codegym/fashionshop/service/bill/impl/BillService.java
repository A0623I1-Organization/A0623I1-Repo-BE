package com.codegym.fashionshop.service.bill.impl;

import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.repository.bill.IBillRepository;
import com.codegym.fashionshop.service.bill.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
