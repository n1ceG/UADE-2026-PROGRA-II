package org.uade.structure.definition;

// Esta interfaz representa el TDA Diccionario Múltiple.
// A diferencia del Diccionario Simple, una misma clave puede tener
// asociado más de un valor.
public interface MultipleDictionaryADT<K, V> {

    /**
     * Descripcion: Retorna el conjunto de claves del diccionario.
     * Precondición: No tiene.
     */
    SetADT<K> getKeys();

    /**
     * Descripcion: Devuelve los valores asociados a una clave.
     * Precondición: La clave debe existir.
     */
    V[] get(K key);

    /**
     * Descripcion: Asocia el value a la clave key. Si la clave no existe la
     * crea. Si el value ya está asociado a la clave no hace nada.
     * Precondición: La estructura no debe sobrepasar la capacidad.
     */
    void add(K key, V value);

    /**
     * Descripcion: Elimina la clave y todos los valores asociados. Si la clave
     * no existe no hace nada.
     * Precondición: No tiene.
     */
    void remove(K key);

    /**
     * Descripcion: Elimina un valor puntual asociado a una clave. Si la clave
     * no existe o el valor no está asociado no hace nada. Si al quitar el
     * valor la clave queda sin valores asociados, también se elimina la clave.
     * Precondición: No tiene.
     */
    void remove(K key, V value);

    /**
     * Descripcion: Debe comprobar si la estructura tiene o no valores.
     * Precondición: No tiene.
     */
    boolean isEmpty();
}
