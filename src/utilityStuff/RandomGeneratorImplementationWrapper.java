package utilityStuff;


import utilityStuff.Herald;
import utilityStuff.Version;


/**
 * The class {@link RandomGeneratorImplementationWrapper} is wrapping an implementation for a class implementing the interface {@link RandomGenerator}.
 * ...
 * 
 * @author  Michael Schaefers  ([UTF-8]:"Michael Schäfers");
 *          Px@Hamburg-UAS.eu
 * @version {@value #encodedVersion}
 */

public class RandomGeneratorImplementationWrapper implements RandomGenerator {
    //
    //--VERSION:-------------------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                               #___~version~___YYYY_MM_DD__dd_
    final static private long encodedVersion = 2___00001_001___2023_03_08__01L;
    //-----------------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static private Version version = new Version( encodedVersion );
    /**
     * The method {@link #getDecodedVersion()} delivers the code version as reground/readable String.
     * @return version as decoded/readable String.
     */
    static public String getDecodedVersion(){ return version.getDecodedVersion(); }
    // Obiges (ab VERSION) dient nur der Versionierung.
    
    
    
    
    
    final private static int selectedImplementation = 0;
    final private RandomGenerator randomGenerator;
    
    
    
    /**
     * ...
     * 
     * @param seed ...
     */
    public RandomGeneratorImplementationWrapper( final long seed ){
        switch( selectedImplementation ){
            case  0:{
              //Herald.proclaimMessage( String.format( "Seed: %d was ignored by random gnerator in thread: #%d:%s\n",  seed, Thread.currentThread().getId(), Thread.currentThread().getName() ));
                randomGenerator = new RandomBasedOnMathRandom();
            };break;
            case  1:{
                randomGenerator = new RandomBasedOnUtilRandom( seed );
            };break;
            case 98:{
              //Herald.proclaimMessage( String.format( "Seed: %d was ignored by random gnerator in thread: #%d:%s\n",  seed, Thread.currentThread().getId(), Thread.currentThread().getName() ));
                randomGenerator = new RandomBasedOnPRBS( System.nanoTime() );
            };break;
            case 99:{
                randomGenerator = new RandomBasedOnPRBS( seed );
            };break;
            //
            default:{
                randomGenerator = new RandomBasedOnUtilRandom( seed );
            };break;
        }//switch
    }//contructor()
    //
    /**
     * ...
     */
    public RandomGeneratorImplementationWrapper(){
        this( 0x123456789ABCDEFL );
    }//contructor()
    
    
    
    @Override
    public int nextInt( final int excludingMaximum ){
        if( 0 >= excludingMaximum ){
            throw new IllegalArgumentException( String.format(
                "natural resp. actual positive value required for excludingMaximum, but was : %d",
                excludingMaximum
            ));
        }//if
        //
        return randomGenerator.nextInt( excludingMaximum );
    }//method()
    
    @Override
    public String identifyImplementation(){
        return randomGenerator.identifyImplementation();
    }//method()
    
}//class
