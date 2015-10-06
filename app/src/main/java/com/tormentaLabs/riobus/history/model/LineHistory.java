package com.tormentaLabs.riobus.history.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author limazix
 * @since 2.1
 * Created on 05/10/15
 */
@Table(name = "LINE_HISTORY")
public class LineHistory extends Model {

    @Column(name = "LINE")
    public String line;

    @Column(name = "LAST_USAGE_DATE")
    public long lastUsage;

    public LineHistory() {
        super();
    }

    public LineHistory(String line, long lastUsage) {
        super();
        this.line = line;
        this.lastUsage = lastUsage;
    }


}
