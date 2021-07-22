package org.jlw.ulid;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PerformanceMeasurement
{
	private static final Duration timeToRun = Duration.ofSeconds(20);

	public static void main(final String... args)
	{
		final int limit = Runtime.getRuntime().availableProcessors() - 1;

		multiThread(1, Duration.ofSeconds(1)); // warm-up

		for (int j = 1, c = limit + 1; j < c; ++j)
		{
			final int threads = j;

			System.out.print(j);
			System.out.print(":\t");
			System.out.println((long) average(() -> multiThread(threads, timeToRun)));
		} // for
	}

	private static double average(final DoubleSupplier supplier)
	{
		return average(Stream.iterate(0, n -> n + 1)
				.limit(5)
				.map(n -> supplier.getAsDouble())
				.collect(Collectors.toList()));
	}

	private static double average(final List<Double> values)
	{
		final double min = values.stream().mapToDouble(x -> x).min().orElse(0);
		final double max = values.stream().mapToDouble(x -> x).min().orElse(0);

		values.remove(min);
		values.remove(max);

		return values.stream().mapToDouble(x -> x).average().orElse(0d);
	}

	private static double multiThread(final int threads, final Duration timeToRun)
	{
		final int limit = Math.min(threads, Runtime.getRuntime().availableProcessors());
		final ForkJoinPool pool = new ForkJoinPool(limit);

		final double perSecond = Stream.iterate(0, n -> n + 1)
				.parallel()
				.limit(limit)
				.map(n -> pool.submit(() -> {
					final long finish = timeToRun.toMillis() + System.currentTimeMillis();
					long cnt = 0;
					final Instant instant = Instant.now();

					while (System.currentTimeMillis() < finish)
					{
						ULID.randomULID();
						++cnt;
					} // while

					final Duration duration = Duration.between(instant, Instant.now());

					return (1.0 / duration.dividedBy(cnt).toNanos()) * Duration.ofSeconds(1).toNanos();
				}))
				.mapToDouble(ForkJoinTask::join)
				.average()
				.orElse(0d);

		pool.shutdown();
		return Math.round(perSecond);
	}
}
