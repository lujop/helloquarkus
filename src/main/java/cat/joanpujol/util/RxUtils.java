package cat.joanpujol.util;

import io.reactivex.Single;
import io.smallrye.reactive.converters.ReactiveTypeConverter;
import io.smallrye.reactive.converters.Registry;

import java.util.concurrent.CompletionStage;

public class RxUtils {
    public static <T> CompletionStage<T> convert(Single<T> single) {
        ReactiveTypeConverter<Single> converter = Registry.lookup(Single.class).orElseThrow(() -> new RuntimeException("No converter found"));
        return converter.toCompletionStage(single);
    }
}
