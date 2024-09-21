import org.junit.Before;
import org.junit.Test;
import org.propertyfinder.utils.RateLimiter;

import static org.junit.Assert.*;

public class RateLimiterTest {

    private RateLimiter rateLimiter;

    @Before
    public void setUp() {
        rateLimiter = new RateLimiter(5);  // 5 requests per second
    }

    @Test
    public void testProcessRequest() {
        long startTime = System.currentTimeMillis();
        rateLimiter.processRequest();
        long endTime = System.currentTimeMillis();

        long elapsedTime = endTime - startTime;
        assertTrue("The rate limiter should pause for the specified interval.", elapsedTime >= 200);  // 1 second / 5 requests = 200ms
    }
}
