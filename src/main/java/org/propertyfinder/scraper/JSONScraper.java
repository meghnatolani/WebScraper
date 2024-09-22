package org.propertyfinder.scraper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.propertyfinder.model.Entity;

/**
 * JSON Scraper
 * */

public class JSONScraper implements ContentScraper {

    @Override
    public Entity scrape(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(content);
            String title = jsonNode.get("title").asText();
            return new Entity(title);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
