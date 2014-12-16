package com.cqupt.sysManger.dao;

import java.io.Serializable;
import java.math.BigDecimal;

public class Popedom implements Serializable{
    private String elementId;
    private String pElementId;
    private String elementName;
    private String elementTitle;
    private String elementUrl;
    private String elementGrade;
    private String elementOrder;
    private String elementDesc;
    private String elementType;

  

    public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getPElementId() {
		return pElementId;
	}

	public void setPElementId(String pElementId) {
		this.pElementId = pElementId;
	}

	public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementName() {
        return this.elementName;
    }

    public void setElementTitle(String elementTitle) {
        this.elementTitle = elementTitle;
    }

    public String getElementTitle() {
        return this.elementTitle;
    }

    public void setElementUrl(String elementUrl) {
        this.elementUrl = elementUrl;
    }

    public String getElementUrl() {
        return this.elementUrl;
    }

    public void setElementGrade(String elementGrade) {
        this.elementGrade = elementGrade;
    }

    public String getElementGrade() {
        return this.elementGrade;
    }

    public void setElementOrder(String elementOrder) {
        this.elementOrder = elementOrder;
    }

    public String getElementOrder() {
        return this.elementOrder;
    }

    public void setElementDesc(String tDesc) {
        this.elementDesc = tDesc;
    }

    public String getElementDesc() {
        return this.elementDesc;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementType() {
        return this.elementType;
    }

}
