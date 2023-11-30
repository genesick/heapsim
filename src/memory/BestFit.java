package memory;

import java.util.Map;
import java.util.TreeMap;

/**
 * This memory model allocates memory cells based on the best-fit method.
 * 
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class BestFit extends Memory {
	private int mapSize;
	private TreeMap<Integer, Integer> memorySpace
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
		// TODO Implement this!
	}
}
