package com.cqupt.sysManger.dao;

import java.io.*;
import java.math.*;
import java.sql.*;

public class RolePopedom implements Serializable {
    private BigDecimal rolePopedomId;
    private BigDecimal roleId;
    private BigDecimal elementId;
    private String roleType;

    public void setRolePopedomId(BigDecimal rolePopedomId) {
        this.rolePopedomId = rolePopedomId;
    }

    public BigDecimal getRolePopedomId() {
        return this.rolePopedomId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public BigDecimal getRoleId() {
        return this.roleId;
    }

    public void setElementId(BigDecimal elementId) {
        this.elementId = elementId;
    }

    public BigDecimal getElementId() {
        return this.elementId;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleType() {
        return this.roleType;
    }

}