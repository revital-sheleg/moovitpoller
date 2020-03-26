package moovit.poller;

import java.util.Date;

public class LineEta {
    String lineNumber;
    Date eta;

    public LineEta(String lineNumber, Date eta) {
        this.lineNumber = lineNumber;
        this.eta = eta;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public Date getEta() {
        return eta;
    }
}
