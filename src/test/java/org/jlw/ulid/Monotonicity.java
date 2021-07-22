package org.jlw.ulid;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class Monotonicity
{
	private Map<Long, List<ULID>> ids;

	@Given("several random ULIDs, generated as quickly as possible,")
	public void creation()
	{
		ids = Stream.iterate(0, n -> n + 1)
				.limit(100_000)
				.parallel()
				.map(n -> ULID.randomULID())
				.collect(Collectors.groupingBy(ULID::timestamp, Collectors.toList()));
	}

	@Then("those created within the same millisecond should be monotonic.")
	public void monotonic()
	{
		// make certain we have created a group of ids
		assertFalse(ids.isEmpty());

		// make certain we have created multiple ids in at least one millisecond time period
		assertTrue(ids.values()
				.stream()
				.mapToInt(List::size)
				.max()
				.orElse(0) > 1);

		// make certain all ids created within the same millisecond are unique
		ids.values().forEach(list -> {
			assertEquals(list.size(), list.stream()
					.distinct()
					.count());
		});

		// make certain all ids created within the same millisecond are monotonic
		ids.forEach((timestamp, list) -> {
			list.sort(ULID::compareTo);

			BigInteger value = null;

			for (final ULID id : list)
			{
				final BigInteger next = new BigInteger(id.entropy());

				if (value != null)
				{
					assertEquals(value.add(BigInteger.ONE), next);
				} // if

				value = next;
			} // for
		});
	}
}
