function f_createDom() {
	var dom = new ActiveXObject("Msxml2.DOMDocument.3.0");
	dom.setProperty("SelectionLanguage", "XPath");
	return dom;
}

var objXMLHTTP;

function XmlRPC(p_connection, p_method, asy, oWindow, obj, context) {
	this.baseUrl = "";
	this.connection = p_connection;
	this.method = p_method?p_method:"POST";
	this.asy = asy?asy:false;
	this.sendXml = f_sendXml;
	this.sendText = f_sendText;
	this.send = f_send;
    this.getXml = f_getXml;
	this.getText = f_getTextR;
	this.abort = f_abort;
	this.doc = null;
	this.oWindow = oWindow?oWindow:window;
  	this.obj = obj;
  	this.context = context;
}

function f_sendXml(dom) {
  	this.send(dom);
}

function f_sendText(text) {
  	this.send(text);
}

function f_send(arg) {
	var content = arg?arg:'';
	objXMLHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	objXMLHTTP.Open(this.method, this.connection, this.asy);
	objXMLHTTP.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	objXMLHTTP.setRequestHeader("charset","GBK");
	try {
		if(this.asy) {
			//锁定页面
	  		showLoadingDiv(this.oWindow, this.context);
	  		objXMLHTTP.onreadystatechange = this.obj.updatePage;
		}
		objXMLHTTP.send(content);
	} catch(e) {
		parent.location.href="/CAWeb/login.jsp";
	}
}

function f_getXml() {
	if(objXMLHTTP.status == 0) {
		var dom = f_createDom();
	    dom.load(objXMLHTTP.responseXML);
	    dom.setProperty("SelectionLanguage", "XPath");
	    var root = dom.createElement("root");
	    var overtimeFlag = dom.createElement("overtimeFlag");
	    overtimeFlag.text = "1";
	    root.appendChild(overtimeFlag);
	    dom.appendChild(root);
	    return dom;
	}
	if (objXMLHTTP.status == 200) {
	    var dom = f_createDom();
	    dom.load(objXMLHTTP.responseXML);
	    dom.setProperty("SelectionLanguage", "XPath");
	    return dom;
	} else {
		return "error!";
	}
}

function f_abort() {
	objXMLHTTP.abort();
}

function f_getTextR() {
	return objXMLHTTP.responseText;
}

