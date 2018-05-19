package com.att.biq.dst.jigsaw.puzzle;

/**
 * author = dorit
 * this class created to support environmental parameters on the puzzle project
 *
 * should support any additional parameters we wish to add (if and when ...)
 *
 *
 */
public class GlobalParameters {

    // command line parameters (used on apache CLI
    public static final String OPTION_ROTATE  = "hasRotate";
    public static final String OPTION_THREADS  = "numThreads";
    public static final String OPTION_INPUTFILE  = "inputFilePath";
    public static final String OPTION_OUTPUTFILE  = "outputFilePath";
    public static final String OPTION_IP  = "serverIP";
    public static final String OPTION_PORT  = "serverPort";


    public static final String OPTION_ROTATE_DESC  = "if the parameter exists roration is available, else it won't be calculated using rotation";
    public static final String OPTION_THREADS_DESC  = "Number of threads to use. in case this parameter is not provided number will be 4";
    public static final String OPTION_INPUTFILE_DESC  = "input file location e.g. c:\\temp\\";
    public static final String OPTION_OUTPUTFILE_DESC  = "output file  e.g. c:\\temp\\myoutput.txt";
    public static final String OPTION_IP_DESC  = "server IP value. put the ip of the server. if not mentioned will be  127.0.0.1";
    public static final String OPTION_PORT_DESC  = "server Port,  if not stated, will be 7095 by default";


}
