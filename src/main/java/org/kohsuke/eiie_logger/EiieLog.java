package org.kohsuke.eiie_logger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is where {@link ExceptionInInitializerError}s are recorded to.
 *
 * @author Kohsuke Kawaguchi
 */
public class EiieLog {

    /**
     * Intercepted instantiation comes here.
     */
    public static void log(ExceptionInInitializerError e) {
        LOGGER.log(Level.WARNING, "ExceptionInInitializerError instantiated", e);

        List<ExceptionInInitializerError> c = COLLECTED;
        synchronized (c) {
            c.add(e);
        }
    }

    /**
     * Logger that reports an instantiated {@link ExceptionInInitializerError}
     */
    public static Logger LOGGER = Logger.getLogger(EiieLog.class.getName());

    /**
     * Reported {@link ExceptionInInitializerError}s are also stored to let you inspect them later.
     */
    public static List<ExceptionInInitializerError> COLLECTED = new ArrayList<ExceptionInInitializerError>();
}
