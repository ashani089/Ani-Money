package com.ani.amoney.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import org.json.JSONObject;

import com.ani.amoney.constants.AMoneyConstants;
import com.ani.amoney.currency.Currency;
import com.ani.amoney.exceptions.DifferentCurrencyException;

/**
 * @author ashishani
 *
 */
public class AMoney {

	private static final int DEFAULT_ROUNDING_SCALE = 2;

	private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

	private Currency currency;

	private BigDecimal value;

	private String formattedValue;

	public AMoney(final Currency currency, final BigDecimal value) {
		this.currency = currency;
		this.value = value;
		this.formattedValue = currency.getCode() + " "
				+ value.setScale(DEFAULT_ROUNDING_SCALE, DEFAULT_ROUNDING_MODE);
	}

	public AMoney(AMoney money) {
		this.currency = money.getCurrency();
		this.value = money.getValue();
		this.formattedValue = money.getFromattedValue();
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getFromattedValue() {
		return formattedValue;
	}

	public void setFromattedValue(String fromattedValue) {
		this.formattedValue = fromattedValue;
	}

	/**
	 * This method adds two {@link AMoney} objects with default Scale value and
	 * Rounding Mode
	 * 
	 * @param {@link AMoney}
	 * @return {@link AMoney}
	 * @throws DifferentCurrencyException
	 */
	public AMoney add(AMoney a) throws DifferentCurrencyException {
		return Objects.nonNull(a) ? this.add(a, DEFAULT_ROUNDING_SCALE,
				DEFAULT_ROUNDING_MODE) : new AMoney(this);
	}

	/**
	 * This method add two {@link AMoney} objects with provided scale value and
	 * rounding value. If scale value is less than 0, then default rounding
	 * scale i.e. 2 will be used. If rounding mode is null, then
	 * RoundingMode.HALF_UP will be used as default.
	 * 
	 * @param {@link AMoney}
	 * @param scale
	 *            The scale value for the final result
	 * @param {@link RoundingMode} The rounding mode to be used while adding two
	 *        AMoney.
	 * @return
	 * @throws DifferentCurrencyException
	 */
	public AMoney add(AMoney a, int scale, RoundingMode roundingMode)
			throws DifferentCurrencyException {
		final int localScale = scale <= 0 ? DEFAULT_ROUNDING_SCALE : scale;
		final RoundingMode localRoundingMode = Objects.nonNull(roundingMode) ? roundingMode
				: DEFAULT_ROUNDING_MODE;

		if (Objects.nonNull(a)) {
			if (this.getCurrency() == a.getCurrency()) {
				final BigDecimal value1 = Objects.nonNull(a.getValue()) ? a
						.getValue() : BigDecimal.ZERO;
				final BigDecimal value2 = Objects.nonNull(a.getValue()) ? a
						.getValue() : BigDecimal.ZERO;
				return new AMoney(this.getCurrency(), value1.add(value2)
						.setScale(localScale, localRoundingMode));
			} else {
				throw new DifferentCurrencyException(
						"Different currency money cannot be added");
			}
		}

		return new AMoney(this);
	}

	/**
	 * @param a
	 * @return
	 * @throws DifferentCurrencyException
	 */
	public AMoney subtract(AMoney a) throws DifferentCurrencyException {
		return Objects.nonNull(a) ? this.subtract(a, DEFAULT_ROUNDING_SCALE,
				DEFAULT_ROUNDING_MODE) : new AMoney(this);
	}

	/**
	 * This method subtract parameter from current AMONEY with provided scale
	 * value and rounding value. If scale value is less than 0, then default
	 * rounding scale i.e. 2 will be used. If rounding mode is null, then
	 * RoundingMode.HALF_UP will be used as default.
	 * 
	 * @param {@link AMoney}
	 * @param scale
	 *            The scale value for the final result
	 * @param {@link RoundingMode} The rounding mode to be used while
	 *        subtracting two AMoney.
	 * @return
	 * @throws DifferentCurrencyException
	 */
	public AMoney subtract(AMoney a, int scale, RoundingMode roundingMode)
			throws DifferentCurrencyException {
		final int localScale = scale <= 0 ? DEFAULT_ROUNDING_SCALE : scale;
		final RoundingMode localRoundingMode = Objects.nonNull(roundingMode) ? roundingMode
				: DEFAULT_ROUNDING_MODE;

		if (Objects.nonNull(a)) {
			if (this.getCurrency() == a.getCurrency()) {
				final BigDecimal value1 = Objects.nonNull(a.getValue()) ? a
						.getValue() : BigDecimal.ZERO;
				final BigDecimal value2 = Objects.nonNull(a.getValue()) ? a
						.getValue() : BigDecimal.ZERO;
				return new AMoney(this.getCurrency(), value1.subtract(value2)
						.setScale(localScale, localRoundingMode));
			} else {
				throw new DifferentCurrencyException(
						"Different currency money cannot be subtracted");
			}
		}

		return new AMoney(this);
	}

	/**
	 * This method return (moneyA + moneyB)
	 * 
	 * @param moneyA
	 * @param moneyB
	 * @return
	 * @throws DifferentCurrencyException
	 */
	public static AMoney add(AMoney moneyA, AMoney moneyB)
			throws DifferentCurrencyException {
		return moneyA.add(moneyB);
	}

	/**
	 * This method return (moneyA - moneyB)
	 * 
	 * @param moneyA
	 * @param moneyB
	 * @return
	 * @throws DifferentCurrencyException
	 */
	public static AMoney subtract(AMoney moneyA, AMoney moneyB)
			throws DifferentCurrencyException {
		return moneyA.subtract(moneyB);
	}

	/**
	 * This method multiply the value of Money with a double value
	 * 
	 * @param n
	 * @return
	 */
	public AMoney multiply(double n, int scale, RoundingMode roundingMode) {
		final int localScale = scale <= 0 ? DEFAULT_ROUNDING_SCALE : scale;
		final RoundingMode localRoundingMode = Objects.nonNull(roundingMode) ? roundingMode
				: DEFAULT_ROUNDING_MODE;

		return new AMoney(this.getCurrency(), this.getValue()
				.multiply(BigDecimal.valueOf(n))
				.setScale(localScale, localRoundingMode));
	}

	/**
	 * Convert AMoney to JSON object.
	 * 
	 * @return
	 */
	public JSONObject toJsonObject() {
		final JSONObject jsonObject = new JSONObject();
		jsonObject.put(AMoneyConstants.CURRENCY_KEY, this.getCurrency()
				.getCode());
		jsonObject.put(AMoneyConstants.VALUE_KEY, this.getValue());
		jsonObject.put(AMoneyConstants.FORMATTED_VALUE_KEY,
				this.getFromattedValue());
		return jsonObject;
	}
	
	@Override
	public String toString() {
		return Objects.nonNull(this.getFromattedValue()) ? this
				.getFromattedValue() : this.getCurrency().getCode()
				+ " "
				+ this.getValue().setScale(DEFAULT_ROUNDING_SCALE,
						DEFAULT_ROUNDING_MODE);
	}
	
}
