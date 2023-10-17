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
	private TreeMap<Pointer, Integer> memorySpace;
	/**
	 * Initializes an instance of a first fit-based memory.
	 * 
	 * @param size The number of cells.
	 */

	public FirstFit(int size) {
		super(size);
		memorySpace = new TreeMap<>();
		memorySpace.put(new Pointer(0, this), 0);
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

		for (Map.Entry<Pointer, Integer> entry : memorySpace.entrySet()) {
			Pointer holdP = entry.getKey();
			startOfSpace = (holdP.pointsAt() + entry.getValue()); //get address + size of it.

			Map.Entry<Pointer, Integer> nextEntry = memorySpace.ceilingEntry(holdP);
			if (nextEntry != null) {
				endOfSpace = memorySpace.get(nextEntry.getKey());
				storage = (startOfSpace-endOfSpace); //count how many spaces there are between the new value vs to the next address = endindex
				if (size <= storage) { // if the stepcounters size is equal or less than the size we want to allocate, then we can put memory in ther
					int address = endOfSpace+1;
					Pointer tempPoint = new Pointer(address, this);
					memorySpace.put(tempPoint, spaceCounter); // store pointer + amt of space
					System.out.println("space found");
					return tempPoint;
				}
				else {
					System.out.println("space occupied");
				}
			}

		}

		return null;
	}
	
	/**
	 * Releases a number of data cells
	 * 
	 * @param p The pointer to release.
	 */
	@Override
	public void release(Pointer p) {
		// TODO Implement this!
		for (Map.Entry<Pointer, Integer> entry : memorySpace.entrySet()) { //iterate until you find a matching address
			if (memorySpace.containsKey(p)) { // if we find the key
				memorySpace.remove(p);
			}
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
		int startIndex = 0;

		for (int i = 0; i < availableMemory.size(); i++) {
			if (availableMemory.get(i) == null) {
				// This cell is free; continue to the next cell.
				continue;
			} else {
				// This cell is allocated; print the range.
				if (startIndex == i) {
					// Start of an allocated range; update startIndex.
					startIndex = i;
				} else {
					// End of an allocated range; print the range information.
					int endIndex = i - 1;
					System.out.println("| " + startIndex + " - " + endIndex + " | Allocated");
					startIndex = i;
				}
			}
		}

		// After the loop, check if there's an allocated range at the end.
		if (startIndex < availableMemory.size() - 1) {
			int endIndex = availableMemory.size() - 1;
			System.out.println("| " + startIndex + " - " + endIndex + " | Allocated");
		}
	}
	
	/**
	 * Compacts the memory space.
	 */
	public void compact() {
		// TODO Implement this!
	}
}
