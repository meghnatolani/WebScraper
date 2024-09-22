import org.junit.Test;
import org.propertyfinder.scraper.ContentScraper;
import org.propertyfinder.scraper.HTMLScraper;
import org.propertyfinder.scraper.JSONScraper;
import org.propertyfinder.scraper.ScraperFactory;

import static org.junit.Assert.*;

public class ScraperFactoryTest {

    // Test if ScraperFactory returns JSONScraper for a .json URL
    @Test
    public void testGetScraper_JSONScraper() {
        ContentScraper scraper = ScraperFactory.getScraper("http://example.com/data.json");
        assertTrue(scraper instanceof JSONScraper);
    }

    // Test if ScraperFactory returns HTMLScraper for a .html URL
    @Test
    public void testGetScraper_HTMLScraper() {
        ContentScraper scraper = ScraperFactory.getScraper("http://example.com/page.html");
        assertTrue(scraper instanceof HTMLScraper);
    }

    // Test if ScraperFactory throws an exception for unsupported URL types
    @Test(expected = IllegalArgumentException.class)
    public void testGetScraper_UnknownType() {
        ScraperFactory.getScraper("http://example.com/file.txt");
    }
}
