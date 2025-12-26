package com.sktelecom.payment.learningtest;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockTest {
    @Test
    public void clockTest() {
        Clock clock = Clock.systemDefaultZone();
        LocalDateTime now1 = LocalDateTime.now(clock);
        LocalDateTime now2 = LocalDateTime.now(clock);

        Assertions.assertThat(now2).isAfter(now1);

    }

    @Test
    void fixedClock() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        LocalDateTime now1 = LocalDateTime.now(clock);
        LocalDateTime now2 = LocalDateTime.now(clock);

        Assertions.assertThat(now2).isEqualTo(now1);

    }
}
