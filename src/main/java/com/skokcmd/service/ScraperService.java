package com.skokcmd.service;

import com.skokcmd.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScraperService {

  public List<Product> getProductItemsForUrl(String url, String productTagClass)
      throws IOException {

    Document document = Jsoup.connect(url).get();
    Elements items = document.getElementsByClass(productTagClass);

    return convertFoundItemsToProducts(items, "h3", "price-line", "strong");
  }

  private List<Product> convertFoundItemsToProducts(
      Elements foundItems, String nameTag, String priceClass, String priceInnerTag) {

    List<Product> productList = new ArrayList<>();

    if (foundItems == null || foundItems.isEmpty()) {
      return productList;
    }

    foundItems.forEach(
        // for each item get name, imgSrc, price -> set it to Product object -> add to product list
        item -> {
          String name = item.getElementsByTag(nameTag).first().text();
          String imgSrc = item.getElementsByTag("img").first().attr("src");
          String price =
              item.getElementsByClass(priceClass)
                  .first()
                  .getElementsByTag(priceInnerTag)
                  .first()
                  .text();

          Product product = new Product();
          product.setName(name);
          product.setImgUrl(imgSrc);
          product.setPriceString(price);

          productList.add(product);
        });

    return productList;
  }
}
