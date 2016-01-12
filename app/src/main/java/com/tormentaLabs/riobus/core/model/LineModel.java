package com.tormentaLabs.riobus.core.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.tormentaLabs.riobus.core.utils.CoreUtils;

/**
 * @author limazix
 * @since 3.0
 * Created on 24/10/15
 */
@Table(name = CoreUtils.TABLE_LINES_NAME, id = BaseColumns._ID)
public class LineModel extends Model {

    @Column(name = CoreUtils.TABLE_LINES_COL_NUMBER)
    public String number;

    public LineModel() {
        super();
    }

    public LineModel(String number) {
        super();
        this.number = number;
    }
}
