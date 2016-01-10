package dbg.misc.calc.drive2;

import dbg.misc.calc.drive.CncCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by dmitri on 10.01.16.
 */
public class CommandQueue {

    private static Logger log = LoggerFactory.getLogger(CommandQueue.class);

    private Queue<CncCommand> commandQueue = new ArrayBlockingQueue<CncCommand>(1000);

    private CncCommand currentCommand;

    public void addCommand(CncCommand command) {
        log.info("Command added to queue " + summary(command));
        commandQueue.add(command);
    }

    private String summary(CncCommand command) {
        return "[" + command.hashCode() + "]: " + command;
    }

    public CncCommand getNext() {

        if (currentCommand != null) {
            log.info("Command complete " + summary(currentCommand));
        }

        CncCommand newCommand = commandQueue.poll();
        currentCommand = newCommand;

        if (currentCommand != null) {
            log.info("Command to execute " + summary(currentCommand));
        }

        return newCommand;
    }

    public void clear() {

        int size = commandQueue.size();

        commandQueue.clear();

        log.info("Queue cleared, " + size + " commands removed");

    }

}
