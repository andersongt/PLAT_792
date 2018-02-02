package com.fox.rampup.plat792.infra.conf;

import io.vertx.core.http.HttpMethod;

/**
 * Interface that defines operations on endpoints
 * @author andersonv
 *
 */
public interface ApiEndpoint
{
  public String getApiPaht();
  public HttpMethod getHttpMethod();
}
