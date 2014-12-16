<%@ page contentType="text/html; charset=UTF-8" %>
 <html xmlns="http://www.w3.org/1999/xhtml">
 <%@ taglib uri="/WEB-INF/tld/organizationTree.tld" prefix="organizationTree" %>

    <head>

        <title></title>


<script type="text/javascript" src="../com_css/js/xtree.js"></script>
<script type="text/javascript" src="../com_css/js/xmlextras.js"></script>
<script type="text/javascript" src="../com_css/js/xloadtree.js"></script>
<link type="text/css" rel="stylesheet" href="../com_css/css/xtree.css" /> 
 
 <script type="text/javascript">


	function changeList(deptId)
    {
		if(deptId)
        {
			var org = deptId.split(":");
			var orgId = org[0];
			var orgName = org[1];
			if(orgId.indexOf(";;") != -1){
				orgId = orgId.substring(0,orgId.indexOf(";;"));
			}
			var listUrl = 'sysManger/organizationList.jsp?deptId='+orgId;
            window.parent.document.getElementById('content_frame').src = listUrl;
   		}
                	
     }
                
                </script> 
   
    </head>

    <body>  

 
 
          
    <organizationTree:organizationTree></organizationTree:organizationTree>

    </body>

    </html>

