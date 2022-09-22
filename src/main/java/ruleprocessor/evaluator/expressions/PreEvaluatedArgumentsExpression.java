package ruleprocessor.evaluator.expressions;

import ruleprocessor.utils.ArrayLike;
import ruleprocessor.type.JsonLogicArray;
import ruleprocessor.evaluator.JsonLogicEvaluationException;
import ruleprocessor.evaluator.JsonLogicEvaluator;
import ruleprocessor.evaluator.JsonLogicExpression;

import java.util.List;

public interface PreEvaluatedArgumentsExpression extends JsonLogicExpression {
  Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException;

  @Override
  default Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
    throws JsonLogicEvaluationException {
    List<Object> values = evaluator.evaluate(arguments, data);

    if (values.size() == 1 && ArrayLike.isEligible(values.get(0))) {
      values = new ArrayLike(values.get(0));
    }

    return evaluate(values, data);
  }
}
