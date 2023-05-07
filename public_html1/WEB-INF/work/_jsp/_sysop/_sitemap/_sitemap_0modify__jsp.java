/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._sysop._sitemap;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _sitemap_0modify__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;


  public boolean copyFile(String inPath, String outPath) throws Exception {
  	//\ubcc0\uc218
  	File inFile = new File(inPath);
  	File outFile = new File(outPath);
  	InputStream inStream = null;
  	OutputStream outStream = null;
  	byte[] buffer = new byte[1024];
  	int length = 0;
  	boolean result = true;

  	try {
  		//\ubcc0\uc218
  		inStream = new FileInputStream(inFile); //\uc6d0\ubcf8
  		outStream = new FileOutputStream(outFile); //\ubcf5\uc0ac

  		//\ubcf5\uc0ac
  		while((length = inStream.read(buffer)) > 0) {
  			outStream.write(buffer, 0, length);
  		}
  	} catch(Exception e) {
  		//e.printStackTrace();
  		result = false;
  	} finally {
  		inStream.close();
  		outStream.close();
  	}

  	return result;
  }

  
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


      

String ch = "sysop"; 
String type = m.rs("type", "SITEMAP");


      

//\uc811\uadfc\uad8c\ud55c
if(!Menu.accessible(125, userId, userKind)) { m.jsError("\uc811\uadfc \uad8c\ud55c\uc774 \uc5c6\uc2b5\ub2c8\ub2e4."); return; }

//\uae30\ubcf8\ud0a4
String code = m.rs("code");
if("".equals(code)) { m.jsError("\uae30\ubcf8\ud0a4\ub294 \ubc18\ub4dc\uc2dc \uc9c0\uc815\ud574\uc57c \ud569\ub2c8\ub2e4."); return; }

//\uac1d\uccb4
SitemapDao sitemap = new SitemapDao(siteId);
BannerDao banner = new BannerDao();

//\uc815\ubcf4
DataSet info = sitemap.find("code = '" + code + "' " + (!"Y".equals(SiteConfig.s("join_b2b_yn")) ? " AND code NOT LIKE 'b2b%'" : "") + " AND status != -1 AND site_id = " + siteId);
if(!info.next()) { m.jsError("\ud574\ub2f9 \uc815\ubcf4\uac00 \uc5c6\uc2b5\ub2c8\ub2e4."); return; }

//\ubcc0\uc218
boolean changed = m.isPost() && !"".equals(f.get("parent_cd")) && !info.s("parent_cd").equals(f.get("parent_cd"));
String parentCd = changed ? f.get("parent_cd") : info.s("parent_cd");

//\uc815\ubcf4-\uc0c1\uc704
DataSet pinfo = sitemap.find("code = '" + parentCd + "' AND status != -1 AND site_id = " + siteId + "");
boolean isNext = pinfo.next();
if(!isNext) pinfo.addRow();

int maxSort = isNext ?
	sitemap.findCount("site_id = " + siteId + " AND status != -1 AND parent_cd = '" + pinfo.s("code") + "' AND depth = " + (pinfo.i("depth") + 1))
	: sitemap.findCount("site_id = " + siteId + " AND status != -1 AND depth = 1");

//\uc21c\uc11c
DataSet sortList = new DataSet();
for(int i = 0; i < maxSort; i++) {
	sortList.addRow();
	sortList.put("idx", i+1);
}

//\ud3fc\uccb4\ud06c
if(1 == siteId) f.addElement("default_yn", info.s("default_yn"), "hname:'\uae30\ubcf8\uba54\ub274\uc5ec\ubd80', required:'Y'");
f.addElement("menu_nm", info.s("menu_nm"), "hname:'\uba54\ub274\uba85', required:'Y'");
//f.addElement("layout", info.s("layout"), "hname:'\ub808\uc774\uc544\uc6c3'");
//f.addElement("module", info.s("module"), "hname:'\ub9c1\ud06c\ubaa8\ub4c8\uc885\ub958'");
//f.addElement("module_id", info.s("module_id"), "hname:'\ub9c1\ud06c\ubaa8\ub4c8\uc544\uc774\ub514'");
//f.addElement("module_nm", sitemap.getModuleNm(info.s("module"), info.i("module_id")), "hname:'\ub9c1\ud06c\ubaa8\ub4c8\uba85'");
f.addElement("link", info.s("link"), "hname:'\ub9c1\ud06c\uc8fc\uc18c'");
f.addElement("target", info.s("target"), "hname:'\ud0c0\uac9f'");
f.addElement("sort", info.i("sort"), "hname:'\uc21c\uc11c', required:'Y', option:'number'");
f.addElement("display_type", info.s("display_type"), "hname:'\ub178\ucd9c\uc720\ud615', required:'Y'");
f.addElement("display_yn", info.s("display_yn"), "hname:'\ub178\ucd9c\uc5ec\ubd80', required:'Y'");
f.addElement("status", info.s("status"), "hname:'\uc0c1\ud0dc', required:'Y'");

//\uc218\uc815
if(m.isPost() && f.validate()) {

	//\ub808\uc774\uc544\uc6c3\ud655\uc778
	//if(0 == pinfo.i("depth") && !info.s("menu_nm").equals(f.get("menu_nm"))) {
	if(0 == pinfo.i("depth")) {
		boolean writeBlock = false;

		String layoutPath = docRoot + "/html/layout/layout_" + info.s("code") + ".html";
		String templatePath = docRoot + "/html/layout/template_layout.html";
		File layoutFile = new File(layoutPath);
		File templateFile = new File(templatePath);
		if(!layoutFile.exists()) {
			//\ud30c\uc77c\uc5c6\uc74c
			writeBlock = true;
		} else {
			//\ud30c\uc77c\uc788\uc74c
			FileInputStream fis = new FileInputStream(layoutFile);
			Reader reader = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			if("<!--#TPL-->".equals(br.readLine())) writeBlock = true;
			br.close();
		}

		//\ucc98\ub9ac-\ub808\uc774\uc544\uc6c3\ud30c\uc77c
		if(writeBlock) {
			//\ubcf5\uc0ac-\ud15c\ud50c\ub9bf
			if(!templateFile.exists()) {
				if(!copyFile(Config.getTplRoot() + "/layout/template_layout.html", templatePath)) {
					m.jsError("\ud15c\ud50c\ub9bf\ud30c\uc77c\uc744 \ubcf5\uc0ac\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.");	
				}
			}

			//\uc77d\uae30-\ud15c\ud50c\ub9bf
			String line = "";
			StringBuffer sb = new StringBuffer();
			FileInputStream fis = new FileInputStream(templateFile);
			Reader reader = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			while((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			//\uc4f0\uae30-\ub808\uc774\uc544\uc6c3\ud30c\uc77c
			FileWriter fw = new FileWriter(layoutPath, false);
			fw.write(m.replace(m.replace(sb.toString(), "{code}", info.s("code")), "{menu_nm}", f.get("menu_nm")));
			fw.close();
		}
		
		//\ucc98\ub9ac-\ubc30\ub108
		DataSet binfo = banner.find("banner_type = 'sub_" + info.s("code") + "' AND site_id = " + siteId + " AND status != -1");
		if(!binfo.next()) {
			//\ud30c\uc77c\ubcf5\uc0ac
			if(!copyFile(Config.getTplRoot() + "/images/sub/sub1.jpg", m.getUploadPath("subvisual_" + sysNow + ".jpg"))) {
				m.jsError("\uc11c\ube0c\ube44\uc8fc\uc5bc \ud30c\uc77c\uc744 \ubcf5\uc0ac\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.");	
			}

			//\ub4f1\ub85d
			banner.item("site_id", siteId);
			banner.item("banner_type", "sub_" + info.s("code"));
			banner.item("banner_nm", "\uc11c\ube0c\ube44\uc8fc\uc5bc - " + f.get("menu_nm"));
			banner.item("banner_text", ""); //f.get("menu_nm")
			banner.item("link", "");
			banner.item("target", "_self");
			banner.item("width", 0);
			banner.item("height", 0);
			banner.item("sort", 1);
			banner.item("banner_file", "subvisual_" + sysNow + ".jpg");
			banner.item("reg_date", m.time("yyyyMMddHHmmss"));
			banner.item("status", f.getInt("status"));

			if(!banner.insert()) {
				m.jsAlert("\uc11c\ube0c\ube44\uc8fc\uc5bc\uc744 \ub4f1\ub85d\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.");
				return;
			}
		}

		//\uc218\uc815
		banner.item("banner_nm", "\uc11c\ube0c\ube44\uc8fc\uc5bc - " + f.get("menu_nm"));
		//banner.item("banner_text", f.get("menu_nm"));
		if(1 > binfo.i("status")) banner.item("status", 1);
		if(!banner.update("id = " + binfo.i("id") + " AND site_id = " + siteId)) { m.jsError("\uc11c\ube0c\ube44\uc8fc\uc5bc\uc744 \uc218\uc815\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4."); return; }
	}

	//sitemap.item("module", f.get("module"));
	//sitemap.item("module_id", f.getInt("module_id"));
	if(1 == siteId) sitemap.item("default_yn", f.get("default_yn"));
	sitemap.item("parent_cd", parentCd);
	sitemap.item("menu_nm", f.get("menu_nm"));
	//sitemap.item("layout", f.get("layout"));
	sitemap.item("target", f.get("target"));
	sitemap.item("link", f.get("link"));
	sitemap.item("depth", pinfo.i("depth") + 1);
	sitemap.item("sort", f.getInt("sort"));
	sitemap.item("display_type", f.get("display_type", "A"));
	sitemap.item("display_yn", f.get("display_yn", "N"));
	sitemap.item("status", f.getInt("status"));

	if(!sitemap.update("code = '" + code + "' AND site_id = " + siteId)) { m.jsError("\uc218\uc815\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4."); return; }

	if(changed) { // \ubd80\ubaa8\uac00 \ubcc0\uacbd \ub418\uc5c8\uc744 \uacbd\uc6b0
		/*
		int cdepth = pinfo.i("depth") + 1 - info.i("depth");
		if(cdepth != 0) {
			sitemap.execute("UPDATE " + sitemap.table + " SET depth = depth + (" + cdepth + ") WHERE code IN (" + sitemap.getSubCodes(code) + ") AND site_id = " + siteId);
		}
		*/
		// \uc774\ub3d9\ub41c \uc704\uce58\ub97c \ub2e4\uc2dc \uc815\ub82c\ud55c\ub2e4.
		sitemap.sortDepth(code, f.getInt("sort"), maxSort + 1);
		// \uc774\ub3d9\uc804 \uc704\uce58\ub97c \uc815\ub82c\ud55c\ub2e4.
		sitemap.autoSort(info.i("depth"), info.s("parent_cd"));
	} else {
		// \ud574\ub2f9 \uc704\uce58\ub9cc \uc815\ub82c\ud55c\ub2e4.
		sitemap.sortDepth(code, f.getInt("sort"), info.i("sort"));
	}

	//\ud398\uc774\uc9c0 \ud30c\uc77c \uc0dd\uc131
	//sitemap.createFile(f.get("link"), ""+id);

	m.js("parent.left.location.href='sitemap_tree.jsp?" + m.qs() + "&scode=" + code + "';");
	m.jsReplace("sitemap_modify.jsp?" + m.qs());
	return;

}

//\uc0c1\uc704\ucf54\ub4dc \uba85
DataSet menus = sitemap.getList();
String pnames = sitemap.getTreeNames(code);
info.put("parent_name", "".equals(pnames) ? "-" : pnames);

//\ud398\uc774\uc9c0 \ucd9c\ub825
p.setLayout("blank");
p.setBody("sitemap.sitemap_insert");
p.setVar("query", m.qs());
p.setVar("list_query", m.qs("code"));
p.setVar("form_script", f.getScript());

p.setVar("modify", true);
p.setVar(info);

p.setVar("pinfo", pinfo);
p.setLoop("sorts", sortList);
p.setLoop("layout_list", sitemap.getLayouts(docRoot + "/html/layout"));
//p.setLoop("modules", m.arr2loop(sitemap.modules));

p.setLoop("display_types", m.arr2loop(sitemap.displayTypes));
p.setLoop("display_yn", m.arr2loop(sitemap.displayYn));
p.setLoop("status_list", m.arr2loop(sitemap.statusList));

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/sitemap/sitemap_modify.jsp"), -3419307026164075722L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/sitemap/init.jsp"), -5256010403917648321L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/init.jsp"), -7592216907510913785L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
}
