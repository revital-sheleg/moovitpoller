package moovit.poller;

import moovit.poller.etaapi.INextBusProvider;

import java.util.List;

//TODO: not implemented
public class PollerConfig {

    public INextBusProvider provider() {
        return null;
    }

    public List<String> lineNumbers() {
        return null;
    }

    public int pollIntervalSeconds() {
        return 1;
    }

    public int maxConcurrency() {
        return 1;
    }

}
