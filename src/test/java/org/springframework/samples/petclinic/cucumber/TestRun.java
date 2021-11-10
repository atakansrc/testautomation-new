package org.springframework.samples.petclinic.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/feature" })
public class TestRun extends AbstractTestNGCucumberTests{

}
