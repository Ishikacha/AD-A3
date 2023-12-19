// This source code is UTF-8 coded - see https://stackoverflow.com/questions/9180981/how-to-support-utf-8-encoding-in-eclipse
package utilityStuff;


import java.util.Comparator;


public class ItemComparatorAscendingNaturalOrder implements Comparator<Item> {
    
    /**
     * Just some Comparator.
     * Just as an example the natural ordering is used.
     * Consider, if the natural ordering makes happy, it won't make sense to implement an extra Comparator ;-)
     * This is only an example, so what...?
     */
    @Override
    public int compare( final Item item1st,  final Item item2nd ){
        return item1st.compareTo( item2nd );
    }//method()
    
}//class
