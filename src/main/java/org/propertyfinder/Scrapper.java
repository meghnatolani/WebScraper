//package org.propertyfinder;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.propertyfinder.scraper.ScraperFactory;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.*;
//
//public class Scrapper {
//    private final String baseUrl;
//    private final RateLimiter rateLimiter;
//    private final ExecutorService executorService;
//
//    public Scrapper(String baseUrl, int rateLimit, int threadCount) {
//        this.baseUrl = baseUrl;
//        this.rateLimiter = new RateLimiter(rateLimit, threadCount);
//        this.executorService = Executors.newFixedThreadPool(threadCount);
//    }
//
//    public List<String> fetchAll(List<String> identifiers, ScraperFactory strategy) throws InterruptedException {
//        List<Future<String>> futures = new ArrayList<>();
//
//        for (String identifier : identifiers) {
//            futures.add(executorService.submit(() -> fetchData(strategy, identifier)));
//        }
//
//        // Collect results
//        List<String> results = new ArrayList<>();
//        for (Future<String> future : futures) {
//            try {
//                String result = future.get();
//                if (result != null) {
//                    results.add(result);
//                }
//            } catch (ExecutionException e) {
//                System.err.println("Failed to fetch data: " + e.getCause().getMessage());
//            }
//        }
//
//        return results;
//    }
//
//    private String fetchData(ScraperFactory strategy, String identifier) throws Exception {
//        String path = strategy.fetchPath(baseUrl, identifier);
//        return fetchData(path);
//    }
//
//    private String fetchData(String path) {
//        try {
//            rateLimiter.await();
//            try (CloseableHttpClient client = HttpClients.createDefault()) {
//                HttpGet request = new HttpGet(baseUrl + path);
//                request.setHeader("User-Agent", "Mozilla/5.0 ...");
//
//                try (CloseableHttpResponse response = client.execute(request)) {
//                    return EntityUtils.toString(response.getEntity());
//                }
//            }
//        } catch (IOException | InterruptedException e) {
//            System.err.println("Error fetching data from " + path + ": " + e.getMessage());
//            return null;
//        }
//    }
//
//    public void shutdown() {
//        executorService.shutdown();
//        try {
//            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
//                executorService.shutdownNow();
//            }
//        } catch (InterruptedException e) {
//            executorService.shutdownNow();
//        }
//    }
//}
