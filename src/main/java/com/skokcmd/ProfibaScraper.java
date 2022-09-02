package com.skokcmd;

import com.skokcmd.service.ScraperService;

import java.io.IOException;
import java.util.List;

public class ProfibaScraper {
  public static void main(String[] args) throws IOException {
    run();
  }

  public static void run() throws IOException {

    String url = "https://www.parkove-lavicky.cz/nabidka-mestskeho-mobiliare/?order=0&ppt=500";

    ScraperService scraperService = new ScraperService();
    List<Product> products = scraperService.getProductItemsForUrl(url, "item");

    products.forEach(product -> System.out.println(product.toString()));
  }
}
