package com.andr.service;

import com.andr.domain.Activity;
import com.andr.domain.Authority;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AccountingService {
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private final ConnectionService connectionService;

    public AccountingService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public Long addActivity(Authority authority, String dateStart, String dateEnd, String volume) throws ParseException, NumberFormatException {
        connectionService.addActivity(new Activity(authority,
                format.parse(dateStart), format.parse(dateEnd), Long.valueOf(volume)));
        return connectionService.countActivity();
    }
}
