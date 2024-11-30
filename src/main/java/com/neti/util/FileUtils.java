package com.neti.util;

import com.neti.model.Customer;
import com.neti.model.CustomerType;
import com.neti.model.Part;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.lines;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FileUtils {

	public static Customer readCustomerFromFile(final String filename) {
		try (final var lines = lines(Paths.get(filename), UTF_8)) {
			return lines //
					.findFirst() //
					.map(line -> line.split(",")) //
					.map(FileUtils::parseCustomer) //
					.orElseThrow(supplierOfCustomerNotFoundException());
		} catch (final IOException e) {
			throw new IllegalStateException("Error reading file: %s".formatted(filename), e);
		}
	}

	private static Supplier<IllegalStateException> supplierOfCustomerNotFoundException() {
		return () -> new IllegalStateException("No customer found");
	}

	private static Customer parseCustomer(final String[] customerData) {
		return new Customer(customerData[0], CustomerType.valueOf(customerData[1]));
	}

	public static List<Part> readPartsFromFile(final String filename) {
		try (final var lines = lines(Paths.get(filename), UTF_8)) {
			return lines //
					.skip(1).map(line -> line.split(",")) //
					.map(FileUtils::parsePart) //
					.toList();
		} catch (final IOException e) {
			throw new IllegalStateException("Error reading file: %s".formatted(filename), e);
		}
	}

	private static Part parsePart(final String[] partData) {
		final var name = partData[0];
		final var netCost = new BigDecimal(partData[1]);
		return new Part(name, netCost);
	}

	public static void writeResultToFile(final String outputPath, final String content) {
		try {
			Files.writeString(Paths.get(outputPath), content, CREATE, TRUNCATE_EXISTING);
		} catch (final IOException e) {
			throw new IllegalStateException(String.format("Error writing to file: %s", outputPath), e);
		}
	}

	public static String getFilename(final String[] args) {
		if (args.length > 0) {
			return args[0];
		}
		throw new IllegalArgumentException("Missing file name");
	}

}
