package org.micro.rpc.util;

import java.util.Random;

/**
 * 随机数工具
 * @author jassassin
 * @date 2018年3月7日
 */
public final class RandomUtil {
	
	private static final Random RANDOM = new Random();

	public static final int nextInt(int startInclusive, int endExclusive){
		if (startInclusive > endExclusive){
			throw new RuntimeException("startInclusive: " + startInclusive + " must be smaller or equal to endExclusive:" + endExclusive);
		}
		
		if (startInclusive < 0){
			throw new RuntimeException("startInclusive must be non-negative.");
		}
		
		if (startInclusive == endExclusive){
			return startInclusive;
		}
		
		return startInclusive + RANDOM.nextInt(endExclusive - startInclusive); 
	}
	
	public static final int nextInt(int endExclusive){
		return nextInt(0, endExclusive);
	}
}
