import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.propertyfinder.scraper.ContentScraper;
import org.propertyfinder.scraper.ScraperFactory;
import org.propertyfinder.service.WebScraper;
import org.propertyfinder.utils.HttpUtils;
import org.propertyfinder.utils.RateLimiter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpUtils.class, ScraperFactory.class})
public class WebScraperTest {

    @Mock
    private RateLimiter rateLimiter;

    @Mock
    private ContentScraper contentScraper;

    @Mock
    private ExecutorService executorService;

    @InjectMocks
    private WebScraper webScraper = new WebScraper(2, 5);

    @Before
    public void setUp() {
        webScraper = new WebScraper(2, 5);
    }

    @Test
    public void testScrapeUrl_Success() throws Exception {
        // Mocking the behavior of HttpUtils and ScraperFactory
        String url = "http://example.com";
        String mockedContent = "Mocked content";

        when(HttpUtils.sendGetRequest(url)).thenReturn(mockedContent);
        when(ScraperFactory.getScraper(url)).thenReturn(contentScraper);

        // Run the method to test
        webScraper.scrapeUrls(Collections.singletonList(url));

        // Verifying that the methods were called as expected
        verify(rateLimiter, never()).processRequest();
        verify(contentScraper).scrape(mockedContent);
    }

    @Test
    public void testScrapeUrls_MultipleUrls() throws IOException, InterruptedException {
        String url1 = "http://example1.com";
        String url2 = "http://example2.com";
        List<String> urls = Arrays.asList(url1, url2);

        when(ScraperFactory.getScraper(anyString())).thenReturn(contentScraper);
        when(ScraperFactory.getScraper(anyString())).thenReturn(contentScraper);
        when(HttpUtils.sendGetRequest(anyString())).thenReturn("Mocked content");

        // Execute the scrapeUrls method
        webScraper.scrapeUrls(urls);

        // Verify that executor service submitted tasks
        verify(executorService, times(2)).submit(any(Runnable.class));
    }

    @Test
    public void testShutdownExecutorService() throws InterruptedException {
        // Invoke the shutdownExecutorService method
        webScraper.shutdownExecutorService();

        // Verifying executor service shutdown
        verify(executorService).shutdown();
        verify(executorService).awaitTermination(60, TimeUnit.SECONDS);
    }
}
