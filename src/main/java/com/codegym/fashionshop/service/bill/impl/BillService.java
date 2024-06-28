package com.codegym.fashionshop.service.bill.impl;

import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.repository.bill.IBillRepository;
import com.codegym.fashionshop.service.bill.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
