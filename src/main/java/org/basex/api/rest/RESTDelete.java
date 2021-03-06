package org.basex.api.rest;

import static javax.servlet.http.HttpServletResponse.*;
import static org.basex.api.rest.RESTText.*;
import static org.basex.util.Token.*;

import java.io.*;

import org.basex.api.*;
import org.basex.core.cmd.*;
import org.basex.server.*;

/**
 * REST-based evaluation of DELETE operations.
 *
 * @author BaseX Team 2005-12, BSD License
 * @author Christian Gruen
 */
public class RESTDelete extends RESTCode {
  @Override
  void run(final HTTPContext http) throws HTTPException, IOException {
    // parse database options
    parseOptions(http);
    // open addressed database
    open(http);

    final Session session = http.session;
    if(http.depth() == 0) {
      throw new HTTPException(SC_NOT_FOUND, ERR_NOPATH);
    } else if(http.depth() == 1) {
      session.execute(new DropDB(http.db()));
    } else {
      session.execute(new Delete(http.dbpath()));
    }
    // return command info
    http.out.write(token(session.info()));
  }
}
