package org.propertyfinder.scraper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.propertyfinder.model.Entity;

/**
 * JSON Scraper
 * */

public class JSONScraper implements ContentScraper {
    @Override
    public void scrape(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(content);
            String title = jsonNode.get("title").asText();
            Entity entity = new Entity(title);
            System.out.println(entity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
