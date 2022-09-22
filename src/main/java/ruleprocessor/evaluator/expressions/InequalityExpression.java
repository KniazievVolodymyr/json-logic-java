package ruleprocessor.evaluator.expressions;

import ruleprocessor.type.JsonLogicArray;
import ruleprocessor.evaluator.JsonLogicEvaluationException;
import ruleprocessor.evaluator.JsonLogicEvaluator;
import ruleprocessor.evaluator.JsonLogicExpression;

public class InequalityExpression implements JsonLogicExpression {
  public static final InequalityExpression INSTANCE = new InequalityExpression(EqualityExpression.INSTANCE);

  private final EqualityExpression delegate;

  private InequalityExpression(EqualityExpression delegate) {
    this.delegate = delegate;
  }

  @Override
  public String key() {
    return "!=";
  }

  @Override
  public Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
    throws JsonLogicEvaluationException {
    boolean result = (boolean) delegate.evaluate(evaluator, arguments, data);

    return !result;
  }
}
