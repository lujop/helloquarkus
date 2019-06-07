package cat.joanpujol;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.smallrye.reactive.converters.ReactiveTypeConverter;
import io.smallrye.reactive.converters.Registry;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Path("/async")
public class HelloResourceReactive {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hello")
    public CompletionStage<String> oldUglyHello(@QueryParam("name") @DefaultValue("Joan") String name) {
        CompletableFuture completableFuture = new CompletableFuture();
        new Thread( () -> completableFuture.complete("Hi " + name)).start();
        return completableFuture;
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hello2")
    public CompletionStage<String> hello(@QueryParam("name") @DefaultValue("Joan") String name) {
        CompletableFuture future = CompletableFuture.supplyAsync( () -> "Hi "+ name);
        return future;
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hello3")
    public CompletionStage<String> helloRxJava(@QueryParam("name") @DefaultValue("Joan") String name) {
        Single<String> result = Single.just("Hi " + name);

        //Conversion, should be automatic...
        ReactiveTypeConverter<Single> converter = Registry.lookup(Single.class).orElseThrow(() -> new RuntimeException("No converter found"));
        return converter.toCompletionStage(result);
    }
}
