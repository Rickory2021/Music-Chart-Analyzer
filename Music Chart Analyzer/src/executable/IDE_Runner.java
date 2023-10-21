package executable;

import java.util.Scanner;

public class IDE_Runner {
	public static final String version = "1.1.1";
	public static Scanner systemScanner;
	

	public static void main(String[] args) {
		systemScanner = new Scanner(System.in);
		
		System.out.println("Music Chart Analyzer");
		System.out.println(String.format("Version: %s\n", version));
		
		boolean continueStartMenu = true;
		while (continueStartMenu) {
			continueStartMenu = startMenu();
		}
		System.out.println("Executable Terminated");
	}
	
	public static boolean startMenu() {
		String line;
		int num;
		boolean continueStartLoop = true;
		while (continueStartLoop) {
			line = getOption(getStartMenuText());
			num = toInteger(line);
			if (line.toLowerCase().equals("Open Billboard.com Executable".toLowerCase()) || num == 1) {
				BillboardComExecutable BBComExecutable = new BillboardComExecutable(systemScanner);
				System.out.println("Loading Billboard.Com Executable");
				BBComExecutable.execute();
			}else if (line.toLowerCase().equals("Open Text Convertor Executable".toLowerCase()) || num == 2) {
				TextConverterExecutable TextConvertorExecutable = new TextConverterExecutable(systemScanner);
				System.out.println("Loading Text Convertor Executable");
				TextConvertorExecutable.execute();
			}else if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
				continueStartLoop=false;
			}
		}
		return false;
	}
	
	public static String getStartMenuText() {
		StringBuilder menu = new StringBuilder();
		menu.append("Start Menu:\n");
		menu.append(String.format("%-31s\n", "1) Open Billboard.com Executable"));
		menu.append(String.format("%-31s\n", "2) Open Text Convertor Executable"));
		menu.append(String.format("%-31s\n", "0) Quit"));
		menu.append("Input: ");
		return menu.toString();
	}
	
	public static String getOption(String incitingText) {
		System.out.print(incitingText);
		return systemScanner.nextLine().trim();
	}

	public static int toInteger(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return Integer.MIN_VALUE;
		}
	}

}
