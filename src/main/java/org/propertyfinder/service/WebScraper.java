package org.propertyfinder.service;

import org.propertyfinder.utils.RateLimiter;
import org.propertyfinder.scraper.ContentScraper;
import org.propertyfinder.scraper.ScraperFactory;
import org.propertyfinder.utils.HttpUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WebScraper {
    private final ExecutorService executorService;
    private final RateLimiter rateLimiter;

    public WebScraper(int threads, int requestsPerSecond) {
        this.executorService = Executors.newFixedThreadPool(threads);
        this.rateLimiter = new RateLimiter(requestsPerSecond);
    }

    public void scrapeUrls(List<String> urls) {
        for (String url : urls) {
            executorService.submit(() -> {
                rateLimiter.processRequest();
                scrapeUrl(url);
            });
        }
        shutdownExecutorService();
    }


    /*
     * Scrape URL based on the File Type
     */
    private void scrapeUrl(String url) {
        try {
            String content = HttpUtils.sendGetRequest(url);
            ContentScraper scraper = ScraperFactory.getScraper(url);
            scraper.scrape(content);

        } catch (Exception e) {
            System.err.println("Failed to scrape URL: " + url + " due to: " + e.getMessage());
        }
    }


    private void shutdownExecutorService() {
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

