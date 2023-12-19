// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package utilityStuff;


/*
 * Internal note:
 * ==============
 * Use int (or long) as working (data) type, otherwise numeric promotion results in nightmare.
 * Further it would result in more computation afford, since numeric promotion has to be neglected by masking.
 * Only exception is LUT (here) as char[].
 */
/**
 * This is an INTERNAL support class.<br />
 * <br />
 * 
 * The class {@link CRC_p16b_l2m_lut08} implements a signature computer.
 * The name results out of:<br />
 * o) CRC ::= Cyclic Redundancy Check similar to / some kind of signature<br />
 * o) (prime)polynomial 17 bit for <b>16 bit signature</b><br />
 * o) using (work-)polynomial coded LSB (on "left/MSB side") to MSB (on "right/LSB side) without MSB<br />
 * o) LookUpTable for 8 bit argument size<br />
 * 
 * 
 * @version {@value #encodedVersion}
 * @author  Michael Schaefers ([UTF-8]:"Michael Schäfers");  Px@Hamburg-UAS.eu 
 */
class CRC_p16b_l2m_lut08b {                                                     // package scope on purpose resp. NOT for public usage
    //
    //--VERSION:-----------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                       #___~version~___YYYY_MM_DD__dd_
    final static long encodedVersion = 2___00000_000___2023_09_24__01L;
    //---------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static Version version = new Version( encodedVersion );
    static String getDecodedVersion(){ return version.getDecodedVersion(); }
    // Obiges (ab VERSION) dient nur der Versionierung
    
    
    
    
    
    static private int[] mirror( int polynomial ){
        if( 0 == polynomial ){
            throw new IllegalArgumentException( "polynomial MUST NOT be 0 - this is definitly false for a prime polynomial" );
        }//if
        
        int resu = 0;
        int bitCnt = 0;
        do {
            resu = ( resu << 1 )  |  ( polynomial & 0b1 );
            polynomial >>>= 1;
            bitCnt++;
        }while( 0 != polynomial );
        
        return new int[]{ resu, bitCnt };
    }//method()
    
    
    
    
    
    // work polynomial without MSB and coded with LSB on "MSB-side"
    final private int workPoly;
    
    // lut ::= LookUp Table that maps current byte to current signature
    final private char[] lut;
    
    
    //cs ::= Current Signature
    private int cs;
    
    
    
    
    
    /**
     * ...
     * 
     * @param primePolynomial ...
     */
    CRC_p16b_l2m_lut08b( final int primePolynomial ){                           // package scope on purpose resp. NOT for public usage
        
        // checks---------------------------------------------------------------
        
        // check and adapt polynomial
        if( 0==primePolynomial )  throw new IllegalArgumentException( "polynomial MUST NOT be 0 - this is definitly false for a prime polynomial" );
        if( 1 != ( 0b1 & primePolynomial ) )  throw new IllegalArgumentException( "LSB MUST NOT be 0 - this is definitly false for a prime polynomial" );
        
        final int[] tmp = mirror( primePolynomial );
        if( 17 != tmp[1] )  throw new IllegalArgumentException( String.format( "17 bit are expected for 16 bit signature and NOT %d bit",  tmp[1] ));
        workPoly = tmp[0] >>> 1;                                                // MSB of (mirrored) polynomial is NOT needed and results in complicated matter(!)
        
        // check if workPoly is adequate
        final int expectedStepCnt = 0xFFFF;
        final int expectedChkSgn = 0xFFFF;
        int chkSign = expectedChkSgn;
        int chkStepCnt = 0;
        do{
            final boolean msbSet = ( 0b1 == ( chkSign & 0b1 ));
            chkSign >>>= 1;
            if( msbSet ) {
                chkSign ^= workPoly;
                assert 0<=chkSign && chkSign<=Character.MAX_VALUE : "Uuuupppss";
            }//if
            chkStepCnt++;
        }while( expectedChkSgn != chkSign  &&  expectedStepCnt > chkStepCnt );
        if( expectedChkSgn != chkSign  ||  expectedStepCnt != chkStepCnt )  throw new IllegalArgumentException( String.format( "0x%x is NO prime polynomial",  primePolynomial ));
        
        
        
        // ACTION---------------------------------------------------------------
        
        // setup LUT
        lut = new char[256];
        int currentSignature;          // Current SiGNature related to lut
        for( char futureArgument=0; futureArgument<256; futureArgument++ ){
            currentSignature = futureArgument;
            for( int bitPosition=0; bitPosition<8; bitPosition++ ){
                final boolean msbSet = ( 0b1 == ( currentSignature & 0b1 ));
                currentSignature >>>= 1;
                if( msbSet ){
                    currentSignature ^= workPoly;
                }//if
            }//for
            assert 0<=currentSignature && currentSignature<=Character.MAX_VALUE : "Uuuupppss";
            lut[futureArgument]=(char)( currentSignature );
        }//for
        
        // setup current signature
        cs = 0xFFFF;                                                            // initial value MUST NOT be zero, any other (16 bit) value is fine
        
    }//constructor()
    //
    /**
     * ...
     */
    CRC_p16b_l2m_lut08b(){                                                      // package scope on purpose resp. NOT for public usage
        this( 0x14881 );                                                        // x^16 + x^14 + x^11 + x^7 + x^0 is at least qualified as generator polynomial
    }//constructor
    
    
    
    
    
    // cb ::= Current Byte
    /**
     * ...
     * 
     * @param cb current byte to be processed
     */
    void process( final int cb ){                                               // package scope on purpose resp. NOT for public usage
        assert 0<=cb && cb<=0xFF : String.format( "Uuuupps : Argument %d out of range [0,255]",  cb );
        
        cs = ( lut[ cs & 0x00FF ]    ^    (( cb << 8 )  |  ( cs >>> 8)));
        
        assert 0<=cs && cs<=Character.MAX_VALUE : "Uuuupppss";
    }//method()
    
    
    
    /**
     * ...
     * 
     * @return ...
     */
    int getCurrentSignature(){                                                  // package scope on purpose resp. NOT for public usage
        return cs;
    }//method()
    
    
    /**
     * ...
     * 
     * @return ...
     */
    int getInternalWorkPolynomial(){                                            // package scope on purpose resp. NOT for public usage
        return workPoly;
    }//method()    
    
}//class
