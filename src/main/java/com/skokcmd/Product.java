package com.skokcmd;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Product {
  private String imgUrl;
  private String name;
  private String priceString;
}
