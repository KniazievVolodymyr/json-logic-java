package ruleprocessor.evaluator.expressions;

import ruleprocessor.type.JsonLogicArray;
import ruleprocessor.evaluator.JsonLogicEvaluationException;
import ruleprocessor.evaluator.JsonLogicEvaluator;
import ruleprocessor.evaluator.JsonLogicExpression;
import ruleprocessor.utils.ArrayLike;
import ruleprocessor.JsonLogic;

public class AllExpression implements JsonLogicExpression {
  public static final AllExpression INSTANCE = new AllExpression();

  private AllExpression() {
    // Use INSTANCE instead.
  }

  @Override
  public String key() {
    return "all";
  }

  @Override
  public Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
    throws JsonLogicEvaluationException {
    if (arguments.size() != 2) {
      throw new JsonLogicEvaluationException("all expects exactly 2 arguments");
    }

    Object maybeArray = evaluator.evaluate(arguments.get(0), data);

    if (!ArrayLike.isEligible(maybeArray)) {
      throw new JsonLogicEvaluationException("first argument to all must be a valid array");
    }

    ArrayLike array = new ArrayLike(maybeArray);

    if (array.size() < 1) {
      return false;
    }

    for (Object item : array) {
      if(!JsonLogic.truthy(evaluator.evaluate(arguments.get(1), item))) {
        return false;
      }
    }

    return true;
  }
}
