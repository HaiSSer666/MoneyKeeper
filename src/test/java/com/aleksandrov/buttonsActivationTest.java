package com.aleksandrov;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import javafx.scene.input.KeyCode;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.api.FxAssert.verifyThat;

public class buttonsActivationTest extends MoneyKeeperGUITestAbstract{
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Start test 2.");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("End test 2. \n");
	}
	
	@After
	public void tearDownAfterEachTest() throws Exception {
		clickOn("#cancelButton");
	}
	
	@Test
	public void testAddButonWithNotFullData(){
		//check that button "add" stays deactivated by filling only one field
		clickOn("#textFieldSum").write("100");
		verifyThat("#addButton", NodeMatchers.isDisabled());
		System.out.println("2.1a. By filling only sum addButton stays deactivated");
		
		clickOn("#cancelButton");
		sleep(1000);
			
		clickOn("#textFieldCategory").write("food");
		verifyThat("#addButton", NodeMatchers.isDisabled());
		System.out.println("2.1b. By filling only category addButton stays deactivated");	
	}
	
	@Test
	public void testAddButonWithFullData(){
		//check that button "add" is activated by filling both fields
		clickOn("#textFieldSum").write("100");
		clickOn("#textFieldCategory").write("food");
		verifyThat("#addButton", NodeMatchers.isEnabled());
		System.out.println("2.2. By filling both fields addButton is activated");
		}
	
	@Test
	public void testAddButonWithFalseData(){
		//check that alert appears by giving the wrong data
		clickOn("#textFieldSum").write("k");
		clickOn("#textFieldCategory").write("food");
		clickOn("#addButton");
		verifyThat(".dialog-pane", NodeMatchers.isVisible());
		System.out.println("2.3. Alert has appeared after adding false data");
		push(KeyCode.ENTER);
		
	}
	
	@Test
	public void testCancelButtonIsEnabled(){
		//check cancelButton is activated by filling minimum one field
		clickOn("#textFieldSum").write("testSum");
		verifyThat("#cancelButton", NodeMatchers.isEnabled());
		System.out.println("2.4a. By filling only sum cancelButton is activated");
		clickOn("#cancelButton");
		sleep(500);
		
		clickOn("#textFieldCategory").write("testCategory");
		verifyThat("#cancelButton", NodeMatchers.isEnabled());
		System.out.println("2.4b. By filling only category cancelButton is activated");
		clickOn("#cancelButton");
		sleep(500);
		
		clickOn("#textAreaComment").write("testComment");
		verifyThat("#addButton", NodeMatchers.isDisabled());
		System.out.println("2.4c. By filling only comment cancelButton is activated");
		clickOn("#cancelButton");
		sleep(500);
		
		clickOn("#textFieldSum").write("testSum");
		clickOn("#textFieldCategory").write("testCategory");
		clickOn("#textAreaComment").write("testComment");
		verifyThat("#addButton", NodeMatchers.isEnabled());
		System.out.println("2.4d. By filling all fields cancelButton is activated");
	}
}

