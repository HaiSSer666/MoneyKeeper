package com.aleksandrov;

import static org.junit.Assert.*;

import org.junit.Test;

public class nextTest {
	//final String LABEL_KOST_ID = "#labelKost";
	//int tableSize = tableOfKosts.getItems().size();
	
	//ObservableList<PieChart.Data> expectedPieChartData = FXCollections.observableArrayList();
	
	/*@Before
	public void setUp() throws Exception {
		expectedPieChartData = FXCollections.observableArrayList();
	}*/
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	/*	
	//@Test
	public void chartsInitialCheck(){
		clickOn("#pieChartTab");
		sleep(1000);
		Assert.assertEquals(expectedPieChartData, pieChart.getData());
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void B_checkAddButtonFunktionality() {	
		//expectedPieChartData = FXCollections.observableArrayList();
		ObservableList<PieChart.Data> pieChartData = pieChart.getData();
				
		clickOn("#textFieldSum").write("k");
		clickOn("#textFieldCategory").write("food");
		clickOn("#addButton");
		sleep(1500);
		push(KeyCode.ENTER);

		//Assert.assertEquals(1, tableSize);
		
		Assert.assertNotNull(tableOfKosts);
		Assert.assertNotNull(tableOfKosts.getItems());
		Assert.assertTrue(tableOfKosts.getItems().size() == 1);
		
		//expectedPieChartData.add(new PieChart.Data("food", 20));
		//Assert.assertEquals(expectedPieChartData, pieChart.getData());
		Assert.assertTrue(pieChartData.size() == 1);
		PieChart.Data data1 = pieChartData.get(0);
		Assert.assertTrue(data1.getPieValue()==20);
	}
	
	/*@Test
	public void checkMenu() {	
	}
		
		/*verifyThat(LABEL_KOST_ID, Label labelKost -> {
			String totalAmount = labelKost.getText();
			return totalAmount.contains("0")
		});*/
	
		/*clickOn("#textFieldSum").write("20");
		clickOn("#textFieldCategory").write("food");
		sleep(3000);
		clickOn("#cancelButton");
		verifyThat("#addButton", NodeMatchers.isDisabled());
		clickOn("#addButton");
		sleep(3000);
		
		clickOn("#textFieldSum").write("10");
		clickOn("#textFieldCategory").write("tools");
		clickOn("#addButton");
		sleep(3000);
		
		clickOn("#textFieldSum").write("50");
		clickOn("#textFieldCategory").write("wage");
		clickOn("#radioGain");
		clickOn("#addButton");
		sleep(3000);
		
		//closeCurrentWindow();
		
	  /*clickOn("#textFieldSum").write("100");
		clickOn("#textFieldCategory").write("wage");
		clickOn("#radioGain");
		clickOn("#addButton");*/
		//clickOn("food");

		//clickOn("#pieChartTab");
		//sleep(5000);
		//clickOn("#barChartTab");
		//sleep(5000);
		
		//clickOn("#tableTab");
		//clickOn("food");
		//sleep(5000);
		
		//Assert.assertEquals("food", tableOfKosts.getSelectionModel().getSelectedItem().getCategory());
		//Assert.assertEquals("-20", );
		//String testName = "food";
		//Assert.assertTrue(testName.equals(currentItem.getCategory()));
		//moveTo("#textFieldCategory");
		
		//Assert.assertTrue(addButton.isDisabled());
	
	/*@Test
	public void selectPlayer() {
		clickOn("Cleveland Cavaliers");
		clickOn("LeBron James");

		//Assert.assertEquals("LeBron James", playerTable.getSelectionModel().getSelectedItem().getName());
	}*/
}
