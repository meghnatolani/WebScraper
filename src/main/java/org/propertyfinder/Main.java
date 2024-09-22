package org.propertyfinder;

import org.propertyfinder.service.WebScraper;
import org.propertyfinder.utils.URLExtractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        try {
            String websiteUrl = "http://localhost:8000";

            // Fetch all the Pages present in a Website.
            URLExtractor urlExtractor = new URLExtractor();
            List<String> urls = new ArrayList<>(urlExtractor.fetchLinks(websiteUrl));

            // Number of threads to use for scraping.
            int threadPoolSize = 10;
            int requestsPerSecond = 50;

            // Initialize the WebScraper service with the defined thread pool size
            WebScraper webScraper = new WebScraper(threadPoolSize, requestsPerSecond);

            // Scrape the list of URLs and collect results as a map
            Map<String, String> scrapedDataMap = webScraper.scrapeUrls(urls);

            // Print results
            scrapedDataMap.forEach((url, data) ->
                    System.out.println("Data from " + url + ": " + data)
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
