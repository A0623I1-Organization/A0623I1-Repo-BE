package com.codegym.fashionshop.repository.bill;

import com.codegym.fashionshop.entities.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBillItemRepository extends JpaRepository<BillItem,Long> {
//    @Query()
//    List<BillItem> getAllBillItem();
//    @Query("SELECT bi.itemId, bi.bill, bi.pricing, bi.quantity, bi.customer, bi.promotion FROM BillItem bi WHERE bi.itemId = :id")
//    BillItem getBillItemByBillId(Long id);
//    @Query("SELECT SUM(bi.quantity) FROM BillItem bi JOIN Customer c ON  c.customerId = :customerId")
//    int getTotalQuantityOfBillItemsByCustomerId(Long id);
}
