Web Scrapper
-------------
How it Works :
1. Expects a website URL as input in Main class.
2. Reads all the URLs/Link present in the Website
3. Parses all the URLs, and scrapes all the data present in it.
4. Maps the data to an Object and prints it.

Implementation :
1. MockServer - Mock Server which reads the URL and returns the html page.
   For testing purpose, we have three files present in src/main/resources
2. Main - Calls the demo url, MockServer returns the respective html
3. URLExtractor - Fetches all the links present in HTML file (CSV, HTML for instance)
4. WebScraper - Links are passed to WebScraper, and all the files are scraped.
5. RateLimiter - Ensures that fixed number of requests are processed within a second.
6. ScraperFactory - Factory Class to determine the type of Content from page URL and return the corresponding ContentScrapper.
7. JSONScraper / HTMLScraper - Type of ContentScraper, which scrapes dummy data given in the PDF.
   This can be further extended to read all data( ex. paragraphs, div in HTML File)
8. Entity / Product - Sample Model class to map the content present in the Question.
   Can be further extended to map different types of content.
9. HttpUtils - Utils class to do a GET call to URL's using HTTPClient

To Run :
1. Run the Mock server
2. Run the Main Class

Unit Tests :
1. HttpUtilsTest
2. RateLimiterTest
3. ScraperFactoryTest
4. WebScraperTest

To Do:
1. Improve the Entity/Product class to map all the content of files.
   Also, Can think of making it a common Object which maps the data, which can be processed further.
2. WAF Rules can be further added - Like, set all relevant headers that a browser would send.
3. Retry Logic - Incase if a Get call fails, Retry Logic can be added.

