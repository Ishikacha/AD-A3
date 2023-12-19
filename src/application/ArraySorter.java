package application;

import java.util.Comparator;

public interface ArraySorter<T> {
    // Konstruktor, der einen Comparator entgegennimmt (wenn erforderlich)
    // (Nicht in der Aufgabenbeschreibung enthalten, optional hinzugefügt)
    // public ArraySorter( final Comparator<T> comp );

    // Methode zum Sortieren eines Arrays
	 void sort(final T[] arrayToBeSorted);

	
}
