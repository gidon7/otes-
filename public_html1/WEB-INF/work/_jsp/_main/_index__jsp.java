/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._main;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _index__jsp extends com.caucho.jsp.JavaPage
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

final String BUILDVERSION = "22.10.01";

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
boolean isDevServer = -1 < request.getServerName().indexOf("lms.malgn.co.kr");
String webUrl = request.getScheme() + "://" + request.getServerName();
int port = request.getServerPort();
if(port != 80 && port != 443) webUrl += ":" + port;
String dataDir = siteinfo.s("doc_root") + "/data";
String tplRoot = siteinfo.s("doc_root") + "/html";
if(isDevServer && siteinfo.i("skin_cd") == 5) tplRoot = siteinfo.s("doc_root") + "/html_v5";
f.dataDir = dataDir;
m.dataDir = dataDir;
//m.dataUrl = "https://cdn.malgnlms.com/cdndata/" + siteinfo.s("ftp_id");
m.dataUrl = Config.getDataUrl() + (!"/data".equals(Config.getDataUrl()) ? siteinfo.s("ftp_id") : "");


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
if(siteinfo.i("skin_cd") == 5) p.setBaseRoot("/home/lms/public_html/html_v5");

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
boolean isRemoteHost = false;
boolean isRespWeb = (5 <= siteinfo.i("skin_cd"));
boolean isGoMobile = !isRespWeb && m.isMobile();

SessionDao mSession = new SessionDao(request, response);

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
	//isRemoteHost = "Y".equals(auth.getString("TUTOR_YN")) && "Y".equals(SiteConfig.s("kt_remote_yn"));
	isRemoteHost = "Y".equals(auth.getString("TUTOR_YN")) && ("L".equals(SiteConfig.s("lanedu_type")) || "B".equals(SiteConfig.s("lanedu_type")));
	if(userGroups != null) {
		if(-1 < userGroups.indexOf(",")) for(String userGroupId : m.split(",", userGroups)) p.setVar("SYS_USERGROUP_" + userGroupId, true);
		else p.setVar("SYS_USERGROUP_" + userGroups, true);
	}
	
	//2\ucc28\uc778\uc99d\uccb4\ud06c
	/*if("direct".equals(loginMethod)
		&& "Y".equals(siteinfo.s("user_auth2_yn"))
		&& !"Y".equals(auth.getString("USER_AUTH2_YN"))
		&& !"".equals(auth.getString("USER_AUTH2_TYPE"))
		&& !"malgn".equals(loginId)
		&& -1 == request.getRequestURI().indexOf("/main/site_cache.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/auth2.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/otpkey_register.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/logout.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/alogin.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/slogin.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/login_facebook.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/login_google.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/login_kakao.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/login_line.jsp")
		&& -1 == request.getRequestURI().indexOf("/member/login_naver.jsp")
	) {
		m.jsReplace("/member/auth2.jsp?returl=" + m.rs("returl", "/main/index.jsp"), "top");
		return;
	}*/

	mSession.put("id", userSessionId);
	mSession.save();

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
			|| -1 < request.getRequestURI().indexOf("/ktle/check_api.jsp")
			|| -1 < request.getRequestURI().indexOf("/common/")
			|| -1 < request.getRequestURI().indexOf("/api/auto_send.jsp")
		) {
			isNeedLogin = false;
		} else if(!"".equals(siteinfo.s("close_except"))) {
			for(int i = 0; i < exceptPages.length; i++) {
				if(-1 < request.getRequestURI().indexOf(exceptPages[i])) { isNeedLogin = false; continue; }
			}
		}

		if(isNeedLogin) {
			m.redirect(!isGoMobile ? auth.loginURL : "/mobile/login.jsp");
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
p.setVar("IS_RESP_WEB", isRespWeb);
p.setVar("IS_MOBILE", m.isMobile());
p.setVar("IS_GO_MOBILE", isGoMobile);
p.setVar("IS_DEV_SERVER", isDevServer);
p.setVar("SYS_LOCALE", sysLocale);
p.setVar("SYS_TODAY", sysToday);
p.setVar("SYS_NOW", sysNow);
p.setVar("IS_REMOTE_HOST", isRemoteHost);
p.setVar("SYS_COMMON_CDN", !isDevServer ? "//cdn.malgnlms.com" : "");
p.setVar("script", siteinfo.s("header_script"));

for(int IndexSkin = 1; IndexSkin < 5; IndexSkin++) {
	p.setVar("SKIN_LT_" + IndexSkin, siteinfo.i("skin_cd") < IndexSkin);
	p.setVar("SKIN_LTE_" + IndexSkin, siteinfo.i("skin_cd") <= IndexSkin);
	p.setVar("SKIN_GT_" + IndexSkin, siteinfo.i("skin_cd") > IndexSkin);
	p.setVar("SKIN_GTE_" + IndexSkin, siteinfo.i("skin_cd") >= IndexSkin);
}

MenuDao Menu = new MenuDao(p, sysLocale);

UserSessionDao UserSession = new UserSessionDao();
UserSession.setSiteId(siteId);
if(userId != 0 && !"SYSLOGIN".equals(userSessionId) && !siteinfo.b("duplication_yn") && !UserSession.isValid(userSessionId, userId)) {
	if(request.getRequestURI().indexOf("/member/logout.jsp") == -1 && request.getRequestURI().indexOf("/mobile/logout.jsp") == -1) {
		m.jsAlert(_message.get("alert.common.logout_session"));
		if(request.getRequestURI().indexOf("/mobile/") != -1) m.jsReplace("/mobile/logout.jsp?mode=session");
		else m.jsReplace("/member/logout.jsp?mode=session");
		return;
	}
}


      

String ch = !userB2BBlock ? m.rs("ch", "main") : "b2b";


      

//\uac1d\uccb4
BannerDao banner = new BannerDao();
MainpageDao mainpage = new MainpageDao();

//\ubaa9\ub85d-\uba54\uc778\ubc30\ub108
DataSet banners1 = banner.find("status = 1 AND banner_type = 'main' AND site_id = " + siteId + "", "*", "sort ASC", 4);
while(banners1.next()) {
	banners1.put("banner_file_url", m.getUploadUrl(banners1.s("banner_file")));
}

//\ubaa9\ub85d-\uc6b0\uce21\ubc30\ub108
DataSet banners2 = banner.find("status = 1 AND banner_type = 'right' AND site_id = " + siteId + "", "*", "sort ASC", 4);
while(banners2.next()) {
	banners2.put("banner_file_url", m.getUploadUrl(banners2.s("banner_file")));
}

//\ubaa9\ub85d-\uc0c1\ub2e8\ubc30\ub108
DataSet banners3 = banner.find("status = 1 AND banner_type = 'top' AND site_id = " + siteId + "", "*", "sort ASC", 4);
while(banners3.next()) {
	banners3.put("banner_file_url", m.getUploadUrl(banners3.s("banner_file")));
}

//\ubaa9\ub85d-\uba54\uc778\ud654\uba74
DataSet mlist = mainpage.find("site_id = " + siteId + " AND display_yn = 'Y' AND status = 1", "*", "sort ASC, id ASC");
while(mlist.next()) {
	if(!"".equals(mlist.s("module_params"))) {
		HashMap<String, Object> sub = Json.toMap(mlist.s("module_params"));
		for(String key : sub.keySet()) {
			mlist.put(m.replace(key, "md_", ""), m.nl2br(sub.get(key).toString()));
		}
	}
}

//\ucd9c\ub825
p.setLayout(ch);
if(userB2BBlock) {
	p.setBody("main.index_b2b");
} else if(new File(tplRoot + "/main/index_skin" + siteinfo.s("skin_cd") + ".html").exists()) {
	p.setBody("main.index_skin" + siteinfo.s("skin_cd"));
} else {
	p.setBody("main.index");
}
p.setLoop("banners1", banners1);
p.setLoop("banners2", banners2);
p.setLoop("banners3", banners3);
p.setLoop("main_list", mlist);

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("main/index.jsp"), 4733464294895261973L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("main/init.jsp"), -7543222371960158649L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("init.jsp"), 6326164006358099841L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
}
