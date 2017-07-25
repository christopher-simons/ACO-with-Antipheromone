/**
 * BatchMain.java
 * 6 June 2017
 * @author Chris Simons
 */

package myGui;

import config.AlgorithmParameters;
import config.Parameters;
import engine.Controller;
import problem.ProblemController;


public class BatchMain 
{
    private static ProblemController problemController;
    
    /**
     * BatchMain developed for adaptive anti-pheromone experiments
     * June 2017
    */
    public static void main( String[] args ) 
    {
        assert args != null;
        if( args.length != 0 )
        {
            assert false : "no command line arguments required";
        }    
        
        // ensure correct settings for July 2017
        assert Parameters.mode == Parameters.Mode.Batch;
        
        assert AlgorithmParameters.algorithm == AlgorithmParameters.MMAS;
        assert AlgorithmParameters.MMAS_SUBTRACTIVE_ANTIPHEROMONE == false; // set MMAS to usual i.e. reduce to minimum of MAXMIN
        
        assert AlgorithmParameters.constraintHandling == true;  // no invalid solutions
        assert AlgorithmParameters.NUMBER_OF_ANTS == 100;
        assert AlgorithmParameters.NUMBER_OF_ITERATIONS == 1000;
        
        // Nov 2015 set up output file path for appropriate platform
        if( Parameters.platform == Parameters.Platform.Mac )
        {
            Parameters.outputFilePath = "/Users/Chris/Documents/data";
        }
        else // must be Windows
        {
            // Parameters.outputFilePath = "W:\\Research\\Writings\\2015 Paper 42 - Antipheromone GECCO 2016\\data";
            // Parameters.outputFilePath = "C:\\Users\\cl-simons\\Dropbox\\2015 10 Antipheromone\\data";
            Parameters.outputFilePath = "D:\\ExperimentOutput";
        }
        System.out.println( "selected path for output files is: " + Parameters.outputFilePath );
        
        AlgorithmParameters.fitness = AlgorithmParameters.COMBINED;  // set up cost to Fcomb
            
        for( int problem = Parameters.RANDOMISED; problem <= Parameters.RANDOMISED; problem++ )
        {
            Parameters.problemNumber = problem;
            generateProblem( problem );
            
            for( int x = 8; x < 9; x++ )
            {
                AlgorithmParameters.ANTIPHEROMONE_PHASE_THRESHOLD_PERCENTAGE = x;
                System.out.println( "******* problem is: " + problem + 
                    ", fitness is: " + AlgorithmParameters.fitness + 
                    // ", MMAS subtractive AP is: " + AlgorithmParameters.MMAS_SUBTRACTIVE_ANTIPHEROMONE +
                    ", AP Threshold is: " + AlgorithmParameters.ANTIPHEROMONE_PHASE_THRESHOLD_PERCENTAGE + " ******"  );
                doAntSearch( );
            
            }   // end for threshold values 0..9

//            for( int y = 10; y <= 100; y += 10 )
//            {
//                AlgorithmParameters.ANTIPHEROMONE_PHASE_THRESHOLD_PERCENTAGE = y;
//                System.out.println( "******* problem is: " + problem + 
//                    ", fitness is: " + AlgorithmParameters.fitness +
//                    ", MMAS subtractive AP is: " + AlgorithmParameters.MMAS_SUBTRACTIVE_ANTIPHEROMONE + 
//                    ", AP Threshold is: " + AlgorithmParameters.ANTIPHEROMONE_PHASE_THRESHOLD_PERCENTAGE + " ******"  );
//                doAntSearch( problem );
//
//            }   // end for threshold values 10..100
            
        }   // end for each problem 
        
    }
    
    /**
     * do the ant search
     * Design problem have been set up
     * 14 June 2017
     */
    public static void doAntSearch( )
    {
        assert problemController != null;
        
        Controller controller = new Controller( problemController );
        
        for( int i = 0; i < Parameters.NUMBER_OF_RUNS; i++ )
        {
           controller.run( i );       
        }
        
        controller.writeResultstoFile( );
        System.out.println( "batch ACO complete" );
    }

    /**
     * Generate the correct design problem for the current batch run,
     * and generate it only once. 
     * 14 June 2017
     * @param problemNumber as int
     */
    private static void generateProblem( final int problemNumber )
    {
        assert problemNumber >= 0;
        assert problemNumber < Parameters.NUMBER_OF_PROBLEMS;
        
        problemController = new ProblemController( );
        assert problemController != null;
        
        // set up the Cinema Booking System (CBS) design problem
        if( problemNumber == Parameters.CBS ) 
        {
            problemController.createDesignProblem5( );
            problemController.setNumberOfClasses( 5 );
            problemController.generateUseMatrix( );
        }
        // set up the GDP design problem
        else if( problemNumber == Parameters.GDP ) 
        {
            problemController.createDesignProblem7( );
            problemController.setNumberOfClasses( 5 );
            problemController.generateUseMatrix( );
        }
        // set up the Randomised design problem
        else if( problemNumber == Parameters.RANDOMISED  ) 
        {
            problemController.createDesignProblem8( );
            problemController.setNumberOfClasses( 8 );
                   
            // 21 January 2016
            problemController.initialiseWithPreGenerated( );
            // problemController.showUseMatrix( );
        } 
        // set up the Select Cruises (SC) design problem
        else if( problemNumber == Parameters.SC ) 
        {
            problemController.createDesignProblem6( );
            problemController.setNumberOfClasses( 16 );
            // 28 May 2012 test of constraint handling
//            problemController.setNumberOfClasses( 5 );
            
            problemController.generateUseMatrix( );
        }
        else
        {
            assert false : "impossible design problem!!";
        }
    }
  
}   // end of class

// ------------- end of file ---------------------------------