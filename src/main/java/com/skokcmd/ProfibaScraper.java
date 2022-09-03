package com.skokcmd;

import com.skokcmd.service.EshopScraperService;
import com.skokcmd.service.IEshopScraperService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ProfibaScraper {
    public static void main(String[] args) throws IOException {
        run();
    }

    public static void run() throws IOException {
//      "https://www.parkove-lavicky.cz/nabidka-mestskeho-mobiliare/?order=0&ppt=500";

        Scanner scanner = new Scanner(System.in);
        IEshopScraperService eshopScraperService = getEshopScraperBasedOnUserInput(scanner);

        List<Product> products = getProductsBasedOnProductClassUserInput(scanner, eshopScraperService);

        products.forEach(product -> System.out.println(product.toString()));
    }

    public static IEshopScraperService getEshopScraperBasedOnUserInput(Scanner scanner) throws IOException {
        System.out.print("Enter url for scraping: ");
        String url = scanner.nextLine();

        return new EshopScraperService(url);
    }

    public static List<Product> getProductsBasedOnProductClassUserInput(
            Scanner scanner, IEshopScraperService eshopScraperService) throws IOException {

        System.out.print("Enter product css class: ");
        String productClass = scanner.nextLine();

        return eshopScraperService.getItemsByClass(productClass);
    }

}
