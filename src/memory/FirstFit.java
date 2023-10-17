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
    private HashMap<Pointer, Integer> memorySpace;

    /**
     * Initializes an instance of a first fit-based memory.
     *
     * @param size The number of cells.
     */

    public FirstFit(int size) {
        super(size);
        this.size = size;
        mapSize = size;
        memorySpace = new HashMap<>();
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
        // TODO Implement this!
        if (size <= mapSize) {
            Pointer tempPoint;
            int address = findNextAddress(size);
            if (address != -1) {
                tempPoint = new Pointer(address, this);
                memorySpace.put(tempPoint, size);
                System.out.println("allocated " + size + " memory at " + address);
                return tempPoint;
            }
        }
        return new Pointer(-1, this);
    }

    private int findNextAddress(int size) {
        int spaceCounter = 0;
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == 0) {
                spaceCounter++;
                for (int j = i; j < cells.length; j++) {
                    if (cells[j] == 0) {
                        spaceCounter++;
                        if (spaceCounter == size) {
                            return i;
                        }
                    } else { // if not empty
                        spaceCounter = 0;
                        break;
                    }
                }
            }
        }
        return -1;
    }


    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {
        int sizeRemoved = 0;
        // Check if the Pointer p is in the memorySpace map
        if (memorySpace.containsKey(p)) {
            int toRemove = memorySpace.get(p) + p.pointsAt();
            for (int i = p.pointsAt(); i < toRemove; i++) {
                cells[i] = 0;
                sizeRemoved = i;
            }
            memorySpace.remove(p);
            System.out.println("removed " + sizeRemoved + " memory space at address: " + p.pointsAt());
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

        for (Map.Entry<Pointer, Integer> entry : memorySpace.entrySet()) {
            int startAddress = entry.getKey().pointsAt();
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
        for (Map.Entry<Pointer, Integer> entry : memorySpace.entrySet()) {
            //System.out.println("address: " + entry.getKey() + ", " + entry.getValue());
        }
    }
}
