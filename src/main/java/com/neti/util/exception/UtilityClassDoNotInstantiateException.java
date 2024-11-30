package com.neti.util.exception;

import java.io.Serial;

public final class UtilityClassDoNotInstantiateException extends IllegalStateException {

	@Serial
	private static final long serialVersionUID = -3791729052828926563L;

	private static final String UTILITY_CLASS_DO_NOT_INSTANTIATE_PATTERN = "'%s' is an utility class, do not instantiate";

	public UtilityClassDoNotInstantiateException(final Class<?> utilityClass) {
		super(String.format(UTILITY_CLASS_DO_NOT_INSTANTIATE_PATTERN, utilityClass.getName()));
	}

}
