import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import domains.Role;

import java.io.PrintWriter;
import java.sql.SQLException;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static Option makeOptionWithArgument(String shortName, String description, boolean isRequired) {
        Option result = new Option(shortName, true, description);
        result.setArgs(1);
        result.setRequired(isRequired);

        return result;
    }

    static void printHelp(Options options) {
        final PrintWriter writer = new PrintWriter(System.out);
        final HelpFormatter helpFormatter = new HelpFormatter();

        helpFormatter.printHelp(
                writer,
                80,
                "[program]",
                "Options:",
                options,
                3,
                5,
                "-- HELP --",
                true);

        writer.flush();
    }

    public static int work(String[] args) throws SQLException {

        Options options = new Options()
                .addOption(makeOptionWithArgument("login", "Login name", true))
                .addOption(makeOptionWithArgument("pass", "Password", true))
                .addOption(makeOptionWithArgument("role", "Role", false))
                .addOption(makeOptionWithArgument("res", "Resource name", false))
                .addOption(makeOptionWithArgument("ds", "Start date", false))
                .addOption(makeOptionWithArgument("de", "End date", false))
                .addOption(makeOptionWithArgument("vol", "Volume", false));

        CommandLine commandLine = null;
        try {
            commandLine = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            printHelp(options);
            return 255;
        }
        logger.warn("***********START***********");
        Auth auth = new Auth();

        auth.authentication(commandLine.getOptionValue("login"), commandLine.getOptionValue("pass"));

        if (!auth.isCorrectLogin()) {
            logger.warn("Incorrect login");
            return 1;
        }

        if (!auth.isCorrectPass()) {
            logger.warn("Incorrect password");
            return 2;
        }
        if (commandLine.hasOption("role") && commandLine.hasOption("res")) {
            Role role = Role.fromString(commandLine.getOptionValue("role"));

            if (role == null) {
                logger.warn("Incorrect role");
                return 3;
            }
            auth.res(commandLine.getOptionValue("res"), role);

            if (!auth.isResAccess()) {
                logger.warn("Access denied");
                return 4;
            }
            if (commandLine.hasOption("ds") && commandLine.hasOption("de") && commandLine.hasOption("vol")) {
                auth.checkVolume(commandLine.getOptionValue("vol"));
                auth.checkDate(commandLine.getOptionValue("ds"), commandLine.getOptionValue("de"));

                if (!auth.isCorrectVolume() || !auth.isCorrectDate()) {
                    logger.warn("Incorrect volume or date");
                    return 5;
                }
                // Insert
                auth.insertToAccau(commandLine.getOptionValue("role"));
            }
        }
        return 0;
    }

    public static void main(String[] args) throws SQLException {
        System.exit(work(args));
    }
}
