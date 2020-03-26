package moovit.poller.cache;

import moovit.poller.LineEta;
import moovit.poller.etaapi.INextBusProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;


public class EtaCacheManager implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtaCacheManager.class);

    private List<String> lineIds;

    private int threadNum;

    private int sleepInterval;

    private INextBusProvider nextBusProvider;

    private EtaCache etaCache;

    public EtaCacheManager(List<String> lineIds, int threadNum, int sleepInterval, INextBusProvider nextBusProvider) {
       this.lineIds = lineIds;
       this.threadNum = threadNum;
       this.sleepInterval = sleepInterval;
       this.nextBusProvider = nextBusProvider;
       etaCache = new EtaCache();
    }


    public List<LineEta> getLineEta(int stop) {
        if(etaCache.isInt) {
            etaCache.etaHashMap.get(stop);
        }
    }

    @Override
    public void run() {
        LOGGER.info("get time line foreach line");
        try {
            LOGGER.info("Program started");

            // Create a list of tasks to be executed
            vat tasks = List.of(lineIds.stream().map()
                    //new EtaTask(nextBusProvider, ),
                    );

            var executor = Executors.newFixedThreadPool(threadNum);

            tasks.stream().map(Worker::new).forEach(executor::execute);
            // All tasks were executed, now shutdown
            executor.shutdown();
            while (!executor.isTerminated()) {
                Thread.yield();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
