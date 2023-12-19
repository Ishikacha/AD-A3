package test;


import application.ArraySorter;

import application.RadixSortArray;
import application.RadixSortIdea;
import utilityStuff.Item;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

public class AcceptanceTestStarter {

    public static void main(final String... unused) {
        // Erstellen von Instanzen für RadixSortArray und RadixSortIdea
        RadixSortArray radixSortArray = new RadixSortArray();
        RadixSortIdea radixSortIdea = new RadixSortIdea();

        // Konfigurieren der Testparameter
        final int amountOfValues = 9_999; // Anzahl der zu sortierenden Werte
        final int excludingMaxValue = amountOfValues * 999 / 1000; // Maximales auszuschließendes Wertlimit
        final int runCnt = 13; // Anzahl der Testläufe

        // Test für RadixSortArray durchführen
        testRadixSortArray(radixSortArray, amountOfValues, excludingMaxValue, runCnt);
        
        // Test für RadixSortIdea durchführen
        testRadixSortIdea(radixSortIdea, amountOfValues, excludingMaxValue, runCnt);
    }

    // Methode für den Test von RadixSortArray
    private static void testRadixSortArray(RadixSortArray sorter, int amountOfValues, int excludingMaxValue, int runCnt) {
        // Vorbereiten einer Liste zur Überprüfung der Sortierung
        List<Item> checkList = new ArrayList<>();

        // Iteration über die Testläufe
        for (int rc = runCnt; --rc >= 0; ) {
            Item[] theArray = new Item[amountOfValues];
            // Erstellen und Hinzufügen von zufälligen Items zum zu sortierenden Array und der Prüfliste
            for (int i = amountOfValues; --i >= 0; ) {
                final int item = (int) (excludingMaxValue * Math.random());
                theArray[i] = new Item(item);
                checkList.add(theArray[i]);
            }

            // Sortieren des Arrays
            sorter.sort(theArray);

            // Überprüfen, ob das Array korrekt sortiert wurde
            for (int i = 1; i < amountOfValues; i++) {
                if (theArray[i - 1].compareTo(theArray[i]) > 0) {
                    System.out.printf("%s   %d   %d\n", sorter.getClass().getSimpleName(),
                            theArray[i - 1].getSortKey(), theArray[i].getSortKey());
                    throw new AssertionError();
                }
            }

            // Überprüfen, ob alle Elemente sortiert und korrekt sind
            for (int i = theArray.length; --i >= 0; ) {
                if (!checkList.remove(theArray[i])) throw new AssertionError();
            }
            if (!checkList.isEmpty()) throw new AssertionError();
        }
    }

    // Methode für den Test von RadixSortIdea
    private static void testRadixSortIdea(RadixSortIdea sorter, int amountOfValues, int excludingMaxValue, int runCnt) {
        // Vorbereiten einer Liste zur Überprüfung der Sortierung
        List<Item> checkList = new ArrayList<>();

        // Iteration über die Testläufe
        for (int rc = runCnt; --rc >= 0; ) {
            Item[] theArray = new Item[amountOfValues];
            // Erstellen und Hinzufügen von zufälligen Items zum zu sortierenden Array und der Prüfliste
            for (int i = amountOfValues; --i >= 0; ) {
                final int item = (int) (excludingMaxValue * Math.random());
                theArray[i] = new Item(item);
                checkList.add(theArray[i]);
            }

            // Sortieren des Arrays mit RadixSortIdea
            sorter.sort(theArray);

            // Überprüfen, ob das Array korrekt sortiert wurde
            for (int i = 1; i < amountOfValues; i++) {
                if (theArray[i - 1].compareTo(theArray[i]) > 0) {
                    System.out.printf("%s   %d   %d\n", sorter.getClass().getSimpleName(),
                            theArray[i - 1].getSortKey(), theArray[i].getSortKey());
                    throw new AssertionError();
                }
            }

            // Überprüfen, ob alle Elemente sortiert und korrekt sind
            for (int i = theArray.length; --i >= 0; ) {
                if (!checkList.remove(theArray[i])) throw new AssertionError();
            }
            if (!checkList.isEmpty()) throw new AssertionError();
        }
    }
}