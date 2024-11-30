package com.neti.util;

import com.neti.model.DetailedOffer;
import com.neti.util.exception.UtilityClassDoNotInstantiateException;

public final class OfferSummaryGenerator {

	private OfferSummaryGenerator() {
		throw new UtilityClassDoNotInstantiateException(OfferSummaryGenerator.class);
	}

	public static String generateOfferSummary(final String customerName, final DetailedOffer detailedOffer) {
		final var summary = new StringBuilder();
		summary.append("Customer: ").append(customerName).append("\n");
		summary.append("Parts: ").append("\n");
		detailedOffer.partsWithMarkup()
				.forEach((key, value) -> summary.append("\t- ").append(key).append(": ").append(value).append(" HUF\n"));
		summary.append("Parts total cost: ").append(detailedOffer.totalCostBeforeLabour()).append(" HUF\n");
		summary.append("Labour cost: ").append(detailedOffer.labourFee()).append(" HUF\n");
		summary.append("Discount: ").append(detailedOffer.appliedDiscount()).append(" HUF\n");
		summary.append("Final price (after discount): ").append(detailedOffer.totalCost()).append(" HUF\n");
		return summary.toString();
	}

}
