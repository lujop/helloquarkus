package cat.joanpujol.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class HiService {
    private AtomicInteger count = new AtomicInteger(0);

    @Inject
    RequestScopedCounter requestScopedCounter;

    @ConfigProperty(name = "environment")
    String environment;

    @ConfigProperty(name = "hi.name")
    String hiName;

    public String getHiMessage() {
        String hiMessage = "hello " + hiName + " I'm in " + environment + " environment";
        count.incrementAndGet();
        requestScopedCounter.incrementAndGet();
        return hiMessage;
    }

    public int getCount() {
        return count.get();
    }

    public int getRequestCount() {
        return requestScopedCounter.getCount();
    }
}
