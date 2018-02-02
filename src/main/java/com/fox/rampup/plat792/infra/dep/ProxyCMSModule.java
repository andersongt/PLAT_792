/**
 * 
 */
package com.fox.rampup.plat792.infra.dep;

import com.fox.platform.lib.cfg.ConfigLibFactory;
import com.fox.platform.lib.cfg.EndpointConfig;
import com.fox.platform.lib.fac.WebClientFactory;
import com.fox.platform.lib.fac.WebClientFactoryImpl;
import com.fox.rampup.plat792.infra.anti.CMSAnticorruption;
import com.fox.rampup.plat792.infra.anti.impl.CMSAnticorruptionImpl;
import com.fox.rampup.plat792.infra.conf.CmsAnticorruptionConfig;
import com.fox.rampup.plat792.infra.conf.RestCMSChannelsVerticleConfig;
import com.fox.rampup.plat792.infra.conf.impl.RestCMSChannelsVerticleConfigImpl;
import com.fox.rampup.plat792.infra.repo.ChannelCMSRepository;
import com.fox.rampup.plat792.infra.repo.impl.ChannelCMSRespositoryImpl;
import com.fox.rampup.plat792.infra.trans.ProxyCmsTranslator;
import com.fox.rampup.plat792.infra.trans.impl.ProxyCmsTranslatorImpl;
import com.google.inject.AbstractModule;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * This module binds classes needed where going to be injected in the proxy component.
 * 
 * @author andersonv
 *
 */
public class ProxyCMSModule extends AbstractModule
{
  private Vertx vertx;
  private RestCMSChannelsVerticleConfig restCMSChannlesVerticleConfig;
  private WebClientFactory webClientFactory;



  public ProxyCMSModule(Vertx vertx, JsonObject config)
  {
    super();
    this.vertx = vertx;
    restCMSChannlesVerticleConfig =
        (RestCMSChannelsVerticleConfig) ConfigLibFactory.FACTORY.createServiceConfig(
            config, RestCMSChannelsVerticleConfigImpl.CONFIG_FIELD,
            RestCMSChannelsVerticleConfigImpl.class);

    webClientFactory = new WebClientFactoryImpl(vertx,
        restCMSChannlesVerticleConfig.getProxyCMSChannelsVerticleConfig().getEndpoint());
  }

  @Override
  protected void configure()
  {
    bind(Vertx.class).toInstance(vertx);
    bind(WebClientFactory.class).toInstance(webClientFactory);

    bind(RestCMSChannelsVerticleConfig.class).toInstance(restCMSChannlesVerticleConfig);
    bind(ChannelCMSRepository.class).to(ChannelCMSRespositoryImpl.class);
    bind(CMSAnticorruption.class).to(CMSAnticorruptionImpl.class);
    bind(CmsAnticorruptionConfig.class)
        .toInstance(restCMSChannlesVerticleConfig.getCmsAnticorruptionConfig());
    bind(EndpointConfig.class).toInstance(
        restCMSChannlesVerticleConfig.getProxyCMSChannelsVerticleConfig().getEndpoint());
    bind(ProxyCmsTranslator.class).to(ProxyCmsTranslatorImpl.class);

  }


}
