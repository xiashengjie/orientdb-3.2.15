/* Generated By:JJTree: Do not edit this line. OInputParameter.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.common.collection.OMultiValue;
import com.orientechnologies.common.exception.OException;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.exception.OCommandExecutionException;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class OInputParameter extends SimpleNode {

  protected static final String dateFormatString = "yyyy-MM-dd HH:mm:ss.SSS";

  public OInputParameter(int id) {
    super(id);
  }

  public OInputParameter(OrientSql p, int id) {
    super(p, id);
  }

  public Object bindFromInputParams(Map<Object, Object> params) {
    return null;
  }

  public Object getValue(Map<Object, Object> params) {
    return null;
  }

  protected Object toParsedTree(Object value) {
    if (value == null) {
      OExpression result = new OExpression(-1);
      result.isNull = true;
      return result;
    }
    if (value instanceof Boolean) {
      OExpression result = new OExpression(-1);
      result.booleanValue = (Boolean) value;
      return result;
    }
    if (value instanceof Integer) {
      OInteger result = new OInteger(-1);
      result.setValue((Integer) value);
      return result;
    }
    if (value instanceof BigDecimal) {
      OExpression result = new OExpression(-1);
      OFunctionCall funct = new OFunctionCall(-1);
      result.mathExpression = new OBaseExpression(-1);
      ((OBaseExpression) result.mathExpression).identifier = new OBaseIdentifier(-1);
      ((OBaseExpression) result.mathExpression).identifier.levelZero = new OLevelZeroIdentifier(-1);
      ((OBaseExpression) result.mathExpression).identifier.levelZero.functionCall = funct;
      funct.name = new OIdentifier("decimal");
      OExpression stringExp = new OExpression(-1);
      stringExp.mathExpression = new OBaseExpression(((BigDecimal) value).toPlainString());
      funct.getParams().add(stringExp);
      return result;
    }

    if (value instanceof Number) {
      OFloatingPoint result = new OFloatingPoint(-1);
      result.sign = ((Number) value).doubleValue() >= 0 ? 1 : -1;
      result.stringValue = value.toString();
      if (result.stringValue.startsWith("-")) {
        result.stringValue = result.stringValue.substring(1);
      }
      return result;
    }
    if (value instanceof String) {
      return value;
    }
    if (OMultiValue.isMultiValue(value)
        && !(value instanceof byte[])
        && !(value instanceof Byte[])) {
      OCollection coll = new OCollection(-1);
      coll.expressions = new ArrayList<OExpression>();
      Iterator iterator = OMultiValue.getMultiValueIterator(value);
      while (iterator.hasNext()) {
        Object o = iterator.next();
        OExpression exp = new OExpression(-1);
        exp.value = toParsedTree(o);
        coll.expressions.add(exp);
      }
      return coll;
    }
    if (value instanceof Map) {
      OJson json = new OJson(-1);
      json.items = new ArrayList<OJsonItem>();
      for (Object entry : ((Map) value).entrySet()) {
        OJsonItem item = new OJsonItem();
        item.leftString = "" + ((Map.Entry) entry).getKey();
        OExpression exp = new OExpression(-1);
        exp.value = toParsedTree(((Map.Entry) entry).getValue());
        item.right = exp;
        json.items.add(item);
      }
      return json;
    }
    if (value instanceof OIdentifiable) {
      // TODO if invalid build a JSON
      ORid rid = new ORid(-1);
      String stringVal = ((OIdentifiable) value).getIdentity().toString().substring(1);
      String[] splitted = stringVal.split(":");
      OInteger c = new OInteger(-1);
      c.setValue(Integer.parseInt(splitted[0]));
      rid.cluster = c;
      OInteger p = new OInteger(-1);
      p.setValue(Integer.parseInt(splitted[1]));
      rid.position = p;
      rid.setLegacy(true);
      return rid;
    }
    if (value instanceof Date) {
      OFunctionCall function = new OFunctionCall(-1);
      function.name = new OIdentifier(-1);
      function.name.value = "date";

      OExpression dateExpr = new OExpression(-1);
      dateExpr.singleQuotes = true;
      dateExpr.doubleQuotes = false;
      SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
      dateExpr.value = dateFormat.format(value);
      function.getParams().add(dateExpr);

      OExpression dateFormatExpr = new OExpression(-1);
      dateFormatExpr.singleQuotes = true;
      dateFormatExpr.doubleQuotes = false;
      dateFormatExpr.value = dateFormatString;
      function.getParams().add(dateFormatExpr);
      return function;
    }
    if (value.getClass().isEnum()) {
      return value.toString();
    }

    return this;
  }

  public OInputParameter copy() {
    throw new UnsupportedOperationException();
  }

  public static OInputParameter deserializeFromOResult(OResult doc) {
    try {
      OInputParameter result =
          (OInputParameter)
              Class.forName(doc.getProperty("__class"))
                  .getConstructor(Integer.class)
                  .newInstance(-1);
      result.deserialize(doc);
    } catch (Exception e) {
      throw OException.wrapException(new OCommandExecutionException(""), e);
    }
    return null;
  }

  @Override
  public void toGenericStatement(StringBuilder builder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void toString(Map<Object, Object> params, StringBuilder builder) {
    throw new UnsupportedOperationException();
  }

  public OResult serialize() {
    OResultInternal result = new OResultInternal();
    result.setProperty("__class", getClass().getName());
    return result;
  }

  public void deserialize(OResult fromResult) {
    throw new UnsupportedOperationException();
  }
}
/* JavaCC - OriginalChecksum=bb2f3732f5e3be4d954527ee0baa9020 (do not edit this line) */
