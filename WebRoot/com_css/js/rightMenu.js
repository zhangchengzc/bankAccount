/**

*根据传入的id显示右键菜单

*/

function showMenu(id, menuDivId) {
	if(menuDivId==null || menuDivId=="") {
		return;
	}
	document.all("" + menuDivId + "Form").id.value = id;
  	if("" != id) {
		var itemMenu = document.all("" + menuDivId + "");
    	popMenu(itemMenu, 100, document.all("" + menuDivId + "aaa").value);
  	}
  
  	event.returnValue=false;
  	event.cancelBubble=true;
  	return false;
}

/**
 * 显示弹出菜单
 * menuDiv:右键菜单的内容
 * width:行显示的宽度
 * rowControlString:行控制字符串，0表示不显示，1表示显示，如“101”，则表示第1、3行显示，第2行不显示
*/
function popMenu(menuDiv, width, rowControlString) {
	//创建弹出菜单
	var pop=window.createPopup();
	
	//设置弹出菜单的内容
	pop.document.body.innerHTML=menuDiv.innerHTML;
	var rowObjs=pop.document.body.all[0].rows;
	
	//获得弹出菜单的行数
	var rowCount=rowObjs.length;
	//循环设置每行的属性
	for(var i=0;i<rowObjs.length;i++) {
		//如果设置该行不显示，则行数减一
		var hide=rowControlString.charAt(i)!='1';
		
		if(hide){
			rowCount--;
		}
		
		//设置是否显示该行
		rowObjs[i].style.display=(hide)?"none":"";
		
		//设置鼠标滑入该行时的效果
		rowObjs[i].cells[0].onmouseover=function() {
		    this.style.background="#818181";
		    this.style.color="white";
		}
		
		//设置鼠标滑出该行时的效果
		rowObjs[i].cells[0].onmouseout=function(){
		    this.style.background="#cccccc";
		    this.style.color="black";
		}
	}
	
	//屏蔽菜单的菜单
	pop.document.oncontextmenu=function() {
		return false;
	}
	
	//选择右键菜单的一项后，菜单隐藏
	pop.document.onclick=function() {
		pop.hide();
	}
	
	//显示菜单
	pop.show(event.clientX-1,event.clientY,width,rowCount*25,document.body);
	return true;
}


function createDefaulRightMenu(menuDivId) {
	var menuStr = "<form name=\"" + menuDivId + "Form\"></form>";
	menuStr += "<div id=\"" + menuDivId + "\" style=\"display:none\">";
	menuStr += "<table border=\"1\" width=\"100%\" height=\"100%\" bgcolor=\"#cccccc\" style=\"border:thin\" cellspacing=\"0\">";
  	menuStr += "<tr>";
  	menuStr += "<td style=\"cursor:default;border:outset 1;\" align=\"center\" onclick=\"parent.create()\">";
	menuStr += "新增";
	menuStr += "</td>";
  	menuStr += "</tr>";
	menuStr += "<tr>";
	menuStr += "<td style=\"cursor:default;border:outset 1;\" align=\"center\" onclick=\"parent.update();\">";
	menuStr += "修改";
	menuStr += "</td>";
	menuStr += "</tr>";
	menuStr += "<tr>";
	menuStr += "<td style=\"cursor:default;border:outset 1;\" align=\"center\" onclick=\"parent.del()\">";
	menuStr += "删除";
	menuStr += "</td>";
	menuStr += "</tr>";
	menuStr += "</table>";
	menuStr += "<input type='hidden' id='" + menuDivId + "aaa' name='" + menuDivId + "aaa' value='111'>"
	menuStr += "</div>";
	document.write(menuStr);
}

function createRightMenu(menuDivId, menuNames, methodNames) {
	if(menuNames == null) {
		createDefaulRightMenu(menuDivId);
		return;	
	}
	menuStr = "<form name=\"" + menuDivId + "Form\"></form>";
	menuStr += "<div id=\"" + menuDivId + "\" style=\"display:none\">";
	menuStr += "<table border=\"1\" width=\"100%\" height=\"100%\" bgcolor=\"#cccccc\" style=\"border:thin\" cellspacing=\"0\">";
	rowControlStr = "";
	
	if(menuNames.length > 0 && methodNames.length > 0) {
		if(menuNames.length != methodNames.length) {
			alert("menuNames.length != methodNames.length");
			return;
		}
		
		for(i=0; i<menuNames.length; i++) {
			menuStr += "<tr>";
		  	menuStr += "<td style=\"cursor:default;border:outset 1;\" align=\"center\" onclick=\"parent.";
		  	menuStr += methodNames[i] + "\">";
			menuStr += menuNames[i];
			menuStr += "</td>";
		  	menuStr += "</tr>";
		  	rowControlStr += "1";
		}
	} else {
		/*menuStr += "<tr>";
	  	menuStr += "<td style=\"cursor:default;border:outset 1;\" align=\"center\" onclick=\"parent.";
	  	menuStr += methodNames + "\">";
		menuStr += menuNames;
		menuStr += "</td>";
	  	menuStr += "</tr>";*/
	}
	menuStr += "</table>";
	menuStr += "<input type='hidden' id='" + menuDivId + "aaa' name='" + menuDivId + "aaa' value='" + rowControlStr+"'>"
	menuStr += "</div>";
	document.write(menuStr);
}