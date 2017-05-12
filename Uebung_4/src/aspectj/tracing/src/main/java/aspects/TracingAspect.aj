package aspects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The aspect for tracing the chained method calls.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public aspect TracingAspect {

    private static Logger log = LoggerFactory.getLogger(TracingAspect.class);

    pointcut methodCall():
            call(* application.*.*(..));

    pointcut fieldAccess():
            (get(* application..*.*) || set(* application..*.*));

    pointcut newObject():
            call(application.*.new(..));

    before(): methodCall(){
        log.info("Before method '{}#{}'", thisJoinPointStaticPart.getSignature().getDeclaringType().getSimpleName(),
                 thisJoinPointStaticPart.getSignature().getName());
    }

    after() returning: methodCall(){
        log.info("After method '{}#{}'", thisJoinPointStaticPart.getSignature().getDeclaringType().getSimpleName(),
                 thisJoinPointStaticPart.getSignature().getName());
    }

    after() throwing(Throwable t): methodCall(){
        log.info("After method '{}#{}' / exception={} / message={}", thisJoinPointStaticPart.getSignature().getDeclaringType().getSimpleName(),
                 thisJoinPointStaticPart.getSignature().getName(),
                 t.getClass().getName(),
                 t.getMessage());
    }

    before(): fieldAccess() {
        log.info("Before field '{}#{}'", thisJoinPointStaticPart.getSignature().getDeclaringType().getSimpleName(),
                 thisJoinPointStaticPart.getSignature().getName());
    }

    after(): fieldAccess() {
        log.info("After field '{}#{}'", thisJoinPointStaticPart.getSignature().getDeclaringType().getSimpleName(),
                 thisJoinPointStaticPart.getSignature().getName());
    }

    before():newObject() {
        log.info("Before constructor '{}'", thisJoinPointStaticPart.getSignature().getDeclaringType());
    }

    after():newObject() {
        log.info("After constructor '{}'", thisJoinPointStaticPart.getSignature().getDeclaringType());
    }
}
