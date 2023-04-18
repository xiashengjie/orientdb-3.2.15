/* Generated By:JJTree: Do not edit this line. OFieldMatchPathItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.record.impl.ODocument;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class OFieldMatchPathItem extends OMatchPathItem {

  protected OIdentifier field;

  private OSuffixIdentifier exp;

  public OFieldMatchPathItem(int id) {
    super(id);
  }

  public OFieldMatchPathItem(OrientSql p, int id) {
    super(p, id);
  }

  /** Accept the visitor. */
  public boolean isBidirectional() {
    return false;
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    builder.append(".");
    field.toString(params, builder);
    if (filter != null) {
      filter.toString(params, builder);
    }
  }

  public void toGenericStatement(StringBuilder builder) {
    builder.append(".");
    field.toGenericStatement(builder);
    if (filter != null) {
      filter.toGenericStatement(builder);
    }
  }

  protected Iterable<OIdentifiable> traversePatternEdge(
      OMatchStatement.MatchContext matchContext,
      OIdentifiable startingPoint,
      OCommandContext iCommandContext) {

    //    Iterable possibleResults = null;
    //    if (filter != null) {
    //      OIdentifiable matchedNode = matchContext.matched.get(filter.getAlias());
    //      if (matchedNode != null) {
    //        possibleResults = Collections.singleton(matchedNode);
    //      } else if (matchContext.matched.containsKey(filter.getAlias())) {
    //        possibleResults = Collections.emptySet();//optional node, the matched element is a
    // null value
    //      } else {
    //        possibleResults = matchContext.candidates == null ? null :
    // matchContext.candidates.get(filter.getAlias());
    //      }
    //    }

    if (exp == null) {
      exp = new OSuffixIdentifier(field);
    }
    // TODO check possible results!
    Object qR = this.exp.execute(startingPoint, iCommandContext);
    return (qR instanceof Iterable && !(qR instanceof ODocument))
        ? (Iterable) qR
        : Collections.singleton((OIdentifiable) qR);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    OFieldMatchPathItem that = (OFieldMatchPathItem) o;
    return Objects.equals(field, that.field) && Objects.equals(exp, that.exp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), field, exp);
  }

  @Override
  public OMatchPathItem copy() {
    OFieldMatchPathItem result = null;
    try {
      result = getClass().getConstructor(Integer.TYPE).newInstance(-1);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    result.field = field == null ? null : field.copy();
    result.method = method == null ? null : method.copy();
    result.filter = filter == null ? null : filter.copy();
    return result;
  }

  public OIdentifier getField() {
    return field;
  }

  public OSuffixIdentifier getExp() {
    if (exp == null) {
      exp = new OSuffixIdentifier(field);
    }
    return exp;
  }
}
/* JavaCC - OriginalChecksum=15b3f60b021891a7fb2a2fa6dfd29400 (do not edit this line) */
