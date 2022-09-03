package com.skokcmd.service;

import com.skokcmd.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


public class EshopScraperService implements IEshopScraperService {

    private String url;
    private Document document;

    private Scanner scanner;

    private final String UNIQUE_NAME_TAG_KEY = "nameHtmlTag";
    private final String NAME_CLASSNAME_KEY = "nameClassName";
    private final String UNIQUE_PRICE_TAG_KEY = "priceHtmlTag";
    private final String PRICE_CLASSNAME_KEY = "priceClassName";
    private final String IMG_SRC_ATTRIBUTE_KEY = "imgSrcAttr";
    private final String IMG_TAG = "img";

    public EshopScraperService(String url) throws IOException {
        this.url = url;
        this.document = Jsoup.connect(this.url).get();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Sets name key & value based on user input
     *
     * @param valuesInputPair
     */
    private void getInputForProductName(Map<String, String> valuesInputPair) {
        String questionForUser = "Is name in a unique tag (eg. <h4>)? [y/n]: ";

        getUniqueTagOrClassProductInput(
                valuesInputPair, questionForUser, this.UNIQUE_NAME_TAG_KEY, this.NAME_CLASSNAME_KEY);
    }

    /**
     * Sets image attribute key & value based on user input
     *
     * @param valuesInputPair
     */
    private void getInputForProductImgAttribute(Map<String, String> valuesInputPair) {
        System.out.print("Enter image src attribute: ");
        valuesInputPair.put(this.IMG_SRC_ATTRIBUTE_KEY, this.scanner.nextLine());
    }

    /**
     * Sets price key & value based on user input
     *
     * @param valuesInputPair
     */
    private void getInputForProductPrice(Map<String, String> valuesInputPair) {
        String questionForUser = "Is price in a unique tag (eg. <strong>)? [y/n]: ";
        getUniqueTagOrClassProductInput(
                valuesInputPair, questionForUser, this.UNIQUE_PRICE_TAG_KEY, this.PRICE_CLASSNAME_KEY);
    }

    /**
     * Sets key & value to the given mapped based on if unique tag or class name (user input)
     *
     * @param valuesInputPair
     * @param questionForUser printed to console
     * @param uniqueTagKey    map key
     * @param classKey        map key
     */
    private void getUniqueTagOrClassProductInput(
            Map<String, String> valuesInputPair, String questionForUser, String uniqueTagKey, String classKey) {

        System.out.print(questionForUser);
        if (this.scanner.nextLine().equals("y")) {
            System.out.print("Enter the unique tag: ");
            valuesInputPair.put(uniqueTagKey, this.scanner.nextLine());
        } else {
            System.out.print("Enter the css class: ");
            valuesInputPair.put(classKey, this.scanner.nextLine());
        }
    }

    /**
     * Handles user input
     *
     * @return map of user input keys and their values
     */
    private Map<String, String> getUserInputForProduct() {
        Map<String, String> neededValuesInputPair = new HashMap<>();

        getInputForProductImgAttribute(neededValuesInputPair);
        getInputForProductName(neededValuesInputPair);
        getInputForProductPrice(neededValuesInputPair);

        return neededValuesInputPair;
    }

    /**
     * Gets list of products based on the given product class name
     *
     * @param productClassName
     * @return list of products
     */
    @Override
    public List<Product> getItemsByClass(String productClassName) {
        Elements items = document.getElementsByClass(productClassName);
        Map<String, String> userArgs = getUserInputForProduct();

        return convertFoundItemsToProducts(items, userArgs);
    }

    /**
     * Converts found items to list of products based on user input
     *
     * @param foundItems list of elements
     * @param userArgs   user input
     * @return list of products
     */
    private List<Product> convertFoundItemsToProducts(Elements foundItems, Map<String, String> userArgs) {
        List<Product> productList = new ArrayList<>();

        if (foundItems == null || foundItems.isEmpty()) {
            return productList;
        }

        foundItems.forEach(
                // for each item get name, imgSrc, price -> set it to Product object -> add to product list
                item -> {
                    // get image src attribute based on userArgs
                    String imgSrc = getItemImgSrc(item, userArgs.get(this.IMG_SRC_ATTRIBUTE_KEY));

                    // get name based on userArgs
                    String name = getItemTextFromUniqueTagOrClassName(
                            item, userArgs, this.UNIQUE_NAME_TAG_KEY, this.NAME_CLASSNAME_KEY);

                    // get price based on userArgs
                    BigDecimal price = new BigDecimal(
                            getItemTextFromUniqueTagOrClassName(
                                    item, userArgs, this.UNIQUE_PRICE_TAG_KEY, this.PRICE_CLASSNAME_KEY).
                                    replaceAll("[^0-9]+", ""));

                    Product product = new Product();
                    product.setName(name);
                    product.setImgUrl(imgSrc);
                    product.setPrice(price);

                    productList.add(product);
                });

        return productList;
    }

    private String getItemTextFromUniqueTagOrClassName(
            Element item, Map<String, String> userArgs, String uniqueTagKey, String classNameKey) {

        if (userArgs.containsKey(uniqueTagKey)) {
            return getItemTextByUniqueTag(item, userArgs.get(uniqueTagKey));
        }
        return getItemTextFromByClassName(item, userArgs.get(classNameKey));
    }

    @Override
    public String getItemTextByUniqueTag(Element item, String tag) {
        return item.getElementsByTag(tag).first().text().trim();
    }

    @Override
    public String getItemTextFromByClassName(Element item, String className) {
        return item.getElementsByClass(className).first().text();
    }

    @Override
    public String getItemImgSrc(Element item, String imgSrcAtr) {
        return item.getElementsByTag(this.IMG_TAG).first().attr(imgSrcAtr).trim();
    }
}
