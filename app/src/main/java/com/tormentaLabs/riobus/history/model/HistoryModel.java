package com.tormentaLabs.riobus.history.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.tormentaLabs.riobus.core.model.LineModel;

/**
 * @author limazix
 * @since 2.1
 * Created on 05/10/15
 */
@Table(name = "HISTORY")
public class HistoryModel extends Model {

    @Column(name = "LINE")
    public LineModel line;

    @Column(name = "CREATED_AT")
    public String createdAt;

    public HistoryModel() {
        super();
    }

    public HistoryModel(LineModel line, String createdAt) {
        super();
        this.line = line;
        this.createdAt = createdAt;
    }
}
