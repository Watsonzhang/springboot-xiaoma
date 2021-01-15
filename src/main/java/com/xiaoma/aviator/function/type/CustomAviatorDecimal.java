package com.xiaoma.aviator.function.type;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.type.AviatorNumber;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * CustomAviatorDecimal
 */
public class CustomAviatorDecimal extends AviatorNumber {

  private static final double DEFAULT_NUMBER = 0.0001;

  public CustomAviatorDecimal(Number number) {
    super(number);
  }

  public static final CustomAviatorDecimal valueOf(BigDecimal d) {
    return new CustomAviatorDecimal(d);
  }

  public static final CustomAviatorDecimal valueOf(String d) {
    return new CustomAviatorDecimal(new BigDecimal(d, AviatorEvaluator.getMathContext()));
  }

  public AviatorObject innerSub(AviatorNumber other) {
    return valueOf(this.toDecimal().subtract(other.toDecimal(), AviatorEvaluator.getMathContext()));
  }

  public AviatorObject neg(Map<String, Object> env) {
    return valueOf(this.toDecimal().negate());
  }

  public AviatorObject innerMult(AviatorNumber other) {
    return valueOf(this.toDecimal().multiply(other.toDecimal(), AviatorEvaluator.getMathContext()));
  }

  public AviatorObject innerMod(AviatorNumber other) {
    return valueOf(this.toDecimal().remainder(other.toDecimal(), AviatorEvaluator.getMathContext()));
  }

  public AviatorObject innerDiv(AviatorNumber other) {
    if (other.toDecimal().doubleValue() == 0) {
      //return valueOf(this.toDecimal().divide(new BigDecimal(DEFAULT_NUMBER), AviatorEvaluator.getMathContext()));
      //分母为0， 商为0
      return valueOf(BigDecimal.ZERO);
    }
    return valueOf(this.toDecimal().divide(other.toDecimal(), AviatorEvaluator.getMathContext()));
  }

  public AviatorNumber innerAdd(AviatorNumber other) {
    return valueOf(this.toDecimal().add(other.toDecimal(), AviatorEvaluator.getMathContext()));
  }

  public int innerCompare(AviatorNumber other) {
    return this.toDecimal().compareTo(other.toDecimal());
  }

  public AviatorType getAviatorType() {
    return AviatorType.Decimal;
  }
}
