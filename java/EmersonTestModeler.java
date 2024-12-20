package com.Emerson.widget.EmersonTest;
import com.dassault_systemes.platform.restServices.ModelerBase;
import javax.ws.rs.ApplicationPath;
@ApplicationPath("/EmersonTestModel")
public class EmersonTestModeler extends ModelerBase
{
  public Class<?>[] getServices()
  {
    return new Class[] {EmersonTestService.class};
  }
}
