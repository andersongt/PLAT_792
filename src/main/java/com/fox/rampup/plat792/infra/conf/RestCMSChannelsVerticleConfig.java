package com.fox.rampup.plat792.infra.conf;

import java.util.Map;
import com.fox.platform.lib.cfg.ServiceConfig;
import io.vertx.core.http.HttpServerOptions;

/**
 * This interface defines operations on a RestConfigurations object.
 * 
 * @author andersonv
 *
 */
public interface RestCMSChannelsVerticleConfig extends ServiceConfig
{

  public Map<String, ? extends ApiEndpoint> getApiEndpoints();

  public CmsAnticorruptionConfig getCmsAnticorruptionConfig();

  public HttpServerOptions getHttpServerOptions();

  public ProxyCmsChannelsVerticleConfig getProxyCMSChannelsVerticleConfig();



}
