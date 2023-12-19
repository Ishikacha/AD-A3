// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package utilityStuff;


import java.util.Comparator;


public class ItemComparatorDescendingNaturalOrder implements Comparator<Item> {
    
    /**
     * Just some Comparator.
     */
    @Override
    public int compare( final Item item1st,  final Item item2nd ){
        return item2nd.compareTo( item1st );
    }//method()
    
}//class
