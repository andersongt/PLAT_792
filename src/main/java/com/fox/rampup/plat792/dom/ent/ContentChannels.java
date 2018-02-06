package com.fox.rampup.plat792.dom.ent;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Data;

/**
 * Encapsulates a set of channels data
 * @author andersonv
 *
 */
@Data
@JsonIgnoreProperties
@JsonIgnoreType
public class ContentChannels
{

  private  List<ContentObject> channels;
  
} 
