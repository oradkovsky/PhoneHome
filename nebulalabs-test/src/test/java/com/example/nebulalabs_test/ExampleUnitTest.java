package com.example.nebulalabs_test;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;
import java.util.List;

import co.nebulabs.phonehome.PhoneHomeConfig;
import co.nebulabs.phonehome.PhoneHomeLogEvent;
import co.nebulabs.phonehome.PhoneHomeLogger;
import co.nebulabs.phonehome.PhoneHomeSimpleLogEvent;
import co.nebulabs.phonehome.PhoneHomeSink;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest(android.util.Log.class)
public class ExampleUnitTest {
    private AppLogSink sink;
    private final int defaultBatchSize = 20;

    @Before
    public void setup() {
        PowerMockito.mockStatic(Log.class);

        sink = spy(new AppLogSink());

        PhoneHomeConfig.getInstance()
                .enabled(true)
                .batchSize(defaultBatchSize)
                .flushIntervalSeconds(300)
                .debugLogLevel(android.util.Log.VERBOSE)
                .productionLogLevel(android.util.Log.INFO)
                .logSink(sink);
    }

    @Test
    public void forceFlush() throws Exception {
        PhoneHomeLogger log = PhoneHomeLogger.forTag("forTag");
        log.app(new PhoneHomeSimpleLogEvent(new Date(), 0, "tag", "value"));
        log.flush();

        verify(sink, times(1)).flushLogs(anyListOf(PhoneHomeLogEvent.class));
    }

    public static class AppLogSink implements PhoneHomeSink {
        @Override
        public void flushLogs(List<PhoneHomeLogEvent> list) {
            System.out.println("flushing " + list.size());
        }
    }
}