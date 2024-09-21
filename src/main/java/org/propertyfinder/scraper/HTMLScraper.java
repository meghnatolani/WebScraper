package org.propertyfinder.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.propertyfinder.model.Product;

/**
 * HTML Scraper
 * */

public class HTMLScraper implements ContentScraper {
    @Override
    public void scrape(String content) {
        Document doc = Jsoup.parse(content);
        // Assuming a div with class "product" and data-id
        String productId = doc.select("div.product").attr("data-id");
        String title = doc.select("div.product").text();
        Product product = new Product(productId, title);
        System.out.println(product);
    }
}
