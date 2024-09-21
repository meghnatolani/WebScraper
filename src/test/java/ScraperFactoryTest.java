import org.junit.Test;
import org.propertyfinder.scraper.ContentScraper;
import org.propertyfinder.scraper.HTMLScraper;
import org.propertyfinder.scraper.JSONScraper;
import org.propertyfinder.scraper.ScraperFactory;

import static org.junit.Assert.*;

public class ScraperFactoryTest {

    @Test
    public void testGetScraper_JSONScraper() {
        // Test if ScraperFactory returns JSONScraper for a .json URL
        ContentScraper scraper = ScraperFactory.getScraper("http://example.com/data.json");
        assertTrue(scraper instanceof JSONScraper);
    }

    @Test
    public void testGetScraper_HTMLScraper() {
        // Test if ScraperFactory returns HTMLScraper for a .html URL
        ContentScraper scraper = ScraperFactory.getScraper("http://example.com/page.html");
        assertTrue(scraper instanceof HTMLScraper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetScraper_UnknownType() {
        // Test if ScraperFactory throws an exception for unsupported URL types
        ScraperFactory.getScraper("http://example.com/file.txt");
    }
}
