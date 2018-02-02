package com.fox.rampup.plat792.infra.conf.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fox.rampup.plat792.infra.conf.ChannelQuery;
import com.fox.rampup.plat792.infra.conf.CmsAnticorruptionConfig;

/**
 * This class implements the Anticorruption configuration
 * @author andersonv
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public  class CmsAnticorruptionConfigImpl implements CmsAnticorruptionConfig
{
  private ChannelQueryImpl channelQuery;
  
  public CmsAnticorruptionConfigImpl()
  {
    this.channelQuery = new ChannelQueryImpl();
  }
  @Override
  public ChannelQuery getChannelQuery()
  {
    return channelQuery;
  }
  public void setChannelQuery(ChannelQueryImpl chquery)
  {
    this.channelQuery = chquery;
  }

  
}
