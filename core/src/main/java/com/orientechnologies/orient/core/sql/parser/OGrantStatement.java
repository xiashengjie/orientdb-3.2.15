/* Generated By:JJTree: Do not edit this line. OGrantStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.exception.OCommandExecutionException;
import com.orientechnologies.orient.core.metadata.security.ORole;
import com.orientechnologies.orient.core.metadata.security.OSecurityInternal;
import com.orientechnologies.orient.core.metadata.security.OSecurityPolicyImpl;
import com.orientechnologies.orient.core.sql.executor.OInternalResultSet;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import java.util.Map;
import java.util.Objects;

public class OGrantStatement extends OSimpleExecStatement {
  protected OPermission permission;
  protected OIdentifier policyName;
  protected OSecurityResourceSegment securityResource;
  protected OIdentifier actor;

  public OGrantStatement(int id) {
    super(id);
  }

  public OGrantStatement(OrientSql p, int id) {
    super(p, id);
  }

  @Override
  public OResultSet executeSimple(OCommandContext ctx) {
    ODatabaseDocumentInternal db = getDatabase();
    ORole role = db.getMetadata().getSecurity().getRole(actor.getStringValue());
    if (role == null)
      throw new OCommandExecutionException("Invalid role: " + actor.getStringValue());

    String resourcePath = securityResource.toString();
    if (permission != null) {
      role.grant(resourcePath, toPrivilege(permission.permission));
      role.save();
    } else {
      OSecurityInternal security = db.getSharedContext().getSecurity();
      OSecurityPolicyImpl policy = security.getSecurityPolicy(db, policyName.getStringValue());
      security.setSecurityPolicy(db, role, securityResource.toString(), policy);
    }

    OInternalResultSet rs = new OInternalResultSet();
    OResultInternal result = new OResultInternal();
    result.setProperty("operation", "grant");
    result.setProperty("role", actor.getStringValue());
    if (permission != null) {
      result.setProperty("permission", permission.toString());
    } else {
      result.setProperty("policy", policyName.getStringValue());
    }
    result.setProperty("resource", resourcePath);
    rs.add(result);
    return rs;
  }

  protected int toPrivilege(String privilegeName) {
    int privilege;
    if ("CREATE".equals(privilegeName)) privilege = ORole.PERMISSION_CREATE;
    else if ("READ".equals(privilegeName)) privilege = ORole.PERMISSION_READ;
    else if ("UPDATE".equals(privilegeName)) privilege = ORole.PERMISSION_UPDATE;
    else if ("DELETE".equals(privilegeName)) privilege = ORole.PERMISSION_DELETE;
    else if ("EXECUTE".equals(privilegeName)) privilege = ORole.PERMISSION_EXECUTE;
    else if ("ALL".equals(privilegeName)) privilege = ORole.PERMISSION_ALL;
    else if ("NONE".equals(privilegeName)) privilege = ORole.PERMISSION_NONE;
    else throw new OCommandExecutionException("Unrecognized privilege '" + privilegeName + "'");
    return privilege;
  }

  @Override
  public void toString(Map<Object, Object> params, StringBuilder builder) {
    builder.append("GRANT ");
    if (permission != null) {
      permission.toString(params, builder);
    } else {
      builder.append("POLICY ");
      policyName.toString(params, builder);
    }
    builder.append(" ON ");
    securityResource.toString(params, builder);
    builder.append(" TO ");
    actor.toString(params, builder);
  }

  @Override
  public void toGenericStatement(StringBuilder builder) {
    builder.append("GRANT ");
    if (permission != null) {
      permission.toGenericStatement(builder);
    } else {
      builder.append("POLICY ");
      policyName.toGenericStatement(builder);
    }
    builder.append(" ON ");
    securityResource.toGenericStatement(builder);
    builder.append(" TO ");
    actor.toGenericStatement(builder);
  }

  @Override
  public OGrantStatement copy() {
    OGrantStatement result = new OGrantStatement(-1);
    result.permission = permission == null ? null : permission.copy();
    result.securityResource = securityResource == null ? null : securityResource.copy();
    result.policyName = this.policyName == null ? null : policyName.copy();
    result.actor = actor == null ? null : actor.copy();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OGrantStatement that = (OGrantStatement) o;
    return Objects.equals(permission, that.permission)
        && Objects.equals(policyName, that.policyName)
        && Objects.equals(securityResource, that.securityResource)
        && Objects.equals(actor, that.actor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(permission, policyName, securityResource, actor);
  }
}
/* JavaCC - OriginalChecksum=c5f7b91e57070a95c6ea490373d16f7f (do not edit this line) */