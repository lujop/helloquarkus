package cat.joanpujol.services.webcrawler;

import cat.joanpujol.util.SimpleResultCallback;
import cat.joanpujol.util.MultipleResultCallback;
import io.vertx.core.impl.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import java.util.Set;
import java.util.concurrent.Executors;

@Dependent
/**
 * An asynchronous non blocking web webcrawler without using functional reactive operators
 * Not robust neither fully implemented, only to show complexity of not using functional programming and only callbacks
 */
public class AsyncWebcrawler extends BaseWebcrawler {
    private static Logger logger = LoggerFactory.getLogger(AsyncWebcrawler.class);

    /**
     * Visit an url getting it's content following level links
     * @param url Url to retreiv
     * @param level Level of links to follow. 0 to retrieve only only the page, 1 for following page links, 2 for following page links
     *              and links from the first level page links
     * @param callback Callback to receive results
     */
    public void get(String url, int level, MultipleResultCallback<Page> callback) {
        if(level<0) {
            callback.onFinished();
        } else {
            Executors.newSingleThreadExecutor().submit( () -> {
                retrievePage(url, level, callback);
            });
        }
        //What if I want a timeout...
        //What if I want retries...
    }

    private void retrievePage(String url, int level, MultipleResultCallback<Page> callback) {
        simpleHttpClient.getContentCallback(url, new SimpleResultCallback<String>() {
            @Override
            public void onResult(String pagecontent) {
                Page page = parse(url,pagecontent);
                callback.onResult(page);

                Set<String> pendingLinks = new ConcurrentHashSet<>();
                pendingLinks.addAll(page.getLinks());
                for(String link : page.getLinks()) {
                    retrievePage(link,level-1,new MultipleResultCallback<Page>() {
                        @Override
                        public void onFinished() {
                            pendingLinks.remove(link);
                            if(pendingLinks.isEmpty())
                                callback.onFinished();
                        }

                        @Override
                        public void onResult(Page pagecontent) {
                            callback.onResult(page);
                        }

                        @Override
                        public void onError(Exception e) {
                            logger.error("Error",e);
                            pendingLinks.remove(url);
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                logger.error("Error",e);
            }
        });
    }

}
