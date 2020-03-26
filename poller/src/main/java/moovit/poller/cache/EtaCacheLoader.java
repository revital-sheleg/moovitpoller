package moovit.poller.cache;

import moovit.poller.etaapi.INextBusProvider;
import moovit.poller.etaapi.StopEta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;


public class EtaCacheLoader implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtaCacheLoader.class);

    private List<String> lineIds;

    private int threadNum;

    private INextBusProvider nextBusProvider;

    public EtaCacheLoader(final Task task) {
       lineIds = loadLines();
       threadNum = 4; //TODO: load from configuration
      //  nextBusProvider //TODO: get, consider injection

    }

    private List<String> loadLines(){
        return null; //TODO: implement get lines
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
