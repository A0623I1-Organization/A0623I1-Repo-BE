package com.codegym.fashionshop.service.bill;

import com.codegym.fashionshop.entities.Bill;
import com.codegym.fashionshop.repository.bill.IBillRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface IBillService extends IGeneralService<Bill> {
    boolean isBillCodeUnique(String billCode);
}
