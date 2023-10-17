package memory;

/**
 * This memory model allocates memory cells based on the first-fit method. 
 * 
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */

import java.awt.*;
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
		int spaceCounter = 0;
		int startOfSpace = 0;
		int endOfSpace = 0;
		int storage = 0;
		// TODO Implement this!
		Pointer tempPoint = new Pointer(0, this);

		for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) { //find first address
			startOfSpace = (entry.getKey() + entry.getValue()) + 1; //get address + size of it.
			
			if (memorySpace.higherKey(entry.getKey()) != null) { //if not out of loop
				endOfSpace = memorySpace.higherKey(entry.getKey());
				System.out.println("number: " + endOfSpace);
				storage = (startOfSpace-endOfSpace); //count how many spaces there are between the new value vs to the next address = endindex
				if (size <= storage) { // if the stepcounters size is equal or less than the size we want to allocate, then we can put memory in ther
					int address = endOfSpace+1;
					tempPoint = new Pointer(address, this);
					memorySpace.remove(entry.getKey());
					memorySpace.put(tempPoint.pointsAt(), spaceCounter); // store pointer + amt of space
					System.out.println("space found");
					return tempPoint;
				}
				else {
					memorySpace.put(tempPoint.pointsAt(), spaceCounter); // store pointer + amt of space
					System.out.println("space occupied");
					continue;
				}
			}
		}
		return tempPoint;
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
	 * 
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
				System.out.printf("| %4d - %3d | Free%n", lastEndAddress + 1, startAddress - 1);
			}

			// Print the allocated memory range.
			System.out.printf("| %4d - %3d | Allocated%n", startAddress, endAddress);

			lastEndAddress = endAddress;
		}

		if (lastEndAddress < size - 1) {
			// Print any remaining free space at the end.
			System.out.printf("| %4d - %3d | Free%n", lastEndAddress + 1, size - 1);
		}
	}
	/**
	 * Compacts the memory space.
	 */
	public void compact() {
		// TODO Implement this!
	}
}
