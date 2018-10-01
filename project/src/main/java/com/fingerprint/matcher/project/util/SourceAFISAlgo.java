package com.fingerprint.matcher.project.util;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

public class SourceAFISAlgo {

	public static boolean match(String toMatch, String matchAgainst, double threshold) throws Exception {
		
		byte[] probeImage = Files.readAllBytes(Paths.get(toMatch));
		byte[] candidateImage = Files.readAllBytes(Paths.get(matchAgainst));

		FingerprintTemplate probe = new FingerprintTemplate().dpi(500).create(probeImage);

		FingerprintTemplate candidate = new FingerprintTemplate().dpi(500).create(candidateImage);
		
		double score = new FingerprintMatcher().index(probe).match(candidate);
		
		boolean matches = score >= threshold;
		
		return matches;
	}
}
