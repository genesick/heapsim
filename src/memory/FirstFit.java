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
        int startOfSpace = 0;
        int endOfSpace = 0;
        Pointer tempPoint;

        // Initialize memorySpace if it's empty
        if (memorySpace.isEmpty()) {
            for (int i = 0; i < mapSize; i++) {
                memorySpace.put(-1, 0);
            }
        }

        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            startOfSpace = entry.getKey();
            endOfSpace = entry.getKey() + entry.getValue();
            if (memorySpace.higherKey(endOfSpace) != null) {
                int startAddress = memorySpace.higherKey(endOfSpace);
            }
            if (startOfSpace != -1) {
                int freeBlock = endOfSpace - startOfSpace;
                if (freeBlock >= size) {//if  enough space for allocation

                    tempPoint = new Pointer(startOfSpace, this);
                   // memorySpace.remove(startOfSpace); // Remove the old free space entry
                    // If the free space is larger than the requested size, split it
                    int newFreeStart = startOfSpace + size;
                    int newFreeSize = freeBlock - size;
                    memorySpace.put(newFreeStart, newFreeSize);

                    System.out.println("allocated " + size + " memory between " + tempPoint.pointsAt() + " and " + (tempPoint.pointsAt() + size));
                    return tempPoint;
                }
            }
        }
        // If no suitable space is found, return a pointer with address -1
        return new Pointer(-1, this);
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
            System.out.println("removed memory space at address: " + p.pointsAt());
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
    public void printLayout() {
        int cBlockStart = 0;
        String cblockType = cells[0] == 0 ? "Free" : "Allocated";

        for (int i = 0; i < cells.length; i++) {
            if ((cells[i] == 0 && cblockType.equals("Allocated")) || (cells[i] != 0 && cblockType.equals("Free"))) {
                System.out.printf("%d - %d | %s%n", cBlockStart, i - 1, cblockType);
                cBlockStart = i;
                cblockType = cells[i] == 0 ? "Free" : "Allocated";
            }
        }
        System.out.printf("%d - %d | %s%n", cBlockStart, cells.length - 1, cblockType);
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
}
