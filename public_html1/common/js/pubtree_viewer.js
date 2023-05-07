
var Pubtree_pr = "";
var ServDomain = "http://www.pubtree.net";
var Pubtree_url = ServDomain + "/pubtree/";
var Pubtree_software = "http://download.namo.com/share/namoreader/NamoReader_ESD.exe";
var Pubtree_websocket = "http://serv-dev.pubtree.net/resources/pubtree/js/web_socket.js";
var Pubtree_swfobject = "http://serv-dev.pubtree.net/resources/pubtree/js/swfobject.js";
var Pubtree_swfobjectSWF = "http://serv-dev.pubtree.net/resources/pubtree/js/WebSocketMain.swf";
var Pubtree_viewerPlay = false;

var PubtreePlatform={cf:{params:"epub?key=",epubDocKey:"",key:"",exeCheck:!1},wsCheck:function(e){wsc=new WebSocket("ws://"+e),setInterval(function(){console.log(wsc)},1e3)},load:function(e){var t=this,n=/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream;if(t.cf.exeCheck=!1,""!=e)if(null==document.getElementById("pubtreeViewerKeyChecker")&&1!=Pubtree_viewerPlay){var o=document.createElement("script");if(o.type="text/javascript",o.src=Pubtree_url+"pr_checkKey?key="+e,o.id="pubtreeViewerKeyChecker",document.getElementsByTagName("head")[0].appendChild(o),null==document.getElementById("pubtreeSWFObject")){var a=document.createElement("script");a.type="text/javascript",a.src=Pubtree_swfobject,a.id="pubtreeSWFObject",document.getElementsByTagName("head")[0].appendChild(a),WEB_SOCKET_SWF_LOCATION=Pubtree_swfobjectSWF}if(null!=document.getElementById("pubtreeLoading")){var d=document.getElementById("pubtreeLoading");document.getElementsByTagName("body")[0].removeChild(d)}if(!navigator.userAgent.toLowerCase().match(/android/i)&&!n&&null==document.getElementById("pubtreeLoading")){var r=document.createElement("div");r.id="pubtreeLoading",r.style.position="fixed",r.style.zIndex="9999",r.style.top="50%",r.style.left="50%",r.style.backgroundColor="white",r.style.backgroundImage="url('"+ServDomain+"/resources/pubtree/images/loading_viewer.gif')",r.style.backgroundRepeat="no-repeat",r.style.backgroundPosition="7px 10px",r.style.border="3px double #ddd",r.style.padding="0px 10px 0px 40px",r.style.width="260px",r.style.height="50px",r.style.lineHeight="50px",r.style.margin="-25px 0px 0px -150px",r.style.border="1px solid #ccc",r.style.fontSize="11px",r.style.cursor="default",r.innerHTML="뷰어를 실행하고 있습니다. 잠시만 기다려주세요.",document.getElementsByTagName("body")[0].appendChild(r)}if(null==document.getElementById("pubtreeWebSocket")){var l=document.createElement("script");l.type="text/javascript",l.src=Pubtree_websocket,l.id="pubtreeWebSocket",document.getElementsByTagName("head")[0].appendChild(l)}setTimeout(function(){var e=document.getElementById("pubtreeViewerKeyChecker");document.getElementsByTagName("head")[0].removeChild(e)},2e3),setTimeout(function(){if(null!=document.getElementById("pubtreeLoading")){var e=document.getElementById("pubtreeLoading");document.getElementsByTagName("body")[0].removeChild(e)}},5e3)}else if(1!=Pubtree_viewerPlay){if(alert("뷰어를 실행 중입니다. 잠시만 기다려주세요."),null!=document.getElementById("pubtreeLoading")){var d=document.getElementById("pubtreeLoading");document.getElementsByTagName("body")[0].removeChild(d)}}else{var d=document.getElementById("pubtreeViewerKeyChecker");document.getElementsByTagName("head")[0].removeChild(d),t.load(e)}else if(alert("잘못된 요청 입니다. #000"),null!=document.getElementById("pubtreeLoading")){var d=document.getElementById("pubtreeLoading");document.getElementsByTagName("body")[0].removeChild(d)}},setData:function(e){var t=this;if("SUCCESS"==e.result)t.cf.key=e.key,t.cf.epubDocKey=e.epubDocKey,t.exe();else{if(alert("잘못된 요청 입니다. #001"),null!=document.getElementById("pubtreeLoading")){var n=document.getElementById("pubtreeLoading");document.getElementsByTagName("body")[0].removeChild(n)}console.log(e)}},exeCheck:function(){var e=this;e.cf.exeCheck=!0},ios_open_app:function(){var e=this;setTimeout(function(){e.open_appstore()},300)},open_appstore:function(){null!=window.top.document.getElementById("namoReaderCaller2_frame")&&window.top.document.getElementById("namoReaderCaller2_frame").remove()},exe:function(){var e=this,t="epub?epubDocKey="+e.cf.epubDocKey+"&key="+this.cf.key+"&downURL="+Pubtree_url+"vdownload/"+e.cf.key,n=("?epubDocKey="+e.cf.epubDocKey+"&key="+this.cf.key+"&downURL="+Pubtree_url+"vdownload/"+e.cf.key,/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream);if(navigator.userAgent.toLowerCase().match(/android/i)){var o="intent://NamoBooks?epubDocKey="+e.cf.epubDocKey+"&key="+this.cf.key+"&downURL="+Pubtree_url+"vdownload/"+e.cf.key+"#Intent;scheme=epub;action=android.intent.action.VIEW;category=android.intent.category.BROWSABLE;package=com.namo.pubtree.books.viewer;end";null!=window.top.document.getElementById("namoReaderCaller")&&window.top.document.getElementsByTagName("head")[0].removeChild(window.top.document.getElementById("namoReaderCaller"));var a=document.createElement("a");a.setAttribute("id","namoReaderCaller"),a.setAttribute("href",o),a.setAttribute("target","_top"),window.top.document.getElementsByTagName("head")[0].appendChild(a);var d=document.createEvent("HTMLEvents");d.initEvent("click",!0,!0),window.top.document.getElementById("namoReaderCaller").dispatchEvent(d)}else if(n){var r=new Date;r.getTime();null!=window.top.document.getElementById("namoReaderCaller")&&window.top.document.getElementById("namoReaderCaller").remove(),null!=window.top.document.getElementById("namoReaderCaller2")&&window.top.document.getElementById("namoReaderCaller2").remove();var l="NamoReader://"+t,a=document.createElement("a");a.setAttribute("id","namoReaderCaller"),a.setAttribute("href",l),a.setAttribute("target","_top"),window.top.document.getElementsByTagName("head")[0].appendChild(a);var d=document.createEvent("HTMLEvents");d.initEvent("click",!0,!0),window.top.document.getElementById("namoReaderCaller").dispatchEvent(d),setTimeout(function(){var e=document.createElement("a");e.setAttribute("id","namoReaderCaller2"),e.setAttribute("href","https://itunes.apple.com/kr/app/namobooks/id1142893998?mt=8"),e.setAttribute("target","_top"),window.top.document.getElementsByTagName("head")[0].appendChild(e);var t=document.createEvent("HTMLEvents");t.initEvent("click",!0,!0),window.top.document.getElementById("namoReaderCaller2").dispatchEvent(t)},1e3)}else{var i=Pubtree_url+"pr_viewerInit?epubDocKey="+e.cf.epubDocKey+"&key="+e.cf.key+"&downURL="+Pubtree_url+"vdownload/"+e.cf.key,m=document.createElement("script");m.type="text/javascript",m.src=i,m.id="pubtreeViewerInit",document.getElementsByTagName("head")[0].appendChild(m),setTimeout(function(){var e=document.getElementById("pubtreeViewerInit");document.getElementsByTagName("head")[0].removeChild(e)},2e3)}},pc_install_error:function(){alert("뷰어 프로그램이 설치되어 있지 않습니다."),self.location=Pubtree_software}};
var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(input){var output="";var chr1,chr2,chr3,enc1,enc2,enc3,enc4;var i=0;input=Base64._utf8_encode(input);while(i<input.length){chr1=input.charCodeAt(i++);chr2=input.charCodeAt(i++);chr3=input.charCodeAt(i++);enc1=chr1>>2;enc2=((chr1&3)<<4)|(chr2>>4);enc3=((chr2&15)<<2)|(chr3>>6);enc4=chr3&63;if(isNaN(chr2)){enc3=enc4=64}else if(isNaN(chr3)){enc4=64}output=output+this._keyStr.charAt(enc1)+this._keyStr.charAt(enc2)+this._keyStr.charAt(enc3)+this._keyStr.charAt(enc4)}return output},decode:function(input){var output="";var chr1,chr2,chr3;var enc1,enc2,enc3,enc4;var i=0;input=input.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(i<input.length){enc1=this._keyStr.indexOf(input.charAt(i++));enc2=this._keyStr.indexOf(input.charAt(i++));enc3=this._keyStr.indexOf(input.charAt(i++));enc4=this._keyStr.indexOf(input.charAt(i++));chr1=(enc1<<2)|(enc2>>4);chr2=((enc2&15)<<4)|(enc3>>2);chr3=((enc3&3)<<6)|enc4;output=output+String.fromCharCode(chr1);if(enc3!=64){output=output+String.fromCharCode(chr2)}if(enc4!=64){output=output+String.fromCharCode(chr3)}}output=Base64._utf8_decode(output);return output},_utf8_encode:function(string){string=string.replace(/\r\n/g,"\n");var utftext="";for(var n=0;n<string.length;n++){var c=string.charCodeAt(n);if(c<128){utftext+=String.fromCharCode(c)}else if((c>127)&&(c<2048)){utftext+=String.fromCharCode((c>>6)|192);utftext+=String.fromCharCode((c&63)|128)}else{utftext+=String.fromCharCode((c>>12)|224);utftext+=String.fromCharCode(((c>>6)&63)|128);utftext+=String.fromCharCode((c&63)|128)}}return utftext},_utf8_decode:function(utftext){var string="";var i=0;var c=c1=c2=0;while(i<utftext.length){c=utftext.charCodeAt(i);if(c<128){string+=String.fromCharCode(c);i++}else if((c>191)&&(c<224)){c2=utftext.charCodeAt(i+1);string+=String.fromCharCode(((c&31)<<6)|(c2&63));i+=2}else{c2=utftext.charCodeAt(i+1);c3=utftext.charCodeAt(i+2);string+=String.fromCharCode(((c&15)<<12)|((c2&63)<<6)|(c3&63));i+=3}}return string}}
Element.prototype.remove=function(){this.parentElement.removeChild(this)};NodeList.prototype.remove=HTMLCollection.prototype.remove=function(){for(var i=this.length-1;i>=0;i--){if(this[i]&&this[i].parentElement){this[i].parentElement.removeChild(this[i])}}}