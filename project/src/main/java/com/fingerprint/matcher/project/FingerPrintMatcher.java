package com.fingerprint.matcher.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang.time.StopWatch;

import com.fingerprint.matcher.project.util.BasicAlgo;
import com.fingerprint.matcher.project.util.ExcelFunctions;
import com.fingerprint.matcher.project.util.SourceAFISAlgo;
import com.machinezoo.sourceafis.FingerprintTemplate;


public class FingerPrintMatcher {
	
	private static StopWatch stopWatch = new StopWatch();
	public static String DIRECTORY = null;

    public static void main( String[] args ) throws Exception {
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("Please enter path of finderprints :");
    	DIRECTORY = scanner.nextLine().replace("\\", "\\\\");
    	
    	System.out.println("Are there only fingerprint images in the file path described (Y/N) :");
    	String only = scanner.nextLine();
    	
    	if(only.equalsIgnoreCase("Y")) {
    		System.out.println("Do you want to serialize all prints (Y/N):");
    		String serializeChoice = scanner.nextLine();
    		
    		if(serializeChoice.equalsIgnoreCase("Y")) {
    			System.out.println("Serializing...");
    			SourceAFISAlgo.serializePrints(DIRECTORY);
    			
    			System.out.println("Matching Prints...");
    			matchSourceAFIS();
    		} else if (serializeChoice.equalsIgnoreCase("N")) {
    			System.out.println("Matching Prints...");
    			matchSourceAFIS();    			
    		} else {
    			System.out.println("Please remove all images that are not files and try again.");
    		}
    		
    		System.out.println("Done with SourceAFIS algorithm... Running Basic algorithm now...");
    		matchBasic();
    		
    	} else {
    		System.out.println("Please make sure theres only fingerprint images... ");
    	}
    	
    	scanner.close();
    	
    }
    
    public static void matchBasic() throws Exception {
    	
    	File folder = new File(DIRECTORY);
    	File[] printStore = folder.listFiles();
    	File[] printsToMatch = folder.listFiles();

    	int[] thresholds = {100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50};

		for(int num : thresholds) {
    		
    		System.out.println("Running for threshold : " + num);
    		ExcelFunctions.createExcel("Basic_Threshold_" + num, "Basic_Threshold_" + num + ".xlsx");
    		
			for (File probe : printStore) {
				if(!probe.isDirectory() && !probe.getAbsolutePath().endsWith(".xlsx")) {
					for (File candidate : printsToMatch) {
						if(!candidate.isDirectory() && !candidate.getAbsolutePath().endsWith(".xlsx")) {
							int threshold = num;
							
							String[] results = new String[6];
							results[0] = String.valueOf(threshold);
							
							stopWatch.start();
							boolean match = BasicAlgo.matchFingerprint(probe, candidate, threshold);
							results[1] = match == true ? "1" : "0";
							results[2] = probe.getAbsolutePath().equals(candidate.getAbsolutePath()) ? "0" : "1";
							stopWatch.split();
							results[3] = stopWatch.toSplitString();
							results[4] = "0";
							results[5] = "0";
							stopWatch.stop();
							stopWatch.reset();
							
							ExcelFunctions.writeToExcel(results, "Basic_Threshold_" + num + ".xlsx");
						}
					}
				}
			}
		}
    	
    	System.out.println("Done!");
    }
    
    public static void matchSourceAFIS() throws Exception {
    	
    	List<String> printStore = new ArrayList<String>();
    	try {
    		
			FileInputStream stream = new FileInputStream(DIRECTORY + "store.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

			String line;
			while ((line = reader.readLine()) != null) {
				printStore.add(line);
			}
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	List<String> printsToMatch = printStore;

    	int[] thresholds = {100, 95, 90, 85, 80, 75, 70, 65, 60, 55, 50};
    	
    	for(int num : thresholds) {
    		
    		System.out.println("Running for threshold : " + num);
    		ExcelFunctions.createExcel("AFIS_Threshold_" + num, "AFIS_Threshold_" + num + ".xlsx");
    		
			for (String probe : printStore) {
				for (String candidate : printsToMatch) {
					int threshold = num;
					
					String[] results = new String[6];
					results[0] = String.valueOf(threshold);
					
					stopWatch.start();
					boolean match = SourceAFISAlgo.match(
							new FingerprintTemplate().deserialize(probe),
							new FingerprintTemplate().deserialize(candidate),
							threshold);
					results[1] = match == true ? "1" : "0";
					results[2] = probe.equals(candidate) ? "0" : "1";
					stopWatch.split();
					results[3] = stopWatch.toSplitString();
					results[4] = "0";
					results[5] = "0";
					stopWatch.stop();
					stopWatch.reset();
					
					ExcelFunctions.writeToExcel(results, "AFIS_Threshold_" + num + ".xlsx");
				}
			}
		}
    	
    	System.out.println("Done!");
    }
}
