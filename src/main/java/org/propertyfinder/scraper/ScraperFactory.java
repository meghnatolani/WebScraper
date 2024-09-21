package org.propertyfinder.scraper;

/**
 * Factory Class to determine the type of Content
 * */

public class ScraperFactory {
    public static ContentScraper getScraper(String url) {
        if (url.endsWith(".json")) {
            return new JSONScraper();
        } else if (url.endsWith(".html")) {
            return new HTMLScraper();
        }
        throw new IllegalArgumentException("No scraper found for this URL type");
    }
}
