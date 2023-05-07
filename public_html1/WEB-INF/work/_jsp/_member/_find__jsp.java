/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._member;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _find__jsp extends com.caucho.jsp.JavaPage
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


      

String ch = "member";

String sslDomain = request.getServerName().indexOf(".malgn.co.kr") > 0 ? "ssl.malgn.co.kr" : "ssl.malgnlms.com";
boolean isSSL = "https".equals(request.getScheme()) && sslDomain.equals(request.getServerName()) && !"".equals(f.get("domain"));

if(siteinfo.b("ssl_yn")) {
	sslDomain = siteinfo.s("domain");
	isSSL = false;
}

//if("edu.kuca.kr".equals(f.get("domain"))) m.js("try { console.log(\"a - " + isSSL + " / " + siteinfo.i("id") + " / " + siteinfo.s("domain") + "\"); } catch {}");
if(isSSL) {
	//Site.remove(f.get("domain"));
	siteinfo = Site.getSiteInfo(f.get("domain"));
	if("".equals(siteinfo.s("doc_root"))) { m.jsError(_message.get("alert.site.nodata")); return; }
	siteId = siteinfo.i("id");
	
	SiteConfig = new SiteConfigDao(siteinfo.i("id"));

	mSession.put("id", f.get("session_id"));
}
p.setVar("SSL_DOMAIN", sslDomain);


      

//\ub85c\uadf8\uc778
if(userId != 0) { m.redirect("../main/index.jsp"); return; }

//SSO
if(siteinfo.b("sso_yn") && !"".equals(siteinfo.s("sso_url"))) {
	String url = siteinfo.s("sso_url") + ( siteinfo.s("sso_url").indexOf("?") > -1 ?  "&mode=find" : "?mode=find");
	m.redirect(url);
	return;
}

//\ud3fc\uc785\ub825
int udid = m.ri("udid");

//\uc815\ubcf4-\uc0ac\uc774\ud2b8\uc124\uc815
DataSet siteconfig = SiteConfig.getArr(new String[] {"ktalk_"});

//\uac1d\uccb4
UserDao user = new UserDao();
FileDao file = new FileDao();
UserDeptDao userDept = new UserDeptDao();
MailDao mail = new MailDao();
MailUserDao mailUser = new MailUserDao();
SmsDao sms = new SmsDao(siteId);
SmsTemplateDao smsTemplate = new SmsTemplateDao(siteId);
if(siteinfo.b("sms_yn")) sms.setAccount(siteinfo.s("sms_id"), siteinfo.s("sms_pw"));
KtalkDao ktalk = new KtalkDao(siteId);
if("Y".equals(siteconfig.s("ktalk_yn"))) ktalk.setAccount(siteinfo.s("sms_id"), siteinfo.s("sms_pw"), siteconfig.s("ktalk_sender_key"));
KtalkTemplateDao ktalkTemplate = new KtalkTemplateDao(siteId);

//\uc815\ubcf4-B2B
boolean isB2B = false;
DataSet B2Binfo = new DataSet();
if(0 < udid) {
	B2Binfo = userDept.query(
		" SELECT a.id, a.b2b_nm, f.filename b2b_file "
		+ " FROM " + userDept.table + " a " 
		+ " LEFT JOIN " + file.table + " f ON f.module = 'dept' AND f.module_id = a.id AND f.status = 1 "
		+ " WHERE a.id = ? AND a.site_id = ? AND a.status = 1 "
		, new Integer[] {udid, siteId}
	);
	if(B2Binfo.next()) {
		isB2B = true;
		B2Binfo.put("b2b_file_url", m.getUploadUrl(B2Binfo.s("b2b_file")));
	}
}

//\ubcc0\uc218
String now = m.time("yyyyMMddHHmmss");

//\ucc98\ub9ac
if(m.isPost()) {
	String lid = f.get("login_id");
	String name = f.get("user_nm");
	String email = f.get("email1") + "@" + f.get("email2");
	String mobile = f.get("mobile1") + "-" + f.get("mobile2") + "-" + f.get("mobile3");

	if("find_id".equals(m.rs("mode"))) {
		DataSet uinfo = user.find("user_nm = ? AND email = ? AND site_id = " + siteId + "", new Object[] {name, email}, 1);
		if(!uinfo.next()) { m.jsAlert(_message.get("alert.member.not_accurate"));	return; }

		m.jsAlert(_message.get("alert.member.find.id", new String[] {"name=>" + name, "login_id=>" + uinfo.s("login_id")}));
		return;

	} else if("email_authno".equals(m.rs("mode"))) {

		//\uc815\ubcf4
		DataSet uinfo = user.find("user_nm = ? AND login_id = ? AND email = ? AND site_id = " + siteId + "", new Object[] {name, lid, email}, 1);
		if(!uinfo.next()) { m.jsAlert(_message.get("alert.member.not_accurate"));	return; }

		//\uba54\uc77c
		if(mail.isMail(email)) {

			//\uc81c\ud55c-3\ubd84
			int gapLimit = 3;
			int gapMinutes = !"".equals(m.getSession("EMAIL_SENDDATE")) ? m.diffDate("I", m.getSession("EMAIL_SENDDATE"), now) : 999;
			if(gapMinutes < gapLimit) {
				m.jsAlert(_message.get("alert.member.find.remain", new String[] {"gapMinutes=>" + gapMinutes, "remain=>" + (gapLimit - gapMinutes)})); return;
			}

			//\ubc1c\uc1a1
			int authNo = m.getRandInt(123456, 864198);
			p.setVar("auth_no", authNo + "");
			mail.send(siteinfo, uinfo, "findpw_authno", p);

			//\uc138\uc158
			m.setSession("SITE_ID", siteId);
			m.setSession("LOGIN_ID", lid);
			m.setSession("EMAIL", email);
			m.setSession("USER_NM", name);
			m.setSession("AUTH_NO", authNo);
			m.setSession("EMAIL_SENDDATE", now);

			m.jsAlert(_message.get("alert.member.find.authno_to_email"));
			return;
		} else {
			m.jsAlert(_message.get("alert.member.unvalid_email"));
			return;
		}

	} else if("email_passwd".equals(m.rs("mode"))) {

		String authNo = f.get("auth_no");

		if(!(""+siteId).equals(m.getSession("SITE_ID"))
			|| !lid.equals(m.getSession("LOGIN_ID"))
			|| !email.equals(m.getSession("EMAIL"))
			|| !name.equals(m.getSession("USER_NM"))
		) {
			m.jsAlert(_message.get("alert.common.abnormal_access")); return;
		}

		//\uc815\ubcf4
		DataSet uinfo = user.find("user_nm = ? AND login_id = ? AND email = ? AND site_id = " + siteId + "", new Object[] {name, lid, email}, 1);
		if(!uinfo.next()) { m.jsAlert(_message.get("alert.member.not_accurate"));	return; }

		//\uc778\uc99d\ubc88\ud638
		if(!authNo.equals(m.getSession("AUTH_NO"))) {
			m.jsAlert(_message.get("alert.member.find.incorrect_authno")); return;
		}

		//\uba54\uc77c
		if(mail.isMail(email)) {

			String newPasswd = m.getUniqId();

			//\uac31\uc2e0
			if(-1 == user.execute("UPDATE " + user.table + " SET passwd = '" + m.encrypt(newPasswd,"SHA-256") + "', fail_cnt = 0 WHERE id = " + uinfo.i("id") + "")) {
				m.jsAlert(_message.get("alert.common.error_modify")); return;
			}

			//\ubc1c\uc1a1
			p.setVar("new_passwd", newPasswd);
			mail.send(siteinfo, uinfo, "findpw_newpw", p);

			//\uc138\uc158
			m.setSession("SITE_ID", "");
			m.setSession("LOGIN_ID", "");
			m.setSession("EMAIL", "");
			m.setSession("USER_NM", "");
			m.setSession("AUTH_NO", "");

			m.jsAlert(_message.get("alert.member.find.info_to_email"));
			m.jsReplace((isSSL ? "http://" + f.get("domain") : "") + "/member/login.jsp", "parent");
			return;
		} else {
			m.jsAlert(_message.get("alert.member.unvalid_email"));
			return;
		}

	} else if("sms_authno".equals(m.rs("mode"))) {

		if(!siteinfo.b("sms_yn")) { m.jsAlert(_message.get("alert.sms.noservice")); return; }

		String target = m.rs("target");
		//boolean passwordBlock = "password".equals(target);
		boolean passwordBlock = !"id".equals(target);

		//\uc815\ubcf4
		DataSet uinfo =
			passwordBlock ? user.find("user_nm = ? AND login_id = ? AND mobile = ? AND site_id = " + siteId + "", new Object[] {name, lid, SimpleAES.encrypt(mobile)}, 1)
			: user.find("user_nm = ? AND mobile = ? AND site_id = " + siteId + "", new Object[] {name, SimpleAES.encrypt(mobile)}, 1);
		if(!uinfo.next()) { m.jsAlert(_message.get("alert.member.not_accurate")); return; }
		if(passwordBlock && !lid.equals(uinfo.s("login_id"))) { m.jsAlert(_message.get("alert.member.not_accurate")); return; }

		//SMS
		if(sms.isMobile(mobile)) {

			//\uc81c\ud55c-3\ubd84
			int gapLimit = 3;
			int gapMinutes = !"".equals(m.getSession("SMS_SENDDATE_" + target)) ? m.diffDate("I", m.getSession("SMS_SENDDATE_" + target), now) : 999;
			if(gapMinutes < gapLimit) {
				m.jsAlert(_message.get("alert.member.find.remain", new String[] {"gapMinutes=>" + gapMinutes, "remain=>" + (gapLimit - gapMinutes)})); return;
			}

			//\ubcc0\uc218
			int authNo = m.getRandInt(123456, 864198);
			
			p.setVar("auth_no", authNo);
			if("Y".equals(siteconfig.s("ktalk_yn"))) {
				ktalkTemplate.sendKtalk(siteinfo, uinfo, "findpw_authno", p);
			} else {
				smsTemplate.sendSms(siteinfo, uinfo, "findpw_authno", p);
			}

			//\uc138\uc158
			m.setSession("SITE_ID", siteId);
			m.setSession("LOGIN_ID", lid);
			m.setSession("MOBILE", mobile);
			m.setSession("USER_NM", name);
			m.setSession("AUTH_NO_" + target, authNo);
			m.setSession("SMS_SENDDATE_" + target, now);

			m.jsAlert(_message.get("alert.member.find.authno_to_mobile"));
			return;
		} else {
			m.jsAlert(_message.get("alert.member.unvalid_mobile"));
			return;
		}

	} else if("sms_passwd".equals(m.rs("mode"))) {

		if(!siteinfo.b("sms_yn")) { m.jsAlert(_message.get("alert.sms.noservice")); return; }

		String authNo = f.get("auth_no");
		String target = m.rs("target");
		//boolean passwordBlock = "password".equals(target);
		boolean passwordBlock = !"id".equals(target);

		if(!(""+siteId).equals(m.getSession("SITE_ID"))
			|| (passwordBlock && !lid.equals(m.getSession("LOGIN_ID")))
			|| !mobile.equals(m.getSession("MOBILE"))
			|| !name.equals(m.getSession("USER_NM"))
		) {
			m.jsAlert(_message.get("alert.common.abnormal_access")); return;
		}

		//\uc815\ubcf4
		DataSet uinfo = 
			passwordBlock ? user.find("user_nm = ? AND login_id = ? AND mobile = ? AND site_id = " + siteId + "", new Object[] {name, lid, SimpleAES.encrypt(mobile)}, 1)
			: user.find("user_nm = ? AND mobile = ? AND site_id = " + siteId + "", new Object[] {name, SimpleAES.encrypt(mobile)}, 1);
		if(!uinfo.next()) { m.jsAlert(_message.get("alert.member.not_accurate"));	return; }
		if(passwordBlock && !lid.equals(uinfo.s("login_id"))) { m.jsAlert(_message.get("alert.member.not_accurate"));	return; }

		//\uc778\uc99d\ubc88\ud638
		if(!authNo.equals(m.getSession("AUTH_NO_" + target))) {
			m.jsAlert(_message.get("alert.member.find.incorrect_authno")); return;
		}

		//SMS
		if(sms.isMobile(mobile)) {

			String newPasswd = m.getUniqId(8);

			//\uac31\uc2e0
			if(passwordBlock && -1 == user.execute("UPDATE " + user.table + " SET passwd = '" + m.encrypt(newPasswd,"SHA-256") + "', fail_cnt = 0 WHERE id = " + uinfo.i("id") + "")) {
				m.jsAlert(_message.get("alert.common.error_modify")); return;
			}

			p.setVar("login_id", uinfo.s("login_id"));
			p.setVar("new_passwd", newPasswd);
			if("Y".equals(siteconfig.s("ktalk_yn"))) {
				ktalkTemplate.sendKtalk(siteinfo, uinfo, passwordBlock ? "findpw_newpw" : "findid", p);
			} else {
				smsTemplate.sendSms(siteinfo, uinfo, passwordBlock ? "findpw_newpw" : "findid", p);
			}

			m.setSession("SITE_ID", "");
			m.setSession("LOGIN_ID", "");
			m.setSession("MOBILE", "");
			m.setSession("USER_NM", "");
			m.setSession("AUTH_NO_" + target, "");

			m.jsAlert(_message.get("alert.member.find.info_to_mobile"));
			m.jsReplace("../member/login.jsp", "parent");
			return;
		} else {
			m.jsAlert(_message.get("alert.member.unvalid_mobile"));
			return;
		}
	}

}

//\ucd9c\ub825
p.setLayout(ch);
p.setBody("member.find");
p.setVar("p_title", "\ube44\ubc00\ubc88\ud638\ucc3e\uae30");
p.setVar("query", m.qs());

p.setVar("sms_block", siteinfo.b("sms_yn"));
p.setVar("close_block", siteinfo.b("close_yn") || isB2B);
p.setVar("domain", request.getServerName());

p.setVar("is_b2b", isB2B);
p.setVar("b2binfo", B2Binfo);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/find.jsp"), -4535427174760969518L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/init.jsp"), 9054591303382699975L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("init.jsp"), 6326164006358099841L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
}
