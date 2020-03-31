package com.parking.parkingapi.model.pricing;

/**
 * DTO for storing Price information.
 */
public class Price {

  private long amount;

  private Currency currency;

  public Price() {
  }

  public Price(long amount, Currency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public static final class PriceBuilder {
    private long amount;
    private Currency currency;

    private PriceBuilder() {
    }

    public static PriceBuilder builder() {
      return new PriceBuilder();
    }

    public PriceBuilder withAmount(long amount) {
      this.amount = amount;
      return this;
    }

    public PriceBuilder withCurrency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public Price build() {
      Price price = new Price();
      price.setAmount(amount);
      price.setCurrency(currency);
      return price;
    }
  }
}
