package com.tormentaLabs.riobus;

import com.tormentaLabs.riobus.common.models.Line;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class APIServiceUnitTest {

    @Test
    public void receivesDataFromServer() throws IOException {
        List<Line> lines = APIService.getInstance().getLines();
        assertNotNull(lines);
        assertNotEquals(lines.size(), 0);
    }
}