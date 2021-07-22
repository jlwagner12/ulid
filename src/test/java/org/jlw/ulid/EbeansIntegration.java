package org.jlw.ulid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.testcontainers.shaded.org.apache.commons.lang.math.RandomUtils;
import org.testcontainers.utility.DockerImageName;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EbeansIntegration extends AbstractDatabaseTestContainer
{
	private ULID id;

	private int value;

	private Exception exception;

	@Given("a postgres database")
	public void createPostgresDatabase() throws SQLException
	{
		initContainer(DockerImageName.parse("postgres"), DatabasePlatform.POSTGRES);
	}

	@Given("an oracle database")
	public void createOracleDatabase() throws SQLException
	{
		initContainer(DockerImageName.parse("wnameless/oracle-xe-11g-r2"), DatabasePlatform.ORACLE);
	}

	@Given("a ms-sql database")
	public void createMsSqlDatabase() throws SQLException
	{
		initContainer(DockerImageName.parse("mcr.microsoft.com/mssql/server"), DatabasePlatform.MSSQL_16);
	}

	@When("I create an entity and insert it into the database,")
	public void insert()
	{
		final DefaultIdTestEntity entity = new DefaultIdTestEntity();

		id = entity.getId();
		value = RandomUtils.nextInt();
		entity.setValue(value);
		getDatabase().insert(entity);
	}

	@When("I create an entity with a generated id and insert it into the database,")
	public void generateAndInsert()
	{
		final DatabaseGeneratedTestEntity entity = new DatabaseGeneratedTestEntity();

		value = RandomUtils.nextInt();
		entity.setValue(value);
		getDatabase().insert(entity);
	}

	@Then("I should be able to select it and it match what was inserted.")
	public void match()
	{
		try
		{
			final DefaultIdTestEntity entity = getDatabase().find(DefaultIdTestEntity.class)
					.where()
					.idEq(id)
					.findOne();

			assertNotNull(entity);
			assertEquals(id, entity.getId());
			assertEquals(value, entity.getValue());
		} // try

		finally
		{
			destroy();
		} // finally
	}

	@Then("It should have a generated id stored in the database.")
	public void matchGenerated()
	{
		try
		{
			final DefaultIdTestEntity entity = getDatabase().find(DefaultIdTestEntity.class)
					.where()
					.eq("value", value)
					.findOne();

			assertNotNull(entity);
			assertNotNull(entity.getId());
			assertEquals(value, entity.getValue());
		} // try

		finally
		{
			destroy();
		} // finally
	}

	@Given("bad data in the ULID column")
	public void badData() throws SQLException
	{
		final String sql = "INSERT INTO TEST_TABLE (TEST_ID, TEST_VALUE) VALUES (?, ?)";

		try (final Connection con = getDatabase().getDataSource().getConnection();
				final PreparedStatement stmt = con.prepareStatement(sql))
		{
			stmt.setString(1, "01F99GM3AAFN3KR86D6JGPKXPI");
			stmt.setLong(2, 1);
			stmt.execute();
		} // try
	}

	@When("I query for the entity,")
	public void query()
	{
		try
		{
			final DefaultIdTestEntity entity = getDatabase().find(DefaultIdTestEntity.class)
					.where()
					.eq("value", 1)
					.findOne();

			exception = null;
		} // try

		catch (final Exception e)
		{
			exception = e;
		} // catch
	}

	@Then("It should fail.")
	public void fail()
	{
		assertNotNull(exception);
		assertEquals(IllegalArgumentException.class, exception.getClass());
	}
}
