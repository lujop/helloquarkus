package cat.joanpujol.services.webcrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import java.util.*;

@Dependent
/**
 * A synchronous blocking web webcrawler
 * Not fully implemented, only to show blocking syncrhonous one is the simpliest solution
 */
public class SynchronousWebcrawler extends BaseWebcrawler {
    private static Logger logger = LoggerFactory.getLogger(SynchronousWebcrawler.class);


    /**
     * Visit an url getting it's content following level links
     * @param url Url to retrieve
     * @param level Level of links to follow. 0 to retrieve only only the page, 1 for following page links, 2 for following page links
     *              and links from the first level page links
     * @return List of pages retrieved
     */
    public List<Page> get(String url, int level) {
        if(level<0) {
            return Collections.emptyList();
        } else {
            List<Page> result = new ArrayList<>();
            String content = simpleHttpClient.getContentSync(url);
            Page page = parse(url,content);
            result.add(page);
            for(String link : page.getLinks()) {
                result.addAll(get(link,level-1));
            }
            return result;
        }
    }
}
