<%@ page language="java" import="java.util.*,com.cqupt.pub.util.JsonUtil" pageEncoding="utf-8"%>
<%@ page
	import="com.cqupt.pub.dao.DataStormSession,java.util.*,java.util.Map,com.cqupt.pub.exception.CquptException,com.cqupt.pub.util.JsonUtil,sun.jdbc.rowset.CachedRowSet"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String appendixName1 = request.getAttribute("appendixName").toString();
%>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公告查看</title>
    
	<link href="../com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="../com_css/LigerUILib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />
    <link href="../com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />    
    <script src="../com_css/LigerUILib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>    
          
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>  
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerForm.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerButton.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>    
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/ligerUI/js/plugins/ligerTip.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
    <script src="../com_css/LigerUILib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../com_css/LigerUILib/jquery-validation/messages_cn.js" type="text/javascript"></script>
    <script src="../com_css/js/jquery.blockUI.js" type="text/javascript"></script> 
<script type="text/javascript"> 
function f_download(appendixName1){
	      		var url = "messageFileDownloadAction?appendixName="+appendixName1;
		  		window.open(url);
	    		window.close();
	      }
</script>
    <style type="text/css">
      body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
        .l-button-submit,.l-button-test{width:80px; float:left; margin-left:10px; padding-bottom:2px;}
        .l-verify-tip{ left:230px; top:120px;}

    .blackbold {  标题样式 
	width: 100px;
	height: 22px;
	font-size: 16px;
	font-weight: bold;
	font-family: Verdana, 微软雅黑;
	}
    </style>

  </head>
 

<body style="padding:6px; overflow:auto; background-color: #F7F8F9;" >
<br>

  <div align="center">
    <table width="85%">
      <tr><td colspan="2" height="10"></td></tr>
      <tr><td align="center"><h4 style="font-size:13pt">${request.messageTitle}</h4></td></tr>
      <tr><td colspan="2" height="3"></td></tr>		
      <tr ><td colspan="2" height="1" bgcolor="#BBD2E9" ></td></tr>
      <tr><td colspan="2" height="3"></td></tr>	
      <tr>
        <td  align="center" style="font-size:13px;">发布时间：${request.sendTime}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布人：${request.sendUser}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类型：${request.messageType}</td>
    </table>
     
     
     <br><br><br>
        <fieldset style="top:inherit;width:100%">
        <table  width="85%"  class="l-table-edit" style="font-size:10pt;font-family: Verdana, 微软雅黑;">
          <tr>                                       
            <td align="left"">
          		${request.messageContent}
			 </td>
          </tr>                  
            </table>
        </fieldset>


<br><br><br>

<table width="85%">
  <!--     <tr><td colspan="2" height="3"></td></tr>		
      <tr ><td colspan="2" height="1" bgcolor="#BBD2E9" ></td></tr>
      <tr><td colspan="2" height="10"></td></tr>	
   -->    <tr>
      <%if(!appendixName1.equals(""))
       {   String[] appendixName = appendixName1.split(";");
       	   for(int i=0; i<appendixName.length; i=i+1){
       %>
       
        <td  align="left" style="font-size:13px;width:80px;">
                <input type="button"  value="附件<%=i+1 %>下载" id="downLoadFileButton" class="l-button"  style="width:150px;border:none;" onclick="f_download('<%=appendixName[i]%>')"/></td>
       
    <%}} %>
    </tr>
    </table>
    

    </div>

</body>
</html>