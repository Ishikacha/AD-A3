package application;
import java.util.ArrayList;
import java.util.List;

import utilityStuff.Item;

public class RadixSortArray implements ArraySorter<Item> {

    private static int sliceSize = 8;
    private static int sliceCnt = 32 / sliceSize;
    private static int bucketCnt = 1 << sliceSize;
    private static int sliceMask = bucketCnt - 1;

    public void sort(final Item[] arrayToBeSorted) {
        // Erstelle Bucket-Arrays für die Sortierung
        List<Item>[] bucketArrays = new ArrayList[bucketCnt];

        // Initialisiere Bucket-Arrays
        for (int i = 0; i < bucketCnt; i++) {
            bucketArrays[i] = new ArrayList<>();
        }

        // Sortiere Elemente basierend auf ihren Schlüsselwerten in die entsprechenden Buckets
        for (int shift = 0; shift < sliceCnt * sliceSize; shift += sliceSize) {
            for (final Item curValue : arrayToBeSorted) {
                final int bIdxCurQl = (curValue.getSortKey() >>> shift) & sliceMask;
                bucketArrays[bIdxCurQl].add(curValue);
            }

            // Füge sortierte Elemente zurück ins Array ein
            int arrayIdx = 0;
            for (List<Item> bucketArray : bucketArrays) {
                for (Item item : bucketArray) {
                    arrayToBeSorted[arrayIdx++] = item;
                }
                bucketArray.clear(); // Leere den Bucket, um ihn für den nächsten Durchlauf vorzubereiten
            }
        }
    }
}