package utilityStuff;


//import utilityStuff.random.RandomGenerator;
//import utilityStuff.random.RandomGeneratorImplementationWrapper;


public class UnsortedItemArrayGenerator {
    
    final private int amountOfItems;
    final private int excludingMaximum;
    
    
    
    
    
    public UnsortedItemArrayGenerator( final int amountOfItems, final int excludingMaximum ){
        this.amountOfItems = amountOfItems;
        this.excludingMaximum = excludingMaximum;
    }//constructor()
    //
    public UnsortedItemArrayGenerator() {
        this( 65521, 1<<16);                                                          // last prime before 2^16=65536
    }//constructor()
    
    
    
    
    
    public Item[] createUnsortedItemArray(){
        final RandomGenerator rg = new RandomGeneratorImplementationWrapper( 1 );
        final Item[] ia = new Item[amountOfItems];                              // ia ::= Item Array
        for( int idx=0;  idx<amountOfItems;  idx++ ){
            ia[idx] = new Item( rg.nextInt( excludingMaximum ) );
        }//for
        return ia;
    }//method()
    
}//class
