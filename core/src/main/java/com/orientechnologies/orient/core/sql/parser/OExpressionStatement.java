/* Generated By:JJTree: Do not edit this line. OExpressionStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.sql.executor.OInternalResultSet;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import java.util.Map;
import java.util.Objects;

public class OExpressionStatement extends OSimpleExecStatement {

  protected OExpression expression;

  public OExpressionStatement(int id) {
    super(id);
  }

  public OExpressionStatement(OrientSql p, int id) {
    super(p, id);
  }

  @Override
  public OResultSet executeSimple(OCommandContext ctx) {
    OInternalResultSet result = new OInternalResultSet();
    Object expResult = expression.execute(new OResultInternal(), ctx);
    OResultInternal item = new OResultInternal();
    item.setProperty("result", expResult);
    result.add(item);
    return result;
  }

  @Override
  public boolean executinPlanCanBeCached() {
    return false;
  }

  @Override
  public OStatement copy() {
    OExpressionStatement result = new OExpressionStatement(-1);
    result.expression = expression.copy();
    return result;
  }

  @Override
  public void toString(Map<Object, Object> params, StringBuilder builder) {
    expression.toString(params, builder);
  }

  @Override
  public void toGenericStatement(StringBuilder builder) {
    expression.toGenericStatement(builder);
  }

  @Override
  public boolean refersToParent() {
    return expression.refersToParent();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OExpressionStatement that = (OExpressionStatement) o;
    return Objects.equals(expression, that.expression);
  }

  @Override
  public int hashCode() {
    return Objects.hash(expression);
  }
}
/* JavaCC - OriginalChecksum=c3eda193cdcf863b4ced490ef1f37734 (do not edit this line) */