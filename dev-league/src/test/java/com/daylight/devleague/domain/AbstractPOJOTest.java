package com.daylight.devleague.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;

import com.google.code.beanmatchers.BeanMatchers;
import com.google.code.beanmatchers.ValueGenerator;

public abstract class AbstractPOJOTest {

	@BeforeEach
	public void before() {
		BeanMatchers.registerValueGenerator(new ValueGenerator<LocalDateTime>() {
			public LocalDateTime generate() {
				Random r = new Random();
				return LocalDateTime.now().plus(r.nextInt(), ChronoUnit.MILLIS);
			}
		}, LocalDateTime.class);
	}
	
}
