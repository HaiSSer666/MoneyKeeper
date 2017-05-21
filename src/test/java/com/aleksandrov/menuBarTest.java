package com.aleksandrov;

import static org.testfx.api.FxAssert.verifyThat;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.matcher.base.NodeMatchers;

import javafx.scene.input.KeyCode;

public class menuBarTest extends MoneyKeeperGUITestAbstract{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Start test 4.");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("End test 4. \n");
	}
	
	public void populateTable() {
		//gain1
		clickOn("#radioGain").clickOn("#textFieldSum").write("850").clickOn("#textFieldCategory").write("Wage");
		clickOn("#textAreaComment").write("test comment").clickOn("#addButton");
		//gain2
		clickOn("#radioGain").clickOn("#textFieldSum").write("350").clickOn("#textFieldCategory").write("Credit");
		clickOn("#textAreaComment").write("test comment").clickOn("#addButton");
		//kost1
		clickOn("#textFieldSum").write("100").clickOn("#textFieldCategory").write("Food").clickOn("#addButton");
		//kost2
		clickOn("#textFieldSum").write("20").clickOn("#textFieldCategory").write("Smth").clickOn("#addButton");
		//kost3
		clickOn("#textFieldSum").write("200").clickOn("#textFieldCategory").write("Tickets").clickOn("#addButton");
	}
	
	@Test
	public void checkAllMenusAreClickable() {
		clickOn("#menuFile_ID").clickOn("#menuEdit_ID").clickOn("#menuHelp_ID");
		System.out.println("4.1. Menu is clickable");
	}
	
	@Test
	public void deleteButtonTest() {
		populateTable();
		Assert.assertEquals(5, tableOfKosts.getItems().size());
		clickOn("Food");
		clickOn("#menuEdit_ID").moveTo("#deleteItem").clickOn();
		Assert.assertEquals(4, tableOfKosts.getItems().size());
		clickOn("#menuEdit_ID").moveTo("#deleteAllItem").clickOn();
		push(KeyCode.ENTER);
		System.out.println("4.2. Delete works");
	}
	
	@Test
	public void deleteAllButtonTest() {
		populateTable();
		Assert.assertEquals(5, tableOfKosts.getItems().size());
		clickOn("#menuEdit_ID").moveTo("#deleteAllItem").clickOn();
		verifyThat(".dialog-pane", NodeMatchers.isVisible());
		push(KeyCode.ESCAPE);
		Assert.assertEquals(5, tableOfKosts.getItems().size());
		clickOn("#menuEdit_ID").moveTo("#deleteAllItem").clickOn();
		verifyThat(".dialog-pane", NodeMatchers.isVisible());
		push(KeyCode.ENTER);
		Assert.assertEquals(0, tableOfKosts.getItems().size());
		verifyThat("#labelKost_ID", NodeMatchers.hasText("0.0"));
		verifyThat("#labelGain_ID", NodeMatchers.hasText("0.0"));
		verifyThat("#labelDifference_ID", NodeMatchers.hasText("0.0"));	 
		sleep(1000);		
		System.out.println("4.3. DeleteAll works");
	}

	@Test
	public void helpMenuItemTest() {
		clickOn("#menuHelp_ID").moveTo("#aboutItem").clickOn();
		sleep(1000);
		//verifyThat(".dialog-pane", NodeMatchers.isVisible());
		clickOn("OK");
		System.out.println("4.4. Help dialog was shown");
	}
	
	//@Test(expected = NullPointerException.class)
	public void closeItemTest() {
		clickOn("#menuFile_ID").moveTo("#exitItem").clickOn();
		verifyThat(".anchor-pane", NodeMatchers.isVisible());
		System.out.println("4.5. App was closed");
	}
	
}
