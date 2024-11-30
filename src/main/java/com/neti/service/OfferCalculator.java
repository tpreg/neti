package com.neti.service;

import com.neti.model.CustomerType;
import com.neti.model.Part;

import java.math.BigDecimal;
import java.util.List;

import static com.neti.model.CustomerType.VIP;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

public class OfferCalculator {

	private static final BigDecimal DEFAULT_MARKUP_RATE = new BigDecimal("0.12");
	private static final BigDecimal VIP_MARKUP_RATE = new BigDecimal("0.10");
	private static final BigDecimal MINIMUM_LABOUR_FEE = new BigDecimal("10000");
	private static final BigDecimal LABOUR_FEE_RATE = new BigDecimal("0.30");
	private static final BigDecimal VIP_DISCOUNT_THRESHOLD_1M = new BigDecimal("1000000");
	private static final BigDecimal VIP_DISCOUNT_THRESHOLD_500K = new BigDecimal("500000");
	private static final BigDecimal VIP_DISCOUNT_THRESHOLD_200K = new BigDecimal("200000");
	private static final BigDecimal VIP_DISCOUNT_AMOUNT_1M = new BigDecimal("120000");
	private static final BigDecimal VIP_DISCOUNT_AMOUNT_500K = new BigDecimal("50000");
	private static final BigDecimal VIP_DISCOUNT_AMOUNT_200K = new BigDecimal("12000");
	private static final BigDecimal LOYALTY_DISCOUNT_RATE = new BigDecimal("0.95");
	private static final int DEFAULT_SCALE = 0;

	public BigDecimal calculateOffer(final CustomerType customerType, final List<Part> parts) {
		final var totalPrice = calculateTotalPrice(parts);
		final var labourFee = calculateLabourFee(totalPrice);
		final var totalMarkUp = calculateTotalMarkUp(customerType, totalPrice);
		final var totalCost = labourFee.add(totalPrice).add(totalMarkUp);
		return applyDiscountBasedOnCustomerType(customerType, totalCost);
	}

	private BigDecimal applyDiscountBasedOnCustomerType(final CustomerType customerType, final BigDecimal totalCost) {
		final var finalCost = switch (customerType) {
			case VIP -> applyVipDiscount(totalCost);
			case NON_REGISTERED -> totalCost;
			case LOYAL_CUSTOMER -> applyLoyaltyDiscount(totalCost);
		};
		return finalCost.setScale(DEFAULT_SCALE, HALF_UP);
	}

	private BigDecimal applyLoyaltyDiscount(final BigDecimal totalCost) {
		return totalCost.multiply(LOYALTY_DISCOUNT_RATE);
	}

	private BigDecimal calculateLabourFee(final BigDecimal totalPrice) {
		final var labourFee = totalPrice.multiply(LABOUR_FEE_RATE);
		return labourFee.max(MINIMUM_LABOUR_FEE);
	}

	private BigDecimal calculateTotalPrice(final List<Part> parts) {
		return parts.stream().map(Part::price).reduce(ZERO, BigDecimal::add);
	}

	private BigDecimal calculateTotalMarkUp(final CustomerType customerType, final BigDecimal purchasePrice) {
		return customerType == VIP ? purchasePrice.multiply(VIP_MARKUP_RATE) : purchasePrice.multiply(DEFAULT_MARKUP_RATE);

	}

	private BigDecimal applyVipDiscount(final BigDecimal totalAmount) {
		if (totalAmount.compareTo(VIP_DISCOUNT_THRESHOLD_1M) > 0) {
			return totalAmount.subtract(VIP_DISCOUNT_AMOUNT_1M);
		}
		if (totalAmount.compareTo(VIP_DISCOUNT_THRESHOLD_500K) > 0) {
			return totalAmount.subtract(VIP_DISCOUNT_AMOUNT_500K);
		}
		if (totalAmount.compareTo(VIP_DISCOUNT_THRESHOLD_200K) > 0) {
			return totalAmount.subtract(VIP_DISCOUNT_AMOUNT_200K);
		}
		return totalAmount;
	}

}
