/* Generated By:JJTree: Do not edit this line. OSecurityResourceSegment.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import java.util.Map;

public class OSecurityResourceSegment extends SimpleNode {

  protected boolean star = false;
  protected OIdentifier identifier;

  protected OSecurityResourceSegment next;

  public OSecurityResourceSegment(int id) {
    super(id);
  }

  public OSecurityResourceSegment(OrientSql p, int id) {
    super(p, id);
  }

  public boolean isStar() {
    return star;
  }

  public OIdentifier getIdentifier() {
    return identifier;
  }

  public OSecurityResourceSegment getNext() {
    return next;
  }

  @Override
  public void toString(Map<Object, Object> params, StringBuilder builder) {
    if (this.star) {
      builder.append("*");
    } else {
      identifier.toString(params, builder);
    }
    if (next != null) {
      builder.append(".");
      next.toString(params, builder);
    }
  }

  @Override
  public void toGenericStatement(StringBuilder builder) {
    if (this.star) {
      builder.append("*");
    } else {
      identifier.toGenericStatement(builder);
    }
    if (next != null) {
      builder.append(".");
      next.toGenericStatement(builder);
    }
  }

  @Override
  public OSecurityResourceSegment copy() {
    OSecurityResourceSegment result = new OSecurityResourceSegment(-1);
    result.star = this.star;
    result.identifier = this.identifier == null ? null : this.identifier.copy();
    result.next = this.next == null ? null : this.next.copy();
    return result;
  }
}
/* JavaCC - OriginalChecksum=f51870252b37ccb5ff69ec19ed9687ab (do not edit this line) */
