package cat.joanpujol.services;

import javax.enterprise.context.RequestScoped;
import java.util.concurrent.atomic.AtomicInteger;

@RequestScoped
public class RequestScopedCounter {
    private AtomicInteger counter = new AtomicInteger(0);

    public int getCount() {
        return counter.get();
    }

    public int incrementAndGet() {
        return counter.incrementAndGet();
    }
}
