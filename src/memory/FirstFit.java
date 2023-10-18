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
        Pointer tempPoint = new Pointer(-1, this);
        // TODO Implement this!
        if (size <= mapSize) {
            int address = findNextAddress(size);
            if (address != -1) {
                tempPoint = new Pointer(address, this);
                memorySpace.put(tempPoint, size);
                return tempPoint;
            }
        }
        return tempPoint; //return invalid pointer
    }


    private int findNextAddress(int size) {
        int spaceCounter = 0;
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == 0) {
                spaceCounter++;
                for (int j = i; j < cells.length; j++) {
                    if (cells[j] == 0) {
                        spaceCounter++;
                        if (spaceCounter == size) { //return when free cells fits
                            return i;
                        }
                    } else { //restart
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
        // TODO Implement this!
        // Check if the Pointer p is in the memorySpace map
        if (memorySpace.containsKey(p)) {
            int toRemove = memorySpace.get(p) + p.pointsAt();
            for (int i = p.pointsAt(); i < toRemove; i++) {
                cells[i] = 0;
                sizeRemoved = i;
            }
            memorySpace.remove(p);
           // System.out.println("removed " + sizeRemoved + " memory space at address: " + p.pointsAt());
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

    public void printTable() {
        for (Map.Entry<Pointer, Integer> entry : memorySpace.entrySet()) {
            //System.out.println("address: " + entry.getKey() + ", " + entry.getValue());
        }
    }
}
