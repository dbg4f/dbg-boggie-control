package dbg.misc.calc.drive2;

import java.util.LinkedList;
import java.util.List;

/**
 * @author bogdel on 8.02.16.
 */
public class ContextArchive {

    public static final int MAX_CAPACITY = 100;

    private List<CommandProcessingContext> contexts = new LinkedList<>();


    public void addCommandContext(CommandProcessingContext commandProcessingContext) {
        contexts.add(commandProcessingContext);

        if (contexts.size() > MAX_CAPACITY) {
            contexts.remove(0);
        }
    }

    public List<CommandProcessingContext> getContexts() {
        return contexts;
    }
}
