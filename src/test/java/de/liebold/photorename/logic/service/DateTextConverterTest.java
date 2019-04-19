package de.liebold.photorename.logic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class DateTextConverterTest {

    @Autowired
    private DateTextConverter dateTextConverter;

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

    @Ignore
    @Test
    public void getText_ValidDate_ReturnsText() throws ParseException {
        String expectedText = "2017-03-10 20.38.31";
        Date date = dateTimeFormat.parse(expectedText);

        assertEquals(expectedText, dateTextConverter.getText(date));
    }

    @Test
    public void getText_NullInput_ReturnsNull() {
        assertEquals(null, dateTextConverter.getText(null));
    }

    @Ignore
    @Test
    public void getDate_ValidText_ReturnsDate() throws ParseException {
        String dateRepresentation = "2017-03-10 20.38.31";
        Date expectedDate = dateTimeFormat.parse(dateRepresentation);

        assertEquals(expectedDate, dateTextConverter.getDate(dateRepresentation));
    }

    @Test
    public void getDate_InvalidText_ReturnsNull() {
        String invalidDateRepresentation = "2017---- 20.38.31";

        assertNull(dateTextConverter.getDate(invalidDateRepresentation));
    }

    @Test
    public void getDate_NullInput_RetunsNull() {
        assertNull(dateTextConverter.getDate((String) null));
    }

}
