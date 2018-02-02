package com.fox.rampup.plat792.infra.repo;

import com.fox.rampup.plat792.dom.ent.ContentChannels;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * This interface defines operations on CMSRepository component.
 * 
 * @author andersonv
 *
 */
public interface ChannelCMSRepository
{
  public void getChannelsFields(String countryCode,
      Handler<AsyncResult<ContentChannels>> resultChannels);

}
