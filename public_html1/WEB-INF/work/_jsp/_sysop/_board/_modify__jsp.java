/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._sysop._board;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _modify__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    javax.servlet.ServletContext application = _jsp_application;
    com.caucho.jsp.PageContextImpl pageContext = _jsp_application.getJspApplicationContext().allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);
    javax.servlet.jsp.PageContext _jsp_parentContext = pageContext;
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    response.setContentType("text/html; charset=utf-8");
    request.setCharacterEncoding("UTF-8");
    try {
      

String docRoot = Config.getDocRoot();
String jndi = Config.getJndi();
String tplRoot = Config.getDocRoot() + "/sysop/html";

Malgn m = new Malgn(request, response, out);

Form f = new Form("form1");
try { f.setRequest(request); } catch (Exception ex) { out.print("\uc81c\ud55c \uc6a9\ub7c9 \ucd08\uacfc - " + ex.getMessage()); return; }

Page p = new Page(tplRoot);
p.setRequest(request);
p.setPageContext(pageContext);
p.setWriter(out);
p.setBaseRoot("/home/lms/public_html/html");

SiteDao Site = new SiteDao();
DataSet siteinfo = Site.getSiteInfo(request.getServerName(), "sysop");
SiteConfigDao SiteConfig = new SiteConfigDao(siteinfo.i("id"));
if(1 != siteinfo.i("sysop_status") || "".equals(siteinfo.s("doc_root"))) { m.jsReplace("/main/guide.jsp", "top"); return; }
//Hashtable<String, String> siteconfig = SiteConfig.getSiteConfig(siteinfo.s("id"));

String siteDomain = request.getScheme() + "://" + request.getServerName();
int port = request.getServerPort();
if(port != 80) siteDomain += ":" + port;
String webUrl = siteDomain + "/sysop";

String dataDir = siteinfo.s("doc_root") + "/data";
f.dataDir = dataDir;
m.dataDir = dataDir;
m.dataUrl = Config.getDataUrl() + (!"/data".equals(Config.getDataUrl()) ? siteinfo.s("ftp_id") : "");

boolean isDevServer = -1 < request.getServerName().indexOf("lms.malgn.co.kr");
siteinfo.put("logo_url", (!"/data".equals(Config.getDataUrl()) ? "" : siteDomain) + m.getUploadUrl(siteinfo.s("logo")));

//IP\ucc28\ub2e8
String userIp = request.getRemoteAddr();
boolean isMalgnOffice = "115.91.52.203".equals(userIp) || "115.91.52.204".equals(userIp) || "125.128.232.145".equals(userIp) || "59.5.222.106".equals(userIp);
if(!"".equals(siteinfo.s("allow_ip_sysop")) && !isMalgnOffice && !Site.checkIP(userIp, siteinfo.s("allow_ip_sysop"))) {
	m.redirect("/");
	return;
}

//\uc5b8\uc5b4
String sysLocale = "".equals(siteinfo.s("locale")) ? "default" : siteinfo.s("locale");
//String sysLocale = "default";
Message _message = new Message(sysLocale);
_message.reloadAll();
m.setMessage(_message);
//p.setMessage(_message);

//\uae30\ubcf8 \ud68c\uc6d0\uc815\ubcf4
int siteId = siteinfo.i("id");
int userId = 0;
String loginId = "";
String userName = "";
String userKind = "";
int userDeptId = 0;
String userGroups = "";
String manageCourses = "";
String userSessionId = "";
boolean isUserMaster = false;
String winTitle = "[\uad00\ub9ac\uc790] " + siteinfo.s("site_nm");
String sysToday = m.time("yyyyMMdd");
String sysNow = m.time("yyyyMMddHHmmss");

//SessionDao mSession = new SessionDao("sysop");
SessionDao mSession = new SessionDao(request, response);
//mSession.setId(session.getId());
mSession.setSiteId(siteId);

//\ub85c\uadf8\uc778 \uc5ec\ubd80\ub97c \uccb4\ud06c
Auth auth = new Auth(request, response);
auth.loginURL = "/sysop/main/login.jsp";
auth.keyName = "MLMSKEY2014" + siteId + "7";
if(0 < siteinfo.i("sysop_session_hour")) auth.setValidTime(siteinfo.i("sysop_session_hour") * 60);
if(auth.isValid()) {
	userId = m.parseInt(auth.getString("ID"));
	loginId = auth.getString("LOGINID");
	userName = auth.getString("NAME");
	userKind = auth.getString("KIND");
	userDeptId = auth.getInt("DEPT");
	userGroups = auth.getString("GROUPS");
	manageCourses = auth.getString("MANAGE_COURSES");
	userSessionId = userSessionId = auth.getString("SESSIONID");
	isUserMaster = "Y".equals(auth.getString("IS_USER_MASTER"));
	if("SYSLOGIN".equals(userSessionId)) siteinfo.put("dup_sysop_yn", "Y");

	mSession.setId(userSessionId);
	mSession.setUserId(userId);

} else {
	if(request.getRequestURI().indexOf("/main/login.jsp") == -1
		&& request.getRequestURI().indexOf("/vod/upload.jsp") == -1
		&& request.getRequestURI().indexOf("/main/slogin.jsp") == -1
		&& request.getRequestURI().indexOf("/site/site_template.jsp") == -1
		&& request.getRequestURI().indexOf("/site/site_maildir.jsp") == -1
		&& (request.getRequestURI().indexOf("/user/sleep_insert.jsp") == -1 || !"log".equals(m.rs("after")))
	) {
		m.jsReplace(auth.loginURL, "top");
		return;
	}
}

MenuDao Menu = new MenuDao(p, siteId, "default");
SiteMenuDao SiteMenu = new SiteMenuDao();

boolean superBlock = "S".equals(userKind);
boolean adminBlock = "S".equals(userKind) || "A".equals(userKind);
boolean courseManagerBlock = "C".equals(userKind);
boolean deptManagerBlock = "D".equals(userKind);

//boolean isAuthCrm = superBlock || (-1 < siteinfo.s("auth_crm").indexOf("|" + userKind + "|"));
boolean isAuthCrm = superBlock || Menu.accessible(-999, userId, userKind, false);

//\ub85c\uadf8\uc544\uc6c3-\uacfc\uc815\uc6b4\uc601\uc790
if(courseManagerBlock && "".equals(manageCourses) && request.getRequestURI().indexOf("/main/logout.jsp") == -1) {
	m.jsAlert("\ub2f4\ub2f9\ud55c \uacfc\uc815\uc774 \uc5c6\uc2b5\ub2c8\ub2e4.\\n \uad00\ub9ac\uc790\uc5d0\uac8c \ubb38\uc758\ud558\uc138\uc694.");
	m.jsReplace("/sysop/main/logout.jsp", "top");
	return;
}

//\ub9e4\ub274\uc5bc
ManualDao Manual = new ManualDao();
int ManualId = Menu.getOneInt("SELECT manual_id FROM " + Menu.table + " WHERE link = '" + m.replace(request.getRequestURI(), "/sysop", "..") + "'");
if(0 < ManualId) {
	int ManualStatus = Manual.getOneInt("SELECT status FROM " + Manual.table + " WHERE id = " + ManualId);
	if(0 < ManualStatus) p.setVar("SYS_MENU_MANUAL_ID", ManualId);
}

p.setVar("WEB_URL", webUrl);
p.setVar("FRONT_URL", siteDomain);
p.setVar("SYS_TITLE", winTitle);
p.setVar("SYS_USERKIND", userKind);
p.setVar("SITE_INFO", siteinfo);
//p.setVar("SITE_CONFIG", siteconfig);
p.setVar("IS_AUTH_CRM", isAuthCrm);
p.setVar("IS_DEV_SERVER", isDevServer);
p.setVar("SYS_LOCALE", sysLocale);
p.setVar("SYS_TODAY", sysToday);
p.setVar("SYS_NOW", sysNow);
p.setVar("SYS_COMMON_CDN", !isDevServer ? "//cdn.malgnlms.com" : "");

p.setVar("user_master_block", isUserMaster || isMalgnOffice);
p.setVar("malgn_office_block", isMalgnOffice);
p.setVar("super_block", superBlock);
p.setVar("admin_block", adminBlock);
p.setVar("course_manager_block", courseManagerBlock);
p.setVar("dept_manager_block", deptManagerBlock);

UserSessionDao UserSession = new UserSessionDao();
UserSession.setSiteId(siteId);
UserSession.setType("sysop");
if(userId != 0 && "N".equals(siteinfo.s("dup_sysop_yn")) && ("".equals(userSessionId) || userSessionId == null || !UserSession.isValid(userSessionId, userId))) {
	if(request.getRequestURI().indexOf("/sysop/main/logout.jsp") == -1) {
		m.jsAlert("\uc138\uc158\uc774 \ub9cc\ub8cc\ub418\uc5c8\uac70\ub098 \uc911\ubcf5 \ub85c\uadf8\uc778\uc774 \ub418\uc5b4 \uc790\ub3d9\uc73c\ub85c \ub85c\uadf8\uc544\uc6c3 \ub429\ub2c8\ub2e4.");
		m.jsReplace("/sysop/main/logout.jsp?mode=session", "top");
		return;
	}
}


      

//\uc544\uc774\ub514
String code = m.rs("code");
String md = "post";

//\uac1d\uccb4
BoardDao board = new BoardDao();
CategoryDao category = new CategoryDao();
PostDao post = new PostDao();
CommentDao comment = new CommentDao();
FileDao file = new FileDao();

//\uc815\ubcf4-\uac8c\uc2dc\ud310
//board.d(out);
DataSet binfo = board.find(
	"code = '" + code + "' AND status != -1 AND site_id = " + siteId + ""
	+ (!("A".equals(userKind) || "S".equals(userKind)) ? " AND admin_idx LIKE '%|" + userId + "|%'" : "")
);
if(!binfo.next()) { m.jsError("\ud574\ub2f9 \uac8c\uc2dc\ud310\uc774 \uc5c6\uc2b5\ub2c8\ub2e4."); return; }

binfo.put("notice_block", binfo.b("notice_yn"));
binfo.put("category_block", binfo.b("category_yn"));
binfo.put("upload_block", binfo.b("upload_yn"));
binfo.put("reply_block", binfo.b("reply_yn"));
binfo.put("comment_block", binfo.b("comment_yn"));

//\ud658\uacbd\uc124\uc815-\ubaa8\ub4c8/\ud0c0\uc785/\uc2a4\ud0a8/\uc0c8\uae00\uae30\uc900
String btype = binfo.s("board_type");
boolean isBoardAdmin = true;
p.setVar(btype + "_type_block", true);

int bid = binfo.i("id");
int newHour = 24;											//\uc0c8\uae00\uae30\uc900(\uc2dc\uac04)
int columnCnt = 5;											//\uac24\ub7ec\ub9ac\uc6a9 \uceec\ub7fc\uc218
int listNum = 20;
p.setVar("contentwidth", post.getContentWidth());

DataSet categories = binfo.b("category_yn") ? category.getList("board", bid, siteId) : new DataSet();	//\uce74\ud14c\uace0\ub9ac

//\ucc44\ub110
String ch = "sysop";


      

//\uc811\uadfc\uad8c\ud55c
if(!(Menu.accessible(80, userId, userKind))) { m.jsError("\uc811\uadfc \uad8c\ud55c\uc774 \uc5c6\uc2b5\ub2c8\ub2e4."); return; }

//\uae30\ubcf8\ud0a4
int id = m.ri("id");
if(id == 0) { m.jsError("\uae30\ubcf8\ud0a4\ub294 \ubc18\ub4dc\uc2dc \uc9c0\uc815\ud574\uc57c \ud569\ub2c8\ub2e4."); return; }

//\uc815\ubcf4
DataSet info = post.find("id = " + id + " AND status != -1 AND site_id = " + siteId + "");
if(!info.next()) { m.jsError("\ud574\ub2f9 \uac8c\uc2dc\ubb3c\uc774 \uc5c6\uc2b5\ub2c8\ub2e4."); return; }
info.put("reg_date_conv", m.time("yyyy.MM.dd HH:mm:ss", info.s("reg_date")));
info.put("mod_date_block", !"".equals(info.s("mod_date")));
info.put("mod_date_conv", m.time("yyyy.MM.dd HH:mm:ss", info.s("mod_date")));

info.put("comment_conv", info.i("comm_cnt") > 0 ? "(" + info.i("comm_cnt") + ")" : "" );
info.put("hit_conv", m.nf(info.i("hit_cnt")));
//info.put("recomm_conv", m.nf(info.i("recomm_cnt")));
info.put("new_block", m.diffDate("H", info.s("reg_date"), m.time("yyyyMMddHHmmss")) <= newHour);
info.put("notice_block", info.b("notice_yn"));
info.put("subject", m.htmlToText(info.s("subject")));

String categoryName = binfo.b("category_yn") ? category.getName(categories, info.s("category_id")) : "" ;
info.put("category_conv", !"".equals(categoryName) ? "[" + categoryName + "]" : "");

boolean isReplyPost = info.s("depth").length() > 1;
info.put("reply_block", isReplyPost);
info.put("content", m.htt(info.s("content")));

//\ud3fc\uccb4\ud06c
if(binfo.b("category_yn")) { f.addElement("category_id", info.s("category_id"), "hname:'\uce74\ud14c\uace0\ub9ac', required:'Y'"); }
f.addElement("writer", info.s("writer"), "hname:'\uc791\uc131\uc790', required:'Y'");
f.addElement("notice_yn", info.s("notice_yn"), "hname:'\uacf5\uc9c0\uae00 \uc5ec\ubd80'");
f.addElement("secret_yn", info.s("secret_yn"), "hname:'\ube44\ubc00\uae00 \uc5ec\ubd80'");
f.addElement("subject", info.s("subject"), "hname:'\uc81c\ubaa9', maxbyte:'250', required:'Y'");
f.addElement("content", null, "hname:'\ub0b4\uc6a9', allowiframe:'Y'");
f.addElement("reg_date", info.s("reg_date"), "hname:'\ub4f1\ub85d\uc77c', required:'Y', minbyte:'14', option:'number'");
f.addElement("hit_cnt", info.s("hit_cnt"), "hname:'\uc870\ud68c\uc218', required:'Y', option:'number'");
//f.addElement("recomm_cnt", info.s("recomm_cnt"), "hname:'\ucd94\ucc9c\uc218', required:'Y', option:'number'");
if("youtube".equals(btype)) f.addElement("youtube_cd", info.s("youtube_cd"), "hname:'\uc720\ud29c\ube0c\ub9c1\ud06c'");
f.addElement("display_yn", info.s("display_yn"), "hname:'\ub178\ucd9c\uc5ec\ubd80'");

//\ub4f1\ub85d
if(m.isPost() && f.validate()) {

	String content = f.get("content");
	//\uc81c\ud55c-\uc774\ubbf8\uc9c0URI
	if(-1 < content.indexOf("<img") && -1 < content.indexOf("data:image/") && -1 < content.indexOf("base64")) {
		m.jsAlert("\uc774\ubbf8\uc9c0\ub294 \ucca8\ubd80\ud30c\uc77c \uae30\ub2a5\uc73c\ub85c \uc5c5\ub85c\ub4dc \ud574 \uc8fc\uc138\uc694.");
		return;
	}

	//\uc81c\ud55c-\uc6a9\ub7c9
	int bytes = content.replace("\r\n", "\n").getBytes("UTF-8").length;
	if(60000 < bytes) {
		m.jsAlert("\ub0b4\uc6a9\uc740 60000\ubc14\uc774\ud2b8\ub97c \ucd08\uacfc\ud574 \uc791\uc131\ud558\uc2e4 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.\\n(\ud604\uc7ac " + bytes + "\ubc14\uc774\ud2b8)");
		return;
	}

	post.item("writer", f.get("writer"));
	post.item("category_id", !isReplyPost? f.get("category_id", "0") : info.s("category_id"));
	post.item("notice_yn", f.get("notice_yn", "N"));
	post.item("secret_yn", f.get("secret_yn", "N"));
	post.item("youtube_cd", f.get("youtube_cd"));

	post.item("subject", f.get("subject"));
	post.item("content", content);

	post.item("mod_date", m.time("yyyyMMddHHmmss"));
	post.item("hit_cnt", f.getInt("hit_cnt"));
	//post.item("recomm_cnt", f.getInt("recomm_cnt"));
	post.item("display_yn", f.get("display_yn"));
	post.item("reg_date", f.get("reg_date"));
	post.item("status", f.get("status", "1"));

	if(!post.update("id = " + id + "")) { m.jsAlert("\uc218\uc815\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud558\uc600\uc2b5\ub2c8\ub2e4."); return; }

	mSession.put("file_module", "");
	mSession.put("file_module_id", 0);
	mSession.save();

	//\uc774\ub3d9
	m.jsReplace("index.jsp?" + m.qs(), "parent");
	return;
}

mSession.put("file_module", "post");
mSession.put("file_module_id", id);
mSession.save();

//\ucd9c\ub825
p.setLayout(ch);
p.setBody("board.write");
p.setVar("p_title", binfo.s("board_nm"));
p.setVar("query", m.qs());
p.setVar("form_script", f.getScript());
p.setVar("list_query", m.qs("id"));

p.setVar("modify", true);
p.setVar(info);
p.setVar("board", binfo);
p.setVar("post_id", id);

p.setLoop("categories", categories);
p.setLoop("display_yn", m.arr2loop(post.displayYn));
p.display();


    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_application.getJspApplicationContext().freePageContext(pageContext);
    }
  }

  private java.util.ArrayList _caucho_depends = new java.util.ArrayList();

  public java.util.ArrayList _caucho_getDependList()
  {
    return _caucho_depends;
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;
    if (com.caucho.server.util.CauchoSystem.getVersionId() != 6749855747778707107L)
      return true;
    for (int i = _caucho_depends.size() - 1; i >= 0; i--) {
      com.caucho.vfs.Dependency depend;
      depend = (com.caucho.vfs.Dependency) _caucho_depends.get(i);
      if (depend.isModified())
        return true;
    }
    return false;
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    com.caucho.server.webapp.WebApp webApp
      = (com.caucho.server.webapp.WebApp) config.getServletContext();
    super.init(config);
    com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
    com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.PageContextImpl(webApp, this);
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/board/modify.jsp"), -1858950659151655873L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/board/init.jsp"), 2020792480877270766L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/init.jsp"), -7592216907510913785L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
}
