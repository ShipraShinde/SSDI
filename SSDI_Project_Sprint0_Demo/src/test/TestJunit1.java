package test;

import org.junit.Test;

import com.model.ScheduleData;

import static org.junit.Assert.assertEquals;

public class TestJunit1 {
	String course = "SSDI";
	ScheduleData schedule = new ScheduleData(course);

	@Test
	public void testGetCourse() {	
		System.out.println("Inside testGetCourse()");    
		assertEquals(course, schedule.getSubject());    
		assertEquals("Ssdi", schedule.getSubject());    
	}
}