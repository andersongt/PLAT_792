package com.fox.rampup.plat792.dom.ent;

import java.util.List;
import lombok.Data;

/**
 * Encapsulates each content channel
 * @author andersonv
 *
 */

@Data
public class ContentObject
{
  private String contentId;
  private List<Field> contentFields;
}
