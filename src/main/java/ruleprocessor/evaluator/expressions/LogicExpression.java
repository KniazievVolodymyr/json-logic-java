package ruleprocessor.evaluator.expressions;

import ruleprocessor.JsonLogic;
import ruleprocessor.type.JsonLogicArray;
import ruleprocessor.type.JsonLogicNode;
import ruleprocessor.evaluator.JsonLogicEvaluationException;
import ruleprocessor.evaluator.JsonLogicEvaluator;
import ruleprocessor.evaluator.JsonLogicExpression;

public class LogicExpression implements JsonLogicExpression {
  public static final LogicExpression AND = new LogicExpression(true);
  public static final LogicExpression OR = new LogicExpression(false);

  private final boolean isAnd;

  private LogicExpression(boolean isAnd) {
    this.isAnd = isAnd;
  }

  @Override
  public String key() {
    return isAnd ? "and" : "or";
  }

  @Override
  public Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
    throws JsonLogicEvaluationException {
    if (arguments.size() < 1) {
      throw new JsonLogicEvaluationException("and operator expects at least 1 argument");
    }

    Object result = null;

    for (JsonLogicNode element : arguments) {
      result = evaluator.evaluate(element, data);

      if ((isAnd && !JsonLogic.truthy(result)) || (!isAnd && JsonLogic.truthy(result))) {
        return result;
      }
    }

    return result;
  }
}
