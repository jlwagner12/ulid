package org.jlw.ulid;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class BadUlids
{
	private String value;

	private Exception exception;

	@Given("a bad ULID string, {string},")
	public void string(final String string)
	{
		this.value = string;
	}

	@Given("it is requested to be converted to an actual ULID.")
	public void convert()
	{
		try
		{
			ULID.of(value);
		} // try

		catch (final Exception e)
		{
			exception = e;
		} // catch
	}

	@Then("a failure is expected.")
	public void failure()
	{
		assertNotNull(exception);
		assertEquals(IllegalArgumentException.class, exception.getClass());
	}
}
