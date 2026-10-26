import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the TreeMap class
 *
 * @author Ace Breaux
 * @version 5-21-2025
 */
public class TreeMapTest {
    @Test
    public void testConstructor() {
        TreeMap<Integer, String> test = new TreeMap<>();
        assertNull(test.get(1));
        assertEquals(0, test.size());
    }

    @Test
    public void testPutAndSize() {
        TreeMap<Integer, String> test = new TreeMap<>();
        assertEquals(0, test.size());

        test.put(3, "Root");
        test.put(2, "Root-Left");
        assertEquals(2, test.size());

        test.put(1, "Root-Left-Left");
        test.put(5, "Root-Right");
        test.put(4, "Root-Right-Left");
        assertEquals(5, test.size());
        test.put(4, null);
        assertEquals(5, test.size());

        // Precondition
        assertThrows(IllegalArgumentException.class, () -> test.put(null, null));
    }

    @Test
    public void testContainsKey() {
        TreeMap<Integer, String> test = new TreeMap<>();
        test.put(3, "3");
        test.put(2, "2");
        test.put(1, "1");
        test.put(5, "5");
        test.put(4, "4");

        assertTrue(test.containsKey(3));
        assertTrue(test.containsKey(1));
        assertFalse(test.containsKey(15));
    }

    @Test
    public void testGet() {
        TreeMap<Double, String> test = new TreeMap<>();
        test.put(1.5, "Root");
        test.put(1.4, "Root-Left");
        test.put(1.6, "Root-Right");
        test.put(1.7, "Root-Right-Right");
        test.put(1.55, "Root-Right-Left");
        test.put(1.3, "Root-Left-Left");
        test.put(1.45, "Root-Left-Right");

        // Main conditions
        assertEquals("Root", test.get(1.5));
        assertEquals("Root-Left-Right", test.get(1.45));
        assertEquals("Root-Right-Left", test.get(1.55));

        // Precondition
        assertThrows(IllegalArgumentException.class, () -> test.get(null));
    }

    @Test
    public void testToKeyArray() {
        TreeMap<String, Integer> test = new TreeMap<>();
        test.put("E", 5);
        test.put("A", 1);
        test.put("C", 3);
        test.put("B", 2);
        test.put("D", 4);
        test.put("I", 9);
        test.put("G", 7);
        test.put("F", 6);
        test.put("H", 8);

        // Cases for arrays that are too small or right-sized
        assertEquals("[A, B, C, D, E, F, G, H, I]",
                Arrays.toString(test.toKeyArray(new String[0])));
        assertEquals("[A, B, C, D, E, F, G, H, I]",
                Arrays.toString(test.toKeyArray(new String[9])));

        // Cases for arrays that are too big
        assertEquals("[A, B, C, D, E, F, G, H, I, null]",
                Arrays.toString(test.toKeyArray(new String[10])));
        assertEquals("[A, B, C, D, E, F, G, H, I, null, 11]",
                Arrays.toString(test.toKeyArray(new String[]{
                        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"})));

        // Precondition
        assertThrows(IllegalArgumentException.class, () -> test.toKeyArray(null));
    }
}
