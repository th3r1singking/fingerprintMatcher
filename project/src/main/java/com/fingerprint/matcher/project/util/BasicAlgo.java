package com.fingerprint.matcher.project.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.io.File;

public class BasicAlgo {
	public static boolean matchFingerprint(File probe, File candidate, int threshold) {
		
		boolean response = false;
		
		try {

			Image probeImage = Toolkit.getDefaultToolkit().getImage(probe.getAbsolutePath());
			Image candidateImage = Toolkit.getDefaultToolkit().getImage(candidate.getAbsolutePath());
			
			try {
				
				PixelGrabber probeGrab = new PixelGrabber(probeImage, 0, 0, -1, -1, false);
				PixelGrabber candidateGrab = new PixelGrabber(candidateImage, 0, 0, -1, -1, false);
				
				int[] probeArray = null;
				
				if (probeGrab.grabPixels()) {
					int width = probeGrab.getWidth();
					int height = probeGrab.getHeight();
					probeArray = new int[width * height];
					probeArray = (int[]) probeGrab.getPixels();
				}
				
				int[] candidateArray = null;
				
				if (candidateGrab.grabPixels()) {
					int width = candidateGrab.getWidth();
					int height = candidateGrab.getHeight();
					candidateArray = new int[width * height];
					candidateArray = (int[]) candidateGrab.getPixels();
				}
				
				int diff = 0;
				for(int i = 0; i < probeArray.length; i++) {
					if(probeArray != null && candidateArray != null) {
						if(probeArray.length == candidateArray.length){
							if(probeArray[i] != candidateArray[i]) {
								diff++;
							}
						}
					}
				}
				
				if(threshold <= (probeArray.length - diff)/probeArray.length * 100) {
					response = true;
				}
				
			} catch (final Exception e1) {
				e1.printStackTrace();
			}

		} catch (final Exception e2) {
			System.out.println("Error - " + e2.getMessage());
		}
		
		return response;
	}
}