package ex2;

// Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
// Modified by Fernando Porrino Serrano for academic purposes.

import java.util.ArrayList;

/**
 * Implementació d'una taula de hash sense col·lisions.
 * Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
 */
public class HashTable {
    private int SIZE = 16;
    private int ITEMS = 0;
    private HashEntry[] entries = new HashEntry[SIZE];

    public int count() {
        return this.ITEMS;
    }

    public int size() {
        return this.SIZE;
    }

    /**
     * Permet afegir un nou element a la taula.
     *
     * @param key   La clau de l'element a afegir.
     * @param value El propi element que es vol afegir.
     */
    public void put(String key, String value) {
        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        if (entries[hash] == null) {
            entries[hash] = hashEntry;
        } else {
            HashEntry temp = entries[hash];

            /* Si la posición del Array donde queremos poner el nuevo HashEntry está ocupada, comprobamos si el primer HashEntry
             * de dicha posición tiene la misma clave que el elemento que queremos introducir. Si es así, actualizamos el
             * valor del elemento existente con el valor del nuevo elemento y salimos del método. */
            if (temp.key.equals(key)) {
                temp.value = value;
                return;
            }

            while (temp.next != null) {
                temp = temp.next;

                /* En caso de que la clave del primer HashEntry de la posición del Array no coincida con la clave
                 * introducida por parámetro, seguimos buscando en los siguientes HashEntry de esa misma
                 * posición en busca de un elemento con la misma clave, con el objetivo de actualizar el
                 * valor si ya existe la clave. */
                if (temp.key.equals(key)) {
                    temp.value = value;
                    return;
                }

            }

            temp.next = hashEntry;
            hashEntry.prev = temp;
        }
        /* Tras cada llamada al método put(), incrementamos el valor de ITEMS para que
         * el método count() funcione correctamente. */
        this.ITEMS++;
    }

    /**
     * Permet recuperar un element dins la taula.
     *
     * @param key La clau de l'element a trobar.
     * @return El propi element que es busca (null si no s'ha trobat).
     */
    public String get(String key) {
        int hash = getHash(key);

        /* Al tratar de obtener un HashEntry cuya clave no existe, el método lanzaba una NullPointerException
        * que no se capturaba. Se ha añadido una condición para que no se itere en el bucle en caso de que
        * el siguiente elemento a comprobar sea nulo. */
        if (entries[hash] != null) {
            HashEntry temp = entries[hash];

            temp = getHashEntry(key, temp);
            if (temp == null) return null;
            return temp.value;
        }
        return null;
    }

    private HashEntry getHashEntry(String key, HashEntry temp) {
        while (!temp.key.equals(key)) {
            if (temp.next != null) temp = temp.next;
            else return null;
        }
        return temp;
    }

    /**
     * Permet esborrar un element dins de la taula.
     *
     * @param key La clau de l'element a trobar.
     */
    public void drop(String key) {
        int hash = getHash(key);
        if (entries[hash] != null) {

            HashEntry temp = entries[hash];

            /*Como en el método anterior, si se trataba de borrar un HashEntry que no existe, el programa lanzaba una
            * NullPointerException. Se ha corregido el error introduciendo una condición que sale del bucle si
            * se intenta llegar a una posición que no existe. */
            temp = getHashEntry(key, temp);
            /*Debido a la extracción de método, se hace necesario añadir la siguiente línea para que las pruebas se sigan
            * pasando sin problemas. El IDE en este caso no ha sido capaz de añadirla tras la refacción. */
            if (temp == null) return;

            //if (temp.prev == null) entries[hash] = null;             //esborrar element únic (no col·lissió)

            /* Con el condicional original, al borrar el primer HashEntru se borraban los demás. Se ha añadido un condicional
            * que comprueba si el primer elemento es también el único y que, en caso de no serlo, lo borra pasando el segundo elemento
            * a la primera posición. */
            if (temp.prev == null) {
                if (temp.next != null) {
                    temp.next.prev = null;
                }
                temp = temp.next;
                entries[hash] = temp;
            } else {
                if (temp.next != null) temp.next.prev = temp.prev;   //esborrem temp, per tant actualitzem l'anterior al següent
                temp.prev.next = temp.next;                         //esborrem temp, per tant actualitzem el següent de l'anterior
            }

            /* Tras cada llamada al método drop(), si se elimina un elemento decrementamos el valor de ITEMS para que
             * el método count() funcione correctamente. */
            this.ITEMS--;
        }
    }

    private int getHash(String key) {
        // piggy backing on java string
        // hashcode implementation.
        return key.hashCode() % SIZE;
    }

    private class HashEntry {
        String key;
        String value;

        // Linked list of same hash entries.
        HashEntry next;
        HashEntry prev;

        public HashEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if (entry == null) {
                bucket++;
                continue;
            }

            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while (temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    /**
     * Permet calcular quants elements col·lisionen (produeixen la mateixa posició dins la taula de hash) per a la clau donada.
     *
     * @param key La clau que es farà servir per calcular col·lisions.
     * @return Una clau que, de fer-se servir, provoca col·lisió amb la que s'ha donat.
     */
    public String getCollisionsForKey(String key) {
        return getCollisionsForKey(key, 1).get(0);
    }

    /**
     * Permet calcular quants elements col·lisionen (produeixen la mateixa posició dins la taula de hash) per a la clau donada.
     *
     * @param key      La clau que es farà servir per calcular col·lisions.
     * @param quantity La quantitat de col·lisions a calcular.
     * @return Un llistat de claus que, de fer-se servir, provoquen col·lisió.
     */
    public ArrayList<String> getCollisionsForKey(String key, int quantity) {
        /*
          Main idea:
          alphabet = {0, 1, 2}

          Step 1: "000"
          Step 2: "001"
          Step 3: "002"
          Step 4: "010"
          Step 5: "011"
           ...
          Step N: "222"

          All those keys will be hashed and checking if collides with the given one.
        * */

        final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ArrayList<Integer> newKey = new ArrayList();
        ArrayList<String> foundKeys = new ArrayList();

        newKey.add(0);
        int collision = getHash(key);
        int current = newKey.size() - 1;

        while (foundKeys.size() < quantity) {
            //building current key
            String currentKey = "";
            for (int i = 0; i < newKey.size(); i++)
                currentKey += alphabet[newKey.get(i)];

            if (!currentKey.equals(key) && getHash(currentKey) == collision)
                foundKeys.add(currentKey);

            //increasing the current alphabet key
            newKey.set(current, newKey.get(current) + 1);

            //overflow over the alphabet on current!
            if (newKey.get(current) == alphabet.length) {
                int previous = current;
                do {
                    //increasing the previous to current alphabet key
                    previous--;
                    if (previous >= 0) newKey.set(previous, newKey.get(previous) + 1);
                }
                while (previous >= 0 && newKey.get(previous) == alphabet.length);

                //cleaning
                for (int i = previous + 1; i < newKey.size(); i++)
                    newKey.set(i, 0);

                //increasing size on underflow over the key size
                if (previous < 0) newKey.add(0);

                current = newKey.size() - 1;
            }
        }

        return foundKeys;
    }

}