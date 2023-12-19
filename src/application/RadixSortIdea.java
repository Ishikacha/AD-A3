package application;


import java.util.LinkedList;
import java.util.Queue;
import utilityStuff.Item;


// RadixSort for long[] configured for 4bit-slices/nibbles
// Demo to explain RadixSort idea via source code
//
// abbreviations
// -------------
// bIdxOldQl ::= bucket index (of) old queue_list
// bIdxCurQl ::= bucket index (of) current queue_list
public class RadixSortIdea implements ArraySorter<Item> {

    // Definiert die Größe eines Slices in Bits
    private static final int sliceSize = 8;
    // Berechnet die Anzahl der Slices, die für eine 64-Bit-Zahl benötigt werden
    private static final int sliceCnt = 64 / sliceSize;
    // Berechnet die Anzahl der "Buckets" (oder Warteschlangen), die für die Sortierung verwendet werden
    private static final int bucketCnt = 1 << sliceSize;
    // Erstellt eine Maske zur Identifikation von Slices
    private static final int sliceMask = bucketCnt - 1;

    @Override
    public void sort(Item[] arrayToBeSorted) {
        // Erstellt eine Matrix von Warteschlangen zur Speicherung der Elemente während der Sortierung
        @SuppressWarnings("unchecked")
        final Queue<Item>[][] queueMatrix = (Queue<Item>[][]) new Queue[2][bucketCnt];
        // Initialisiert die Warteschlangenmatrix
        for (int queueListSelectionIdx = 2; --queueListSelectionIdx >= 0; ) {
            for (int bc = bucketCnt; --bc >= 0; ) {
                queueMatrix[queueListSelectionIdx][bc] = new LinkedList<>();
            }
        }

        // Variablen für die Listenverwaltung und Verschiebung
        int oldQueueList = 0b0;
        int curQueueList = 0b1;
        int shift = 0;

        // Verteilt die Elemente in die Warteschlangen basierend auf ihren Sortierschlüsseln
        for (Item curValue : arrayToBeSorted) {
            final int bIdxCurQl = (curValue.getSortKey() >>> shift) & sliceMask;
            queueMatrix[curQueueList][bIdxCurQl].add(curValue);
        }

        // Schleife für die Sortierung
        for (int stillToDo = sliceCnt; --stillToDo > 0; ) {
            // Verschiebt die Slice-Position für die nächste Sortierung
            shift += sliceSize;
            // Wechselt zwischen altem und aktuellem Listenindex
            curQueueList ^= 0b1;
            oldQueueList ^= 0b1;

            // Verteilt die Elemente basierend auf den Sortierschlüsseln in den Warteschlangen
            for (int bIdxOldQl = 0; bIdxOldQl < bucketCnt; bIdxOldQl++) {
                while (!queueMatrix[oldQueueList][bIdxOldQl].isEmpty()) {
                    Item curValue = queueMatrix[oldQueueList][bIdxOldQl].remove();
                    int bIdxCurQl = (curValue.getSortKey() >>> shift) & sliceMask;
                    queueMatrix[curQueueList][bIdxCurQl].add(curValue);
                }
            }
        }

        // Führt die sortierten Elemente in das ursprüngliche Array zurück
        int arrayIdx = 0;
        for (int bIdxCurQl = 0; bIdxCurQl < bucketCnt; bIdxCurQl++) {
            while (!queueMatrix[curQueueList][bIdxCurQl].isEmpty()) {
                arrayToBeSorted[arrayIdx++] = queueMatrix[curQueueList][bIdxCurQl].remove();
            }
        }
    }
}
