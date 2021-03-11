package ex3;

public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable();

        // Put some key values.
        for (int i = 0; i < 500; i++) {
            final String key = String.valueOf(i);
            hashTable.put(key, key);
        }

        // Print the HashTable structure
        log("****   HashTable  ***");
        log(hashTable.toString());
        System.out.println(hashTable.getCollisionsForKey("48", 6));
        System.out.println(hashTable.size());
        System.out.println(hashTable.count());


        System.out.println(hashTable.get("1").hashCode() % hashTable.size());
        log("\nValue for key(20) : " + hashTable.get("20"));
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}