package org.propertyfinder.service;

import org.propertyfinder.model.ScrapedData;
import org.propertyfinder.scraper.ContentScraper;
import org.propertyfinder.scraper.ScraperFactory;
import org.propertyfinder.utils.HttpUtils;
import org.propertyfinder.utils.RateLimiter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class WebScraper {
    private final ExecutorService executorService;
    private final RateLimiter rateLimiter;

    public WebScraper(int threads, int requestsPerSecond) {
        this.executorService = Executors.newFixedThreadPool(threads);
        this.rateLimiter = new RateLimiter(requestsPerSecond);
    }


    public Map<String, String> scrapeUrls(List<String> urls) {
        Map<String, Future<ScrapedData>> futuresMap = new ConcurrentHashMap<>();

        for (String url : urls) {
            Future<ScrapedData> future = executorService.submit(() -> {
                rateLimiter.processRequest();
                return scrapeUrl(url);
            });
            futuresMap.put(url, future);
        }

        shutdownExecutorService();

        Map<String, String> results = new ConcurrentHashMap<>();
        for (Map.Entry<String, Future<ScrapedData>> entry : futuresMap.entrySet()) {
            String url = entry.getKey();
            try {
                ScrapedData data = entry.getValue().get(); // Get the result
                results.put(url, data != null ? data.toString() : "No data scraped");
            } catch (InterruptedException | ExecutionException e) {
                results.put(url, "Error: " + e.getMessage());
            }
        }

        return results;
    }

    /*
     * Scrape URL based on the File Type
     */
    private ScrapedData scrapeUrl(String url) {
        try {
            String content = HttpUtils.sendGetRequest(url);
            ContentScraper scraper = ScraperFactory.getScraper(url);
            return scraper.scrape(content);

        } catch (Exception e) {
            System.err.println("Failed to scrape URL: " + url + " due to: " + e.getMessage());
            return null;
        }
    }


    public void shutdownExecutorService() {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}

