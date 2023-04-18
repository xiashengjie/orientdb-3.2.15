/* Generated By:JJTree: Do not edit this line. OMatchFilter.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OMatchFilter extends SimpleNode {
  // TODO transform in a map
  protected List<OMatchFilterItem> items = new ArrayList<OMatchFilterItem>();

  public OMatchFilter(int id) {
    super(id);
  }

  public OMatchFilter(OrientSql p, int id) {
    super(p, id);
  }

  public String getAlias() {
    for (OMatchFilterItem item : items) {
      if (item.alias != null) {
        return item.alias.getStringValue();
      }
    }
    return null;
  }

  public void setAlias(String alias) {
    boolean found = false;
    for (OMatchFilterItem item : items) {
      if (item.alias != null) {
        item.alias = new OIdentifier(alias);
        found = true;
        break;
      }
    }
    if (!found) {
      OMatchFilterItem newItem = new OMatchFilterItem(-1);
      newItem.alias = new OIdentifier(alias);
      items.add(newItem);
    }
  }

  public OWhereClause getFilter() {
    for (OMatchFilterItem item : items) {
      if (item.filter != null) {
        return item.filter;
      }
    }
    return null;
  }

  public void setFilter(OWhereClause filter) {
    boolean found = false;
    for (OMatchFilterItem item : items) {
      if (item.filter != null) {
        item.filter = filter;
        found = true;
        break;
      }
    }
    if (!found) {
      OMatchFilterItem newItem = new OMatchFilterItem(-1);
      newItem.filter = filter;
      items.add(newItem);
    }
  }

  public OWhereClause getWhileCondition() {
    for (OMatchFilterItem item : items) {
      if (item.whileCondition != null) {
        return item.whileCondition;
      }
    }
    return null;
  }

  public String getClassName(OCommandContext context) {
    for (OMatchFilterItem item : items) {
      if (item.className != null) {
        if (item.className.value instanceof String) return (String) item.className.value;
        else if (item.className.value instanceof SimpleNode) {
          StringBuilder builder = new StringBuilder();

          ((SimpleNode) item.className.value)
              .toString(context == null ? null : context.getInputParameters(), builder);
          return builder.toString();
        } else if (item.className.isBaseIdentifier()) {
          return item.className.getDefaultAlias().getStringValue();
        } else {
          return item.className.toString();
        }
      }
    }
    return null;
  }

  public String getClusterName(OCommandContext context) {
    for (OMatchFilterItem item : items) {
      if (item.clusterName != null) {
        return item.clusterName.getStringValue();
      } else if (item.clusterId != null) {
        int cid = item.clusterId.value.intValue();
        String clusterName = context.getDatabase().getClusterNameById(cid);
        if (clusterName != null) {
          return clusterName;
        }
      }
    }
    return null;
  }

  public ORid getRid(OCommandContext context) {
    for (OMatchFilterItem item : items) {
      if (item.rid != null) {
        return item.rid;
      }
    }
    return null;
  }

  public Integer getMaxDepth() {
    for (OMatchFilterItem item : items) {
      if (item.maxDepth != null) {
        return item.maxDepth.value.intValue();
      }
    }
    return null;
  }

  public boolean isOptional() {
    for (OMatchFilterItem item : items) {
      if (Boolean.TRUE.equals(item.optional)) {
        return true;
      }
    }
    return false;
  }

  public String getDepthAlias() {
    for (OMatchFilterItem item : items) {
      if (item.depthAlias != null) {
        return item.depthAlias.getStringValue();
      }
    }
    return null;
  }

  public String getPathAlias() {
    for (OMatchFilterItem item : items) {
      if (item.pathAlias != null) {
        return item.pathAlias.getStringValue();
      }
    }
    return null;
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    builder.append("{");
    boolean first = true;
    for (OMatchFilterItem item : items) {
      if (!first) {
        builder.append(", ");
      }
      item.toString(params, builder);
      first = false;
    }
    builder.append("}");
  }

  public void toGenericStatement(StringBuilder builder) {
    builder.append("{");
    boolean first = true;
    for (OMatchFilterItem item : items) {
      if (!first) {
        builder.append(", ");
      }
      item.toGenericStatement(builder);
      first = false;
    }
    builder.append("}");
  }

  @Override
  public OMatchFilter copy() {
    OMatchFilter result = new OMatchFilter(-1);
    result.items =
        items == null ? null : items.stream().map(x -> x.copy()).collect(Collectors.toList());
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OMatchFilter that = (OMatchFilter) o;

    if (items != null ? !items.equals(that.items) : that.items != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return items != null ? items.hashCode() : 0;
  }

  public void addItem(OMatchFilterItem item) {
    this.items.add(item);
  }
}
/* JavaCC - OriginalChecksum=6b099371c69e0d0c1c106fc96b3072de (do not edit this line) */