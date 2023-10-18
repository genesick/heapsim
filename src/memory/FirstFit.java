package memory;

/**
 * This memory model allocates memory cells based on the first-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */

import com.sun.source.tree.Tree;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Används för uppgift 1 & 3
 * read first available block, and then continue until amount of available spaces in a row fits the memory space.
 */
public class FirstFit extends Memory {
    private LinkedList availableMemory;
    private int size;
    private int memoryCounter = 0;
    private int mapSize;
    private TreeMap<Integer, Integer> memorySpace;

    /**
     * Initializes an instance of a first fit-based memory.
     *
     * @param size The number of cells.
     */

    public FirstFit(int size) {
        super(size);
        this.size = size;
        mapSize = size;
        memorySpace = new TreeMap<>();
        // TODO Implement this!
    }


    /**
     * Allocates a number of memory cells.
     *
     * @param size the number of cells to allocate.
     * @return The address of the first cell.
     */
    @Override
    public Pointer alloc(int size) {
        int startPoint = -1;
        for (int i = 0; i < mapSize - size; i++) {

        }
        Pointer tempPoint = new Pointer(-1, this);

        if (memorySpace.isEmpty()) {
            // If the memory map is empty, allocate memory at the beginning
            memorySpace.put(0, size);
            return tempPoint = new Pointer(0, this);
        }

        // Iterate through the memory space map
        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            int cBlockStart = entry.getKey();
            int cBlockSize = entry.getValue();

            if (memorySpace.higherKey(cBlockStart) != null) { // if there is an address next higher than the first one
                if ((cBlockSize + cBlockStart) >= size) {
                    int totalSize = cBlockStart + cBlockSize;
                    memorySpace.put(totalSize, size);
                    System.out.println("next address : " + totalSize);
                    return tempPoint = new Pointer(totalSize, this);
                }
            }
            System.out.println("start block " + cBlockStart);
            System.out.println("size of block " + cBlockSize);
        }

        // If no suitable block is found, allocate memory at the end
        int lastBlockStart = memorySpace.lastEntry().getKey();
        memorySpace.put(lastBlockStart + size, 0); // Add a new block at the end
        return tempPoint = new Pointer(lastBlockStart, this);
    }



    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {
        // TODO Implement this!

        if (memorySpace.containsKey(p.pointsAt())) { // if we find the key
            memorySpace.remove(p.pointsAt());
        }
    }

    /**
     * Prints a simple model of the memory. Example:
     * <p>
     * |    0 -  110 | Allocated
     * |  111 -  150 | Free
     * |  151 -  999 | Allocated
     * | 1000 - 1024 | Free
     */
    @Override
    public void printLayout() {
        int lastEndAddress = -1;

        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            int startAddress = entry.getKey();
            int endAddress = startAddress + entry.getValue();

            if (lastEndAddress >= 0) {
                // Print the free space between the last end address and the current start address.
                System.out.printf("| %4d - %4d | Free%n", lastEndAddress + 1, startAddress - 1);
            }

            // Print the allocated memory range.
            System.out.printf("| %4d - %4d | Allocated%n", startAddress, endAddress);

            lastEndAddress = endAddress;
        }

        if (lastEndAddress < size - 1) {
            // Print any remaining free space at the end.
            System.out.printf("| %4d - %4d | Free%n", lastEndAddress + 1, size - 1);
        }
    }

    /**
     * Compacts the memory space.
     */
    public void compact() {
        // TODO Implement this!
    }

    public void printTable() {
        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            System.out.println("address: " + entry.getKey() + ", " + entry.getValue());
        }
    }

    @Override
    public void printMap() {
        System.out.println(memorySpace.toString());
    }
}
