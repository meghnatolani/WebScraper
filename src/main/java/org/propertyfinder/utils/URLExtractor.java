package org.propertyfinder.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class URLExtractor {
    public Set<String> fetchLinks(String url) throws IOException {
        Set<String> links = new HashSet<>();
        URL urlContent = new URL(url);
        Document document = Jsoup.parse(urlContent.openStream(), "UTF-8", url.toString());

        // Select all links in the document
        Elements anchorTags = document.select("a[href]");

        for (Element anchor : anchorTags) {
            String link = anchor.attr("abs:href");
            // Filter for JSON or HTML
            if (link.endsWith(".json") || link.endsWith(".html")) {
                links.add(link);
            }
        }

        return links;
    }
}
