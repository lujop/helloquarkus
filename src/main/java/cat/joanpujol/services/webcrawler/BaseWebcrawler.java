package cat.joanpujol.services.webcrawler;

import cat.joanpujol.util.SimpleHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class BaseWebcrawler {
    private static Logger logger = LoggerFactory.getLogger(BaseWebcrawler.class);

    @Inject
    SimpleHttpClient simpleHttpClient;

    protected final Page parse(String url, String pageContent) {
        Document parsed = Jsoup.parse(pageContent);

        List<String> links = new ArrayList<>();
        Elements elements = parsed.select("a[href]");
        for (Element element : elements) {
            String link = element.attr("href");
            if (link != null && (link.startsWith("http") || link.startsWith("https"))) {
                links.add(link);
            }
        }
        logger.info("Parsed {}. It has {} links",url,links.size());
        return new Page(url, pageContent, links);
    }
}
