import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

class MyHashTableTester {
    @Test
    public void hashTableConstructor1Test(){
        MyHashTable test = new MyHashTable();
        assert(test.capacity() == 20);
    }

    @Test
    public void hashTableConstructor2Test(){
        MyHashTable test = new MyHashTable();
        assert(test.size() == 0);
    }

    @Test
    public void hashTableConstructor3Test(){
        MyHashTable test = new MyHashTable();
        assert(!test.lookup("Monkey"));
    }

    @Test
    public void hashTableConstructor4Test(){
        MyHashTable test = new MyHashTable(5);
        assert(test.capacity() == 5);
    }

    @Test
    public void hashTableConstructor5Test(){
        MyHashTable test = new MyHashTable(1000);
        assert(test.size() == 0);
    }

    @Test
    public void hashTableConstructor6Test(){
        MyHashTable test = new MyHashTable(55);
        assert(!test.lookup("Monkey"));
    }

    @Test
    public void hashTableConstructor7Test(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MyHashTable test = new MyHashTable(4);
            assert(!test.lookup("Monkey"));
        });
    }

    @Test
    public void hashTableInsertTest(){
        MyHashTable test = new MyHashTable();
        test.insert("Man");
        test.insert("Mantis");
        test.insert("Donkey");
        assert(test.lookup("Man"));
        assert(test.lookup("Mantis"));
        assert(test.size() == 3);
        assert(test.lookup("Donkey"));
        assert(!test.lookup("Hen"));
    }

    @Test
    public void hashTableInsert2Test(){
        MyHashTable test = new MyHashTable(5);
        test.insert("Man");
        test.insert("Hippy");
        test.insert("Maxim");
        test.insert("Many");
        test.insert("Hen");
        test.insert("Happy");
        assert(test.lookup("Man"));
        assert(test.lookup("Hippy"));
        assert(test.size() == 6);
        assert(test.capacity() == 10);
        assert(test.lookup("Maxim"));
        assert(test.lookup("Hen"));

    }

    @Test
    public void hashTableInsert3Test(){
        MyHashTable test = new MyHashTable();
        test.insert("Apple");
        test.delete("Apple");
        assert(test.insert("Apple"));
        assert(!test.insert("Apple"));
    }

    @Test
    public void hashTableInsert4Test(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            MyHashTable test = new MyHashTable(6);
            assert(test.insert(null));
        });
    }

    @Test
    public void hashTableRemoveTest(){
        MyHashTable test = new MyHashTable();
        test.insert("Man");
        test.insert("Mantis");
        test.insert("Donkey");
        assert(test.delete("Man"));
        assert(test.delete("Mantis"));
        assert(test.size() == 1);
        assert(test.delete("Donkey"));
        assert(!test.delete("Hen"));
    }

    @Test
    public void hashTableRemove2Test(){
        MyHashTable test = new MyHashTable(5);
        test.insert("Man");
        test.insert("Hippy");
        test.insert("Maxim");
        test.insert("Many");
        test.insert("Hen");
        test.insert("Happy");
        test.insert("Gibby");
        assert(test.lookup("Man"));
        assert(test.lookup("Hippy"));
        assert(test.size() == 7);
        assert(test.capacity() == 10);
        assert(test.delete("Maxim"));
        assert(test.delete("Hen"));

    }

    @Test
    public void hashTableRemove3Test(){
        MyHashTable test = new MyHashTable();
        test.insert("Apple");
        test.delete("Apple");
        assert(test.insert("Apple"));
        assert(test.delete("Apple"));
    }

    @Test
    public void hashTableRemove4Test(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            MyHashTable test = new MyHashTable(6);
            assert(test.delete(null));
        });
    }

    @Test
    public void hashTableLookupTest(){
        MyHashTable test = new MyHashTable();
        test.insert("Man");
        test.insert("Mantis");
        test.insert("Donkey");
        assert(!test.lookup("man"));
        assert(test.delete("Donkey"));
    }

    @Test
    public void hashTableLookup2Test(){
        MyHashTable test = new MyHashTable(5);
        test.insert("Man");
        test.insert("Hippy");
        test.insert("Maxim");
        test.insert("Many");
        test.insert("Hen");
        test.insert("Happy");
        test.insert("Gibby");
        assert(!test.lookup("man"));
        assert(test.lookup("Hippy"));
        assert(test.delete("Maxim"));
        assert(test.delete("Hen"));

    }

    @Test
    public void hashTableLookup3Test(){
        MyHashTable test = new MyHashTable();
        test.insert("Apple");
        test.delete("Apple");
        assert(!test.lookup("Apple"));
        test.insert("Apple");
        assert(test.lookup("Apple"));
    }

    @Test
    public void hashTableLookup4Test(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            MyHashTable test = new MyHashTable(6);
            assert(test.lookup(null));
        });
    }

    @Test
    public void hashTableReturnAllTest(){
        MyHashTable test = new MyHashTable();
        String[] returned = test.returnAll();
        assert(returned.length == 0);
    }

    @Test
    public void hashTableReturnAll2Test(){
        MyHashTable test = new MyHashTable(5);
        test.insert("Man");
        test.insert("Hippy");
        test.insert("Maxim");
        test.insert("Many");
        test.insert("Hen");
        test.insert("Happy");
        test.insert("Gibby");
        String[] truth = new String[]{"Man", "Hippy", "Maxim", "Many", "Hen", "Happy", "Gibby"};
        String[] returned = test.returnAll();
        assert(truth.length == returned.length);

    }

    @Test
    public void hashTableReturnAll3Test(){
        MyHashTable test = new MyHashTable();
        test.insert("Apple");
        test.delete("Apple");
        test.insert("Apple");
        assert(test.lookup("Apple"));
    }

    @Test
    public void hashTableSizeTest(){
        MyHashTable test = new MyHashTable();
        assert(test.size() == 0);
    }

    @Test
    public void hashTableSize2Test(){
        MyHashTable test = new MyHashTable(5);
        test.insert("Man");
        test.insert("Hippy");
        test.insert("Maxim");
        test.insert("Many");
        test.insert("Hen");
        test.insert("Happy");
        test.insert("Gibby");
        assert(test.size() == 7);

    }

    @Test
    public void hashTableSize3Test(){
        MyHashTable test = new MyHashTable();
        test.insert("Apple");
        test.delete("Apple");
        test.insert("Apple");
        assert(test.size() == 1);
    }

    @Test
    public void hashTableCapacityTest(){
        MyHashTable test = new MyHashTable(25);
        assert(test.capacity() == 25);
    }

    @Test
    public void hashTableCapacity2Test(){
        MyHashTable test = new MyHashTable(5);
        test.insert("Man");
        test.insert("Hippy");
        test.insert("Maxim");
        test.insert("Many");
        test.insert("Hen");
        test.insert("Happy");
        test.insert("Gibby");
        assert(test.capacity() == 10);

    }

    @Test
    public void hashTableCapacity3Test(){
        MyHashTable test = new MyHashTable(5);
        test.insert("Apple");
        test.delete("Apple");
        test.insert("Apple");
        assert(test.capacity() == 5);
    }

    @Test
    public void hashTablegetStatsTest(){
        MyHashTable test = new MyHashTable(25);
        assert(test.getStatsLog().equals(""));
    }

    @Test
    public void hashTablegetStats2Test(){
        MyHashTable test = new MyHashTable(5);
        test.insert("Man");
        test.insert("Hippy");
        test.insert("Maxim");
        test.insert("Many");
        test.insert("Hen");
        test.insert("Gibby");
        assert(test.getStatsLog().equals("Before rehash # 1: load factor 1.20, 2 collision(s).\n"));

    }

    @Test
    public void hashTablegetStats3Test(){
        MyHashTable test = new MyHashTable(5);
        test.insert("Man");
        test.insert("Hippy");
        test.insert("Maxim");
        test.insert("Many");
        test.insert("Hen");
        test.insert("Gibby");
        test.insert("Mans");
        test.insert("Hippys");
        test.insert("Maxims");
        test.insert("Manys");
        test.insert("Hens");
        test.insert("Gibbys");
        test.insert("Gibbons");
        assertEquals("Before rehash # 1: load factor 1.20, 2 collision(s).\n" +
                "Before rehash # 2: load factor 1.10, 5 collision(s).\n", test.getStatsLog());
    }

}