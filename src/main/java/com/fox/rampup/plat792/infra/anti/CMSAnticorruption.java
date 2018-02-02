package com.fox.rampup.plat792.infra.anti;

import java.util.Map;
import com.fox.rampup.plat792.dom.ent.ContentChannels;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Interface to make requests to external systems.
 * 
 * @author andersonv
 *
 */
public interface CMSAnticorruption
{
  public void getContentChannels(Map<String, String> params,
      Handler<AsyncResult<ContentChannels>> resultChannels);
}
