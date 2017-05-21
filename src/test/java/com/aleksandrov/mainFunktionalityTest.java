package com.aleksandrov;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.matcher.base.NodeMatchers;
import com.aleksandrov.model.Kost;
import com.aleksandrov.model.SpendType;

import javafx.scene.input.KeyCode;

import static org.testfx.api.FxAssert.verifyThat;

public class mainFunktionalityTest extends MoneyKeeperGUITestAbstract{
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Start test 3.");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("End test 3. \n");
	}
	
	@After
	public void tearDownAfterEachTest() throws Exception {
		clickOn("#menuEdit_ID").moveTo("#deleteAllItem").clickOn();
		push(KeyCode.ENTER);
	}
	
	
	@Test
	public void cancelButtonFunktionTest() {
		clickOn("#textFieldSum").write("150").clickOn("#textFieldCategory").write("Wage");
		clickOn("#textAreaComment").write("test comment for adding gain").clickOn("#radioGain");
		verifyThat("#textFieldSum", NodeMatchers.hasText("150"));
		verifyThat("#textFieldCategory", NodeMatchers.hasText("Wage"));
		verifyThat("#textAreaComment", NodeMatchers.hasText("test comment for adding gain"));
			
		clickOn("#cancelButton");
		verifyThat("#textFieldSum", NodeMatchers.hasText(""));
		verifyThat("#textFieldCategory", NodeMatchers.hasText(""));
		verifyThat("#textAreaComment", NodeMatchers.hasText(""));	
		
		System.out.println("3.1. Cancel button works");
		}
	
	@Test
	public void addingKostTest() {
		clickOn("#textFieldSum").write("100").clickOn("#textFieldCategory").write("Food").clickOn("#addButton");
		clickOn("Food");
		Kost currentItem = tableOfKosts.getSelectionModel().getSelectedItem();
		Assert.assertEquals(100.0, currentItem.getAmount(), 0);
		Assert.assertTrue("Food".equals(currentItem.getCategory()));
		Assert.assertEquals(SpendType.KOST, currentItem.getSpendType());
		Assert.assertTrue("".equals(currentItem.getComment()));
		
		System.out.println("3.2. Kost can be added");
	}

	@Test
	public void addingGainTest() {
		clickOn("#radioGain").clickOn("#textFieldSum").write("150").clickOn("#textFieldCategory").write("Wage");
		clickOn("#textAreaComment").write("test comment for adding gain").clickOn("#addButton");
		clickOn("Wage");
		Kost currentItem = tableOfKosts.getSelectionModel().getSelectedItem();
		Assert.assertEquals(150.0, currentItem.getAmount(), 0);
		Assert.assertTrue("Wage".equals(currentItem.getCategory()));
		Assert.assertEquals(SpendType.GAIN, currentItem.getSpendType());
		Assert.assertTrue("test comment for adding gain".equals(currentItem.getComment()));
		
		System.out.println("3.3. Gain can be added");
	}	
	
	@Test
	public void checkTableIsPopulated() {
		clickOn("#textFieldSum").write("100").clickOn("#textFieldCategory").write("Food").clickOn("#addButton");
		clickOn("#radioGain").clickOn("#textFieldSum").write("150").clickOn("#textFieldCategory").write("Wage");
		clickOn("#textAreaComment").write("test comment for adding gain").clickOn("#addButton");
		
		Assert.assertEquals(2, tableOfKosts.getItems().size());
		System.out.println("3.4. 2 elements were added to the table");
	}
	
	@Test
	public void checkLabels() {
		clickOn("#textFieldSum").write("100").clickOn("#textFieldCategory").write("Food").clickOn("#addButton");
		clickOn("#radioGain").clickOn("#textFieldSum").write("150").clickOn("#textFieldCategory").write("Wage");
		clickOn("#textAreaComment").write("test comment for adding gain").clickOn("#addButton");
		
		//check whether label are updated
		verifyThat("#labelKost_ID", NodeMatchers.hasText("100.0"));
		verifyThat("#labelGain_ID", NodeMatchers.hasText("150.0"));
		verifyThat("#labelDifference_ID", NodeMatchers.hasText("50.0"));	
		System.out.println("3.5. Labels are updated");
	}
	
}
