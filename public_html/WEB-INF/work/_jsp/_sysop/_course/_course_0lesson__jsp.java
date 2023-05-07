/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._sysop._course;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _course_0lesson__jsp extends com.caucho.jsp.JavaPage
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
      
//
//\ubcc0\uc218
String docRoot = Config.getDocRoot();
String tplRoot = docRoot + "/sysop/html";

//\uac1d\uccb4
Malgn m = new Malgn(request, response, out);

Form f = new Form("form1");
f.setRequest(request);

Page p = new Page(tplRoot);
p.setRequest(request);
p.setPageContext(pageContext);
p.setWriter(out);

// \uae30\ubcf8 \uad00\ub9ac\uc790 \uc815\ubcf4
int userId = 0;
String loginId = "";
String userName = "";
String userType = "";

// \ub85c\uadf8\uc778 \uc5ec\ubd80 \uccb4\ud06c
Auth auth = new Auth(request, response);
auth.loginURL = "/sysop/main/login.jsp";
auth.keyName = "ENTER2022BO";
if(auth.isValid()) {
    userId = m.parseInt(auth.getString("ID"));
    loginId = auth.getString("LOGINID");
    userName = auth.getString("NAME");
    userType = auth.getString("TYPE");

} else {
    if(request.getRequestURI().indexOf("/main/login.jsp") == -1) { //\ub85c\uadf8\uc778 \ud398\uc774\uc9c0\uba74 \uc81c\uc678
        m.jsReplace(auth.loginURL, "top");
        return;
    }
}

p.setVar("SYS_TITLE", "[\uad00\ub9ac\uc790] enter \uc624\ud508 \ub354 \uc774\ub7ec\ub2dd \uc0ac\uc774\ud2b8");


      

String ch = "sysop";


      

//\uae30\ubcf8\ud0a4
int cid = m.ri("cid");
if(cid == 0) { m.jsError("\uae30\ubcf8\ud0a4\ub294 \ubc18\ub4dc\uc2dc \uc9c0\uc815\ud574\uc57c \ud569\ub2c8\ub2e4."); return; }

//\uac1d\uccb4
CourseDao course = new CourseDao();
LessonDao lesson = new LessonDao();


//\uc815\ubcf4-\uacfc\uc815
DataSet cinfo = course.find("id = " + cid + " AND status != -1");
if(!cinfo.next()) { m.jsError("\ud574\ub2f9 \uc815\ubcf4\uac00 \uc5c6\uc2b5\ub2c8\ub2e4."); return; }


//\uc0ad\uc81c
if("del".equals(m.rs("mode"))) {

    if("".equals(f.get("idx"))) { m.jsError("\uae30\ubcf8\ud0a4\ub294 \ubc18\ub4dc\uc2dc \uc9c0\uc815\ud574\uc57c \ud569\ub2c8\ub2e4."); return; }

    lesson.item("status", -1);
    if(!lesson.update("id IN (" + f.get("idx") + ")")) { m.jsAlert("\uc0ad\uc81c\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4."); return; }

    DataSet list = lesson.find("course_id = " + cid + " AND status = 1 ORDER BY sort ASC ");
    
    int sort = 1;
    while(list.next()) {
        lesson.execute("UPDATE " + lesson.table + " SET sort = " + sort + " WHERE id = " + list.i("id") );
        sort++;
    }
    //\uc774\ub3d9
    m.jsAlert("\uc0ad\uc81c \uc644\ub8cc\ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
    m.jsReplace("course_lesson.jsp?" + m.qs("mode, idx"));
    return;
}

//\uc218\uc815
if(m.isPost() && f.validate()) {

    //\ucc28\uc2dc sort \uc218\uc815
    if(f.getArr("lesson_id") != null) {
        int sort = 1;

        for(int i = 0; i < f.getArr("lesson_id").length; i++) {
            lesson.item("sort", sort++);
            if(!lesson.update("id = " + f.getArr("lesson_id")[i])) { }
        }
    }

    //\uc774\ub3d9
    m.jsAlert("\uc218\uc815\ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
    m.jsReplace("course_lesson.jsp?" + m.qs(), "parent");
    return;
}

DataSet list = lesson.find("course_id = " + cid + " AND status != -1 ORDER BY sort ASC ");
if(!list.next()) { m.log("course_lesson", "lesson not found"); }


//\ucd9c\ub825
p.setLayout(ch);
p.setBody("course.course_lesson");
p.setVar("p_title", "\uac15\uc758\ubaa9\ucc28");
p.setVar("query", m.qs());
p.setVar("list_query", m.qs("cid"));
p.setVar("form_script", f.getScript());

p.setLoop("list", list);
p.setVar("list_total", list.size());
p.setVar("course", cinfo);
p.setVar("tab_lesson", "current");

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/course/course_lesson.jsp"), 3137127579620672860L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/course/init.jsp"), 6133038957141066510L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/init.jsp"), -2253010914700824916L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
}
