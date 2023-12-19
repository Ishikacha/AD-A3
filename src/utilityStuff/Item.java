// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package utilityStuff;


import java.util.concurrent.atomic.AtomicLong;


/*
 * Immutable objects have "stable" place in sorting order
 */
/**
 * Note(s):<br/>
 * 
 * (1)
 * Item<i>s</i> are ordered/sorted by their "(sort-)Key-Values",
 * other fields/attributes/state variables are neglected for ordering/sorting.
 * Hence, most probably this immutable class/record has a 
 * <strong>natural ordering</strong> that <strong>is <u>inconsistent</u> with equals()</strong>.<br/>.
 * 
 * (2)
 * In each program-run there shall be no more than 0xFFFFFFFF items
 */
public record Item( int sortKey,  Object placeHolderForOtherFields ) implements Comparable<Item>{
    
    private static AtomicLong sequentialItemProductionNumber = new AtomicLong();
    //
    public static void resetSequentialItemProductionNumber(){
        sequentialItemProductionNumber.set( 0 );
    }//method()
    
    
    
    
    
    public Item( final int sortKey ){
        this(
            sortKey,                                                                    // the very sort key
            ((0L|sortKey) << 32) | sequentialItemProductionNumber.getAndIncrement()     // placeholder for other stuff  diverted for "stable checks"
        );
        //
        // for safty's sake
        final long currentSequentialItemProductionNumber = sequentialItemProductionNumber.get();
        if( currentSequentialItemProductionNumber < 0  ||  0x7FFFFFFF < currentSequentialItemProductionNumber ){
            throw new IllegalStateException( String.format(
                "Maximum number of supported items exceeded -> #0x%X",
                currentSequentialItemProductionNumber
            ));
        }//if
    }//constructor()
    
    
 // Getter und Setter für sortKey
    public int getSortKey() {
        return sortKey;
    }

  

  
    
    @Override
    public int compareTo( final Item other ){
        return Integer.compare( sortKey,  other.sortKey );
    }//method()
    
}//record
