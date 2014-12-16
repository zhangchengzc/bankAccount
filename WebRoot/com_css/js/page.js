function Page() {
  myShowRowCount = document.all("myShowRowCount");
  showRowCount = myShowRowCount?myShowRowCount:document.all("showRowCount");
  this.showRowCount = showRowCount?showRowCount.value:20;//当前页要显示的行数
  this.allRowCount=0; //总行数
  this.beginRow=0; //起始行号
  this.endRow=(new Number(this.beginRow) + new Number(this.showRowCount))-1; //结束行号

  this.allPageCount=0; //总页数
  this.currentPage=0; //当前页号
  this.methodName = null;
  this.tableName = null;
  this.onClickName = null;

  this.setAllRowCount = f_setAllRowCount;
  this.firstPage = f_firstPage;
  this.prePage = f_prePage;
  this.nextPage = f_nextPage;
  this.lastPage = f_lastPage;
  this.forwardPage = f_forwardPage;
  this.getPageMsgDom = f_createPageMsgDom;
  this.init = f_init;

  this.pageMsg = null;
  this.isReLoad = true;
  this.isMemmony = false;
}

/**
 * 初始化总行数，及其他分页信息
 **/
function f_setAllRowCount(doc) {
  if(!this.isReLoad) {
    return;
  }
  root = doc.selectSingleNode("root");

  allRowCount = root.selectSingleNode("allRowCount");

  this.allRowCount = allRowCount.text;
  if((new Number(this.allRowCount)) < (new Number(this.showRowCount))) { //总行数小于当前页显示的行数
    this.endRow = (new Number(this.allRowCount))-1;
    this.allPageCount=1; //总页数
    return;
  } else { //总行数大于当前页显示的行数
    if(this.allRowCount%this.showRowCount == 0) { //总行数除以当前页显示的行数正好为整数
      this.allPageCount = this.allRowCount/this.showRowCount;
    } else { //总行数除以当前页显示的行数不为整数
      tmp = parseInt(this.allRowCount/this.showRowCount);
      this.allPageCount = (new Number(tmp)) + (new Number(1));
    }
  }
}

/**
 * 转到第一页
 **/
function f_firstPage(methodName) {
  this.isReLoad = false;
  showRowCount = document.all("showRowCount");
  this.showRowCount = showRowCount?showRowCount.value:20;//当前页要显示的行数
  if((new Number(this.currentPage)) == 0) {
    return;
  }
  this.currentPage = 0;
  this.beginRow=0; //起始行号
  this.endRow=(new Number(this.beginRow) + new Number(this.showRowCount))-1; //结束行号

  this.pageMsg = f_createPageMsgDom(this.beginRow, this.endRow, this.currentPage, this.isReLoad);
  eval(methodName);
}

/**
 * 转到上一页
 **/
function f_prePage(methodName) {
  this.isReLoad = false;
  showRowCount = document.all("showRowCount");
  this.showRowCount = showRowCount?showRowCount.value:20;//当前页要显示的行数
  if(new Number(this.currentPage) <= 0) { //当是第一页时
    this.currentPage = 0;
    return;
  } else {
    this.currentPage = (new Number(this.currentPage)) - 1;
    this.beginRow = (new Number(this.beginRow)) - (new Number(this.showRowCount)); //起始行号
    this.endRow = (new Number(this.beginRow) + new Number(this.showRowCount))-1; //结束行号
  }

  this.pageMsg = f_createPageMsgDom(this.beginRow, this.endRow, this.currentPage, this.isReLoad);
  eval(methodName);
}

/**
 * 转到下一页
 **/
function f_nextPage(methodName) {
  this.isReLoad = false;
  showRowCount = document.all("showRowCount");
  this.showRowCount = showRowCount?showRowCount.value:20;//当前页要显示的行数
  if((new Number(this.currentPage)) >= ((new Number(this.allPageCount))-1)) { //最后一页
    this.currentPage = (new Number(this.allPageCount)) - 1;
    return;
  } else {
    this.currentPage = (new Number(this.currentPage)) + 1;
    this.beginRow = (new Number(this.beginRow)) + (new Number(this.showRowCount)); //起始行号
    this.endRow = (new Number(this.beginRow) + new Number(this.showRowCount))-1; //结束行号
  }
  
  this.pageMsg = f_createPageMsgDom(this.beginRow, this.endRow, this.currentPage, this.isReLoad);
  eval(methodName);
}

/**
 * 转到最后一页
 **/
function f_lastPage(methodName) {
  this.isReLoad = false;
  showRowCount = document.all("showRowCount");
  this.showRowCount = showRowCount?showRowCount.value:20;//当前页要显示的行数
 
  if((new Number(this.currentPage)) >= ((new Number(this.allPageCount)) - 1)) {
    this.currentPage = (new Number(this.allPageCount)) - 1;
    return;
  }
  this.currentPage = (new Number(this.allPageCount)) - 1; //当前页号

  this.endRow = (new Number(this.allRowCount)) - 1 ; //结束行号
  if(((new Number(this.allRowCount)) % (new Number(this.showRowCount))) != 0) {
  	this.beginRow = (this.endRow + 1) - ((new Number(this.allRowCount)) % (new Number(this.showRowCount)));
  } else {
  	this.beginRow = (this.endRow + 1) - (new Number(this.showRowCount));
  }
  
  this.pageMsg = f_createPageMsgDom(this.beginRow, this.endRow, this.currentPage, this.isReLoad);
  eval(methodName);
}

/**
 * 转到指定一页
 **/
function f_forwardPage(methodName) {
  var forwardPage = document.all("forwardPage")?document.all("forwardPage").value:1;
  if((forwardPage > this.allPageCount) || forwardPage < 1) {
    alert("输入页数不能超出第一页到最后一页的范围！");
    return;
  }
  this.isReLoad = false;
  showRowCount = document.all("showRowCount");
  this.showRowCount = showRowCount?showRowCount.value:20;//当前页要显示的行数
  this.currentPage = (new Number(forwardPage)) - 1; //当前页号
  this.beginRow = (new Number(this.showRowCount)) * this.currentPage;
  this.endRow = this.beginRow + (new Number(this.showRowCount)) - 1;

  this.pageMsg = f_createPageMsgDom(this.beginRow, this.endRow, this.currentPage, this.isReLoad);
  eval(methodName);
}

/**
 * 将从第哪行到哪行的数据保存为xml，做为后台查询条件
 **/
function f_createPageMsgDom(beginRow, endRow, currentPage, isReLoad) {
  var dom = new ActiveXObject("Msxml2.DOMDocument.3.0");
  dom.setProperty("SelectionLanguage", "XPath");
  root = dom.createElement("root");

  beginRowDoc = dom.createElement("beginRow");
  beginRowDoc.text = beginRow;

  endRowDoc = dom.createElement("endRow");
  endRowDoc.text = endRow;

  currentPageDoc = dom.createElement("currentPage");
  currentPageDoc.text = currentPage;

  isReLoadDoc = dom.createElement("flag");
  isReLoadDoc.text = isReLoad?"true":"false";

  root.appendChild(beginRowDoc);
  root.appendChild(endRowDoc);
  root.appendChild(currentPageDoc);
  root.appendChild(isReLoadDoc);

  dom.appendChild(root);
  return dom;
}

function f_init() {
  //showRowCount = document.all("showRowCount");
  myShowRowCount = document.all("myShowRowCount");
  showRowCount = myShowRowCount?myShowRowCount:document.all("showRowCount");
  this.showRowCount = showRowCount?showRowCount.value:20;//当前页要显示的行数
  this.allRowCount=0; //总行数
  this.beginRow=0; //起始行号
  this.endRow=(new Number(this.beginRow) + new Number(this.showRowCount))-1; //结束行号

  this.allPageCount=0; //总页数
  this.currentPage=0; //当前页号
}

function createPageHtml(pageDiv, methodName) {
  myShowRowCount = document.all("myShowRowCount");
  showRowCount = myShowRowCount?myShowRowCount:document.all("showRowCount");
  showRowCount = showRowCount?showRowCount.value:20;//当前页要显示的行数

  var tableHtml = "<table>\n";
  tableHtml = tableHtml + "  <tr>\n";
  var currentPage = (new Number(page.currentPage)) + 1;
  var allPageCount = page.allPageCount;
  tableHtml = tableHtml + "    <td valign='bottom'>第<span class='numStyle'>" + currentPage
    + "</span>页/共<span class='numStyle'>" + allPageCount + "</span>页</td>\n";
  tableHtml = tableHtml + "    <td>&nbsp;</td>\n";
  tableHtml = tableHtml + "    <td valign='bottom'>每页";
  tableHtml = tableHtml + "<input type='hidden' value='" + showRowCount + "' name='showRowCount' " +
    "style='width:40' readonly/><span class='numStyle'>" + showRowCount + "</span>条</td>\n";
  tableHtml = tableHtml + "    <td>&nbsp;</td>\n";
  var allRowCount = page.allRowCount;
  tableHtml = tableHtml + "    <td valign='bottom'>共<span class='numStyle'>" + allRowCount + "</span>条</td>\n";
  tableHtml = tableHtml + "    <td>&nbsp;</td>\n";
  tableHtml = tableHtml + "    <td valign='bottom'>" +
    "<a href=\"javascript:page.firstPage('" + methodName +"')\">" +
    "第一页</a></td>\n";
  tableHtml = tableHtml + "    <td>&nbsp;</td>\n";
  tableHtml = tableHtml + "    <td valign='bottom'>" +
    "<a href=\"javascript:page.prePage('" + methodName + "')\">" +
    "上一页</a></td>\n";
  tableHtml = tableHtml + "    <td>&nbsp;</td>\n";
  tableHtml = tableHtml + "    <td valign='bottom'>" +
    "<a href=\"javascript:page.nextPage('" + methodName + "')\">" +
    "下一页</a></td>\n";
  tableHtml = tableHtml + "    <td>&nbsp;</td>\n";
  tableHtml = tableHtml + "    <td valign='bottom'>" +
    "<a href=\"javascript:page.lastPage('" + methodName + "')\">最后一页</a></td>\n";
  tableHtml = tableHtml + "    <td>&nbsp;&nbsp;&nbsp;</td>\n";
  tableHtml = tableHtml + "    <td valign='bottom'>" +
    "转到<input type='text' name='forwardPage' style='width:40' value='"+currentPage+"' onbeforepaste='pasteOnlyInt()'  onkeyup='onlyInteger(this)'  />页</td>\n";
  tableHtml = tableHtml + "    <td>&nbsp;</td>\n";
  tableHtml = tableHtml + "    <td valign='bottom'>" +
    "<input type='button' class='button_2z' value='跳转' " +
    "onclick=\"page.forwardPage('" + methodName + "')\" /></td>\n";
  tableHtml = tableHtml + "  </tr>\n</table>\n";

  pageDiv.innerHTML = tableHtml;
}

function createPage(url, isInit, methodName, tableName, paramDom, onClickName) {
  page.isReLoad = isInit?isInit:false;

  var pageMsg;
  if(page.isReLoad) {
    page.init();
    pageMsg = page.getPageMsgDom(page.beginRow, page.endRow, page.currentPage, page.isReLoad);
  } else {
    pageMsg = page.pageMsg;
  }
  if(paramDom != null) {
    createParamDom(pageMsg, paramDom);
  }

  page.methodName = methodName;
  page.tableName = tableName;
  page.onClickName = onClickName;
  //doc = sendXml(url, pageMsg);
  var xmlRPC = new XmlRPC(url, "POST", false);
  xmlRPC.send(pageMsg);
  doc = xmlRPC.getXml();
  
  page.setAllRowCount(doc);

  createPageHtml(document.all("pageDiv"), methodName);
  root = doc.selectSingleNode("root");
  var resultList = root.selectNodes("resultList");

  var tableDiv = document.all("tableDiv");
  var tableObj = document.all(tableName);
  //refreshTable(tableDiv, tableObj, resultList, '', '', page.beginRow, 'oracle');
  var dataTable = new DataTable(tableDiv, tableObj, resultList, '', '', page.beginRow, 'oracle');
  dataTable.refreshTable();
  tigra_tables(tableName, 1, 0, '#FFFFFF', '#F5F5F5', '#E0EAF2', '#E4FAB5');
  eval(page.onClickName);
}

function createLoadPage(url, isInit, methodName, tableName, paramDom, onClickName, context) {
  page.isReLoad = isInit?isInit:false;
  var pageMsg;
  if(page.isReLoad) {
    page.init();
    pageMsg = page.getPageMsgDom(page.beginRow, page.endRow, page.currentPage, page.isReLoad);
  } else {
    pageMsg = page.pageMsg;
  }
  page.methodName = methodName;
  page.tableName = tableName;
  page.onClickName = onClickName;
  if(paramDom != null) {
    createParamDom(pageMsg, paramDom);
  }
  var dbPageObj = new DbPageObj();
  //var ajaxCreateTable = new AjaxCreateTable(url, window, dbPageObj);
  //ajaxCreateTable.ajaxSendXml(pageMsg);
  var xmlRPC = new XmlRPC(url, "POST", true, window, dbPageObj, context);
  xmlRPC.send(pageMsg);
}

function DbPageObj() {}

DbPageObj.prototype.updatePage = function() {
  if (objXMLHTTP.readyState == 4) {
    if (objXMLHTTP.status == 200) {
      var doc = f_createDom();
      doc.load(objXMLHTTP.responseXML);
      doc.setProperty("SelectionLanguage", "XPath");
      root = doc.selectSingleNode("root");
      exMsg = root.selectSingleNode("exMsg");
	  if(exMsg != null) {
  	  	  alert(exMsg.text);
	  } else {
	      page.setAllRowCount(doc);
	      createPageHtml(document.all("pageDiv"), page.methodName);
	      var resultList = root.selectNodes("resultList");
	
	      var tableDiv = document.all("tableDiv");
	      var tableObj = document.all(page.tableName);
	      //refreshTable(tableDiv, tableObj, resultList, '', '', page.beginRow, 'oracle');
		  var dataTable = new DataTable(tableDiv, tableObj, resultList, '', '', page.beginRow, 'oracle');
	      dataTable.refreshTable();
	      tigra_tables(page.tableName, 1, 0, '#FFFFFF', '#F5F5F5', '#E0EAF2', '#E4FAB5');
	
	      eval(page.onClickName);
	  }
      //解锁
      hideLoadingDiv(window);
    }
  }
}

function createLoadPageSectionDetailUser(url, isInit, methodName, tableName, paramDom, onClickName, context) {
  page.isReLoad = isInit?isInit:false;
  var pageMsg;
  if(page.isReLoad) {
    page.init();
    pageMsg = page.getPageMsgDom(page.beginRow, page.endRow, page.currentPage, page.isReLoad);
  } else {
    pageMsg = page.pageMsg;
  }
  page.methodName = methodName;
  page.tableName = tableName;
  page.onClickName = onClickName;
  if(paramDom != null) {
    createParamDom(pageMsg, paramDom);
  }
  var dbPageObj = new DbPageObjSectionDetailUser();
  var xmlRPC = new XmlRPC(url, "POST", true, window, dbPageObj, context);
  xmlRPC.send(pageMsg);
}

function DbPageObjSectionDetailUser() {}

DbPageObjSectionDetailUser.prototype.updatePage = function() {
  if (objXMLHTTP.readyState == 4) {
    if (objXMLHTTP.status == 200) {
      var doc = f_createDom();
      doc.load(objXMLHTTP.responseXML);
      doc.setProperty("SelectionLanguage", "XPath");
      root = doc.selectSingleNode("root");
      exMsg = root.selectSingleNode("exMsg");
	  if(exMsg != null) {
  	  	  alert(exMsg.text);
	  } else {
	      page.setAllRowCount(doc);
	      createPageHtml(document.all("pageDiv"), page.methodName);
	      var resultList = root.selectNodes("resultList");
	
	      var tableDiv = document.all("tableDivUser");
	      var tableObj = document.all(page.tableName);
		  var dataTable = new DataTable(tableDiv, tableObj, resultList, '', '', page.beginRow, 'oracle');
	      dataTable.refreshTable();
	      tigra_tables(page.tableName, 1, 0, '#FFFFFF', '#F5F5F5', '#E0EAF2', '#E4FAB5');
	
	      eval(page.onClickName);
	  }
      //解锁
      hideLoadingDiv(window);
    }
  }
}



function createLoadPageSectionDetailCust(url, isInit, methodName, tableName, paramDom, onClickName, context) {
  page.isReLoad = isInit?isInit:false;
  var pageMsg;
  if(page.isReLoad) {
    page.init();
    pageMsg = page.getPageMsgDom(page.beginRow, page.endRow, page.currentPage, page.isReLoad);
  } else {
    pageMsg = page.pageMsg;
  }
  page.methodName = methodName;
  page.tableName = tableName;
  page.onClickName = onClickName;
  if(paramDom != null) {
    createParamDom(pageMsg, paramDom);
  }
  var dbPageObj = new DbPageObjSectionDetailCust();
  var xmlRPC = new XmlRPC(url, "POST", true, window, dbPageObj, context);
  xmlRPC.send(pageMsg);
}

function DbPageObjSectionDetailCust() {}

DbPageObjSectionDetailCust.prototype.updatePage = function() {
  if (objXMLHTTP.readyState == 4) {
    if (objXMLHTTP.status == 200) {
      var doc = f_createDom();
      doc.load(objXMLHTTP.responseXML);
      doc.setProperty("SelectionLanguage", "XPath");
      root = doc.selectSingleNode("root");
      exMsg = root.selectSingleNode("exMsg");
	  if(exMsg != null) {
  	  	  alert(exMsg.text);
	  } else {
	      page.setAllRowCount(doc);
	      createPageHtml(document.all("pageDiv"), page.methodName);
	      var resultList = root.selectNodes("resultList");
	
	      var tableDiv = document.all("tableDivCust");
	      var tableObj = document.all(page.tableName);
		  var dataTable = new DataTable(tableDiv, tableObj, resultList, '', '', page.beginRow, 'oracle');
	      dataTable.refreshTable();
	      tigra_tables(page.tableName, 1, 0, '#FFFFFF', '#F5F5F5', '#E0EAF2', '#E4FAB5');
	
	      eval(page.onClickName);
	  }
      //解锁
      hideLoadingDiv(window);
    }
  }
}




function createParamDom(pageMsgDom, paramDom) {
  if(paramDom == null) {  // || paramDom == ''
    return;
  }
  var params = paramDom.selectSingleNode("params");
  var paramDoc = pageMsgDom.createElement("params");

  paramsDoc = params;
  var paramRoot = pageMsgDom.selectSingleNode("root");

  paramRoot.appendChild(paramsDoc);
}

function createMemmonyPage(url, isInit, tableName, methodName, onClickName, tableDiv, context) {
  if(isInit) {
	var xmlRPC = new XmlRPC(url, "POST", false);
    xmlRPC.send();
    doc = xmlRPC.getXml();
	root = doc.selectSingleNode("root");
	exMsg = root.selectSingleNode("exMsg");
	if(exMsg != null) {
  	  alert(exMsg.text);
  	  return;
	}
  }
  page.isReLoad = isInit?isInit:false;
  var pageMsg;
  if(page.isReLoad) {
    page.init();
    pageMsg = page.getPageMsgDom(page.beginRow, page.endRow, page.currentPage, page.isReLoad);
  } else {
    pageMsg = page.pageMsg;
  }
  
  page.methodName = methodName;
  page.tableName = tableName;
  page.onClickName = onClickName;
  page.setAllRowCount(doc);
  
  createPageHtml(document.all("pageDiv"), methodName);
  root = doc.selectSingleNode("root");
  var resultList = root.selectNodes("resultList");
  var results = [];
  var place = 0;
  if(page.endRow >= resultList.length) {
  	page.endRow = resultList.length - 1;
  }
  
  for(var i=page.beginRow; i<=page.endRow; i++) {
  	results[place] = resultList[i];
  	place++;
  }
  if(tableDiv == null || tableDiv == "") {
  	tableDiv = "tableDiv";
  }
  var tableDiv = document.all(tableDiv);
  var tableObj = document.all(tableName);

  //refreshTable(tableDiv, tableObj, results, '', '',page.beginRow);
  var dataTable = new DataTable(tableDiv, tableObj, results, "", "", page.beginRow);
  dataTable.refreshTable();
  tigra_tables(tableName, 1, 0, '#FFFFFF', '#F5F5F5', '#E0EAF2', '#E4FAB5');
  eval(page.onClickName);
}


var pageDom = new ActiveXObject("Msxml2.DOMDocument.3.0");
pageDom.setProperty("SelectionLanguage", "XPath");

/**
 * 带等待条的内存分页
 */
function createMemmonyLoadPage(url, isInit, tableName, methodName, onClickName, context, tableDiv) {
  page.isReLoad = isInit?isInit:false;
  var pageMsg;
  if(page.isReLoad) {
    page.init();
    pageMsg = page.getPageMsgDom(page.beginRow, page.endRow, page.currentPage, page.isReLoad);
  } else {
    pageMsg = page.pageMsg;
  }
  if(isInit) {
	var memmonyLoadPage = new MemmonyLoadPage();
	page.methodName = methodName;
	page.tableName = tableName;
	page.onClickName = onClickName;
	page.tableDiv = tableDiv;

	var xmlRPC = new XmlRPC(url, "POST", true, window, memmonyLoadPage, context);
	xmlRPC.send(pageMsg);
  } else {
  	page.methodName = methodName;
	page.tableName = tableName;
	page.onClickName = onClickName;
	page.setAllRowCount(pageDom);
	
	createPageHtml(document.all("pageDiv"), methodName);
	root = pageDom.selectSingleNode("root");
	var resultList = root.selectNodes("resultList");
	var results = [];
	var place = 0;
	if(page.endRow >= resultList.length) {
		page.endRow = resultList.length - 1;
	}
	for(var i=page.beginRow; i<=page.endRow; i++) {
      results[place] = resultList[i];
	  place++;
	}
	if(tableDiv == null || tableDiv == "") {
	  tableDiv = "tableDiv";
	}
	var tableDiv = document.all(tableDiv);
	var tableObj = document.all(tableName);
	//refreshTable(tableDiv, tableObj, results, "", "", page.beginRow);
	var dataTable = new DataTable(tableDiv, tableObj, results, "", "", page.beginRow);
	dataTable.refreshTable();
	tigra_tables(tableName, 1, 0, '#FFFFFF', '#F5F5F5', '#E0EAF2', '#E4FAB5');
	eval(page.onClickName);
  }
}

function MemmonyLoadPage() {}

MemmonyLoadPage.prototype.updatePage = function() {
  if (objXMLHTTP.readyState == 4) {
    if (objXMLHTTP.status == 200) {
      pageDom.load(objXMLHTTP.responseXML);
      pageDom.setProperty("SelectionLanguage", "XPath");
      root = pageDom.selectSingleNode("root");
	  exMsg = root.selectSingleNode("exMsg");
	  if(exMsg != null) {
  	  	  alert(exMsg.text);
	  } else {
		  page.setAllRowCount(pageDom);
		  createPageHtml(document.all("pageDiv"), page.methodName);
		  
		  var resultList = root.selectNodes("resultList");
		  var results = [];
		  var place = 0;
		  if(page.endRow >= resultList.length) {
		  	page.endRow = resultList.length - 1;
		  }
		  
		  for(var i=page.beginRow; i<=page.endRow; i++) {
		  	results[place] = resultList[i];
		  	place++;
		  }
		  if(page.tableDiv == null || page.tableDiv == "") {
		  	page.tableDiv = "tableDiv";
		  }
		  var tableDiv = document.all(page.tableDiv);
		  var tableObj = document.all(page.tableName);
		
		  var dataTable = new DataTable(tableDiv, tableObj, results, "", "", page.beginRow);
		  dataTable.refreshTable();
		  tigra_tables(page.tableName, 1, 0, '#FFFFFF', '#F5F5F5', '#E0EAF2', '#E4FAB5');
		  eval(page.onClickName);
	  }
      //解锁
      hideLoadingDiv(window);
    }
  }
}
var page = new Page();
