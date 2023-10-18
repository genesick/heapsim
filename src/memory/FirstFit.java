package memory;

/**
 * This memory model allocates memory cells based on the first-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */

import com.sun.source.tree.Tree;

import java.awt.*;
import java.util.*;

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
     * @param cells the number of cells to allocate.
     * @return The address of the first cell.
     */
    @Override
    public Pointer alloc(int cells) {
        Pointer tempPoint = new Pointer(-1, this);
        int startAddress = -1;
        int okSpace;

        if (memorySpace.isEmpty()) {
            for (int i = 0; i < mapSize; i++) {
                memorySpace.put(i, -1); // initialize memory space to all unallocated
            }
        }

        for (int i = 0; i <= mapSize; i++) { // find address here
            okSpace = 1;
            for (int j = i; j < i + cells; j++) {
                if (memorySpace.containsKey(j)) {
                    if (memorySpace.get(j) != -1) {
                        okSpace = 0;
                        i = j; //skip since address is occupied
                        break;
                    }
                }

            }
            if (okSpace == 1) { // if address not occupied, we know where to start
                startAddress = i;
                break;
            } else {
                //System.out.println("address occupied");
            }
        }

        if (startAddress != -1 && startAddress <= mapSize) {

                memorySpace.put(startAddress, cells);

            tempPoint = new Pointer(startAddress, this);
            System.out.println("allocate " + cells + " cells starting from address " + startAddress);
            return tempPoint;
        } else {
            System.out.println("cannot allocate");
        }

        return tempPoint;
    }


    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    public void release(Pointer p) {
        Iterator<Map.Entry<Integer, Integer>> iterator = memorySpace.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            if (p.pointsAt() == entry.getKey()) {
                iterator.remove(); // Safely remove the entry
                System.out.println("removed cells");
            }
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
        int currentAddress = 0;
        int size = cells.length;

        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            int startAddress = entry.getKey();
            int blockSize = entry.getValue();
            int endAddress = startAddress + blockSize - 1;

            // Print the free memory block if there's any gap before the allocated block
            if (currentAddress < startAddress) {
                int freeBlockEnd = startAddress - 1;
                System.out.printf("| %4d - %4d | Free%n", currentAddress, freeBlockEnd);
            }

            // Print the allocated memory block
            System.out.printf("| %4d - %4d | Allocated%n", startAddress, endAddress);

            currentAddress = endAddress + 1;
        }

        // Print any remaining free memory block after the last allocated block
        if (currentAddress < size) {
            int freeBlockEnd = size - 1;
            System.out.printf("| %4d - %4d | Free%n", currentAddress, freeBlockEnd);
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
            System.out.println();
        }
    }

    @Override
    public void printMap() {
        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            System.out.println("address: " + entry.getKey() + ", " + entry.getValue());
        }
    }
}
