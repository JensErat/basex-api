package org.basex.api.xmldb;

import java.io.IOException;

import org.basex.BaseX;
import org.basex.data.XMLSerializer;
import org.basex.io.CachedOutput;
import org.basex.query.xpath.values.Item;
import org.basex.query.xpath.values.NodeSet;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ErrorCodes;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;

/**
 * Implementation of the Resource Interface for the XMLDB:API.
 *
 * @author Workgroup DBIS, University of Konstanz 2005-08, ISC License
 * @author Andreas Weiler
 */
public class BXResource implements Resource {
  /** Result. */
  Item result;
  /** Position for value. */
  int pos;

  /**
   * Standard Constructor.
   * @param r result
   * @param p position
   */
  public BXResource(final Item r, final int p) {
    result = r;
    pos = p;
  }

  public Object getContent() throws XMLDBException {
    try {
      final CachedOutput out = new CachedOutput();
      final XMLSerializer ser = new XMLSerializer(out);
      if(result instanceof NodeSet) {
        final NodeSet nodes = (NodeSet) result;
        ser.xml(nodes.data, nodes.nodes[pos]);
      } else {
        ser.item(result.str());
      }
      return out.toString();
    } catch(final IOException ex) {
      throw new XMLDBException(ErrorCodes.UNKNOWN_ERROR, ex.getMessage());
    }
  }

  public String getId() {
    BaseX.notimplemented();
    return null;
  }

  public Collection getParentCollection() {
    BaseX.notimplemented();
    return null;
  }

  public String getResourceType() {
    return result.getClass().getSimpleName();
  }

  public void setContent(final Object value) {
    BaseX.notimplemented();
  }
}