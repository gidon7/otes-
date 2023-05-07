/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._mobile;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _popup__jsp extends com.caucho.jsp.JavaPage
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
      

//request.setCharacterEncoding("utf-8");

String docRoot = Config.getDocRoot();
String jndi = Config.getJndi();

Malgn m = new Malgn(request, response, out);

Form f = new Form("form1");
try { f.setRequest(request); }
catch(Exception ex) { out.print("Overflow file size. - " + ex.getMessage()); return; }

SiteDao Site = new SiteDao(); //Site.clear();
DataSet siteinfo = Site.getSiteInfo(request.getServerName());
SiteConfigDao SiteConfig = new SiteConfigDao(siteinfo.i("id"));
if(1 != siteinfo.i("status") || "".equals(siteinfo.s("doc_root"))) {
	m.jsReplace("about:blank", "top"); 
	return;
}
//Hashtable<String, String> siteconfig = SiteConfig.getSiteConfig(siteinfo.s("id"));

String webUrl = request.getScheme() + "://" + request.getServerName();
int port = request.getServerPort();
if(port != 80) webUrl += ":" + port;
String dataDir = siteinfo.s("doc_root") + "/data";
String tplRoot = siteinfo.s("doc_root") + "/html";
f.dataDir = dataDir;
m.dataDir = dataDir;
//m.dataUrl = "https://cdn.malgnlms.com/cdndata/" + siteinfo.s("ftp_id");
m.dataUrl = Config.getDataUrl() + (!"/data".equals(Config.getDataUrl()) ? siteinfo.s("ftp_id") : "");

boolean isDevServer = -1 < request.getServerName().indexOf("lms.malgn.co.kr");
if(!"".equals(siteinfo.s("logo"))) siteinfo.put("logo_url", m.getUploadUrl(siteinfo.s("logo")));
else siteinfo.put("logo_url", "/common/images/default/malgn_logo.jpg");

//IP\ucc28\ub2e8
String userIp = request.getRemoteAddr();
if(!"".equals(siteinfo.s("allow_ip_user")) && !Site.checkIP(userIp, siteinfo.s("allow_ip_user"))) {
	m.redirect("/main/guide.jsp");
	return;
}

Page p = new Page(tplRoot);
p.setRequest(request);
p.setPageContext(pageContext);
p.setWriter(out);
p.setBaseRoot("/home/lms/public_html/html");

//\uc5b8\uc5b4
String sysLocale = "".equals(siteinfo.s("locale")) ? "default" : siteinfo.s("locale");
Message _message = new Message(sysLocale);
_message.reloadAll();
m.setMessage(_message);
//p.setMessage(_message);

int siteId = siteinfo.i("id");
int userId = 0;
String loginId = "";
String loginMethod = "";
String userName = "";
String userEmail = "";
String userKind = "";
int userDeptId = 0;
String userGroups = "";
int userGroupDisc = 0;
//String aloginYn = "";
String userSessionId = "";
boolean userB2BBlock = false;
String userB2BName = "";
String userB2BFile = "";
String sysToday = m.time("yyyyMMdd");
String sysNow = m.time("yyyyMMddHHmmss");

//SessionDao mSession = new SessionDao("user");
SessionDao mSession = new SessionDao(request, response);
//mSession.setId(session.getId());
mSession.setSiteId(siteId);

Auth auth = new Auth(request, response);
auth.loginURL = "/member/login.jsp";
auth.keyName = "MLMS14" + siteId + "7";
if(0 < siteinfo.i("session_hour")) auth.setValidTime(siteinfo.i("session_hour") * 3600);
if(auth.isValid()) {
	userId = auth.getInt("ID");
	loginId = auth.getString("LOGINID");
	loginMethod = auth.getString("LOGINMETHOD");
	userName = auth.getString("NAME");
	userEmail = auth.getString("EMAIL");
	userKind = auth.getString("KIND");
	userDeptId = auth.getInt("DEPT");
	userGroups = auth.getString("GROUPS");
	userGroupDisc = !"null".equals(auth.getString("GROUPS_DISC")) ? m.parseInt(auth.getString("GROUPS_DISC")) : 0;
	//aloginYn = auth.getString("ALOGIN_YN");
	userSessionId = auth.getString("SESSIONID");
	userB2BName = auth.getString("B2BNAME");
	userB2BFile = auth.getString("B2BFILE");
	if("SYSLOGIN".equals(userSessionId)) siteinfo.put("duplication_yn", "Y");

	mSession.setId(userSessionId);
	mSession.setUserId(userId);

	if(userGroups != null) {
		if(-1 < userGroups.indexOf(",")) for(String userGroupId : m.split(",", userGroups)) p.setVar("SYS_USERGROUP_" + userGroupId, true);
		else p.setVar("SYS_USERGROUP_" + userGroups, true);
	}

	p.setVar("login_block", true);
} else {
	p.setVar("login_block", false);

	if(siteinfo.b("close_yn")) {		
		boolean isNeedLogin = true;
		String[] exceptPages = m.split("|", siteinfo.s("close_except"));
		
		if(-1 < request.getRequestURI().indexOf("/member/login.jsp")
			|| -1 < request.getRequestURI().indexOf("/member/find.jsp")
			|| -1 < request.getRequestURI().indexOf("/member/alogin.jsp")
			|| -1 < request.getRequestURI().indexOf("/member/slogin.jsp")
			|| -1 < request.getRequestURI().indexOf("/member/slogin_input.jsp")
			|| -1 < request.getRequestURI().indexOf("/member/sysop_slogin.jsp")
			|| -1 < request.getRequestURI().indexOf("/main/site_cache.jsp")
			|| -1 < request.getRequestURI().indexOf("/mobile/login.jsp")
			|| -1 < request.getRequestURI().indexOf("/mypage/certificate.jsp")
			|| -1 < request.getRequestURI().indexOf("/mypage/certificate_course.jsp")
			|| -1 < request.getRequestURI().indexOf("/kollus/check_api.jsp")
			|| -1 < request.getRequestURI().indexOf("/common/")
		) {
			isNeedLogin = false;
		} else if(!"".equals(siteinfo.s("close_except"))) {
			for(int i = 0; i < exceptPages.length; i++) {
				if(-1 < request.getRequestURI().indexOf(exceptPages[i])) { isNeedLogin = false; continue; }
			}
		}

		if(isNeedLogin) {
			m.redirect(!m.isMobile() ? auth.loginURL : "/mobile/login.jsp");
			return;
		}
	}
}

userB2BBlock = !"".equals(userB2BName) && null != userB2BName;
p.setVar("SYS_HTTPHOST", request.getServerName());
p.setVar("SYS_LOGINID", loginId);
p.setVar("SYS_USERNAME", userName);
p.setVar("SYS_USEREMAIL", userEmail);
p.setVar("SYS_USERKIND", userKind);
p.setVar("SYS_DEPTID", userDeptId);
p.setVar("SYS_GROUP_DISC", userGroupDisc);
p.setVar("SYS_B2BBLOCK", userB2BBlock);
p.setVar("SYS_B2BNAME", userB2BName);
p.setVar("SYS_B2BFILE", userB2BFile);
p.setVar("SYS_PAGE_URL", request.getRequestURL() + (!"".equals(m.qs()) ? "?" + m.qs() : ""));
p.setVar("SYS_TITLE", siteinfo.s("site_nm"));
p.setVar("webUrl", webUrl);
p.setVar("SITE_INFO", siteinfo);
//p.setVar("SITE_CONFIG", siteconfig);
p.setVar("CURR_DATE", m.time("yyyyMMdd"));
p.setVar("SYS_EK", m.encrypt(loginId + siteinfo.s("sso_key") + m.time("yyyyMMdd"), "SHA-256"));
p.setVar("IS_MOBILE", m.isMobile());
p.setVar("IS_DEV_SERVER", isDevServer);
p.setVar("SYS_LOCALE", sysLocale);
p.setVar("SYS_TODAY", sysToday);
p.setVar("SYS_NOW", sysNow);
p.setVar("SYS_COMMON_CDN", !isDevServer ? "//cdn.malgnlms.com" : "");

MenuDao Menu = new MenuDao(p, sysLocale);

UserSessionDao UserSession = new UserSessionDao();
UserSession.setSiteId(siteId);
if(userId != 0 && !siteinfo.b("duplication_yn") && !UserSession.isValid(userSessionId, userId)) {
	if(request.getRequestURI().indexOf("/member/logout.jsp") == -1 && request.getRequestURI().indexOf("/mobile/logout.jsp") == -1) {
		m.jsAlert(_message.get("alert.common.logout_session"));
		if(request.getRequestURI().indexOf("/mobile/") != -1) m.jsReplace("/mobile/logout.jsp?mode=session");
		else m.jsReplace("/member/logout.jsp?mode=session");
		return;
	}
}


      

String ch = "mobile";
auth.loginURL = "/mobile/login.jsp";
//auth.loginURL = request.getScheme() + "://" + siteinfo.s("domain") + "/mobile/login.jsp";

String sslDomain = request.getServerName().indexOf(".malgn.co.kr") > 0 ? "ssl.malgn.co.kr" : "ssl.malgnlms.com";
boolean isSSL = "https".equals(request.getScheme()) && sslDomain.equals(request.getServerName()) && !"".equals(f.get("domain"));

if(siteinfo.b("ssl_yn")) {
	sslDomain = siteinfo.s("domain");
	isSSL = false;
}

if(isSSL) {
	siteinfo = Site.getSiteInfo(f.get("domain"));
	if("".equals(siteinfo.s("doc_root"))) { m.jsError(_message.get("alert.site.nodata")); return; }
	siteId = siteinfo.i("id");
	
	//SiteConfig.remove(siteinfo.s("id"));
	//siteconfig = SiteConfig.getSiteConfig(siteId + "");

	mSession.setId(f.get("session_id"));
	mSession.setSiteId(siteId);
}
p.setVar("SSL_DOMAIN", sslDomain);


      

//\uac1d\uccb4
PopupDao popup = new PopupDao();

//\ubaa9\ub85d
DataSet list = popup.find("popup_type = 'mobile' AND site_id = " + siteId + " AND status = 1 AND '" + m.time("yyyyMMdd") + "' BETWEEN start_date AND end_date", "*", "id DESC");

//\uc815\ubcf4
DataSet info = new DataSet();

//\ud3ec\ub9f7\ud305
while(list.next()) {
	if(!"done".equals(m.getCookie("POPUP_MOBILE" + list.i("id")))) {
		info.addRow(list.getRow());
		break;
	}
}

//\ucd9c\ub825
p.setBody("mobile.popup");
p.setVar(info);
p.setVar("banner_block", info.size() > 0);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("mobile/popup.jsp"), -3944662390612232735L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("mobile/init.jsp"), 3140104728474978406L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("init.jsp"), -919485065315744250L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
}
