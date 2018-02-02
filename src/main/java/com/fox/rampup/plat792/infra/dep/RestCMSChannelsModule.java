package com.fox.rampup.plat792.infra.dep;

import com.fox.platform.lib.cfg.ConfigLibFactory;
import com.fox.rampup.plat792.infra.conf.RestCMSChannelsVerticleConfig;
import com.fox.rampup.plat792.infra.conf.impl.RestCMSChannelsVerticleConfigImpl;
import com.google.inject.AbstractModule;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * This module binds restConfiguration 
 * @author andersonv
 *
 */
public class RestCMSChannelsModule extends AbstractModule
{
  private Vertx vertx;
  private RestCMSChannelsVerticleConfig restCMSChannlesVerticleConfig;

  public RestCMSChannelsModule(Vertx vertx, JsonObject config)
  {
    super();
    this.vertx = vertx;
    restCMSChannlesVerticleConfig = (RestCMSChannelsVerticleConfig)ConfigLibFactory
        .FACTORY
        .createServiceConfig(config, RestCMSChannelsVerticleConfigImpl.CONFIG_FIELD
            , RestCMSChannelsVerticleConfigImpl.class);
  }

  @Override
  protected void configure()
  {
    bind(Vertx.class).toInstance(vertx);
    bind(RestCMSChannelsVerticleConfig.class).toInstance(restCMSChannlesVerticleConfig);
  }


}
