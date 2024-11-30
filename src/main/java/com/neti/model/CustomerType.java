package com.neti.model;

import java.math.BigDecimal;

import static com.neti.model.MarkupRates.DEFAULT_MARKUP_MULTIPLIER;
import static com.neti.model.MarkupRates.VIP_MARKUP_MULTIPLIER;

public enum CustomerType {

	LOYAL_CUSTOMER(DEFAULT_MARKUP_MULTIPLIER),
	NON_REGISTERED(DEFAULT_MARKUP_MULTIPLIER),
	VIP(VIP_MARKUP_MULTIPLIER);

	private final BigDecimal markupMultiplier;

	CustomerType(final BigDecimal markupMultiplier) {
		this.markupMultiplier = markupMultiplier;
	}

	public BigDecimal getMarkupMultiplier() {
		return this.markupMultiplier;
	}
}
