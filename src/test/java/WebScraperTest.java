import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.propertyfinder.scraper.ContentScraper;
import org.propertyfinder.scraper.HTMLScraper;
import org.propertyfinder.scraper.ScraperFactory;
import org.propertyfinder.service.WebScraper;
import org.propertyfinder.utils.HttpUtils;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpUtils.class, ScraperFactory.class}) // Prepare the classes with static methods
@SuppressStaticInitializationFor({"HttpUtils"}) // fully qualified name in here
public class WebScraperTest {

    private WebScraper webScraper;

    @Before
    public void setUp() {
        webScraper = new WebScraper(10, 5); // Instantiate the WebScraper
        Whitebox.setInternalState(HttpUtils.class, "httpClient", (HttpClient) null);
    }

    @Test
    public void testScrapeUrl_Success() throws Exception {
        String url = "http://example.com/product1";
        List<String> urls = List.of(url);
        String expectedResponse = "<html><div><product data-id=\"123\"></product></div></html>";

        PowerMockito.mockStatic(HttpUtils.class);
        PowerMockito.when(HttpUtils.sendGetRequest(any())).thenAnswer((Answer<String>) response -> expectedResponse);

        ContentScraper mockScraper = PowerMockito.mock(HTMLScraper.class);
        PowerMockito.mockStatic(ScraperFactory.class);
        PowerMockito.when(ScraperFactory.getScraper(any())).thenAnswer((Answer<ContentScraper>)mock -> mockScraper);

        // Mock the static method HttpUtils.sendGetRequest
//        PowerMockito.when(HttpUtils.sendGetRequest(Mockito.anyString()))
//                .thenAnswer(new Answer<String>() {
//                    @Override
//                    public String answer(InvocationOnMock invocation) {
//                        String url = invocation.getArgument(0);
//                        return "Mocked content for " + url;
//                    }
//                });
//
//        // Mock the static method ScraperFactory.getScraper
//        PowerMockito.when(ScraperFactory.getScraper(Mockito.anyString()))
//                .thenAnswer(new Answer<ContentScraper>() {
//                    @Override
//                    public ContentScraper answer(InvocationOnMock invocation) {
//                        String url = invocation.getArgument(0);
//                        if (url.contains("product")) {
//                            return new HTMLScraper();
//                        } else {
//                            return new JSONScraper();
//                        }
//                    }
//                });

        // Call the method under test
        Map<String, String> result = webScraper.scrapeUrls(urls);

        // Verify results
        Map<String, String> expected = new HashMap<>();
        expected.put("http://example.com/product1", "Mock content");
        expected.put("http://example.com/product2", "Mock content");

        assertEquals(expected, result);
    }

    @Test
    public void testScrapeUrls_Success_HTMLScraper() throws Exception {
        List<String> urls = Arrays.asList("http://example.com/product1");

        // Mocking HttpUtils.sendGetRequest
        PowerMockito.when(HttpUtils.sendGetRequest(Mockito.anyString()))
                .thenAnswer((Answer<String>) invocation -> {
                    String url = invocation.getArgument(0);
                    return "Mocked content for " + url;
                });

        // Mocking ScraperFactory.getScraper to return HTMLScraper
        PowerMockito.when(ScraperFactory.getScraper(Mockito.anyString()))
                .thenReturn(new HTMLScraper());

        // Call the method under test
        Map<String, String> result = webScraper.scrapeUrls(urls);

        // Expected results for HTMLScraper
        Map<String, String> expected = new HashMap<>();
        expected.put("http://example.com/product1", "Mocked content for http://example.com/product1");

        // Verify results
        assertEquals(expected, result);
    }
}
