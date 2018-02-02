package com.fox.rampup.plat792.infra.anti.impl;

import java.util.Map;
import com.fox.platform.lib.cfg.EndpointConfig;
import com.fox.platform.lib.fac.WebClientFactory;
import com.fox.rampup.plat792.dom.ent.ContentChannels;
import com.fox.rampup.plat792.infra.anti.CMSAnticorruption;
import com.fox.rampup.plat792.infra.conf.CmsAnticorruptionConfig;
import com.fox.rampup.plat792.infra.trans.ProxyCmsTranslator;
import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.web.client.WebClient;


/**
 * This class implements methods for make requests to external systems.
 * 
 * @author andersonv
 *
 */
public class CMSAnticorruptionImpl implements CMSAnticorruption
{

  private CmsAnticorruptionConfig cmsAnticorruptionConfig;
  private WebClient webClient;
  private EndpointConfig endPointConfig;
  private ProxyCmsTranslator proxyCmsTranslator;

  @Inject
  public CMSAnticorruptionImpl(CmsAnticorruptionConfig antiConfig,
      WebClientFactory webclientfactory, EndpointConfig endConfig,
      ProxyCmsTranslator translator)
  {
    this.cmsAnticorruptionConfig = antiConfig;
    this.webClient = webclientfactory.createWebClient();
    this.endPointConfig = endConfig;
    this.proxyCmsTranslator = translator;
  }

  /**
   * Requests data to an external system
   * 
   * @param params
   * @param resultChannels
   */
  @Override
  public void getContentChannels(Map<String, String> params,
      Handler<AsyncResult<ContentChannels>> resultChannels)
  {

    Future<ContentChannels> futureChannels = Future.future();
    futureChannels.setHandler(resultChannels);

    webClient
        .post(endPointConfig.getPort(),
            cmsAnticorruptionConfig.getChannelQuery().getHost(),
            cmsAnticorruptionConfig.getChannelQuery().getUrlPath())
        .ssl(endPointConfig.isSsl()).sendJson(
            proxyCmsTranslator.mapRequestBodyGetConentToJson(
                cmsAnticorruptionConfig.getChannelQuery().getRequestPayload(), params),
            response -> {
              if (response.succeeded())
              {

                ContentChannels resultContent =
                    proxyCmsTranslator.mapCmsResponseJsonToContentChannels(
                        response.result().bodyAsJsonObject());

                futureChannels.complete(resultContent);
              } else
              {

                futureChannels.fail("Fail request");
              }
            });
  }
}