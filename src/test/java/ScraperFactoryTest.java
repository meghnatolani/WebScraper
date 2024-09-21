import org.junit.jupiter.api.Test;
import org.propertyfinder.scraper.ContentScraper;
import org.propertyfinder.scraper.HTMLScraper;
import org.propertyfinder.scraper.JSONScraper;
import org.propertyfinder.scraper.ScraperFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScraperFactoryTest {

    @Test
    public void testGetScraperForJson() {
        ContentScraper scraper = ScraperFactory.getScraper("http://example.com/entity-slug-123.json");
        assertTrue(scraper instanceof JSONScraper);
    }

    @Test
    public void testGetScraperForHtml() {
        ContentScraper scraper = ScraperFactory.getScraper("http://example.com/product-slug-123.html");
        assertTrue(scraper instanceof HTMLScraper);
    }

}
