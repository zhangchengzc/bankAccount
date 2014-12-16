var menuNames = [];
var methodNames = [];
menuNames[0] = "增加权限";
menuNames[1] = "修改权限";
menuNames[2] = "删除权限";
methodNames[0] = "addPopedom()";
methodNames[1] = "updatePopedom()";
methodNames[2] = "delPopedom()";

var menuNames2 = [];
var methodNames2 = [];
menuNames2[0] = "修改权限";
menuNames2[1] = "删除权限";
methodNames2[0] = "updatePopedom()";
methodNames2[1] = "delPopedom()";

var menuNames3 = [];
var methodNames3 = [];
menuNames3[0] = "增加权限";
methodNames3[0] = "addPopedom()";

createRightMenu("popedomMenu", menuNames, methodNames);
createRightMenu("popedomMenu2", menuNames2, methodNames2);
createRightMenu("noneMenu", [], []);
createRightMenu("popedomMenu3", menuNames3, methodNames3);
var tree;

function createTree() {
	tree = new WebFXTree('权限', 'javascript:clearText()', '', 'popedomMenu3');
    tree.setBehavior('classic');
    url = "javascript:showPopedom()";
    a1 = new WebFXTreeItem('首页', url, '1000', '1', '', 'noneMenu');
	tree.add(a1);
	actionurl = "0.do";
	dom = sendXml(actionurl);
	root = dom.selectSingleNode("root");

	if(root == null) {
		document.all("treeDiv").innerHTML = tree;
    	tree.expandAll();
		return;
	}
	var exMsg = root.selectSingleNode("exMsg");
  	if(exMsg != null) {
    	alert(exMsg.text);
    	return;
  	}
	firsts = root.selectNodes("first");
	
	//取得一级节点
	for(i=0; i<firsts.length; i++) {
		name = firsts[i].selectSingleNode("name").text;
		title = firsts[i].selectSingleNode("name").text;
		id = firsts[i].selectSingleNode("id").text;
		
		firstNode = new WebFXTreeItem(title, url, id, '1', '', 'popedomMenu');
		tree.add(firstNode);

		seconds = firsts[i].selectNodes("second");
		for(ii=0; ii<seconds.length; ii++) {
			name = seconds[ii].selectSingleNode("name").text;
			id = seconds[ii].selectSingleNode("id").text;
			title = seconds[ii].selectSingleNode("name").text;
			secondNode = new WebFXTreeItem(title, url, id, '2', '', 'popedomMenu');
			firstNode.add(secondNode);
			
			thirds = seconds[ii].selectNodes("third");
			for(iii=0; iii<thirds.length; iii++) {
				name = thirds[iii].selectSingleNode("name").text;
				id = thirds[iii].selectSingleNode("id").text;
				title = thirds[iii].selectSingleNode("name").text;
				thirdNode = new WebFXTreeItem(title, url, id, '3', '', 'popedomMenu2');
				secondNode.add(thirdNode);
			}
		}
	}

	document.all("treeDiv").innerHTML = tree;
    tree.expandAll();
}

function clearText() {
	setPopedom(true, "add");
}

function showPopedom() {
	getPopedom();
	setPopedom(true);
	noneOperButton();
}

function addPopedom() {
	setPopedom(false, "add");
	createOperButton("增加", "add()");
	
	if(tree.getSelected()) {
		node = tree.getSelected();
		if(node.nodeType == undefined) {
			node.nodeType = "0";
		}
		newNodeType = new Number(node.nodeType) + 1;
		elementGradeValue = "";
		if(newNodeType == "1") {
			elementGradeValue = "一级菜单";
		} if(newNodeType == "2") {
			elementGradeValue = "二级菜单";
		} if(newNodeType == "3") {
			elementGradeValue = "三级菜单";
		}
		document.all("elementGrade").disabled = true;
		document.all("elementGrade").value = elementGradeValue;
		
		if(node.nodeId == undefined) {
			node.nodeId = "";
		}
		document.all("elementId").value = node.nodeId;
	}
}
function updatePopedom() {
	getPopedom();
	setPopedom(false);
	createOperButton("修改", "update()");
}

function delPopedom() {
	if(tree.getSelected()) {
		node = tree.getSelected();
		if(node.nodeId == undefined) {
			return;
		}
		//删除数据库里的数据
		url = "delPopedomAction.do?elementId=" + node.nodeId;
		//删除数据库里的数据
		if(!confirm("是否确定删除？")) {
			return;
		}
		
		doc = sendXml(url);
		
		root = doc.selectSingleNode("root");
		if(root != null) {
			var exMsg = root.selectSingleNode("exMsg");
	  		if(exMsg != null) {
	    		alert(exMsg.text);
	    		return;
	  		}
  		}
		tree.getSelected().remove();
		setPopedom(true);
		noneOperButton();
		alert("删除成功！");
	}
}

function getPopedom() {
	if(tree.getSelected()) {
		node = tree.getSelected();
		//到数据库中
		if(node.nodeId == "1000") {
			document.all("elementId").value = node.nodeId;
			document.all("elementName").value = "首页";
			document.all("elementTitle").value = "首页";
			document.all("elementUrl").value = "url";
			document.all("elementGrade").value = "一级菜单";
			document.all("elementOrder").value = "0";
			document.all("elementDesc").value = "首页，该节点不再数据库中保存！";
			return;
		}
		actionurl = "showPopedomAction.do?elementId=" + node.nodeId;
		dom = sendXml(actionurl);
		root = dom.selectSingleNode("root");
		var exMsg = root.selectSingleNode("exMsg");
  		if(exMsg != null) {
    		alert(exMsg.text);
    		return;
  		}
		result = root.selectSingleNode("result");
		document.all("elementId").value = node.nodeId;
		document.all("elementName").value = result.selectSingleNode("elementName").text;
		document.all("elementTitle").value = result.selectSingleNode("elementTitle").text;
		document.all("elementUrl").value = result.selectSingleNode("elementUrl").text;
		if(result.selectSingleNode("elementGrade").text == "1") {
			elementGradeValue = "一级菜单";
		} if(result.selectSingleNode("elementGrade").text == "2") {
			elementGradeValue = "二级菜单";
		} if(result.selectSingleNode("elementGrade").text == "3") {
			elementGradeValue = "三级菜单";
		} 
		document.all("elementGrade").value = elementGradeValue;
		document.all("elementOrder").value = result.selectSingleNode("elementOrder").text;
		document.all("elementDesc").value = result.selectSingleNode("elementDesc").text;
		document.all("elementType").value = result.selectSingleNode("elementType").text;
	}
}

function setPopedom(flag, operFlag) {
	document.all("elementName").disabled = flag;
	document.all("elementTitle").disabled = flag;
	document.all("elementUrl").disabled = flag;
	document.all("elementOrder").disabled = flag;
	document.all("elementDesc").disabled = flag;
	document.all("elementGrade").disabled = flag;
	document.all("elementType").disabled = flag;
	
	if(operFlag == "add") {
		document.all("elementId").value = "";
		document.all("elementName").value = "";
		document.all("elementTitle").value = "";
		document.all("elementUrl").value = "";
		document.all("elementOrder").value = "";
		document.all("elementDesc").value = "";
		document.all("elementGrade").value = "";
		document.all("elementType").value = "0";
	}
}

function createOperButton(value, clickMethod) {
	document.all("buttonTd").innerHTML = "<input class='button_2z' type='button' value='" + value 
		+ "' onclick='" + clickMethod + "'>&nbsp;&nbsp;<input class='button_2z' type='reset' value='取消'>";
}

function noneOperButton() {
	document.all("buttonTd").innerHTML = "&nbsp;";
}

function add() {
	if(tree.getSelected()) {
		node = tree.getSelected();
		//增加到数据库
		nameValue = document.all("elementName").value;
		if(nameValue == "") {
			alert("请输入菜单名称！");
			return;
		}
		titleValue = document.all("elementTitle").value;
		if(titleValue == "") {
			alert("请输入菜单标题！");
			return;
		}
		urlValue = document.all("elementUrl").value;
		orderValue = document.all("elementOrder").value;
		if(orderValue == "" || isNaN(orderValue)) {
			alert("菜单顺序请输入数字！");
			return;
		}
		descValue = document.all("elementDesc").value;
		newNodeType = new Number(node.nodeType) + 1;
		gradeValue = newNodeType;
		//增加到数据库
		pId = node.nodeId;
		if(node.nodeId == undefined) {
			pId = "";
		}
		url = createUrl("addPopedomAction.do") + "&grade=" + gradeValue + "&pId=" + node.nodeId;

		if(!confirm("是否确定增加？")) {
			return;
		}
		
		doc = sendXml(url);
		root = doc.selectSingleNode("root");
		var exMsg = root.selectSingleNode("exMsg");
  		if(exMsg != null) {
    		alert(exMsg.text);
    		return;
  		}
  	
		newNode = new WebFXTreeItem(document.all("elementName").value, "javascript:showPopedom()");
		newNode.setNodeId(root.selectSingleNode("id").text);
		if(node.nodeType == undefined) {
			node.nodeType = "0";
		}
		
		newNode.setNodeType(newNodeType);
		if(node.nodeType == "2") {
			menu = "popedomMenu2";
		} else {
			menu = "popedomMenu";
		}
		newNode.setRightMenu(menu);
		tree.getSelected().add(newNode);
		
		node.expand();
		setPopedom(true);
		noneOperButton();
		alert("增加成功！");
	}
}

function update() {
	if(tree.getSelected()) {
		node = tree.getSelected();
		pNode = node.parentNode;
		pId = pNode.nodeId;
		if(pId == undefined) {
			pId = "";
		}
		//将数据保存到数据库
		url = createUrl("updatePopedomAction.do") + "&grade=" + node.nodeType + "&pId=" + pId;
		//将数据保存到数据库
		if(!confirm("是否确定修改？")) {
			return;
		}
		
		doc = sendXml(url);
		root = doc.selectSingleNode("root");
		if(root != null) {
			var exMsg = root.selectSingleNode("exMsg");
	  		if(exMsg != null) {
	    		alert(exMsg.text);
	    		return;
	  		}
  		}
		document.all(node.id + "-anchor").innerText=document.all("elementName").value; //改节点名字
		setPopedom(true);
		noneOperButton();
		alert("修改成功!");
	}
}

function createUrl(action) {
	idValue = document.all("elementId").value;
	nameValue = document.all("elementName").value;
	titleValue = document.all("elementTitle").value;
	urlValue = document.all("elementUrl").value;
	orderValue = document.all("elementOrder").value;
	descValue = document.all("elementDesc").value;
	typeValue = document.all("elementType").value;
	
	url = action + "?id=" + idValue + "&name=" + nameValue + 
		"&title=" + titleValue + "&url=" + urlValue + 
		"&order=" + orderValue + "&desc=" + descValue +
		"&type=" + typeValue;
		
	return url;
}

function hideOrShow(display) {
	firstId = [];
	firstIndex = 0;
	
	secondId = [];
	secondIndex = 0;
	
	thirdId = [];
	thirdIndex = 0;
	
	node = tree.getSelected();
	alert(node.parentNode.nodeId);
	
	for(j=0; j<node.childNodes.length; j++) {
		firstNode = node.childNodes[j];
		firstId[firstIndex++] = firstNode.id;
    	if(firstNode.childNodes.length > 0) {
    		for(jj=0; jj<firstNode.childNodes.length; jj++) {
    			secondNode = firstNode.childNodes[jj];
    			secondId[secondIndex++] = secondNode.id;
    			if(secondNode.childNodes.length > 0) {
    				for(jjj=0; jjj<secondNode.childNodes.length; jjj++) {
    					thirdNode = secondNode.childNodes[jjj];
    					thirdId[thirdIndex++] = thirdNode.id;
    				}
    			}
    		}
    	}
    }
    
    for(i=0; i<thirdId.length; i++) {
    	document.all(thirdId[i]).style.display = display;
    }
    for(i=0; i<secondId.length; i++) {
    	document.all(secondId[i]).style.display = display;
    }
    for(i=0; i<firstId.length; i++) {
    	document.all(firstId[i]).style.display = display;
    }
	if(node.nodeType != undefined) {
		document.all(node.id).style.display = display;
	}
}