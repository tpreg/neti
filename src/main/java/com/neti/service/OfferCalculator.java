package com.neti.service;

import com.neti.model.CustomerType;
import com.neti.model.DetailedOffer;
import com.neti.model.Part;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toMap;

public class OfferCalculator {

	private static final BigDecimal MINIMUM_LABOUR_FEE = new BigDecimal("10000");
	private static final BigDecimal LABOUR_FEE_RATE = new BigDecimal("0.30");
	private static final BigDecimal VIP_DISCOUNT_THRESHOLD_1M = new BigDecimal("1000000");
	private static final BigDecimal VIP_DISCOUNT_THRESHOLD_500K = new BigDecimal("500000");
	private static final BigDecimal VIP_DISCOUNT_THRESHOLD_200K = new BigDecimal("200000");
	private static final BigDecimal VIP_DISCOUNT_AMOUNT_1M = new BigDecimal("120000");
	private static final BigDecimal VIP_DISCOUNT_AMOUNT_500K = new BigDecimal("50000");
	private static final BigDecimal VIP_DISCOUNT_AMOUNT_200K = new BigDecimal("12000");
	private static final BigDecimal LOYALTY_DISCOUNT_RATE = new BigDecimal("0.05");
	private static final int DEFAULT_SCALE = 0;

	public DetailedOffer calculateOffer(final CustomerType customerType, final List<Part> parts) {
		final var totalPrice = calculateTotalPrice(parts);
		final var labourFee = calculateLabourFee(totalPrice);
		final var markupPrices = calculatePartPriceWithMarkup(customerType, parts);
		final var totalCostBeforeLabour = calculateTotalCostWithoutLabour(markupPrices);
		final var totalCost = calculateTotalCost(labourFee, totalCostBeforeLabour);
		final var discountMap = calculateDiscount(customerType, totalCost);
		final var appliedDiscount = discountMap.get(customerType.name());
		final var finalCost = calculateFinalCost(totalCost, appliedDiscount);
		return new DetailedOffer(markupPrices, totalCostBeforeLabour, labourFee, appliedDiscount, finalCost);
	}

	private static BigDecimal calculateTotalCost(final BigDecimal labourFee, final BigDecimal totalCostBeforeLabour) {
		return labourFee.add(totalCostBeforeLabour).setScale(DEFAULT_SCALE, HALF_UP);
	}

	private BigDecimal calculateFinalCost(final BigDecimal totalCost, final BigDecimal discount) {
		return totalCost.subtract(discount).setScale(DEFAULT_SCALE, HALF_UP);
	}

	private Map<String, BigDecimal> calculatePartPriceWithMarkup(final CustomerType customerType, final List<Part> parts) {
		return parts.stream().collect(toMap(Part::name, part -> calculateMarkUp(customerType, part.price())));
	}

	private Map<String, BigDecimal> calculateDiscount(final CustomerType customerType, final BigDecimal totalCost) {
		final var name = customerType.name();
		return switch (customerType) {
			case LOYAL_CUSTOMER -> Map.of(name, calculateLoyaltyDiscount(totalCost));
			case NON_REGISTERED -> Map.of(name, ZERO);
			case VIP -> Map.of(name, calculateVipDiscount(totalCost));
		};
	}

	private BigDecimal calculateLoyaltyDiscount(final BigDecimal totalCost) {
		return totalCost.multiply(LOYALTY_DISCOUNT_RATE).setScale(DEFAULT_SCALE, HALF_UP);
	}

	private BigDecimal calculateLabourFee(final BigDecimal totalPrice) {
		final var labourFee = totalPrice.multiply(LABOUR_FEE_RATE);
		return labourFee.max(MINIMUM_LABOUR_FEE).setScale(DEFAULT_SCALE, HALF_UP);
	}

	private BigDecimal calculateTotalPrice(final List<Part> parts) {
		return parts.stream().map(Part::price).reduce(ZERO, BigDecimal::add);
	}

	private BigDecimal calculateMarkUp(final CustomerType customerType, final BigDecimal purchasePrice) {
		return purchasePrice.multiply(customerType.getMarkupMultiplier()).setScale(DEFAULT_SCALE, HALF_UP);
	}

	private BigDecimal calculateTotalCostWithoutLabour(final Map<String, BigDecimal> markupPrices) {
		return markupPrices.values().stream().reduce(ZERO, BigDecimal::add).setScale(DEFAULT_SCALE, HALF_UP);
	}

	private BigDecimal calculateVipDiscount(final BigDecimal totalAmount) {
		if (totalAmount.compareTo(VIP_DISCOUNT_THRESHOLD_1M) > 0) {
			return VIP_DISCOUNT_AMOUNT_1M;
		}
		if (totalAmount.compareTo(VIP_DISCOUNT_THRESHOLD_500K) > 0) {
			return VIP_DISCOUNT_AMOUNT_500K;
		}
		if (totalAmount.compareTo(VIP_DISCOUNT_THRESHOLD_200K) > 0) {
			return VIP_DISCOUNT_AMOUNT_200K;
		}
		return ZERO;
	}

}
