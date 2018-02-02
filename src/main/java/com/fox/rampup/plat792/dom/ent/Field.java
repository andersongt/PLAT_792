package com.fox.rampup.plat792.dom.ent;

import lombok.Data;

/**
 * Encapsulates field data
 * @author andersonv
 *
 */
@Data
public class Field
{
  private  String key;
  private  String value;
  
   public Field(String k, String v)
   {
     this.key = k;
     this.value = v;
   }
}
