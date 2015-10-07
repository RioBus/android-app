package com.tormentaLabs.riobus.history.controller;

import com.activeandroid.query.Select;
import com.tormentaLabs.riobus.history.model.HistoryItem;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

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
        HistoryItem historyItem = new Select().from(HistoryItem.class)
                .where("LINE = ?", line).executeSingle();

        if(historyItem == null) {
            historyItem = new HistoryItem();
            historyItem.line = line;
        }

        historyItem.lastUsage = System.currentTimeMillis();
        historyItem.save();
    }

    public String[] getHistory() {
        ArrayList<HistoryItem> historyItems = (ArrayList) new Select().from(HistoryItem.class).execute();

        String[] lines = new String[historyItems.size()];
        for(HistoryItem item : historyItems) {
            lines[historyItems.indexOf(item)] = item.line;
        }

        return lines;
    }

}
