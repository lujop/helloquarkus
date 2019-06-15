package cat.joanpujol.util;

public interface SimpleResultCallback<T> {
     /**
      * Callback to receive the result when it's correct
      */
     void onResult(T result);

     /**
      * Callback to receive an error if any
      */
     void onError(Exception e);
}
