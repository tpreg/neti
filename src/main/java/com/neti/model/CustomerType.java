package com.neti.model;

import java.math.BigDecimal;

public enum CustomerType {

	LOYAL_CUSTOMER(new BigDecimal("1.12")),
	NON_REGISTERED(new BigDecimal("1.12")),
	VIP(new BigDecimal("1.10"));

	private final BigDecimal markupMultiplier;

	CustomerType(final BigDecimal markupMultiplier) {
		this.markupMultiplier = markupMultiplier;
	}

	public BigDecimal getMarkupMultiplier() {
		return this.markupMultiplier;
	}
}
