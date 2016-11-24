package co.nebulabs.phonehome;

import java.util.List;

public interface PhoneHomeSink {
    /**
     * Flush log events externally somewhere.
     * <p>
     * You may want to send them to your own server or perhaps to some third
     * party.
     */
    void flushLogs(List<PhoneHomeLogEvent> logEvents);
}
