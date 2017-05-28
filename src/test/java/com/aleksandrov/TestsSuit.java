package com.aleksandrov;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ initialStateTest.class, buttonsActivationTest.class, mainFunktionalityTest.class,
		menuBarTest.class })
public class TestsSuit {
}
     