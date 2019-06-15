package cat.joanpujol;

import cat.joanpujol.services.HiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {
    private static Logger logger = LoggerFactory.getLogger(HelloResource.class);
    @Inject
    HiService hiService;


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String message =  hiService.getHiMessage();
        logger.info("Hi global count is = {}  request count is= {}",hiService.getCount(),hiService.getRequestCount());
        return message;
    }

}