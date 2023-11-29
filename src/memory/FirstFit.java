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
        Pointer tempPoint = new Pointer(-1, this);
        // TODO Implement this!
        /* locate the next address by adding the address and memory space
        check if theres a lower key
        use higher key to find the next position
        lowerkey and higherkey to pinpoint space between for first fit
        * */

        if (memorySpace.isEmpty()) {
            tempPoint = new Pointer(0, this);
            memorySpace.put(tempPoint.pointsAt(), size);
            return tempPoint;
        }

            for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
                 if (entry.getKey() == memorySpace.lastKey()) { //if were at the end
                     /**
                      * issue here is if we remove something in the middle, then it will keep on stacking
                      * the values at the end when we want it to remove it in the middle
                      */
                     int freeSpace = memorySpace.lastKey() + entry.getValue();
                     if (freeSpace < mapSize) {
                         tempPoint = new Pointer(freeSpace, this);
                         memorySpace.put(tempPoint.pointsAt(), size);
                         System.out.println("Allocated: " +  tempPoint.pointsAt() + " - " + (tempPoint.pointsAt() + size));
                         return tempPoint;
                     }
                 }
                 if (memorySpace.lowerEntry(entry.getValue()) != null) {
                     System.out.println(memorySpace.lowerEntry(entry.getValue()).getValue());
                     int freeSpace = memorySpace.lowerEntry(entry.getValue()).getValue();
                     tempPoint = new Pointer(freeSpace, this);
                     memorySpace.put(tempPoint.pointsAt(), size);
                 }
            }

        //System.out.println("allocated " + size + " at address: " + tempPoint.pointsAt());

        return tempPoint; //return invalid pointer
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {
        // TODO Implement this!
        // Check if the Pointer p is in the memorySpace map
        if (memorySpace.containsKey(p.pointsAt())) {
            memorySpace.remove(p.pointsAt());
            System.out.println("removed " + p.pointsAt());
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
        //printTable();


    }

    public void printTable() {
        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            System.out.println("startAddress: " + entry.getKey() + ", " + (entry.getKey() + entry.getValue()));
          }
    }
}
