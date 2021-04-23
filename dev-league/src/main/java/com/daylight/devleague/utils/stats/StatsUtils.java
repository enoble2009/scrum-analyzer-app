package com.daylight.devleague.utils.stats;

import java.util.Collection;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class StatsUtils {

	public static double calculateStandardDeviation(Collection<Integer> intQueue) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		intQueue.forEach(x -> stats.addValue(x));
		return stats.getStandardDeviation();
	}
	
	public static double calculateAverage(Collection<Integer> sumStoryPoints) {
		return sumStoryPoints.stream().mapToInt(i -> i).average().orElse(0f);
	}
	
}
