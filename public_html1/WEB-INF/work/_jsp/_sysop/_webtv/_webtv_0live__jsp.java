/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._sysop._webtv;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _webtv_0live__jsp extends com.caucho.jsp.JavaPage
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


      

if(!(Menu.accessible(926, userId, userKind))) { m.jsError("\uc811\uadfc \uad8c\ud55c\uc774 \uc5c6\uc2b5\ub2c8\ub2e4."); return; }

//\uac1d\uccb4
WebtvLiveDao webtvLive = new WebtvLiveDao();
LessonDao lesson = new LessonDao();
MCal mcal = new MCal();

//\uc815\ubcf4
DataSet info = webtvLive.query(
	" SELECT a.*, l.lesson_nm, l.content_width, l.content_height "
	+ " FROM " + webtvLive.table + " a "
	+ " LEFT JOIN " + lesson.table + " l ON a.lesson_id = l.id AND l.site_id = " + siteId + " AND l.status != -1 "
	+ " WHERE a.site_id = " + siteId + " AND a.status != -1"
);
if(!info.next()) {
	webtvLive.item("site_id", siteId);
	webtvLive.item("start_date", m.time("yyyyMMddHH0000"));
	webtvLive.item("end_date", m.time("yyyyMMddHH0000"));
	webtvLive.item("login_yn", "N");
	webtvLive.item("status", "0");
	if(!webtvLive.insert()) { m.jsAlert("\uc2e4\uc2dc\uac04\ubc29\uc1a1 \uc815\ubcf4\ub97c \ub4f1\ub85d\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4."); return; }

	m.js("location.reload();");
	return;
}

f.addElement("live_nm", info.s("live_nm"), "hname:'\ub77c\uc774\ube0c\ubc29\uc1a1\uba85', required:'Y'");
f.addElement("live_option", info.s("live_option"), "hname:'\ubc29\uc1a1\uc635\uc158\uac12'");
f.addElement("option_desc", null, "hname:'\ubc29\uc1a1\uc635\uc158\uc124\uba85'");
f.addElement("lesson_id", info.i("lesson_id"), "hname:'\ubc29\uc1a1\uac15\uc758', required:'Y'");
f.addElement("lesson_nm", info.s("lesson_nm"), "hname:'\ubc29\uc1a1\uac15\uc758\uba85'");
f.addElement("start_day", m.time("yyyy-MM-dd", info.s("start_date")), "hname:'\ubc29\uc1a1\uc2dc\uc791\uc77c', required:'Y'");
f.addElement("start_time_hour", m.time("HH", info.s("start_date")), "hname:'\ubc29\uc1a1\uc2dc\uc791\uc2dc\uac04', required:'Y'");
f.addElement("start_time_min", m.time("mm", info.s("start_date")), "hname:'\ubc29\uc1a1\uc2dc\uc791\uc2dc\uac04(\ubd84)', required:'Y'");
f.addElement("end_day", m.time("yyyy-MM-dd", info.s("end_date")), "hname:'\ubc29\uc1a1\uc885\ub8cc\uc77c', required:'Y'");
f.addElement("end_time_hour", m.time("HH", info.s("end_date")), "hname:'\ubc29\uc1a1\uc885\ub8cc\uc2dc\uac04', required:'Y'");
f.addElement("end_time_min", m.time("mm", info.s("end_date")), "hname:'\ubc29\uc1a1\uc885\ub8cc\uc2dc\uac04(\ubd84)', required:'Y'");
f.addElement("login_yn", info.s("login_yn"), "hname:'\ud68c\uc6d0\uc804\uc6a9\uc5ec\ubd80', required:'Y'");
f.addElement("status", info.s("status"), "hname:'\uc0c1\ud0dc', required:'Y'");

//\uc218\uc815
if(m.isPost() && f.validate()) {
	webtvLive.d(out);
	webtvLive.item("live_nm", f.get("live_nm"));
	webtvLive.item("live_option", f.get("live_option"));
	webtvLive.item("option_desc", f.get("option_desc"));
	webtvLive.item("lesson_id", f.getInt("lesson_id"));
	webtvLive.item("start_date", m.time("yyyyMMdd", f.get("start_day")) + f.get("start_time_hour") + f.get("start_time_min") + "00");
	webtvLive.item("end_date", m.time("yyyyMMdd", f.get("end_day")) + f.get("end_time_hour") + f.get("end_time_min") + "00");
	webtvLive.item("login_yn", f.get("login_yn"));
	webtvLive.item("status", f.get("status"));

	if(!webtvLive.update("site_id = " + siteId)) { m.jsAlert("\ub77c\uc774\ube0c\ubc29\uc1a1 \uc815\ubcf4\ub97c \uc218\uc815\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4."); return; }
	m.jsAlert("\ub77c\uc774\ube0c\ubc29\uc1a1 \uc815\ubcf4\uac00 \uc218\uc815\ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
	m.jsReplace("../webtv/webtv_live.jsp", "parent");
	return;
}

//\ud3ec\ub9f7\ud305
info.put("option_desc_conv", m.nl2br(info.s("option_desc")));

//\ucd9c\ub825
p.setBody("webtv.webtv_live");
p.setVar("query", m.qs());
p.setVar("form_script", f.getScript());

p.setVar(info);

p.setLoop("hours", mcal.getHours());
p.setLoop("minutes", mcal.getMinutes(5));

p.setLoop("status_list", m.arr2loop(webtvLive.statusList));

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/webtv/webtv_live.jsp"), -5546455962213153023L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/init.jsp"), -7592216907510913785L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
}
