package aspects;

/**
 * This class intercepts the log calls within the TRacingAspect for log message indention.
 *
 * @author Thomas Herzog <herzog.thomas81@gmail.com>
 * @since 05/12/17
 */
public aspect IndentionLogTrace {

    private String currentIndent = "";
    private static final String INDENT = "      ";
    private static final int MAX_INDENT_IDX = INDENT.length() - 1;

    pointcut logCall(String msg):
            if(application.Main.logIndentionEnabled)
                    && call(void org.slf4j.Logger.* (String, ..)) && !within(IndentionLogTrace)
                    && args(msg, ..)
                    && within(TracingAspect);

    void around(String msg): logCall(msg){
        if ((msg.startsWith("After method") || msg.startsWith("After constructor")) && (currentIndent.length() >= MAX_INDENT_IDX)) {
            currentIndent = currentIndent.substring(MAX_INDENT_IDX, currentIndent.length() - 1);
        }
        proceed(currentIndent + msg);
        if (msg.startsWith("Before method") || msg.startsWith("Before constructor")) {
            currentIndent = INDENT + currentIndent;
        }
    }
}
