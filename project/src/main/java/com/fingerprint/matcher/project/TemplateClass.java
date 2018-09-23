package com.fingerprint.matcher.project;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

public class TemplateClass {

	public static void main(String[] args) throws Exception {
		
		byte[] probeImage = Files.readAllBytes(Paths.get("E:\\Downloads\\New folder\\socofing\\Real\\1__M_Left_index_finger.bmp"));
		byte[] candidateImage = Files.readAllBytes(Paths.get("E:\\Downloads\\New folder\\socofing\\Real\\1__M_Left_little_finger.bmp"));

		FingerprintTemplate probe = new FingerprintTemplate().dpi(500).create(probeImage);

		FingerprintTemplate candidate = new FingerprintTemplate().dpi(500).create(candidateImage);
		
		double score = new FingerprintMatcher().index(probe).match(candidate);
		
		double threshold = 40;
		
		boolean matches = score >= threshold;
		
		System.out.println(matches);
	}
}
