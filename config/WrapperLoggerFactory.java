package capstone_project.av_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class WrapperLoggerFactory {

    /**
     * @return a new {@link Logger} using the name of the caller class.
     */
    public static Logger getLogger() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String callersClassName = stackTrace[2].getClassName();
        return LoggerFactory.getLogger(callersClassName);
    }
}
