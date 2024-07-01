package com.codegym.fashionshop.controller.product;

import com.codegym.fashionshop.entities.Color;
import com.codegym.fashionshop.exceptions.HttpExceptions;
import com.codegym.fashionshop.service.product.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/color")
@CrossOrigin("*")
public class ColorRestController {
    @Autowired
    private IColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> getColor() {
        List<Color> colors = colorService.findAllColor();
        if (colors.isEmpty()) {
            throw new HttpExceptions.NotFoundException("Không tìm thấy thông tin màu sắc");
        }
        return new ResponseEntity<>(colors,HttpStatus.OK);
    }
}
