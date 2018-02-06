package com.fox.rampup.plat792.infra.conf.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fox.rampup.plat792.infra.conf.ChannelQuery;
import lombok.Data;

/**
 * This class implements  a channel query configuration.
 * 
 * @author andersonv
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class ChannelQueryImpl implements ChannelQuery
{
  private String requestPayload;
  private String host;
  private String urlPath;
  private static final String DEFAULT_REQUESTPAYLOAD = "";
  private static final String DEFAULT_URLPATH = "";

  public ChannelQueryImpl()
  {
    this.requestPayload = DEFAULT_REQUESTPAYLOAD;
    this.urlPath = DEFAULT_URLPATH;
  }

  @Override
  public String getRequestPayload()
  {
    return requestPayload;
  }

  @Override
  public String getHost()
  {
    return host;
  }

  @Override
  public String getUrlPath()
  {
    return urlPath;
  }

  public void setHost(String pHost)
  {
    this.host = pHost;
  }

}
