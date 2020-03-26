package moovit.poller;

import moovit.poller.cache.EtaCacheManager;

import java.util.List;

public class PollerImpl implements IPoller {

    EtaCacheManager etaCacheManager;

    @Override
    public void init(PollerConfig pollerConfig) {
        etaCacheManager = new EtaCacheManager(pollerConfig.lineNumbers(), pollerConfig.maxConcurrency(),
                pollerConfig.pollIntervalSeconds(), pollerConfig.provider());
    }

    @Override
    public List<LineEta> getStopArrivals(int stopId) {
        etaCacheManager.getLineEta(stopId);
    }
}
