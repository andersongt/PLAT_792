package com.fox.rampup.plat792.infra.conf;

import com.fox.platform.lib.cfg.ProxyConfig;

/**
 * This interface defines operations on a Proxy configuration object.
 * 
 * @author andersonv
 */
public interface ProxyCmsChannelsVerticleConfig extends ProxyConfig
{
  public String getAddress();
}
