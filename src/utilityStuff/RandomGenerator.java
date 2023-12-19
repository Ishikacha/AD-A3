package utilityStuff;


import utilityStuff.Version;


/**
 * The interface {@link RandomGenerator} defines operations needed.
 * ...
 * 
 * @author  Michael Schaefers  ([UTF-8]:"Michael Schäfers");
 *          Px@Hamburg-UAS.eu
 * @version {@value #encodedVersion}
 */
public interface RandomGenerator {
    //
    //--VERSION:-----------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                       #___~version~___YYYY_MM_DD__dd_
    final static long encodedVersion = 2___00001_001___2023_03_06__11L;
    //---------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static Version version = new Version( encodedVersion );
    /**
     * The method {@link #getDecodedVersion()} delivers the code version as reground/readable String.
     * @return version as decoded/readable String.
     */
    static String getDecodedVersion(){ return version.getDecodedVersion(); }
    // Obiges (ab VERSION) dient nur der Versionierung
    
    
    
    
    
    /**
     * ...
     * 
     * @param excludingMaximum ...
     * @return...
     */
    int nextInt( int excludingMaximum );
    
    /**
     * ...
     * 
     * @return ...
     */
    String identifyImplementation();
    
}//interface
