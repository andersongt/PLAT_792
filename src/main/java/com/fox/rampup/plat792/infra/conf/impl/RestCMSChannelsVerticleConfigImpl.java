/**
 * 
 */
package com.fox.rampup.plat792.infra.conf.impl;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fox.rampup.plat792.infra.conf.ApiEndpoint;
import com.fox.rampup.plat792.infra.conf.CmsAnticorruptionConfig;
import com.fox.rampup.plat792.infra.conf.ProxyCmsChannelsVerticleConfig;
import com.fox.rampup.plat792.infra.conf.RestCMSChannelsVerticleConfig;
import io.vertx.core.http.HttpServerOptions;


/**
 * This class implements the RestApi configuration.
 * @author andersonv
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestCMSChannelsVerticleConfigImpl implements RestCMSChannelsVerticleConfig
{

  public static final String CONFIG_FIELD = "restCMSChannelsVerticleConfig";
  private static final int DEFAULT_PORT = 8081;

  private Map<String, ApiEndpointImpl> apiEndpoints;
  private CmsAnticorruptionConfigImpl cmsAnticorruptionConfig;
  private HttpServerOptions httpServerOptions;
  private ProxyCmsChannelsVerticleConfigImpl proxyCmsChannelsVerticleConfig;



  public RestCMSChannelsVerticleConfigImpl()
  {
    this.httpServerOptions = new HttpServerOptions();
    this.httpServerOptions.setPort(DEFAULT_PORT);
  }
  
  @Override
  public Map<String, ? extends ApiEndpoint> getApiEndpoints()
  {
    return apiEndpoints;
  }
  
  @Override
  public CmsAnticorruptionConfig getCmsAnticorruptionConfig()
  {
    return cmsAnticorruptionConfig;
  }

  @Override
  public HttpServerOptions getHttpServerOptions()
  {
    return httpServerOptions;
  }

  @Override
  public ProxyCmsChannelsVerticleConfig getProxyCMSChannelsVerticleConfig()
  {
    return proxyCmsChannelsVerticleConfig;
  }
  public void setApiEndpoints(Map<String, ApiEndpointImpl> mapEndpoint)
  {
    this.apiEndpoints = mapEndpoint;
  }

  public void setCmsAnticorruptionConfig(CmsAnticorruptionConfigImpl anti)
  {
    this.cmsAnticorruptionConfig = anti;
  }

  public void setProxyCmsChannelsVerticleConfig(ProxyCmsChannelsVerticleConfigImpl prox)
  {
    this.proxyCmsChannelsVerticleConfig = prox;
  }



}
