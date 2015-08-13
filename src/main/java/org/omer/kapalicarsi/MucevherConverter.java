/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.omer.kapalicarsi;

/**
 *
 * @author pc
 */

 import java.util.ArrayList;
 import java.util.List;

 import javax.faces.application.FacesMessage;
 import javax.faces.component.UIComponent;
 import javax.faces.context.FacesContext;
 import javax.faces.convert.Converter;
 import javax.faces.convert.ConverterException;
 import javax.faces.convert.FacesConverter;

  @FacesConverter(forClass = MucevherDetay.class,value="mucevherlerConvert")
public class MucevherConverter  implements Converter{



    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (MucevherDetay s : Servis.getAvaibleItems()) {
                    if (s.getMucId()== number) {
                        return s;
                    }
                }

            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((MucevherDetay) value).getMucId());
        }
    }
}
