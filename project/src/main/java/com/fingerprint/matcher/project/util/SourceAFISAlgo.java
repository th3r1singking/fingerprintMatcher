package com.fingerprint.matcher.project.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

public class SourceAFISAlgo {

	public static boolean match(FingerprintTemplate probe, FingerprintTemplate candidate, int threshold) throws Exception {
		
		double score = new FingerprintMatcher().index(probe).match(candidate);
		
		return score >= threshold;
	}
	
	public static void serializePrints(String path) throws Exception{
		
		BufferedWriter writer;
		File f = new File(path + "store.txt");
		if(!f.exists()) { 
			writer = new BufferedWriter(new FileWriter(path + "store.txt"));
		} else {
			writer = new BufferedWriter(new FileWriter(path + "store.txt", true));
		}
		
		//"E:\\Downloads\\Fingerprints\\fingerprints\\"
		final File folder = new File(path);
		
		try {
			for(final File print : folder.listFiles()) {
				if(!print.isDirectory() && !print.getAbsolutePath().endsWith(".xlsx")){
					byte[] printImage = Files.readAllBytes(Paths.get(print.getAbsolutePath()));
					
					
					FingerprintTemplate printTemplate = new FingerprintTemplate().dpi(500).create(printImage);
					String printJSON = printTemplate.serialize();
					
					writer.write(printJSON);
					writer.newLine();
				}
			}
		} catch (final Exception e) {
			System.out.println("File error. Make sure only picture (PNG, JPG) files are in fingerprint directory.");
		} finally {
			writer.close();
		}
	}
	
}
