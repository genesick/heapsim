package memory;

/**
 * This memory model allocates memory cells based on the first-fit method. 
 * 
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Används för uppgift 1 & 3
 * read first available block, and then continue until amount of available spaces in a row fits the memory space.
 */
public class FirstFit extends Memory {
	private LinkedList availableMemory;
	private TreeMap<Integer, Integer> memorySpace;
	/**
	 * Initializes an instance of a first fit-based memory.
	 * 
	 * @param size The number of cells.
	 */

	public FirstFit(int size) {
		super(size);
		availableMemory = new LinkedList(); //checks memory space if available or not
		for (int i = 0; i < size; i++) { //initialize empty spaces
			availableMemory.add(null);
		}
		memorySpace = new TreeMap<>();
		//integer = points to the processes (key)
		//string = the memory of the processes (value)
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
		int index = 0;
		// TODO Implement this!
		for (int i = 0; i < availableMemory.size(); i++) { //check if space is available first
			if (availableMemory.get(i) == null) {
				spaceCounter++;
				if (spaceCounter >= size) {
					System.out.println("space found");
					for (int j = index; j < spaceCounter; j++) {
						availableMemory.set(j, cells); //occupy space
					}
					Pointer tempPoint = new Pointer(spaceCounter, this);
					memorySpace.put(tempPoint, spaceCounter); // address + amt of space

					return new Pointer(spaceCounter, this);
					memorySpace.put(index, spaceCounter);
				}
			}
			else { // space occupied, reset counter
				spaceCounter = 0;
				index = i + 1; //index keeps track of where were moving
				System.out.println("occupied space");
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
		int address = p.pointsAt();
		for (Map.Entry<Integer, Integer> entry : memorySpace.entrySet()) { //iterate until you find a matching address
			if (memorySpace.containsKey(address)) { // if we find the key
				int sizeToRelease = entry.getValue(); //amt of space to remove
				for (int i = 0; i < sizeToRelease; i++) {

				}
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
