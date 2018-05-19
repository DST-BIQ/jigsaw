package com.att.biq.dst.jigsaw.puzzle;

import org.apache.commons.cli.*;

/**
 * Author - Dorit
 * In the constructor there are two arguments - if server (boolean) if not it will be a client. and the list of arguments from main.
 *
 * this class come to read the arguments line and set the correct parameters on the puzzleManager according to the args line
 * this class is using the apache cli library
 * if argument appear - you can will set the value, else it will ignore it.
 * hasRotate - if the parameter exists  - rotation is available, else it won't be calculated using rotation.
 * numThreads - Number of threads to use. in case this parameter is not provided number will be 4.
 * inputFilePath - input file location e.g. c:\temp\input.txt (this  parameter is mandatory)
 * outputFilePath - output file  e.g. c:\temp\myoutput.txt (this parameter is mandatory)
 * ip - ip of the server - this is a mandatory parameter. e.g. 127.0.0.1 for localhost
 * port - port of the server. if not mentioned, default will be 7095, otherwise port stated.
 */

public class ArgumentsManager {

    private Options options = new Options();
    CommandLine cmd = null;
    HelpFormatter helpFormatter = new HelpFormatter();
    String[] args;
    boolean isServer;

    /**
     * select run options using the apache common CLI
     * Using a boolean option
     * <p>
     * A boolean option is represented on a command line by the presence of the option, i.e. if the option is found then the option value is true, otherwise the value is false.
     */
// for client
    public ArgumentsManager(String[] args, boolean isServer) {
        this.isServer = isServer;
        this.args = args;

        setOptionsFromArguments();
        setParametersValue();
    }



    /**
     * Set the options we support on the puzzle project
     */
    public void setOptionsFromArguments() {
        if (isServer) {
            options.addOption(new Option(GlobalParameters.OPTION_THREADS, true, GlobalParameters.OPTION_THREADS_DESC));
            options.addOption(new Option(GlobalParameters.OPTION_PORT, true, GlobalParameters.OPTION_PORT_DESC));

        } else {
            options.addOption(new Option(GlobalParameters.OPTION_ROTATE, false, GlobalParameters.OPTION_ROTATE_DESC));
            options.addRequiredOption(GlobalParameters.OPTION_INPUTFILE, GlobalParameters.OPTION_INPUTFILE_DESC, true, GlobalParameters.OPTION_INPUTFILE_DESC);
            options.addRequiredOption(GlobalParameters.OPTION_OUTPUTFILE, GlobalParameters.OPTION_OUTPUTFILE_DESC, true, GlobalParameters.OPTION_OUTPUTFILE_DESC);
            options.addOption(new Option(GlobalParameters.OPTION_IP, true, GlobalParameters.OPTION_IP_DESC));
            options.addOption(new Option(GlobalParameters.OPTION_PORT, true, GlobalParameters.OPTION_PORT_DESC));

            }

        }

        public void setParametersValue(){
            try {
                CommandLineParser parser = new DefaultParser();
                cmd = parser.parse(options, args);
                if (isServer) {
                    getThreadNumberFromCommandLine();
                    getPortFromCommandLine();
                } else {
                    getInputFilePathFromCommandLine();
                    getOutputFilePathFileFromCommandLine();
                    getRotationStatus();
                    getPortFromCommandLine();
                    getIPFromCommandLine();

                }





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

    /**
     * get the server IP from the command line. if not mentioned will be set to 127.0.0.1 by default.
     *
     * @param
     * @return server IP
     */
    public String getIPFromCommandLine() {


        if (cmd.hasOption(GlobalParameters.OPTION_IP)) {
            return cmd.getOptionValue(GlobalParameters.OPTION_IP);

        } else {
            return "127.0.0.1";

        }

    }

    /**
     * get the server IP from the command line. if not mentioned will be set to 127.0.0.1 by default.
     *
     * @param
     * @return server IP
     */
    public String getPortFromCommandLine() {


        if (cmd.hasOption(GlobalParameters.OPTION_PORT)) {
            return cmd.getOptionValue(GlobalParameters.OPTION_PORT);

        } else {
            return "7095";

        }

    }
}
