package com.neti;

import com.neti.service.OfferCalculator;

import static com.neti.util.FileUtils.*;
import static com.neti.util.OfferSummaryGenerator.generateOfferSummaryPDF;

public class CarServiceApplication {

	public static void main(final String[] args) {
		final var offerCalculator = new OfferCalculator();
		final var filename = getFilenameFromArgs(args);
		final var customer = readCustomerFromFile(filename);
		final var parts = readPartsFromFile(filename);
		final var detailedOffer = offerCalculator.calculateOffer(customer.customerType(), parts);
		generateOfferSummaryPDF(customer.name(), detailedOffer, "offer.pdf");
	}

}
