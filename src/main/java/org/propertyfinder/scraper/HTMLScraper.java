package org.propertyfinder.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.propertyfinder.model.Product;
import org.propertyfinder.model.ScrapedData;

/**
 * HTML Scraper
 * */

public class HTMLScraper implements ContentScraper {

    @Override
    public ScrapedData scrape(String content) {
        Document doc = Jsoup.parse(content);
        String productId = doc.select("h1").attr("data-id");
        String title = doc.select("h1").text();
        return new Product(productId, title);
    }
}
