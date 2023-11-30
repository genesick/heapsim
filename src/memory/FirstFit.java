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

        if (memorySpace.isEmpty()) {
            tempPoint = new Pointer(0, this);
            memorySpace.put(tempPoint.pointsAt(), size);
            //System.out.println("1Allocated: " +  tempPoint.pointsAt() + " - " + (tempPoint.pointsAt() + size));
            return tempPoint;
        }

        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            if (memorySpace.lowerEntry(entry.getKey()) != null) {
                int endOfSpace = entry.getKey();
                int freeSpace = memorySpace.lowerEntry(entry.getKey()).getValue() + memorySpace.lowerEntry(entry.getKey()).getKey();
                //check if enough space to allocate something there
                //this part is for when we want to allocate memory in the middle
                if ((endOfSpace-freeSpace) >= size) { // if there is enough space to allocate, then OK
                    tempPoint = new Pointer(freeSpace, this);
                    memorySpace.put(tempPoint.pointsAt(), size);
                    return tempPoint;
                }
            }
            else if (memorySpace.lowerKey(entry.getKey()) == null) { //there is no lower key than entry.getKey
                if (entry.getKey() != 0) {//if its not 0, lets see if we can add memory from 0
                    if (entry.getKey() >= size) { //if there is space to add without overriding, then allocate OK
                        tempPoint = new Pointer(0, this);
                        memorySpace.put(0, size);
                        return tempPoint;
                    }
                }
            }

            if (entry.getKey() == memorySpace.lastKey()) { //if were at the end
                int freeSpace = entry.getKey() + entry.getValue();
                if (freeSpace <= mapSize) {
                    tempPoint = new Pointer(freeSpace, this);
                    memorySpace.put(tempPoint.pointsAt(), size);
                    return tempPoint;
                }
            }

        }
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
            //System.out.println("removed " + p.pointsAt());
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
        System.out.println("Memory Layout:");

        int currentBlockStart = -1;
        String currentBlockType = "Free";

        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            int blockStart = entry.getKey();
            int blockSize = entry.getValue();

            // Check for gaps between the previous block and the current block
            if (currentBlockStart != -1 && blockStart > currentBlockStart + 1) {
                System.out.printf("| %4d - %4d | %s%n", currentBlockStart + 1, blockStart - 1, "Free");
            }

            System.out.printf("| %4d - %4d | %s%n", blockStart, blockStart + blockSize - 1, "Allocated");

            // Update for the next iteration
            currentBlockStart = blockStart + blockSize - 1;
        }

        // Check for any remaining free space at the end
        if (currentBlockStart < mapSize - 1) {
            System.out.printf("| %4d - %4d | %s%n", currentBlockStart + 1, mapSize - 1, "Free");
        }
    }


}
