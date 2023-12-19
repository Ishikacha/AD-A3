package utilityStuff;


/**
 * The class {@link OptimizationBlocker} implements an OptimizationBlocker.
 * The purpose is to block any kind of compiler resp. JIT optimization and
 * to get rid of nasty '@SuppressWarnings("unused")'.
 * To achieve this, it's important to call {@link #getSignature()} in the end
 * and to use the result in anyway e.g. print it.
 * 
 * @version {@value #encodedVersion}
 * @author  Michael Schaefers ([UTF-8]:"Michael Schäfers");  Px@Hamburg-UAS.eu 
 */
public class OptimizationBlocker {
    //
    //--VERSION:-----------------------#---vvvvvvvvv---vvvv-vv-vv--vv
    //  ========                       #___~version~___YYYY_MM_DD__dd_
    final static long encodedVersion = 2___00000_000___2023_09_24__01L;
    //---------------------------------#---^^^^^-^^^---^^^^-^^-^^--^^
    final static Version version = new Version( encodedVersion );
    static String getDecodedVersion(){ return version.getDecodedVersion(); }
    // Obiges (ab VERSION) dient nur der Versionierung
    
    
    
    
    
    // signature computer
    final private CRC_p16b_l2m_lut08b signatureComputer;
    
    
    
    
    
    /**
     * The constructor creates an {@link OptimizationBlocker}.
     */
    public OptimizationBlocker(){
        signatureComputer = new CRC_p16b_l2m_lut08b();
    }//constructor();
    
    
    
    
    
    /**
     * The method {@link #getSignature()} delivers the signature of all processed values.
     * 
     * @return  the signature of all processed values.
     */
    public int getSignature(){
        return signatureComputer.getCurrentSignature();
    }//method()
    
    
    
    /*##########################################################################
     * 
     * vv~~~~~v~~~~~~~~~~~~~~v~~~~~~~~~~~
     * bo ::= Block compiler Optimization
     * ^^~~~~~^~~~~~~~~~~~~~~^~~~~~~~~~~~
     * 
     * TS.bo(...) is used to block any kind of compiler/JIT optimization and to get rid of nasty '@SuppressWarnings("unused")'
     * 
     */
    
    
    
    //
    //
    //  Reference Types, that aint NO wrapper classes
    //
    
    // NO(!!!) SUPPORT for Object as long as there is no urgent need - it might get complicated otherwise ;-)
    /* OBJECT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( Object )} is used to block any kind of compiler optimization and to get rid of nasty nasty '@SuppressWarnings("unused")'
     * 
     * @param obj  some object
     */
    public void bo( final Object obj ){
        throw new UnsupportedOperationException();
    }//method()
    
    
    
    /* Item[] ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( Item[] )} is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'.
     * It is specific to given {@link Item} type.
     * 
     * @param value  some Item[] value 
     */
    public void bo( final Item[] ai ){
        if( null==ai ){
            boOfNull();
        }else{
            for( final Item elem : ai )  bo( elem );
        }//if
    }//method()
    //
    /* Item[] ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( Item[] )} is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'.
     * It is specific to given {@link Item} type.
     * 
     * @param value  some Item value 
     */
    public void bo( final Item item ){
        if( null==item ){
            boOfNull();
        }else{
            // sortKey
            bo( item.sortKey());
            
            // placeHolderForOtherFields
          //if( null==item.placeHolderForOtherFields() ){                       // for safty's sake
          //    boOfNull();
          //}else{                                                              // still must NOT be lon
                // phfof ::= placeHolderForOtherFields
                final long phfofValue = (long)( item.placeHolderForOtherFields() );
                bo( phfofValue );
          //}//if
        }//if
    }//method()
    
    
    
    //
    //
    //  Simple Types
    //
    
    /* "FLOATING-POINT"-double ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( double )}  is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'
     * 
     * @param value  some double value 
     */
    public void bo( final double value ){
        final long ieee754representationDoubleFormat = Double.doubleToRawLongBits( value );
        bo( ieee754representationDoubleFormat );
    }//method()
    //
    /* "FLOATING-POINT"-float ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( float )} is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'
     * 
     * @param value  some float value
     */
    public void bo( final float value ){
        final int ieee754representationSingleFormat = Float.floatToRawIntBits( value );
        bo( ieee754representationSingleFormat );
    }//method()
    //
    /* "Integer"-long ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( long )} is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'
     * 
     * @param value  some long value
     */
    public void bo( final long value ){
        // "little-endian-thinking"
        signatureComputer.process((int)(   value          & 0xFF ));
        signatureComputer.process((int)( ( value >>>  8 ) & 0xFF ));
        signatureComputer.process((int)( ( value >>> 16 ) & 0xFF ));
        signatureComputer.process((int)( ( value >>> 24 ) & 0xFF ));
        signatureComputer.process((int)( ( value >>> 32 ) & 0xFF ));
        signatureComputer.process((int)( ( value >>> 40 ) & 0xFF ));
        signatureComputer.process((int)( ( value >>> 48 ) & 0xFF ));
        signatureComputer.process((int)(   value >>> 56          ));            // long => NO sign extension since value is long - NO "numeric promotion problems"
    }//method()
    //
    /* "Integer"-int ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( int )} is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'
     * 
     * @param value  some int value
     */
    public void bo( final int value ){
        // "little-endian-thinking"
        signatureComputer.process(  value          & 0xFF );
        signatureComputer.process(( value >>>  8 ) & 0xFF );
        signatureComputer.process(( value >>> 16 ) & 0xFF );
        signatureComputer.process(  value >>> 24          );                    // int => NO sign extension since value is int - NO "numeric promotion problems"
    }//method()
    //
    /* "Integer"-short ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( short )} is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'
     * 
     * @param value  some short value
     */
    public void bo( final short value ){
        // "little-endian-thinking"
        signatureComputer.process(  value          & 0xFF );
        signatureComputer.process(( value >>>  8 ) & 0xFF );                    // mask necessary, since be aware of possible sign extension as result of numeric promotion
    }//method()
    //
    /* "Integer"-byte ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( byte )} is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'
     * 
     * @param value  some byte value
     */
    public void bo( final byte value ){
        signatureComputer.process( value & 0xFF );                              // mask necessary, since be aware of possible sign extension as result of numeric promotion
    }//method()
    //
    /* "Integer"-char ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( char )} is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'
     * 
     * @param value  some char value
     */
    public void bo( final char value ){
        // "little-endian-thinking"
        signatureComputer.process(  value          & 0xFF );
        signatureComputer.process(  value >>>  8          );                    // char => NO sign extension since value is (unsigned) char - NO "numeric promotion problems"
    }//method()
    //
    /* boolean ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /**
     * The method {@link #bo( boolean )} is used to block any kind of compiler optimization and to get rid of nasty '@SuppressWarnings("unused")'
     * 
     * @param value  some boolean value
     */
    public void bo( final boolean bool ){
        if( bool ){
            signatureComputer.process( 0xFF );
        }else{
            signatureComputer.process( 0x00 );
        }//if
    }//method()
    
    
    
    private void boOfNull(){
        signatureComputer.process( 0xF1 );                                      // just some value (1st)
        signatureComputer.process( 0xF2 );                                      // just some value (2nd)
        signatureComputer.process( 0xF3 );                                      // just some value (3rd)
    }//method()
    
}//class
