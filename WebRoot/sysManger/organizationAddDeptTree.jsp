<%@ page contentType="text/html; charset=UTF-8" %>
 <%@ taglib uri="/WEB-INF/tld/organizationTree.tld" prefix="organizationTree" %>
<html>
<head>

<base target="_self">
<title>部门树</title>
 <script type="text/javascript" src="../com_css/js/xtree.js"></script>
<script type="text/javascript" src="../com_css/js/xmlextras.js"></script>
<script type="text/javascript" src="../com_css/js/xloadtree.js"></script>
<link type="text/css" rel="stylesheet" href="../com_css/css/xtree.css" /> 

</head>
<body leftmargin="5" topmargin="5">
<organizationTree:organizationTree></organizationTree:organizationTree>
</body>
<script type="text/javascript">

 function changeList(obj)
{
    window.returnValue = obj;
	window.close();
   }
                
</script>
</html>
