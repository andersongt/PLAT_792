package com.fox.rampup.plat792.infra.repo.impl;

import java.util.HashMap;
import java.util.Map;
import com.fox.rampup.plat792.dom.ent.ContentChannels;
import com.fox.rampup.plat792.infra.anti.CMSAnticorruption;
import com.fox.rampup.plat792.infra.repo.ChannelCMSRepository;
import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * This class implements operations needed by the Proxy component.
 * 
 * @author andersonv
 *
 */
public class ChannelCMSRespositoryImpl implements ChannelCMSRepository
{

  private CMSAnticorruption cmsAnticorruption;
  private static final String FIRST_PARAM_KEY = "country";

  @Inject
  public ChannelCMSRespositoryImpl(CMSAnticorruption cmsAnti)
  {
    this.cmsAnticorruption = cmsAnti;
  }

  @Override
  public void getChannelsFields(String countryCode,
      Handler<AsyncResult<ContentChannels>> resultContnetChannels)
  {

    Map<String, String> params = new HashMap<>();
    params.put(FIRST_PARAM_KEY, countryCode);
    this.cmsAnticorruption.getContentChannels(params, resultContnetChannels);


  }



}
