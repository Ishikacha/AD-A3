// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package utilityStuff;


import utilityStuff.Version;


/**
 * The class {@link RandomBasedOnMathRandom} implements the interface {@link RandomGenerator}.
 * Actually it's some wrapper for {@link java.lang.Math#random()}.
 * 
 * @author  Michael Schaefers  ([UTF-8]:"Michael Schäfers");
 *          Px@Hamburg-UAS.eu
 * @version {@value #encodedVersion}
 */
public class RandomBasedOnMathRandom implements RandomGenerator {
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
    
    
    
    
    
    @Override
    public int nextInt( final int excludingMaximum ){
        if( 0 >= excludingMaximum ){
            throw new IllegalArgumentException( String.format(
                "natural resp. actual positive value required for excludingMaximum, but was : %d",
                excludingMaximum
            ));
        }//if
        //
        return (int)( excludingMaximum * Math.random() );
    }//method()
    
    @Override
    public String identifyImplementation(){
        return "java.lang.Math.random()";
    }//method()
    
}//class
