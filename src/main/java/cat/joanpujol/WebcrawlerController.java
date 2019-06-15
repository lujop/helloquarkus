package cat.joanpujol;

import cat.joanpujol.services.webcrawler.Page;
import cat.joanpujol.services.webcrawler.ReactiveWebcrawler;
import cat.joanpujol.util.RxUtils;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Path("/async")
public class WebcrawlerController {

    private static Logger logger = LoggerFactory.getLogger(WebcrawlerController.class);

    @Inject
    ReactiveWebcrawler webcrawler;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("webcrawler" +
            "")
    public CompletionStage<String> get(@QueryParam("url") String url, @QueryParam("level") int level) {
        logger.info("Starting");

        Single<String> result = webcrawler.get(url,level)
                .doOnNext(page -> logger.info("Processed page {}",page.getUrl()))
                .toList() // Join all results into a list (It's not blocking)
                .map(WebcrawlerController::pageList2String) // Render pages list to string to send to browser
                .doOnTerminate(()->logger.info("FINISHED"));

        logger.info("Returning observable to server, retriving will continue asynchronously");

        return RxUtils.convert(result);
    }

    /**
     * Render a list of pages as a string with page url in each line
     */
    private static String pageList2String(List<Page> pages) {
        return pages.stream().map(page -> page.getUrl())
                .collect(Collectors.joining("\n"));
    }

}
