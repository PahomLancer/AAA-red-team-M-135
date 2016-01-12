package com.andr;

import com.andr.service.AccountingService;
import com.andr.service.AuthenticationService;
import com.andr.service.AuthorizationService;
import com.andr.service.ConnectionService;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    private final Options options;
    private final ConnectionService connectionService = new ConnectionService();
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final AuthorizationService authorizationService = new AuthorizationService(connectionService, authenticationService);
    private final AccountingService accountingService = new AccountingService(connectionService);

    public static void main(String[] args) {
        ResultType result = new Main().connect(args);
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

    private ResultType connect(String[] args) {
        try {
            connectionService.connect();
            return parseArgs(args);
        } finally {
            connectionService.disconnect();
        }
    }

    private ResultType parseArgs(String[] args) {
        log.info("Received params {}", (Object) args);
        CommandLineParser parser = new DefaultParser();
        ResultType result;
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                log.info("Printing help");
                printHelp();
                result = ResultType.SUCCESS;
            } else {
                // First step: Authentication
                if (cmd.hasOption("login") && cmd.hasOption("pass")) {
                    String login = cmd.getOptionValue("login");
                    String pass = cmd.getOptionValue("pass");
                    log.info("Received login {} and pass {}", login, pass);
                    result = authenticate(login, pass);

                    // Second step: Authorization
                    if (result == ResultType.SUCCESS && cmd.hasOption("res") && cmd.hasOption("role")) {
                        String role = cmd.getOptionValue("role");
                        String res = cmd.getOptionValue("res");
                        log.info("Received res {} and role {}", role, res);
                        result = authorize(login, role, res);

                        // Third step: Accounting
                        if (result == ResultType.SUCCESS &&
                                // And all activity values specified
                                cmd.hasOption("ds") && cmd.hasOption("de") && cmd.hasOption("vol")) {
                            String ds = cmd.getOptionValue("ds");
                            String de = cmd.getOptionValue("de");
                            String vol = cmd.getOptionValue("vol");
                            log.info("Received ds {} de {} vol {}", ds, de, vol);
                            result = account(login, role, res, ds, de, vol);
                        }
                    }
                    return result;
                } else {
                    log.info("No Login and password specified");
                    printHelp();
                    result = ResultType.SUCCESS;
                }
            }
        } catch (ParseException e) {
            log.error("Cannot parse params", e);
            result = ResultType.SUCCESS;
        }
        log.info("Result {}", result);
        return result;
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("aaa", options);
    }

    private ResultType authenticate(String username, String password) {
        if (!authorizationService.isUserExist(username)) {
            log.warn("User {} not exists", username);
            return ResultType.UNKNOWN_LOGIN;
        } else if (!authorizationService.isPasswordCorrect(username, password)) {
            log.warn("Password {} incorrect", password);
            return ResultType.INVALID_PASSWORD;
        } else {
            log.info("User {} authenticated with password {}", username, password);
            return ResultType.SUCCESS;
        }
    }

    private ResultType authorize(String username, String role, String site) {
        if (!authorizationService.isRoleExist(role)) {
            log.warn("Role {} not exist", role);
            return ResultType.UNKNOWN_ROLE;
        } else if (!authorizationService.isAuthorized(username, role, site)) {
            log.warn("User {} not authorized {} to {}", username, role, site);
            return ResultType.ACCESS_DENIED;
        } else {
            log.info("User {} authorized {} to {}", username, role, site);
            return ResultType.SUCCESS;
        }
    }

    private ResultType account(String login, String role, String res, String ds, String de, String vol) {
        try {
            Long total = accountingService.addActivity(
                    authorizationService.getAuthority(login, role, res),
                    ds, de, vol);
            log.info("Successfully accounted {} total {} activities", login, total);
            return ResultType.SUCCESS;
        } catch (java.text.ParseException e) {
            log.error("Cannot parse data {} or {}", ds, de);
            return ResultType.INVALID_ACTIVITY;
        } catch (NumberFormatException e) {
            log.error("Cannot parse vol {}", vol);
            return ResultType.INVALID_ACTIVITY;
        }
    }
}
