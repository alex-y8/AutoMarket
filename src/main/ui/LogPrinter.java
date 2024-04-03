package ui;

import model.Event;
import model.EventLog;

// Class that prints the logs from the event logger
public class LogPrinter {

    // EFFECTS: prints the event log
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }

}
