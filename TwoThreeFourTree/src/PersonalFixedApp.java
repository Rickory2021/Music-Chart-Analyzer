import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class PersonalFixedApp {
	static long RandomSeed = 1;
	static Random RandomGenerator = new Random(RandomSeed);

    private static ArrayList<Integer> generateIntArrayList(int howMany) {
		ArrayList<Integer> list = new ArrayList<Integer>(howMany);
//System.out.println("\nGenerating Tree List\n");
		HashSet<Integer> hs=new HashSet<>();

		while(hs.size()<howMany){
			int num=RandomGenerator.nextInt();
			hs.add(num);
		}

		Iterator<Integer> it=hs.iterator();

		while(it.hasNext()){
			list.add(it.next());
		}
//System.out.println("\nFinished Tree List\n");
		return list;
	}

	private static ArrayList<Integer> generateStrikeList(ArrayList<Integer> fromList, int howMany) {
		ArrayList<Integer> strikeList = new ArrayList<Integer>(howMany);
		int fromLast = fromList.size();
//System.out.println("\nGenerating Strike List\n");
		HashSet<Integer> hs=new HashSet<>();
		
		while(hs.size()<howMany){
			int index=RandomGenerator.nextInt(fromList.size());
			hs.add(index);
		}
		
		Iterator<Integer> it=hs.iterator();
		
		while(it.hasNext()){
			strikeList.add(fromList.get(it.next()));
		}
//System.out.println("\nFinished Strike List\n");
		return strikeList;
	}

	private static ArrayList<Integer> generateRemoveList(ArrayList<Integer> fromList) {
		ArrayList<Integer> removeList = new ArrayList<Integer>(fromList.size()/2);
		
		for(int i = 0; i < fromList.size() / 2; i++) {
			removeList.add(fromList.get(i));
		}
		
		return removeList;
	}

	private static <T> int executeFinds(TwoFourTree coll, ArrayList<Integer> strikes) {
		boolean sentinel;
		int failures = 0;

		for (Integer e: strikes) {
			sentinel = coll.hasValue(e.intValue());
			if(sentinel == false) {
				failures++;
			}
		}
		
		if(failures > 0) {
			System.out.printf("(%,d missing) ", failures);
		}
		
		return 0;
	}

    public static void executeIntCase(int listSize, int strikeSize, boolean includeRemoves) {
		System.out.printf("CASE: %,d integers, %,d finds, %,d removals.  Generating...\n", listSize, strikeSize, strikeSize/2);

		ArrayList<Integer> intlist = generateIntArrayList(listSize);
		ArrayList<Integer> strikes = generateStrikeList(intlist, strikeSize);
        ArrayList<Integer> removeList = generateRemoveList(strikes);
		long start;
		long end;
		long ms;
//System.out.println(intlist.toString());
//System.out.println(strikes);
//System.out.println(removeList);
		TwoFourTree theTree = new TwoFourTree();
		
        System.out.printf("  TwoFourTree ");

        start = System.currentTimeMillis();
        for (Integer e: intlist) {
            theTree.addValue(e.intValue());
        }
        end = System.currentTimeMillis();
        ms = end - start;
		System.out.printf("add: %,6dms  ", ms);
//System.out.println();
//theTree.printInOrder();
        start = System.currentTimeMillis();
        executeFinds(theTree, strikes);
        end = System.currentTimeMillis();
        ms = end - start;
		System.out.printf("find: %,6dms  ", ms);

        if(includeRemoves) {
            start = System.currentTimeMillis();
            for (Integer e: removeList) {
//System.out.printf("----- delete %d from tree\n", e.intValue());
                theTree.deleteValue(e.intValue());
//theTree.printInOrder();
//System.out.println();
            }
            end = System.currentTimeMillis();
            ms = end - start;
            System.out.printf("del: %,6dms  ", ms);

            start = System.currentTimeMillis();
executeFinds(theTree, strikes);
//executeFinds(theTree, removeList);
            end = System.currentTimeMillis();
            ms = end - start;
            System.out.printf("find: %,6dms  ", ms);
        }
	
		System.out.printf("\n");
        // theTree.printInOrder();
	}

    public static void main(String[] args) throws Exception {
        TwoFourTree tft = new TwoFourTree();
        tft.printInOrder();
        tft.addValue(2);tft.printInOrder();System.out.println();
        tft.addValue(3);tft.printInOrder();System.out.println();
        tft.addValue(5);tft.printInOrder();System.out.println();
        tft.addValue(7);tft.printInOrder();System.out.println();
        tft.addValue(11);tft.printInOrder();System.out.println();
        tft.addValue(13);tft.printInOrder();System.out.println();
        tft.addValue(17);tft.printInOrder();System.out.println();
        tft.addValue(19);tft.printInOrder();System.out.println();
        tft.addValue(23);tft.printInOrder();System.out.println();
        tft.addValue(29);tft.printInOrder();System.out.println();
        tft.addValue(31);tft.printInOrder();System.out.println();
        tft.addValue(37);tft.printInOrder();System.out.println();
        tft.addValue(41);tft.printInOrder();System.out.println();
        tft.addValue(43);tft.printInOrder();System.out.println();
        tft.addValue(47);tft.printInOrder();System.out.println();
        tft.addValue(53);tft.printInOrder();System.out.println();
        tft.addValue(59);tft.printInOrder();System.out.println();
        tft.addValue(67);tft.printInOrder();System.out.println();
        tft.addValue(71);tft.printInOrder();System.out.println();
        tft.addValue(73);tft.printInOrder();System.out.println();
        tft.addValue(79);tft.printInOrder();System.out.println();
        tft.addValue(83);tft.printInOrder();System.out.println();
        tft.addValue(89);tft.printInOrder();System.out.println();
        tft.addValue(97);tft.printInOrder();System.out.println();

        System.out.println("Static test: first few prime numbers:");
        tft.printInOrder();
        
       /* System.out.println("\nDeleting 2:");
        tft.deleteValue(2);
        System.out.println("\nWithout 2:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 47:");
        tft.deleteValue(47);
        System.out.println("\nWithout 47:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 31:");
        tft.deleteValue(31);
        System.out.println("\nWithout 31:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 59:");
        tft.deleteValue(59);
        System.out.println("\nWithout 59:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 53:");
        tft.deleteValue(53);
        System.out.println("\nWithout 53:");
        tft.printInOrder();*/
        
        /*System.out.println("\nDeleting 41:");
        tft.deleteValue(41);
        System.out.println("\nWithout 41:");
        tft.printInOrder();*/
        
        /*System.out.println("\nDeleting 97:");
        tft.deleteValue(97);
        System.out.println("\nWithout 97:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 11:");
        tft.deleteValue(11);
        System.out.println("\nWithout 11:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 41:");
        tft.deleteValue(41);
        System.out.println("\nWithout 41:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 43:");
        tft.deleteValue(43);
        System.out.println("\nWithout 43:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 71:");
        tft.deleteValue(71);
        System.out.println("\nWithout 71:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 89:");
        tft.deleteValue(89);
        System.out.println("\nWithout 89:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 83:");
        tft.deleteValue(83);
        System.out.println("\nWithout 83:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 79:");
        tft.deleteValue(79);
        System.out.println("\nWithout 79:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 23:");
        tft.deleteValue(23);
        System.out.println("\nWithout 23:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 29:");
        tft.deleteValue(29);
        System.out.println("\nWithout 29:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 37:");
        tft.deleteValue(37);
        System.out.println("\nWithout 37:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 7:");
        tft.deleteValue(7);
        System.out.println("\nWithout 7:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 3:");
        tft.deleteValue(3);
        System.out.println("\nWithout 3:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 13:");
        tft.deleteValue(13);
        System.out.println("\nWithout 13:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 19:");
        tft.deleteValue(19);
        System.out.println("\nWithout 19:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 73:");
        tft.deleteValue(73);
        System.out.println("\nWithout 73:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 5:");
        tft.deleteValue(5);
        System.out.println("\nWithout 5:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 17:");
        tft.deleteValue(17);
        System.out.println("\nWithout 17:");
        tft.printInOrder();
        
        System.out.println("\nDeleting 67:");
        tft.deleteValue(67);
        System.out.println("\nWithout 67:");
        tft.printInOrder();*/
        
        tft.deleteValue(37);
        System.out.println("\nWithout 37:");
        tft.printInOrder();
        tft.deleteValue(73);
        System.out.println("\nWithout 73:");
        tft.printInOrder();
        tft.deleteValue(97);
        System.out.println("\nWithout 97:");
        tft.printInOrder();

        executeIntCase(10, 10, true);
        executeIntCase(100, 20, true);
        executeIntCase(1000, 200, true);
        executeIntCase(10000, 2000, true);
        executeIntCase(100000, 20000, true);
        executeIntCase(1000000, 200000, true);
        executeIntCase(10000000, 2000000, true);
    }
}
