package com.fox.rampup.plat792.infra.conf;

/**
 * Interface that defines operations on a request to external systems.
 * @author andersonv
 *
 */
public interface ChannelQuery
{
  public String getRequestPayload();
  public String getHost();
  public String getUrlPath();
  public void setHost(String host);

}
