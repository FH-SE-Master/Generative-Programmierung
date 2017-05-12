package aspects;

/**
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/05/17
 */
public aspect Tracing {
    // All method calls (any arguments) of all classes within application package
    pointcut methodCall():
            call(* application.*.*(..))
                    && !within(Tracing); // Only needed if aspects in the same packages

    pointcut fieldAccess():
            (get(* application..*.*) || set(* application..*.*));

    before(): methodCall(){
        // code is executed before a method calls for joinpoints selected by the pointcut methodCall
        System.out.println("Entering " + thisJoinPointStaticPart.getSignature().getName());
    }

    after() returning: methodCall(){
        System.out.println("Leaving " + thisJoinPointStaticPart.getSignature().getName());
    }

    after() throwing(Throwable t): methodCall(){
        System.out.println("Leaving with error: " + thisJoinPointStaticPart.getSignature().getName()
                                   + t.getClass().getName() + ": " + t.getMessage());
    }

    before(): fieldAccess() {
        System.out.println("Accessing field: " + thisJoinPointStaticPart.getSignature().getName());
    }

    after(): fieldAccess() {
        System.out.println("Leaving field: " + thisJoinPointStaticPart.getSignature().getName());
    }
}
