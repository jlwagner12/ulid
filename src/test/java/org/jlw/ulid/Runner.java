package org.jlw.ulid;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:feature-tests",
		glue = "org.jlw.ulid")
class Runner
{
}
