package aspects;

import aspects.util.AspectjConfig;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tsp.api.Solution;

import java.util.Date;

public aspect MeasureRuntime {

    private static final Logger log = LoggerFactory.getLogger(AspectjConfig.LOGGER_NAME);

    Solution around(): if(aspects.util.AspectjConfig.measureRuntime) &&
            Pointcuts.execute() {
        Solution best;

        StopWatch watch = new StopWatch();
        watch.start();;
        best = proceed();
        watch.stop();

        log.info("Runtime in ms={}", watch.getTime());
        watch = null;

        return best;
    }
}