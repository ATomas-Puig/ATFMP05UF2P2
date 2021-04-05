package ex3;

import org.junit.jupiter.api.Assertions;

class HashTableTest {

    @org.junit.jupiter.api.Test
    void count() {
        HashTable hashTable = new HashTable();
        Assertions.assertEquals(0, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());

        for (int i = 0; i < 50; i++){
            hashTable.put(String.valueOf(i), String.valueOf(i));
        }
        Assertions.assertEquals(50, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(128, hashTable.size());

        hashTable.drop("43");
        Assertions.assertEquals(49, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(128, hashTable.size());

        hashTable.drop("0");
        Assertions.assertEquals(48, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(128, hashTable.size());

        hashTable.drop("49");
        Assertions.assertEquals(47, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(128, hashTable.size());

        hashTable.drop("11");
        Assertions.assertEquals(46, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(128, hashTable.size());

        for (int i = 0; i < 50; i++){
            hashTable.drop(String.valueOf(i));
        }
        Assertions.assertEquals(0, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(128, hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void size() {
        HashTable hashTable = new HashTable();
        Assertions.assertEquals(0, hashTable.count());
        Assertions.assertEquals(16, hashTable.size());

        for (int i= 0; i < 20; i++){
            hashTable.put(String.valueOf(i), String.valueOf(i));
        }
        Assertions.assertEquals(20, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(32, hashTable.size());

        hashTable.drop("1");
        Assertions.assertEquals(19, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(32, hashTable.size());

        for (int i = 0; i < 100; i++){
            hashTable.put(String.valueOf(i), String.valueOf(i));
        }
        Assertions.assertEquals(100, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(512, hashTable.size());

        for (int i = 0; i < 50; i++){
            hashTable.drop(String.valueOf(i));
        }
        Assertions.assertEquals(50, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(512, hashTable.size());


        for (int i = 50; i < 100; i++){
            hashTable.drop(String.valueOf(i));
        }
        Assertions.assertEquals(0, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(512, hashTable.size());
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
        /*Assertions.assertEquals(
                "\n bucket[0] = [0, 3] -> [11, 68]" +
                        "\n bucket[2] = [354, 354] -> [24, 24]" +
                        "\n bucket[6] = [6, 6]" +
                        "\n bucket[14] = [20, 21]", hashTable.toString());*/
        Assertions.assertEquals(
                "\n bucket[0] = [11, 68]" +
                        "\n bucket[2] = [24, 24]" +
                        "\n bucket[16] = [0, 3]" +
                        "\n bucket[18] = [354, 354]" +
                        "\n bucket[22] = [6, 6]" +
                        "\n bucket[30] = [20, 21]", hashTable.toString());
        Assertions.assertEquals(6, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(32, hashTable.size());
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
        /*Assertions.assertEquals(
                "\n bucket[0] = [0, 0] -> [11, 11] -> [22, 22]" +
                        "\n bucket[1] = [1, 1] -> [12, 12] -> [23, 23]", hashTable.toString());*/
        Assertions.assertEquals(
                "\n bucket[0] = [22, 22]" +
                        "\n bucket[1] = [23, 23]" +
                        "\n bucket[32] = [11, 11]" +
                        "\n bucket[33] = [12, 12]" +
                        "\n bucket[48] = [0, 0]" +
                        "\n bucket[49] = [1, 1]", hashTable.toString());
        Assertions.assertEquals(6, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(64, hashTable.size());

        hashTable.drop("11");
        /*Assertions.assertEquals(
                "\n bucket[0] = [0, 0] -> [22, 22]" +
                        "\n bucket[1] = [1, 1] -> [12, 12] -> [23, 23]", hashTable.toString());*/
        Assertions.assertEquals(
                "\n bucket[0] = [22, 22]" +
                        "\n bucket[1] = [23, 23]" +
                        "\n bucket[33] = [12, 12]" +
                        "\n bucket[48] = [0, 0]" +
                        "\n bucket[49] = [1, 1]", hashTable.toString());
        hashTable.drop("0");
        /*Assertions.assertEquals(
                "\n bucket[0] = [22, 22]" +
                        "\n bucket[1] = [1, 1] -> [12, 12] -> [23, 23]", hashTable.toString());*/
        Assertions.assertEquals(
                "\n bucket[0] = [22, 22]" +
                        "\n bucket[1] = [23, 23]" +
                        "\n bucket[33] = [12, 12]" +
                        "\n bucket[49] = [1, 1]", hashTable.toString());
        hashTable.drop("22");
        /*Assertions.assertEquals("\n bucket[1] = [1, 1] -> [12, 12] -> [23, 23]", hashTable.toString());*/
        Assertions.assertEquals(
                "\n bucket[1] = [23, 23]" +
                        "\n bucket[33] = [12, 12]" +
                        "\n bucket[49] = [1, 1]", hashTable.toString());
        hashTable.drop("1");
        /*Assertions.assertEquals("\n bucket[1] = [12, 12] -> [23, 23]", hashTable.toString());*/
        Assertions.assertEquals(
                "\n bucket[1] = [23, 23]" +
                        "\n bucket[33] = [12, 12]", hashTable.toString());
        hashTable.drop("1");
        /*Assertions.assertEquals("\n bucket[1] = [12, 12] -> [23, 23]", hashTable.toString());*/
        Assertions.assertEquals(
                "\n bucket[1] = [23, 23]" +
                        "\n bucket[33] = [12, 12]", hashTable.toString());
        hashTable.drop("0");
        //Assertions.assertEquals( "\n bucket[1] = [12, 12] -> [23, 23]", hashTable.toString());
        Assertions.assertEquals("\n bucket[1] = [23, 23]\n bucket[33] = [12, 12]",hashTable.toString());
        Assertions.assertEquals(2, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(64, hashTable.size());

        hashTable.drop("12");
        Assertions.assertEquals("\n bucket[1] = [23, 23]", hashTable.toString());
        Assertions.assertEquals(1, hashTable.count());
        //Assertions.assertEquals(16, hashTable.size());
        Assertions.assertEquals(64, hashTable.size());
    }
}