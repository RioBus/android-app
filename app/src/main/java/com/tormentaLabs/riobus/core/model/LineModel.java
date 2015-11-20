package com.tormentaLabs.riobus.core.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author limazix
 * @since 3.0
 * Created on 24/10/15
 */
@Table(name = "LINES", id = BaseColumns._ID)
public class LineModel extends Model {

    @Column(name = "NUMBER")
    public String number;

    public LineModel() {
        super();
    }

    public LineModel(String number) {
        super();
        this.number = number;
    }
}
