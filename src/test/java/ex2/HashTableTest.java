package ex2;

import org.junit.jupiter.api.Assertions;

class HashTableTest {

    @org.junit.jupiter.api.Test
    void count() {
        HashTable hashTable = new HashTable();
        for (int i = 0; i < 50; i++){
            hashTable.put(String.valueOf(i), String.valueOf(i));
        }
        Assertions.assertEquals(50, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());

        for (int i = 0; i < 500; i++){
            hashTable.put(String.valueOf(i), String.valueOf(i));
        }
        Assertions.assertEquals(500, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void size() {
        HashTable hashTable = new HashTable();
        for (int i = 0; i < 100; i++){
            hashTable.put(String.valueOf(i), String.valueOf(i));
        }
        Assertions.assertEquals(100, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void put() {
        HashTable hashTable = new HashTable();
        Assertions.assertEquals("", hashTable.toString());
        Assertions.assertEquals(0, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());

        hashTable.put("0", "1");
        hashTable.put("0", "2");
        hashTable.put("11", "21");
        hashTable.put("11", "68");
        hashTable.put("0", "3");
        hashTable.put("20", "20");
        hashTable.put("6", "6");
        hashTable.put("20", "21");
        hashTable.put("354", "354");
        hashTable.put("24", "24");
        Assertions.assertEquals(
                "\n bucket[0] = [0, 3] -> [11, 68]" +
                        "\n bucket[2] = [354, 354] -> [24, 24]" +
                        "\n bucket[6] = [6, 6]" +
                        "\n bucket[14] = [20, 21]", hashTable.toString());
        Assertions.assertEquals(6, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void get() {
        HashTable hashTable = new HashTable();
        for (int i = 0; i < 50; i++){
            hashTable.put(String.valueOf(i), String.valueOf(i));
        }
        Assertions.assertNull(hashTable.get("510"));
        Assertions.assertEquals("43", hashTable.get("43"));
        Assertions.assertEquals("0", hashTable.get("0"));
        Assertions.assertNull(hashTable.get("-1"));
    }

    @org.junit.jupiter.api.Test
    void drop() {
        HashTable hashTable = new HashTable();
        hashTable.put("0", "0");
        hashTable.put("11", "11");
        hashTable.put("22", "22");
        hashTable.put("1", "1");
        hashTable.put("12", "12");
        hashTable.put("23", "23");
        Assertions.assertEquals(
                "\n bucket[0] = [0, 0] -> [11, 11] -> [22, 22]" +
                        "\n bucket[1] = [1, 1] -> [12, 12] -> [23, 23]", hashTable.toString());
        Assertions.assertEquals(6, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());

        hashTable.drop("11");
        Assertions.assertEquals(
                "\n bucket[0] = [0, 0] -> [22, 22]" +
                        "\n bucket[1] = [1, 1] -> [12, 12] -> [23, 23]", hashTable.toString());
        hashTable.drop("0");
        Assertions.assertEquals(
                "\n bucket[0] = [22, 22]" +
                        "\n bucket[1] = [1, 1] -> [12, 12] -> [23, 23]", hashTable.toString());
        hashTable.drop("22");
        Assertions.assertEquals("\n bucket[1] = [1, 1] -> [12, 12] -> [23, 23]", hashTable.toString());
        hashTable.drop("1");
        Assertions.assertEquals("\n bucket[1] = [12, 12] -> [23, 23]", hashTable.toString());
        hashTable.drop("1");
        Assertions.assertEquals("\n bucket[1] = [12, 12] -> [23, 23]", hashTable.toString());
        hashTable.drop("0");
        Assertions.assertEquals( "\n bucket[1] = [12, 12] -> [23, 23]", hashTable.toString());

        Assertions.assertEquals(2, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());

        hashTable.drop("12");
        Assertions.assertEquals("\n bucket[1] = [23, 23]", hashTable.toString());
        Assertions.assertEquals(1, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());
    }
}