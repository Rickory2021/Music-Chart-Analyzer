

import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Random;

public class BrokenApp {
	static long RandomSeed = 1;
	static Random RandomGenerator = new Random(RandomSeed);

    private static ArrayList<Integer> generateIntArrayList(int howMany) {
		ArrayList<Integer> list = new ArrayList<Integer>(howMany);
		for(int i = 0; i < howMany; i++) {
			list.add(Integer.valueOf(RandomGenerator.nextInt()));
		}
		
		return list;
	}

	private static ArrayList<Integer> generateStrikeList(ArrayList<Integer> fromList, int howMany) {
		ArrayList<Integer> strikeList = new ArrayList<Integer>(howMany);
		int fromLast = fromList.size() - 1;
		
		for(int i = 0; i < howMany; i++) {
			strikeList.add(fromList.get(RandomGenerator.nextInt(fromLast)));
		}
		
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
System.out.println(intlist);
System.out.println(strikes);
System.out.println(removeList);
		long start;
		long end;
		long ms;

        TwoFourTree theTree = new TwoFourTree();
		
        System.out.printf("  TwoFourTree ");

        start = System.currentTimeMillis();
        for (Integer e: intlist) {
            theTree.addValue(e.intValue());
            //theTree.printInOrder();System.out.println();
        }
        end = System.currentTimeMillis();
        ms = end - start;
		System.out.printf("add: %,6dms  ", ms);
        start = System.currentTimeMillis();
        executeFinds(theTree, strikes);
        end = System.currentTimeMillis();
        ms = end - start;
		System.out.printf("find: %,6dms  ", ms);

        if(includeRemoves) {
            start = System.currentTimeMillis();
            for (Integer e: removeList) {
                System.out.printf("----- delete %d from tree\n", e.intValue());
                theTree.printInOrder();
                theTree.deleteValue(e.intValue());
            }
            end = System.currentTimeMillis();
            ms = end - start;
            System.out.printf("del: %,6dms  ", ms);

            start = System.currentTimeMillis();
            executeFinds(theTree, strikes);
            end = System.currentTimeMillis();
            ms = end - start;
            System.out.printf("find: %,6dms  ", ms);
        }
	
		System.out.printf("\n");
		System.out.println();
		//System.out.println(intlist);
        theTree.printInOrder();
        //theTree.printAllInfo();
	}

    public static void main(String[] args) throws Exception {
    	System.out.println("FROM OLD APP");
        TwoFourTree tft = new TwoFourTree();
        /*tft.addValue(2);
        tft.printInOrder();
        tft.printAllInfo();
        System.out.println();
        tft.addValue(3);

        tft.printInOrder();
        System.out.println();
        tft.addValue(5);
        tft.printInOrder();
        System.out.println();
        tft.addValue(7);
        tft.printInOrder();System.out.println();
        
        tft.addValue(11);
        tft.printInOrder();
        System.out.println();
        tft.addValue(13);
        tft.printInOrder();System.out.println();
        tft.addValue(17);
        tft.printInOrder();System.out.println();
        tft.addValue(19);
        tft.printInOrder();System.out.println();
        tft.addValue(23);
        tft.printInOrder();System.out.println();
        tft.addValue(29);
        tft.printInOrder();System.out.println();
        tft.addValue(31);
        tft.printInOrder();System.out.println();
        tft.addValue(37);
        tft.printInOrder();System.out.println();
        tft.addValue(41);
        tft.printInOrder();System.out.println();
        tft.addValue(43);
        tft.printInOrder();System.out.println();
        tft.addValue(47);
        tft.printInOrder();System.out.println();
        tft.addValue(53);
        tft.printInOrder();System.out.println();
        tft.addValue(59);
        tft.printInOrder();System.out.println();
        tft.addValue(67);
        tft.printInOrder();System.out.println();
        tft.addValue(71);
        tft.printInOrder();System.out.println();
        tft.addValue(73);
        tft.printInOrder();System.out.println();
        tft.addValue(79);
        tft.printInOrder();System.out.println();
        tft.addValue(83);
        tft.printInOrder();System.out.println();
        tft.addValue(89);
        tft.printInOrder();System.out.println ();
        tft.addValue(97);
        tft.printInOrder();System.out.println();
        tft.printAllInfo();

        System.out.println("Static test: first few prime numbers:");
        tft.printInOrder();
        
        /*tft.deleteValue(37);
        System.out.println("\nWithout 37:");
        tft.printInOrder();
        tft.deleteValue(73);
        System.out.println("\nWithout 73:");
        tft.printInOrder();
        tft.deleteValue(97);
        System.out.println("\nWithout 97:");
        tft.printInOrder();*/
        
        executeIntCase(10, 10, true);
        //executeIntCase(1000, 200, false);
        //executeIntCase(10000, 2000, false);S
        //executeIntCase(100000, 20000, false);
        //executeIntCase(1000000, 200000, false);
        //executeIntCase(10000000, 2000000, false);
    }
}