import EnumConstants.AuthErrorCodes;
import EnumConstants.Roles;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.*;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by Артем on 04.10.2015.
 */

public class MainClass {
    private static final Logger logger = LogManager.getLogger(MainClass.class);

    public static void main(String[] args) {
        logger.trace("Application started");
        CommandLineParser clParser = new DefaultParser();
        Options options = createOptions();
        try {
            CommandLine commandLine = clParser.parse(options, args);

            if ((commandLine.hasOption("h")) || (args.length == 0)) {
                logger.info("Printing help");
                printHelp(options, 80, "Options", "", 3, 5, true, System.out);
                System.exit(AuthErrorCodes.AUTH_SUCCESS.toInt());
            }

            String login = commandLine.getOptionValue("login");
            logger.info("login: " + login);

            String pass = commandLine.getOptionValue("pass");
            logger.info("pass: " + pass);

            String roleStr = commandLine.getOptionValue("role");
            logger.info("role: "+roleStr);
            Roles role = null;
            if (roleStr != null) {
                try {
                    role = Roles.valueOf(roleStr);
                } catch (Exception e) {
                    logger.error("Unknown role "+roleStr);
                    System.exit(AuthErrorCodes.AUTH_UNKNOWN_ROLE.toInt());
                }
            }

            String res = commandLine.getOptionValue("res");
            logger.info("resource: "+res);

            String ds = commandLine.getOptionValue("ds");
            logger.info("start date: "+ds);

            String de = commandLine.getOptionValue("de");
            logger.info("end date: "+de);

            String volStr = commandLine.getOptionValue("vol");
            logger.info("volume: "+volStr);
            Integer vol = null;
            if (volStr != null) {
                try {
                    vol = Integer.parseInt(volStr);
                } catch (NumberFormatException e) {
                    logger.error("Invalid volume "+volStr);
                    System.exit(AuthErrorCodes.AUTH_INCORRECT_ACTIVITY.toInt());
                }
            }

            AuthService authMan = new AuthService();
            AuthErrorCodes code = AuthErrorCodes.AUTH_WRONG_DB;
            if (authMan.connect("jdbc:h2:./dbase/aaa", "sa", "")) {
                code = authMan.signIn(login, pass, res, role, ds, de, vol);
                authMan.closeConnection();
            }

            logger.trace("Result code: "+code.toString()+"(" + code.toInt()+") - "+code.getDescription()+"");

            System.exit(code.toInt());
        } catch (ParseException e) {
            logger.info("Wrong arguments");
            printHelp(options, 80, "Options", "", 3, 5, true, System.out);
            System.exit(AuthErrorCodes.AUTH_SUCCESS.toInt());
        }
    }

    public static Options createOptions() {
        return new Options().addOption("login", true, "Login")
                            .addOption("pass", true, "Password")
                            .addOption("role", true, "User role")
                            .addOption("res", true, "Resource")
                            .addOption("ds", true, "Start date")
                            .addOption("de", true, "End date")
                            .addOption("vol", true, "Volume")
                            .addOption("h", true, "Print this help");
    }

    public static void printHelp(
            final Options options,
            final int printedRowWidth,
            final String header,
            final String footer,
            final int spacesBeforeOption,
            final int spacesBeforeOptionDescription,
            final boolean displayUsage,
            final OutputStream out) {
        final String commandLineSyntax = "java MainClass.jar";
        final PrintWriter writer = new PrintWriter(out);
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(
                writer,
                printedRowWidth,
                commandLineSyntax,
                header,
                options,
                spacesBeforeOption,
                spacesBeforeOptionDescription,
                footer,
                displayUsage);
        writer.flush();
    }
}
