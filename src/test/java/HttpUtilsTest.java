import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.propertyfinder.utils.HttpUtils;

import java.io.IOException;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpUtils.class)
public class HttpUtilsTest {

    @Test
    public void testSendGetRequest_Success() throws Exception {
        String url = "http://example.com";
        String expectedResponse = "Mocked response content";

        // Mocking the static method sendGetRequest in HttpUtils
        try (MockedStatic<HttpUtils> mockedHttpUtils = Mockito.mockStatic(HttpUtils.class)) {
            mockedHttpUtils.when(() -> HttpUtils.sendGetRequest(url)).thenReturn(expectedResponse);

            // Test sendGetRequest method
            String response = HttpUtils.sendGetRequest(url);
            assertEquals(expectedResponse, response);
            mockedHttpUtils.verify(() -> HttpUtils.sendGetRequest(url));
        }
    }

    @Test(expected = IOException.class)
    public void testSendGetRequest_Failure() throws Exception {
        String url = "http://example.com";

        try (MockedStatic<HttpUtils> mockedHttpUtils = Mockito.mockStatic(HttpUtils.class)) {
            mockedHttpUtils.when(() -> HttpUtils.sendGetRequest(url)).thenThrow(new IOException("HTTP error"));

            HttpUtils.sendGetRequest(url);
        }
    }
}
