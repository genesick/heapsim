package memory;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * This memory model allocates memory cells based on the best-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class BestFit extends Memory {
    private ArrayList<Integer> freeMemory = new ArrayList<>();
    private ArrayList<Integer> endSpace = new ArrayList<>();
    private int mapSize;
    private TreeMap<Integer, Integer> memorySpace;
    /**
     * Används för uppgift 2
     */

    /**
     * Initializes an instance of a best fit-based memory.
     *
     * @param size The number of cells.
     */
    public BestFit(int size) {
        super(size);
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
        int nextFreeSpace = 0;
        int endOfSpace = 0;

        if (memorySpace.isEmpty()) {
            tempPoint = new Pointer(0, this);
            memorySpace.put(tempPoint.pointsAt(), size);
            return tempPoint;
        }
        System.out.println("----------------" + "SIZE TO ALLOCATE: " + size + " ----------------");

        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            nextFreeSpace = entry.getKey() + entry.getValue();
            if ((nextFreeSpace + size) <= mapSize) {
                if (memorySpace.higherKey(entry.getKey()) != null) { //if there is a higher key
                    endOfSpace = memorySpace.higherKey(entry.getKey());
                    if ((endOfSpace-nextFreeSpace) >= size) { //if there is enough space
                        freeMemory.add(nextFreeSpace);
                        endSpace.add(endOfSpace);
                        continue;
                    }
                } else { //if there is no higherkey, we can append it directly
                    freeMemory.add(nextFreeSpace);
                    endSpace.add(mapSize);
                    break;
                } if (memorySpace.lowerKey(entry.getKey()) == null) { //there is no lower key than entry.getKey
                    if (entry.getKey() != 0) {//if its not 0, lets see if we can add memory from 0
                        if (entry.getKey() >= size) { //if there is space to add without overriding, then allocate OK
                            tempPoint = new Pointer(0, this);
                            memorySpace.put(tempPoint.pointsAt(), size);
                            System.out.println("3Allocated: " + tempPoint.pointsAt() + " - " + (tempPoint.pointsAt() + size));
                            return tempPoint;
                        }
                    }
                }
            }
        }

        //check where most optimal size is
        int smallestGap = Integer.MAX_VALUE;
        int newAddress = -1; // Initialize with an invalid address

        for (int i = 0; i < freeMemory.size(); i++) {
            int currentGap = endSpace.get(i) - freeMemory.get(i);

            if (currentGap <= smallestGap) {
                smallestGap = currentGap;
                newAddress = freeMemory.get(i);
            }

            //System.out.println("Free Memory: " + freeMemory.get(i));
            //System.out.println("End Space: " + endSpace.get(i));
        }

       // System.out.println("Best fit address: " + newAddress);
        tempPoint = new Pointer(newAddress, this);
        memorySpace.put(tempPoint.pointsAt(), size);
        //System.out.println("Allocated: " + tempPoint.pointsAt() + " - " + (tempPoint.pointsAt() + size));

        freeMemory.clear();
        endSpace.clear();

        System.out.println("----------------------------------------");

        return tempPoint; //return invalid pointer
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {
        if (memorySpace.containsKey(p.pointsAt())) {
            memorySpace.remove(p.pointsAt());
            System.out.println("Released " + p.pointsAt());
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

        for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) {
            int blockStart = entry.getKey();
            int blockSize = entry.getValue();

            //check for gaps between the previous block and the current block
            if (currentBlockStart != -1 && blockStart > currentBlockStart + 1) {
                System.out.printf("| %4d - %4d | %s%n", currentBlockStart + 1, blockStart - 1, "Free");
            }

            System.out.printf("| %4d - %4d | %s%n", blockStart, blockStart + blockSize - 1, "Allocated");

            currentBlockStart = blockStart + blockSize - 1;
        }

        if (currentBlockStart < mapSize - 1) {
            System.out.printf("| %4d - %4d | %s%n", currentBlockStart + 1, mapSize - 1, "Free");
        }
    }
}
