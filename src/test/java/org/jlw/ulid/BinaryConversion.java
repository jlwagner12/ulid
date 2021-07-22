package org.jlw.ulid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class BinaryConversion
{
	private ULID value;

	@Given("a pair of long values, {string} and {string},")
	public void values(final String msb, final String lsb)
	{
		value = new ULID(Long.parseLong(msb), Long.parseLong(lsb));
	}

	@Then("a derived ULID should equal a valid value, {string}.")
	public void expected(final String string)
	{
		assertEquals(ULID.of(string), value);
	}

	@Given("a string value, {string}")
	public void initial(final String string)
	{
		value = ULID.of(string);
	}

	@Then("the ULID should have a most significant bits value of {string},")
	public void msb(final String string)
	{
		assertEquals(Long.parseLong(string), value.getMostSignificantBits());
	}

	@Then("a least significant bits value of {string}.")
	public void lsb(final String string)
	{
		assertEquals(Long.parseLong(string), value.getLeastSignificantBits());
	}
}
