package com.cqupt.sysManger.dao;

import java.io.*;
import java.math.*;
import java.sql.*;

public class Role implements Serializable {
    private BigDecimal roleId;
    private String roleName;
    private String roleDesc;

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public BigDecimal getRoleId() {
        return this.roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleDesc() {
        return this.roleDesc;
    }

}