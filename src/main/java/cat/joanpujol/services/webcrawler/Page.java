package cat.joanpujol.services.webcrawler;

import java.util.List;

public class Page {
    private final String url;
    private final String content;
    private final List<String> links;

    public Page(String url, String content, List<String> links) {
        this.url = url;
        this.content = content;
        this.links = links;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public List<String> getLinks() {
        return links;
    }
}
