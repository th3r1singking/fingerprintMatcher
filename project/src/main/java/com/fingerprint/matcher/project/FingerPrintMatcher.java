package com.fingerprint.matcher.project;

import java.io.File;

import com.fingerprint.matcher.project.util.ExcelFunctions;
import com.fingerprint.matcher.project.util.SourceAFISAlgo;
import org.apache.commons.lang.time.StopWatch;


public class FingerPrintMatcher {
	
	private static final String FILE_PATH = "E:\\Downloads\\Fingerprints\\DB3_B\\";
	private static StopWatch stopWatch = new StopWatch();

    public static void main( String[] args ) throws Exception {
    	
    	ExcelFunctions.createExcel("Data Set 1");
    	
    	File directory = new File(FILE_PATH);
    	File[] toMatch = directory.listFiles();
    	File[] matchAgainst = toMatch;
    	int[] thresholds = {95, 90, 85, 80, 70, 60, 50};

    	System.out.println("Running....");

    	for(int num : thresholds) {
    		if (toMatch != null) {
    			for (File toMatchChild : toMatch) {
    				if(matchAgainst != null) {
    					for (File matchAgainstChild : toMatch) {
    						
    						double threshold = num;
    						
    						String[] results = new String[6];
    						results[0] = String.valueOf(threshold);
    						
    						stopWatch.start();
    						
    						boolean match = SourceAFISAlgo.match(toMatchChild.getAbsolutePath(), matchAgainstChild.getAbsolutePath(), threshold);
    						results[1] = match == true ? "1" : "0";
    						results[2] = toMatchChild.getAbsolutePath() == matchAgainstChild.getAbsolutePath() ? "0" : "1";
    						stopWatch.split();
    						results[3] = stopWatch.toSplitString();
    						stopWatch.stop();
    						stopWatch.reset();
    						
    						ExcelFunctions.writeToExcel(results);
    					}
    				}
    			}
    		}
    	}
    	System.out.println("Done");
    }
    
}
