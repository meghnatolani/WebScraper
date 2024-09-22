package org.propertyfinder.scraper;

import org.propertyfinder.model.ScrapedData;

/**
 * Common Interface for Content-type
 * */

public interface ContentScraper {

    ScrapedData scrape(String content);
}