package com.aleksandrov;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxRobotException;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.api.FxAssert.verifyThat;

public class initialStateTest extends MoneyKeeperGUITestAbstract{
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Start test 1.");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("End test 1. \n");
	}
	
	@After
	public void tearDown() throws Exception {
		sleep(100);
	}
	
	@Test(expected = FxRobotException.class)
	public void clickNonexistentElement() {
		clickOn("Nonexistent Element");
	}
	
	@Test
	public void checkButtonsAreDisabled() {	
		//check that button are initially disabled
		verifyThat("#addButton", NodeMatchers.isDisabled());
		verifyThat("#cancelButton", NodeMatchers.isDisabled());
		System.out.println("1.1. Buttons are disabled");
	}	
	
	@Test
	public void checkLabelsAreZero() {			
		//check initial state of labels
		verifyThat("#labelKost_ID", NodeMatchers.hasText("0.0"));
		verifyThat("#labelGain_ID", NodeMatchers.hasText("0.0"));
		verifyThat("#labelDifference_ID", NodeMatchers.hasText("0.0"));	
		System.out.println("1.2. Labels are 0.");
	}
		
	@Test
	public void checkTableViewIsEmpty() {			
		//check that TableView is empty
		Assert.assertEquals(0, tableOfKosts.getItems().size());
		System.out.println("1.3. Table is empty");
	}
	
	@Test
	public void checkTabsAreClickeable() {				
		//check that tabs are clickable
		clickOn("#pieChartTab").sleep(500).clickOn("#barChartTab").sleep(500).clickOn("#tableTab");
		System.out.println("1.4. Tabs are clickable");	
	}		
}
