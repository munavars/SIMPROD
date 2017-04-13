package com.tiredex.yoko.utils;

import java.io.Serializable;

/*****************************************************************************
Program Name		: AddressView
Program Description	: See JavaDoc Comments

Program History		:

Programmer		Date			Description
Hemant Gaur		05-08-01		NEW
*****************************************************************************/
public class AddressView implements Serializable {

    private static final String CLASS_NAME = "AddressView";

	static final long serialVersionUID = 123;
	
    public String address2 = "";
    public String city = "";
    public String state = "";
    public String zipCode = "";
    /**
     * AddressView constructor comment.
     */
    public AddressView() {
        super();
    }
    /**
     * Insert the method's description here.
     * Creation date: (05/08/01 22:08:43)
     * @return String
     */
    public String getAddress2() {
        return address2;
    }
    /**
     * Insert the method's description here.
     * Creation date: (05/08/01 22:08:43)
     * @return String
     */
    public String getCity() {
        return city;
    }
    /**
     * Insert the method's description here.
     * Creation date: (05/08/01 22:08:43)
     * @return String
     */
    public String getState() {
        return state;
    }
    /**
     * Insert the method's description here.
     * Creation date: (05/08/01 22:08:43)
     * @return String
     */
    public String getZipCode() {
        return zipCode;
    }
    /**
     * Insert the method's description here.
     * Creation date: (05/08/01 22:08:43)
     * @param newAddress2 String
     */
    public void setAddress2(String newAddress2) {
        address2 = newAddress2;
    }
    /**
     * Insert the method's description here.
     * Creation date: (05/08/01 22:08:43)
     * @param newCity String
     */
    public void setCity(String newCity) {
        city = newCity;
    }
    /**
     * Insert the method's description here.
     * Creation date: (05/08/01 22:08:43)
     * @param newState String
     */
    public void setState(String newState) {
        state = newState;
    }
    /**
     * Insert the method's description here.
     * Creation date: (05/08/01 22:08:43)
     * @param newZipCode String
     */
    public void setZipCode(String newZipCode) {
        zipCode = newZipCode;
    }

    public String toString() {
        String s =
            CLASS_NAME + ": "
                + "address2 = [" + address2 + "], " 
                + "city = [" + city + "], "
                + "state = [" + state + "], "
                + "zipCode = [" + zipCode + "], "
                + "object hashCode = [" + this.hashCode() + "]";

        return s;
    }
}