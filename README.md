# CLI based web scraper for static eshops - single page product listings

## How to run

- Clone the project
- Build the project using Maven
- Run the StaticEshopScraper main function

## User input arguments

- URL = URL of the scraped website (https://www.alza.cz/televize/18849604.htm)
- Product css class - class name of the product outer tag (for <div class="product"> the proper input is product; exclude quotes!)
- Is name in a unique tag? - can the name be identified based on the unique tag (eg. <h3>) - else enter className
- Is price in a unique tag? - can the price be identified based on the unique tag (eg. <strong>) - else enter className
- Image src attribute - usually src; some sites use different attribute (eg. data-src)
