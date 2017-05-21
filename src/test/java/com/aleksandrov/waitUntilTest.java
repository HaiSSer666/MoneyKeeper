package com.aleksandrov;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.loadui.testfx.controls.impl.VisibleNodesMatcher;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.support.WaitUntilSupport;
import org.testfx.util.WaitForAsyncUtils;
public class waitUntilTest extends MoneyKeeperGUITestAbstract{

	@Test
	public void test() {
		clickOn("#testButton");
		WaitForAsyncUtils.waitForFxEvents();
		verifyThat("#textAreaComment", NodeMatchers.hasText("***"));
	}

}
