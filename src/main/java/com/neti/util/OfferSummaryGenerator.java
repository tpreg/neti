package com.neti.util;

import com.neti.util.exception.UtilityClassDoNotInstantiateException;

import java.math.BigDecimal;

public final class OfferSummaryGenerator {

	private OfferSummaryGenerator() {
		throw new UtilityClassDoNotInstantiateException(OfferSummaryGenerator.class);
	}

	public static String generateOfferSummary(final String customerName, final BigDecimal finalOfferPrice) {
		return "Name: %s%nOffer price: %s HUF".formatted(customerName, finalOfferPrice.stripTrailingZeros());
	}

}
