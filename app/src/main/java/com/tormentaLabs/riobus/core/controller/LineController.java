package com.tormentaLabs.riobus.core.controller;

import com.activeandroid.query.Select;
import com.tormentaLabs.riobus.core.model.LineModel;

import org.androidannotations.annotations.EBean;

/**
 * Used to control line data
 * @author limazix
 * @since 3.0
 * Created at 24/10/15
 */
@EBean
public class LineController {

    public LineModel createIfNotExists(String line) {

        LineModel lineModel = new Select().from(LineModel.class)
                .where("LINE = ? ", line)
                .executeSingle();

        if(lineModel == null) {
            lineModel = new LineModel();
            lineModel.line = line;
            lineModel.save();
        }

        return lineModel;
    }
}
