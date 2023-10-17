package memory;

import java.util.HashMap;

/**
 * This memory model allocates memory cells based on the best-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class BestFit extends Memory {
    private HashMap<Pointer, Integer> memorySpace;
    private int size;
    private int mapSize;
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

    /**
     * Releases a number of data cells
     * @param size The pointer to release.
     */
    @Override
    public Pointer alloc(int size) {
        Pointer bestFitPointer = null;
        if (size <= mapSize) {
            int bestFitSize = mapSize + 1; //initialize to min. largest value
            for (int i = 0; i < cells.length; i++) { //repeat until best found fit
                if (cells[i] == 0) {
                    int spaceCounter = 0;
                    int j = i;
                    while (j < cells.length && cells[j] == 0) { //check through whole array until blocked
                        spaceCounter++;
                        j++;
                    }
                    if (spaceCounter >= size && spaceCounter < bestFitSize) {
                        bestFitSize = spaceCounter;
                        bestFitPointer = new Pointer(i, this);
                    }
                    i = j; //skip to end of free block & repeat
                }
            }
            if (bestFitPointer != null) {
                memorySpace.put(bestFitPointer, size);
                //System.out.println("allocated " + size + " memory at " + bestFitPointer.pointsAt());
                return bestFitPointer;
            }
        }
        return new Pointer(-1, this);
    }

        @Override
        public void release (Pointer p){
            int sizeRemoved = 0;
            if (memorySpace.containsKey(p)) {
                int toRemove = memorySpace.get(p) + p.pointsAt();
                for (int i = p.pointsAt(); i < toRemove; i++) {
                    cells[i] = 0;
                    sizeRemoved = i; //only for print statement
                }
                memorySpace.remove(p);
                //System.out.println("removed " + sizeRemoved + " memory space at address: " + p.pointsAt());
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
        public void printLayout () {
            int currentBlockStart = 0;
            String currentBlockType = cells[0] == 0 ? "Free" : "Allocated";

            for (int i = 0; i < cells.length; i++) {
                if ((cells[i] == 0 && currentBlockType.equals("Allocated")) || (cells[i] != 0 && currentBlockType.equals("Free"))) {
                    System.out.printf("%d - %d | %s%n", currentBlockStart, i - 1, currentBlockType);
                    currentBlockStart = i;
                    currentBlockType = cells[i] == 0 ? "Free" : "Allocated";
                }
            }

            System.out.printf("%d - %d | %s%n", currentBlockStart, cells.length - 1, currentBlockType);
        }
    }
