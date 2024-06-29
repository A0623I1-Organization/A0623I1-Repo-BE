package com.codegym.fashionshop.repository.bill;

import com.codegym.fashionshop.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillRepository extends JpaRepository<Bill,Long> {
}
