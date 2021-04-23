package com.daylight.devleague.domain.stats.dimension;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.daylight.devleague.domain.AbstractPOJOTest;
import com.daylight.devleague.domain.stats.dimensions.SprintSummaryStatsDimension;

public class SprintSummaryStatsDimensionTest extends AbstractPOJOTest {

	@Test
    public void testBean() {
        assertThat(SprintSummaryStatsDimension.class, allOf(
                hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCode(),
                hasValidBeanEquals(),
                hasValidBeanToString()
        ));
    }
	
}
