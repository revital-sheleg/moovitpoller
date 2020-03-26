package moovit.poller.cache;

import moovit.poller.LineEta;
import moovit.poller.etaapi.StopEta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EtaCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtaCache.class);

    ConcurrentHashMap<Integer, List<LineEta>> etaHashMap;

    //update temp eta map and replace.
    ConcurrentHashMap<Integer, List<LineEta>> etaHashMapTmp;

    public EtaCache(){
        etaHashMap = new ConcurrentHashMap<>();
        etaHashMapTmp = new ConcurrentHashMap<>();
    }

    public void updateStop(StopEta stopEta, String line, ConcurrentHashMap map) {
        List<LineEta> lineEtaList = etaHashMapTmp.containsKey(stopEta.getStopId()) ?
            lineEtaList = etaHashMapTmp.get(stopEta.getStopId()): new ArrayList<>();

        LineEta lineEta = new LineEta(line, stopEta.getEta());
        lineEtaList.add(lineEta);
        etaHashMapTmp.put(stopEta.getStopId(), lineEtaList);
    }

    public void updateEtaCacheWithEta(String lineId, List<StopEta> stopList, ConcurrentHashMap map) {
        try {
            stopList.forEach(stop -> {
                updateStop(stop, lineId, map);
            });

        } catch (Exception e) {
            LOGGER.error("updateEtaCacheWithEta exception");
        }
    }

    public void updateEtaCacheWithEtaList(Map<String, List<StopEta>> lineStopList, ConcurrentHashMap map) {
        try {
            etaHashMapTmp = new ConcurrentHashMap<>();
            for (Map.Entry<String,List<StopEta>> entry : lineStopList.entrySet()) {
                updateEtaCacheWithEta(entry.getKey(), entry.getValue(), map);
            }
            //replace maps ()
            etaHashMap = etaHashMapTmp;

        } catch (Exception e) {
            LOGGER.error("updateEtaCacheWithEta exception");
        }
    }


public List<LineEta> getLineEta(int stop) {
        return etaHashMap.get(stop);
}


}
