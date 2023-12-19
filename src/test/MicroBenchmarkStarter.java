package test;


import application.ArraySorter;
import application.RadixSortArray;
import application.RadixSortIdea;
//import application.StraightInsertionV1;
import utilityStuff.Beautician;
import utilityStuff.Item;
import utilityStuff.OptimizationBlocker;
import utilityStuff.UnsortedItemArrayGenerator;


public class MicroBenchmarkStarter {
    
    // Array der Sortierer, die getestet werden sollen
    @SuppressWarnings("unchecked")
    private static final ArraySorter<Item>[] sorter =
        (ArraySorter<Item>[])( new ArraySorter[]{
            // Hier werden die zu testenden Sortieralgorithmen hinzugefügt
            new RadixSortArray(), // Verwendet Radix Sort Algorithmus
            new RadixSortIdea() // Verwendet eine andere Implementierung von Radix Sort
            // Weitere Sortieralgorithmen könnten hier hinzugefügt werden
        }
    );
    
    public static void main(final String... unused) {
        final OptimizationBlocker ob = new OptimizationBlocker();

        // Konfiguration der Testparameter
        final int runCnt = 10; // Anzahl der Testläufe
        final int[] numberOfItemsToBeSorted = {10, 100, 1_000, 10_000, 100_000, 1_000_000}; // Anzahl der zu sortierenden Elemente
        final long timeLimit = 600_000_000_000L; // Zeitlimit für jeden Testlauf (hier 10 Minuten in Nanosekunden)

        System.out.printf("Start measurement at : %s\n", Beautician.getPimpedTime());
        System.out.printf("\n");

        // Iteration über jeden Sortierer im Array
        for (final ArraySorter<Item> currentSorter : sorter) {
            // Iteration über verschiedene Anzahlen von Elementen zum Sortieren
            for (final int amountOfItems : numberOfItemsToBeSorted) {
                // Berechnen der Anzahl der zu sortierenden Elemente, exklusive des maximalen Werts
                final int excludingMaximum = amountOfItems * 999 / 1000;
                // Erstellen eines Generators für unsortierte Elemente
                final UnsortedItemArrayGenerator uiag = new UnsortedItemArrayGenerator(amountOfItems, excludingMaximum);
                long duration = 0; // Initialisierung der Gesamtdauer des Sortiervorgangs
                long startTimeOverall = System.nanoTime(); // Startzeit des Gesamtvorgangs

                // Iteration über jeden einzelnen Testlauf
                for (int rc = runCnt; --rc >= 0; ) {
                    // Erstellen eines neuen unsortierten Arrays
                    final Item[] arrayToBeSorted = uiag.createUnsortedItemArray();
                    final long startTime = System.nanoTime(); // Startzeit des aktuellen Sortiervorgangs

                    // Sortieren des Arrays mit dem aktuellen Sortieralgorithmus
                    currentSorter.sort(arrayToBeSorted);

                    final long endTime = System.nanoTime(); // Endzeit des aktuellen Sortiervorgangs
                    duration += (endTime - startTime); // Aktualisieren der Gesamtdauer
                    ob.bo(arrayToBeSorted); // Blockierung von Optimierungen, um das Entfernen von generierten Daten zu verhindern

                    // Überprüfen, ob das Zeitlimit für diesen Sortiervorgang überschritten wurde
                    if ((System.nanoTime() - startTimeOverall) > timeLimit) {
                        System.out.println("Abbruch der Messung für " + currentSorter.getClass().getSimpleName() + " bei " + amountOfItems + " Elementen aufgrund von Zeitüberschreitung.");
                        break; // Beenden des aktuellen Testlaufs, falls das Zeitlimit überschritten wurde
                    }
                }

                // Ausgabe der Testergebnisse
                System.out.printf(
                        "%s   %12d ~ %-12d : %40s\n",
                        currentSorter.getClass().getSimpleName(), // Name des verwendeten Sortieralgorithmus
                        amountOfItems, // Anzahl der sortierten Elemente
                        excludingMaximum, // Maximales ausgeschlossenes Wertlimit
                        Beautician.nanoSecondBasedTimeToString(duration / runCnt) // Durchschnittliche Sortierdauer pro Element
                );
            }
            System.out.printf("\n");
        }

        System.out.printf(">>>>>>>>>>>>>>>>>>>>----\n");
        System.out.printf("don't care about :  %x\n", ob.getSignature()); // Ausgabe eines Signaturewerts für Debug-Zwecke
    }
}
