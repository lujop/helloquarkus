package cat.joanpujol.util;

/**
 * Extends simple result callback to provide multiple results.
 * Using this callback {@link #onResult(Object)} will be invoked for each result until there
 * are no more results that {@link #onFinished()} (Object)} will be called
 * @param <T>
 */
public interface MultipleResultCallback<T> extends SimpleResultCallback<T> {
     void onFinished();
}
