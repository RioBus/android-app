package com.tormentaLabs.riobus.history.controller;

import com.activeandroid.query.Select;
import com.tormentaLabs.riobus.history.model.LineHistory;

import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Used to control history data access
 * @author limazix
 * @since 2.1
 * Created at 05/10/15
 */
@EBean
public class HistoryController {

    public void addLines(String[] lines) {
        for(String line : lines)
            addLine(line);
    }

    public void addLine(String line) {
        LineHistory lh = new Select().from(LineHistory.class)
                .where("LINE = ?", line).executeSingle();

        if(lh == null) {
            lh = new LineHistory();
            lh.line = line;
        }

        lh.lastUsage = System.currentTimeMillis();
        lh.save();
    }

    public List<LineHistory> getLineHistory() {
        return new Select().from(LineHistory.class).execute();
    }

}
