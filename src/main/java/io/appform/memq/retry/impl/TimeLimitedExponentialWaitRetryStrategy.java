

package io.appform.memq.retry.impl;


import io.appform.memq.retry.RetryStrategy;
import io.appform.memq.retry.config.TimeLimitedExponentialWaitRetryConfig;
import io.appform.memq.utils.CommonUtils;
import net.jodah.failsafe.RetryPolicy;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class TimeLimitedExponentialWaitRetryStrategy extends RetryStrategy {
    public TimeLimitedExponentialWaitRetryStrategy(TimeLimitedExponentialWaitRetryConfig config) {
        super(new RetryPolicy<Boolean>()
                .handleIf(exception -> CommonUtils.isRetriable(config.getRetriableExceptions(), exception))
                .withMaxDuration(Duration.of(config.getMaxTimeInMillis(), ChronoUnit.MILLIS))
                .withBackoff(config.getDelayInMillis(),
                    config.getDelayInMillis(),
                    ChronoUnit.MILLIS,
                    config.getMultipier())
        );
    }
}
