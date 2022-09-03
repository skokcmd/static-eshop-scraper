package com.skokcmd;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Product {
    private String imgUrl;
    private String name;
    private BigDecimal price;
}
