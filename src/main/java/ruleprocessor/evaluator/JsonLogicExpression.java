package ruleprocessor.evaluator;

import ruleprocessor.type.JsonLogicArray;

public interface JsonLogicExpression {
  String key();

  Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
    throws JsonLogicEvaluationException;
}
