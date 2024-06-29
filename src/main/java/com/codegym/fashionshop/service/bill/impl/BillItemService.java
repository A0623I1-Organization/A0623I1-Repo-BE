package com.codegym.fashionshop.service.bill.impl;

import com.codegym.fashionshop.entities.BillItem;
import com.codegym.fashionshop.repository.bill.IBillItemRepository;
import com.codegym.fashionshop.service.bill.IBillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BillItemService implements IBillItemService {
    @Autowired
    private IBillItemRepository repository;
    @Override
    public List<BillItem> findAll() {
        return repository.findAll();
    }

    @Override
    public BillItem findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void save(BillItem billItem) {
        repository.save(billItem);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
