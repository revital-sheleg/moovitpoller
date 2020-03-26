package moovit.poller.cache;

import moovit.poller.LineEta;
import moovit.poller.etaapi.INextBusProvider;
import moovit.poller.etaapi.StopEta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.*;


public class EtaCacheManager implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtaCacheManager.class);

    private List<String> lineIds;

    private int threadNum;

    private int sleepInterval;

    private INextBusProvider nextBusProvider;

    private EtaCache etaCache;

    final ScheduledExecutorService execService = Executors.newSingleThreadScheduledExecutor();

    public EtaCacheManager(List<String> lineIds, int threadNum, int sleepInterval, INextBusProvider nextBusProvider) {
       this.lineIds = lineIds;
       this.threadNum = threadNum;
       this.sleepInterval = sleepInterval;
       this.nextBusProvider = nextBusProvider;
       etaCache = new EtaCache();
       execService.schedule(this::loadCache ,sleepInterval, TimeUnit.MILLISECONDS);
    }


    public List<LineEta> getLineEta(int stop) {
        if(etaCache.isInt) {
            return  etaCache.etaHashMap.get(stop);
        }else {
            LOGGER.warn("cache not ready");
        }
        return null;
    }


    private void loadCache() {
        LOGGER.info("started load tasks");

        List<Task> tasks = new ArrayList<>();
        // Create a list of tasks to be executed
        ConcurrentHashMap<String, List<StopEta>> taskResultMap = new ConcurrentHashMap();
        for (String line: lineIds) {
            tasks.add(new EtaTask(nextBusProvider, line, taskResultMap));
        }
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        tasks.stream().forEach(executor::execute);
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.yield();
        }
        //call update cache with results
        etaCache.loadEtaCacheWithEtaList(taskResultMap);
    }

    @Override
    public void run() {
        loadCache();
    }
}
