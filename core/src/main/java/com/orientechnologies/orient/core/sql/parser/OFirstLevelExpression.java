/* Generated By:JJTree: Do not edit this line. OFirstLevelExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

public class OFirstLevelExpression extends OMathExpression {
  public OFirstLevelExpression(int id) {
    super(id);
  }

  public OFirstLevelExpression(OrientSql p, int id) {
    super(p, id);
  }

  @Override
  protected boolean supportsBasicCalculation() {
    return super.supportsBasicCalculation();
  }

  // never used, this class is never returned by the parser!
  public boolean isBaseIdentifier() {
    if (value instanceof OIdentifier) {
      return true;
    }
    return false;
  }
}
/* JavaCC - OriginalChecksum=30dc1016b686d4841bbd57d6e6c0bfbd (do not edit this line) */