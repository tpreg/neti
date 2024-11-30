package com.neti.model;

import java.math.BigDecimal;
import java.util.Map;

public record DetailedOffer(Map<String, BigDecimal> partsWithMarkup, BigDecimal totalCostBeforeLabour, BigDecimal labourFee,
		BigDecimal appliedDiscount, BigDecimal totalCost) {

}
