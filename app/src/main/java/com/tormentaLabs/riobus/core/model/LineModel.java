package com.tormentaLabs.riobus.core.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author limazix
 * @since 3.0
 * Created on 24/10/15
 */
@Table(name = "LINES")
public class LineModel extends Model {

    @Column(name = "LINE")
    public String line;

    public LineModel() {
        super();
    }

    public LineModel(String line) {
        super();
        this.line = line;
    }
}
