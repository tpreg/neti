package com.neti;

import com.neti.service.OfferCalculator;

import static com.neti.util.FileUtils.*;

public class CarServiceApplication {

	public static void main(final String[] args) {
		final var offerCalculator = new OfferCalculator();
		final var filename = getFilename(args);
		final var customer = readCustomerFromFile(filename);
		final var parts = readPartsFromFile(filename);
		final var finalOfferPrice = offerCalculator.calculateOffer(customer, parts);
		final var content = "Name: %s%nOffer price: %s HUF".formatted(customer.name(), finalOfferPrice.stripTrailingZeros());
		writeResultToFile("offer.txt", content);
	}

}
