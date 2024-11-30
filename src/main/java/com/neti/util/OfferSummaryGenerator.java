package com.neti.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.neti.model.DetailedOffer;
import com.neti.util.exception.UtilityClassDoNotInstantiateException;

import java.io.IOException;

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

	public static void generateOfferSummaryPDF(final String customerName, final DetailedOffer detailedOffer, final String filePath) {
		try (final var document = new Document(new PdfDocument(new PdfWriter(filePath)))) {
			final var list = new List().setSymbolIndent(20);
			detailedOffer.partsWithMarkup().forEach((key, value) -> list.add(new ListItem("%s: %s HUF".formatted(key, value))));
			document.add(new Paragraph("Customer: %s".formatted(customerName))) //
					.add(new Paragraph("Parts:")) //
					.add(list) //
					.add(new Paragraph("Parts total cost: %s HUF".formatted(detailedOffer.totalCostBeforeLabour())))
					.add(new Paragraph("Labour cost: %s HUF".formatted(detailedOffer.labourFee())))
					.add(new Paragraph("Discount: %s HUF".formatted(detailedOffer.appliedDiscount())))
					.add(new Paragraph("Final price (after discount): %s HUF".formatted(detailedOffer.totalCost())));
		} catch (final IOException e) {
			throw new IllegalStateException("Error writing to file: %s".formatted(filePath), e);
		}

	}

}
