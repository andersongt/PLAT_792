package com.fox.rampup.plat792.infra.conf.impl;

import com.fox.platform.lib.cfg.impl.EndpointConfigImpl;
import com.fox.rampup.plat792.infra.conf.ProxyCmsChannelsVerticleConfig;
import lombok.Data;

/**
 * This class implements  the ProxyChannel Configuration.
 * 
 * @author andersonv
 *
 */
@Data
public class ProxyCmsChannelsVerticleConfigImpl implements ProxyCmsChannelsVerticleConfig
{
  private String address;
  private EndpointConfigImpl endpoint;
  private static final String DEFAULT_ADDRESS = "getChannelsProxyAddr";

   public ProxyCmsChannelsVerticleConfigImpl()
   {
     this.address = DEFAULT_ADDRESS;

   }
  @Override
  public String getAddress()
  {
    return address;
  }
  public void setAddress(String a)
  {
    this.address = a;
  }


}
