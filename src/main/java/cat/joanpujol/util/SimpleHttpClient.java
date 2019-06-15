package cat.joanpujol.util;

import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;
import java.io.IOException;


@ApplicationScoped
public class SimpleHttpClient {
    private static Logger logger = LoggerFactory.getLogger(SimpleHttpClient.class);

    private OkHttpClient client;
    @PostConstruct
    void init() {
        client = new OkHttpClient();
    }

    public Single<String> getContent(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        PublishSubject<String> subject = PublishSubject.create();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                logger.error("Error getting {}",url,e);
                subject.onError(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                subject.onNext(response.body().string());
                subject.onComplete();
            }
        });
        return subject.singleOrError();
    }

    public String getContentSync(String url) {
        throw new RuntimeException("Unimplemented");
    }

    public void getContentCallback(String url, SimpleResultCallback<String> result) {
        throw new RuntimeException("Unimplemented");
    }

}
