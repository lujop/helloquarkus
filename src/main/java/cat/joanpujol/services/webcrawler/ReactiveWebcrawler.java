package cat.joanpujol.services.webcrawler;

import io.reactivex.Observable;

import javax.enterprise.context.Dependent;
import java.util.concurrent.TimeUnit;

@Dependent
/**
 * A non blocking web crawler using functional reactive operators
 */
public class ReactiveWebcrawler extends BaseWebcrawler {

    /**
     * Visit an url getting it's content following level links
     * @param url Url to retrieve
     * @param level Level of links to follow. 0 to retrieve only only the page, 1 for following page links, 2 for following page links
     *              and links from the first level page links
     * @return Observable with pages results
     */
    public Observable<Page> get(String url, int level) {
        if (level < 0) {
            return Observable.empty();
        } else {
            return simpleHttpClient.getContent(url)
                    .map(content -> parse(url, content))
                    .flatMapObservable(page -> Observable.concat(Observable.just(page),Observable.fromIterable(page.getLinks())
                            .flatMap(link -> get(link,level-1))))
                    .retry(2)
                    .timeout(3, TimeUnit.MINUTES)
                    .onErrorResumeNext(Observable.empty());
        }
    }
}
