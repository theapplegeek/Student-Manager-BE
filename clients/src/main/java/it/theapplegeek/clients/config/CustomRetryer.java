package it.theapplegeek.clients.config;

import feign.RetryableException;
import feign.Retryer;
import lombok.extern.java.Log;

@Log
public class CustomRetryer implements Retryer {
    private final int maxAttempts;
    private final long backoff;
    int attempt;

    public CustomRetryer() {
        this(1000, 3);
    }

    public CustomRetryer(long backoff, int maxAttempts) {
        this.backoff = backoff;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    public void continueOrPropagate(RetryableException e) {
        if (attempt++ >= maxAttempts) {
            throw e;
        } else {
            log.warning(String.format("===== Tentativo di connessione %s =====", attempt));
        }

        try {
            Thread.sleep(backoff);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    public Retryer clone() {
        return new CustomRetryer(backoff, maxAttempts);
    }
}
