package moovit.poller.etaapi;

import java.util.List;

public interface INextBusProvider {
    List<StopEta> getLineEta(String lineNumber);
}
