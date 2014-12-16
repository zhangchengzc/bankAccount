package com.cqupt.sysManger.tld;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class OrganizationTree  extends Component{
	HttpServletRequest request=null;
	
	public OrganizationTree(ValueStack stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}
	
	public boolean start(Writer writer){
		boolean result = super.start(writer);
		try{
			String str = getOrganization();
			writer.write(str);
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return result;
	}
	private String getOrganization(){
		this.request = ServletActionContext.getRequest();
//System.out.println("==========================================================123");
	//	System.out.println(request.getSession().getAttribute("deptId"));
	//	System.out.println(request.getSession().getAttribute("deptName"));
		
		//String deptId = (String)request.getSession().getAttribute("deptId");
		String deptId = "1";
		String deptName = "管理平台";
		
		String deptName1 = "管理平台";
		String deptName2 = "客商";
		String deptId1 ="1";
		String deptId2 ="2";
		StringBuilder resultStr = new StringBuilder();
		resultStr.append("<script type=\"text/javascript\">");
		
		resultStr.append("webFXTreeConfig.rootIcon		= \"../images/sysManage/folder.png\";");
		resultStr.append("webFXTreeConfig.openRootIcon = \"../images/sysManage/folder.png\";");
		resultStr.append("webFXTreeConfig.folderIcon = \"../images/sysManage/deptfolder.png\";");
		resultStr.append("webFXTreeConfig.openFolderIcon = \"../images/sysManage/deptfolder-open.png\";");
		resultStr.append("webFXTreeConfig.fileIcon	= \"../images/sysManage/dept.png\";");
		resultStr.append("webFXTreeConfig.lMinusIcon = \"../images/sysManage/Lminus.png\";");
		resultStr.append("webFXTreeConfig.lPlusIcon	= \"../images/sysManage/Lplus.png\";");
		resultStr.append("webFXTreeConfig.tMinusIcon = \"../images/sysManage/Tminus.png\";");
		resultStr.append("webFXTreeConfig.tPlusIcon	= \"../images/sysManage/Tplus.png\";");
		resultStr.append("webFXTreeConfig.iIcon	= \"../images/sysManage/I.png\";");
		resultStr.append("webFXTreeConfig.lIcon	= \"../images/sysManage/L.png\";");
		resultStr.append("webFXTreeConfig.tIcon	= \"../images/sysManage/T.png\";");
		resultStr.append("webFXTreeConfig.blankIcon	= \"../images/sysManage/blank.png\";");  
		resultStr.append("var tree = new WebFXTree(\"重庆终端公司\");");
		
		if(deptId.equals("1")){
			resultStr.append("tree.add(new WebFXLoadTreeItem(\""+deptName+"\", \"organizationQueryAction?deptId="+deptId+"\",action=\"javascript:changeList('"+deptId+":"+deptName+"')\"));");
			resultStr.append("tree.add(new WebFXLoadTreeItem(\""+deptName2+"\", \"organizationQueryAction?deptId="+deptId2+"\",action=\"javascript:changeList('"+deptId2+":"+deptName2+"')\"));");
			
		}else if(deptId.equals("2")){
			//resultStr.append("tree.add(new WebFXLoadTreeItem(\""+deptName+"\", \"organizationQueryAction?deptId="+deptId+"\",action=\"javascript:changeList('"+deptId+":"+deptName+"')\"));");
			resultStr.append("tree.add(new WebFXLoadTreeItem(\""+deptName2+"\", \"organizationQueryAction?deptId="+deptId1+"\",action=\"javascript:changeList('"+deptId2+":"+deptName2+"')\"));");
		}else{ 
			//deptName,deptId为根,deptName,deptId要从session获取
			resultStr.append("tree.add(new WebFXLoadTreeItem(\""+deptName+"\", \"organizationQueryAction?deptId="+deptId+"\",action=\"javascript:changeList('"+deptId+":"+deptName+"')\"));");
		}
		
		resultStr.append("document.write(tree)");
		resultStr.append("</script>");
		
		//System.out.println("345");
		return resultStr.toString();
	}

}
