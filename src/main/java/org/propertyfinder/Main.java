package org.propertyfinder;

import org.propertyfinder.service.WebScraper;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<String> urls = List.of(
                "http://mockserver.com/page1.html",
                "http://example.com/page2.html"
        );

        // Number of threads to use for scraping.
        int threadPoolSize = 10;
        int requestsPerSecond = 50;

        // Initialize the WebScraper service with the defined thread pool size
        WebScraper webScraper = new WebScraper(threadPoolSize, requestsPerSecond);

        // Scrape the list of URLs
        webScraper.scrapeUrls(urls);
    }
}
