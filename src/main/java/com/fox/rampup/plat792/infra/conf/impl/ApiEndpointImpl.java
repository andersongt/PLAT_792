package com.fox.rampup.plat792.infra.conf.impl;

import com.fox.rampup.plat792.infra.conf.ApiEndpoint;
import io.vertx.core.http.HttpMethod;
import lombok.Data;

/**
 * This class implements an ApiEndpoint
 * 
 * @author andersonv
 *
 */

@Data
public class ApiEndpointImpl implements ApiEndpoint
{
  private String apiPath;
  private HttpMethod httpMethod;

  @Override
  public String getApiPaht()
  {
    return apiPath;
  }

  @Override
  public HttpMethod getHttpMethod()
  {
    return httpMethod;
  }

}
