package moovit.poller.cache;

import moovit.poller.LineEta;
import moovit.poller.etaapi.StopEta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class EtaCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtaCache.class);

    ConcurrentHashMap<String, List<LineEta>> etaHashMap;

    //update temp eta map and replace.
    ConcurrentHashMap<String, List<LineEta>> etaHashMapTmp;

    public EtaCache(){
        etaHashMap = new ConcurrentHashMap<>();
        etaHashMapTmp = new ConcurrentHashMap<>();
    }

    public void updateStop(StopEta stopEta, String line) {
        List<LineEta> lineEtaList = new ArrayList<>();
        if (etaHashMapTmp.containsKey(stopEta.getEta()))
        {
            lineEtaList = etaHashMapTmp.get(stopEta.getEta());
        }
        LineEta lineEta = new LineEta()
    }

    public void updateEtaCacheWithEta(String lineId, List<StopEta> stopList) {
        try {
            etaHashMapTmp = new ConcurrentHashMap<>();


        } catch (Exception e) {
            LOGGER.error("updateEtaCacheWithEta exception");
        }
    }

}
