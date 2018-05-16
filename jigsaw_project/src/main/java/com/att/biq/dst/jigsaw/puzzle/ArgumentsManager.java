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


    public  void handleCommandLineOptions(String[] args) {

            setOptions(args);

        System.out.println(getInputFilePathFromCommandLine());
        System.out.println(getOutputFilePathFileFromCommandLine());
                getInputFilePathFromCommandLine();
                getOutputFilePathFileFromCommandLine();
                getRotationStatus();
                getThreadNumberFromCommandLine();
            }


    /**
     * Set the options we support on the puzzle project
     */
   public void setOptions(String[] args) {
HelpFormatter helpFormatter = new HelpFormatter();

        options.addOption(new Option(GlobalParameters.OPTION_ROTATE,false, GlobalParameters.OPTION_ROTATE_DESC));
        options.addOption(new Option(GlobalParameters.OPTION_THREADS, true, GlobalParameters.OPTION_THREADS_DESC));
        options.addRequiredOption(GlobalParameters.OPTION_INPUTFILE, GlobalParameters.OPTION_INPUTFILE_DESC,true, GlobalParameters.OPTION_INPUTFILE_DESC);
        options.addRequiredOption(GlobalParameters.OPTION_OUTPUTFILE, GlobalParameters.OPTION_OUTPUTFILE_DESC,true, GlobalParameters.OPTION_OUTPUTFILE_DESC);

        CommandLineParser parser = new DefaultParser();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("DST Puzzle game", options);
            System.exit(1); //TODO throw exception

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
            throw new RuntimeException("You are missing an import file,");

        }

//        return null;
    }


    public String getOutputFilePathFileFromCommandLine() {

        if (cmd.hasOption(GlobalParameters.OPTION_OUTPUTFILE)) {
            return cmd.getOptionValue(GlobalParameters.OPTION_OUTPUTFILE);

        } else {
            throw new RuntimeException("You are missing an output file,");
        }


    }



}
