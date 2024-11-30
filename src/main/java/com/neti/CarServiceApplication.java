package com.neti;

import com.neti.service.OfferCalculator;

import static com.neti.util.FileUtils.*;
import static com.neti.util.OfferSummaryGenerator.generateOfferSummary;

public class CarServiceApplication {

	public static void main(final String[] args) {
		final var offerCalculator = new OfferCalculator();
		final var filename = getFilenameFromArgs(args);
		final var customer = readCustomerFromFile(filename);
		final var parts = readPartsFromFile(filename);
		final var finalOfferPrice = offerCalculator.calculateOffer(customer.customerType(), parts);
		final var content = generateOfferSummary(customer.name(), finalOfferPrice);
		writeResultToFile("offer.txt", content);
	}

}
