<%@ page contentType="text/html; charset=UTF-8" %>
 <html xmlns="http://www.w3.org/1999/xhtml">
 <%@ taglib uri="/WEB-INF/tld/organizationTree.tld" prefix="organizationTree" %>

    <head>

        <title></title>

<link href="com_css/LigerUILib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="com_css/LigerUILib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />  
<script src="com_css/LigerUILib/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
<script src="com_css/LigerUILib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="com_css/LigerUILib/ligerUI/js/plugins/ligerLayout.js" type="text/javascript"></script>
<link href="com_css/css/xtree.css" rel="stylesheet"  type="text/css"/> 
<script type="text/javascript" src="com_css/js/xtree.js"></script>
<script type="text/javascript" src="com_css/js/xmlextras.js"></script>
<script type="text/javascript" src="com_css/js/xloadtree.js"></script>
<style type="text/css"> 

body{ padding:5px; margin:0; padding-bottom:15px;}
#layout1{  width:100%;margin:0; padding:0;  }
.l-page-top{ height:80px; background:#f8f8f8; margin-bottom:3px;}
h4{ margin:20px;}
.l-layout-content{
height:100%;
width:100%;
}
</style>
<script type="text/javascript">
				$(function ()
                {
                    $("#layout1").ligerLayout({ leftWidth:250,centerWidth:600});
                 

                });
                
</script> 
    </head>

    <body style="padding:10px">  

      <div id="layout1">
 
            <div position="left" title="组织结构" >
            <!--  <iframe name="tree_frame" id="tree_frame"  marginwidth=10 marginheight=25 width="100%" height="100%" src="pub/organizationAllDeptTree.jsp" frameborder=0></iframe>    
          -->
           <iframe name="tree_frame" id="tree_frame"  marginwidth=10 marginheight=25 width="100%" height="100%" src="sysManger/organizationTree.jsp" frameborder=0></iframe>    
             
            </div>
 
            <div position="center" title="机构信息" >
            <iframe name="content_frame" id="content_frame"  marginwidth=0 marginheight=0 width="100%" height="100%" src="sysManger/organizationList.jsp" frameborder=0></iframe>
             </div>

 

        </div> 

    </body>

    </html>

