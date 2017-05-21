package com.aleksandrov;

import javafx.scene.chart.PieChart;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import com.aleksandrov.model.Kost;

public abstract class MoneyKeeperGUITestAbstract extends ApplicationTest {

	protected TableView<Kost> tableOfKosts;
	protected PieChart pieChart;
	Kost currentItem;
	protected GuiTest controller;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		ApplicationTest.launch(Main.class);
	}

	@Before
	public void setUp() throws Exception {
		tableOfKosts = lookup("#tableOfKosts").query();
		//pieChart = lookup("#pieChart_ID").query();
	}

	@After
	public void afterEachTest() throws TimeoutException{
		FxToolkit.hideStage();
		release(new KeyCode[] {});
		release(new MouseButton[] {});
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.show();
	}

}
