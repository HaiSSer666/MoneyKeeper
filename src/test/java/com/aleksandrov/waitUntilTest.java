package com.aleksandrov;

import static org.testfx.api.FxAssert.verifyThat;
import org.junit.Test;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;
public class waitUntilTest extends MoneyKeeperGUITestAbstract{

	@Test
	public void test() {
		clickOn("#testButton");
		WaitForAsyncUtils.waitForFxEvents();
		verifyThat("#textAreaComment", NodeMatchers.hasText("***"));
	}

}
