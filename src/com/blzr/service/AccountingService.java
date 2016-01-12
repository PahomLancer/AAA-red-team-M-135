package com.blzr.service;

import com.blzr.domain.Activity;
import com.blzr.domain.Authority;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class AccountingService {
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private List<Activity> activities = new LinkedList<Activity>();

    public void addActivity(Authority authority, String dateStart, String dateEnd, String volume) throws ParseException, NumberFormatException {
        activities.add(new Activity((long) activities.size(), authority,
                format.parse(dateStart), format.parse(dateEnd), Long.valueOf(volume)));
    }
}
