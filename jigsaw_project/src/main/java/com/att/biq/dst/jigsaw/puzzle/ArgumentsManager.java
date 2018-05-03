package com.att.biq.dst.jigsaw.puzzle;

import org.apache.commons.cli.*;

/**
 * Author - Dorit
 * this class come to read the arguments line and set the correct parameters on the puzzleManager according to the args line
 * this class is using the apache cli library
 * if argument appear - you can will set the value, else it will ignore it.
 *
 *
 */

public class ArgumentsManager {

    private Options options = new Options();
    CommandLine cmd = null;

    /**
     * select run options using the apache common CLI
     * Using a boolean option
     * <p>
     * A boolean option is represented on a command line by the presence of the option, i.e. if the option is found then the option value is true, otherwise the value is false.
     */


    public void handleCommandLineOptions(String[] args) {
        setOptions(args);
        getRotationStatus();
        getThreadNumberFromCommandLine();
        getInputFilePathFromCommandLine();
        getOutputFilePathFileFromCommandLine();

        // todo print usage in case of failure
    }

    /**
     * Set the options we support on the puzzle project
     */
    void setOptions(String[] args) {


        options.addOption(new Option(GlobalParameters.OPTION_INPUTFILE, true, "input file location")); //TODO mandatory
        options.addOption(new Option(GlobalParameters.OPTION_OUTPUTFILE, true, "output file location")); // todo mandatory
        options.addOption(new Option(GlobalParameters.OPTION_ROTATE, "rotation is enabled"));
        options.addOption(new Option(GlobalParameters.OPTION_THREADS, true, "number of threads. if 0 - no threads."));

        CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            //TODO exception
            e.printStackTrace();
        }

    }

    /**
     * get the rotation status (use later for select if we use rotation oe not)
     *
     * @return true/false
     */

    public boolean getRotationStatus() {

        if (cmd.hasOption(GlobalParameters.OPTION_ROTATE)) {
            return true;
        } else {
            return false;
        }


    }




    /**
     * gets thread number from command line. if not exists, set threadNumber to 4
     *
     * @param
     * @return number of threads to use
     */
    public int getThreadNumberFromCommandLine() {


        if (cmd.hasOption(GlobalParameters.OPTION_THREADS)) {
            return Integer.valueOf((cmd.getOptionValue(GlobalParameters.OPTION_THREADS)));

        } else {
            return 4; // as decided on spec if not specified otherwise
        }

    }


    public String getInputFilePathFromCommandLine() {

        if (cmd.hasOption(GlobalParameters.OPTION_INPUTFILE)) {
            return cmd.getOptionValue(GlobalParameters.OPTION_INPUTFILE);

        } else {
            //TODO SHOW USAGE + UNIT TEST
            System.exit(0);
        }

        return null;
    }


    public String getOutputFilePathFileFromCommandLine() {

        if (cmd.hasOption(GlobalParameters.OPTION_OUTPUTFILE)) {
            return cmd.getOptionValue(GlobalParameters.OPTION_OUTPUTFILE);

        } else {
            //TODO SHOW USAGE + UNIT TEST
            System.exit(0);
        }

        return null;
    }



}
