package ruleprocessor.evaluator.expressions;

import ruleprocessor.type.JsonLogicArray;
import ruleprocessor.evaluator.JsonLogicEvaluationException;
import ruleprocessor.evaluator.JsonLogicEvaluator;
import ruleprocessor.evaluator.JsonLogicExpression;
import ruleprocessor.utils.ArrayLike;
import ruleprocessor.JsonLogic;

public class ArrayHasExpression implements JsonLogicExpression {
  public static final ArrayHasExpression SOME = new ArrayHasExpression(true);
  public static final ArrayHasExpression NONE = new ArrayHasExpression(false);

  private final boolean isSome;

  private ArrayHasExpression(boolean isSome) {
    this.isSome = isSome;
  }

  @Override
  public String key() {
    return isSome ? "some" : "none";
  }

  @Override
  public Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
    throws JsonLogicEvaluationException {
    if (arguments.size() != 2) {
      throw new JsonLogicEvaluationException("some expects exactly 2 arguments");
    }

    Object maybeArray = evaluator.evaluate(arguments.get(0), data);

    // Array objects can have null values according to http://jsonlogic.com/
    if (maybeArray == null) {
      if (isSome) {
        return false;
      } else {
        return true;
      }
    }

    if (!ArrayLike.isEligible(maybeArray)) {
      throw new JsonLogicEvaluationException("first argument to some must be a valid array");
    }

    for (Object item : new ArrayLike(maybeArray)) {
      if(JsonLogic.truthy(evaluator.evaluate(arguments.get(1), item))) {
        return isSome;
      }
    }

    return !isSome;
  }
}