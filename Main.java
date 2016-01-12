package com.blzr;

import com.blzr.service.AccountingService;
import com.blzr.service.AuthorizationService;
import org.apache.commons.cli.*;

public class Main {
    private final Options options;
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final AccountingService accountingService = new AccountingService();

    public static void main(String[] args) {
        ResultType result = new Main().parseArgs(args);
        System.exit(result.getCode());
    }

    private Main() {
        options = new Options()
                .addOption("login", true, "username")
                .addOption("pass", true, "password")
                .addOption("res", true, "site")
                .addOption("role", true, "role")
                .addOption("ds", true, "date start yyyy-mm-dd")
                .addOption("de", true, "date end yyyy-mm-dd")
                .addOption("vol", true, "volume")
                .addOption("h", false, "help");
    }

    private ResultType parseArgs(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                printHelp();
                return ResultType.SUCCESS;
            } else {
                ResultType result;
                // First step: Authentication
                if (cmd.hasOption("login") && cmd.hasOption("pass")) {
                    result = authenticate(cmd.getOptionValue("login"), cmd.getOptionValue("pass"));

                    // Second step: Authorization
                    if (result == ResultType.SUCCESS && cmd.hasOption("res") && cmd.hasOption("role")) {
                        result = authorize(cmd.getOptionValue("login"), cmd.getOptionValue("res"), cmd.getOptionValue("role"));


                        // Third step: Accounting
                        if (result == ResultType.SUCCESS &&
                                // And all activity values specified
                                cmd.hasOption("ds") && cmd.hasOption("de") && cmd.hasOption("vol")) {
                            try {
                                accountingService.addActivity(
                                        authorizationService.getAuthority(
                                                cmd.getOptionValue("login"),
                                                cmd.getOptionValue("res"),
                                                cmd.getOptionValue("role")),
                                        cmd.getOptionValue("ds"),
                                        cmd.getOptionValue("de"),
                                        cmd.getOptionValue("vol"));
                            } catch (java.text.ParseException | NumberFormatException e) {
                                // Cannot parse activity
                                printHelp();
                                return ResultType.INVALID_ACTIVITY;
                            }
                        }
                    }
                    return result;
                } else {
                    // No known arguments specified
                    printHelp();
                    return ResultType.SUCCESS;
                }
            }
        } catch (ParseException e) {
            printHelp();
            return ResultType.SUCCESS;
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("aaa", options);
    }

    private ResultType authenticate(String username, String password) {
        if (!authorizationService.isUserExist(username)) {
            return ResultType.UNKNOWN_LOGIN;
        } else if (!authorizationService.isPasswordCorrect(username, password)) {
            return ResultType.INVALID_PASSWORD;
        } else {
            return ResultType.SUCCESS;
        }
    }

    private ResultType authorize(String username, String site, String role) {
        if (!authorizationService.isRoleExist(role)) {
            return ResultType.UNKNOWN_ROLE;
        } else if (!authorizationService.isAuthorized(username, site, role)) {
            return ResultType.ACCESS_DENIED;
        } else {
            return ResultType.SUCCESS;
        }
    }
}
