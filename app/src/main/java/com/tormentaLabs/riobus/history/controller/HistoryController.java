package com.tormentaLabs.riobus.history.controller;

import com.activeandroid.query.Select;
import com.tormentaLabs.riobus.core.controller.LineController;
import com.tormentaLabs.riobus.core.model.LineModel;
import com.tormentaLabs.riobus.history.model.HistoryModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Used to control history data access
 * @author limazix
 * @since 2.1
 * Created at 05/10/15
 */
@EBean
public class HistoryController {

    @Bean
    LineController lineController;

    public void addLines(String[] lines) {
        for(String line : lines)
            addLine(line);
    }

    public void addLine(String line) {

        LineModel lineModel = lineController.createIfNotExists(line);

        HistoryModel historyItem = new HistoryModel();
        historyItem.line = lineModel;
        historyItem.createdAt = new DateTime().toString();
        historyItem.save();
    }

    public List<HistoryModel> getHistory() {
        return new Select().from(HistoryModel.class).execute();
    }

}
