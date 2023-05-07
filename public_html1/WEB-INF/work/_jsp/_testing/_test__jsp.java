/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._testing;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _test__jsp extends com.caucho.jsp.JavaPage
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
      

    String tplRoot = Config.getDocRoot() + "/testing/html";
    String docRoot = Config.getDocRoot();

    Malgn m = new Malgn(request, response, out);

    Form f = new Form();
    f.setRequest(request);

    Page p = new Page(tplRoot);
    p.setRequest(request);
    p.setWriter(out);
    p.setPageContext(pageContext);


    String userId = null;
    Auth auth = new Auth(request, response);
    if(auth.isValid()) {
        userId = auth.getString("user_id");
    }


      

    //\uac1d\uccb4
TestDao test = new TestDao();

test.setDebug(out);

DataSet users = test.find("user_nm = 'hong'","user_nm", 5);
DataSet info = test.query("SELECT * FROM TB_TEST");
if(!info.next()) { // next() \ub97c \ud1b5\ud574 \ud3ec\uc778\ud130\ub97c \uc774\ub3d9\uc2dc\ucf1c \uccab\ubc88\uc9f8 \ub370\uc774\ud130\uac00 \uc874\uc7ac\ud558\ub294\uc9c0 \ud655\uc778\ud55c\ub2e4.
    m.jsError("\ud14c\uc2a4\ud2b8\uc815\ubcf4\ub97c \ucc3e\uc744 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4."); // alert \ud55c \ud6c4 history.go(-1) \uc774\uc804\ud398\uc774\uc9c0\ub85c
    return; // \ub9ac\ud134\uc744 \ud558\uc9c0 \uc54a\uc73c\uba74 \uc544\ub798 \ub0b4\uc6a9\uc774 \uc2e4\ud589\ub420 \uc218 \uc788\ub2e4.
}
//m.p(info);

//test.item("user_nm", "money");
//test.item("email", "igd@gmail.com");
//test.insert();
int inputNum = test.execute("INSERT INTO TB_TEST (user_nm, email) VALUES ('GD', 'honghong@gmail.com')");// \uc601\ud5a5 \ubc1b\uc744 \ub808\ucf54\ub4dc \uac1c\uc218\ub97c \ub9ac\ud134


//DB \uc218\uc815
//test.item("email", "test@gmail.com");
//test.update("user_nm = 'GD'");
int updateNum = test.execute("UPDATE TB_TEST SET email = 'igd@gmail.com' WHERE id = '34' "); // 24\ubc88\uc9f8\ub098 \ub458\uc911 \ud558\ub098 \uc0ac\uc6a9

//DB \uc0ad\uc81c
//    test.delete("user_nm = 'hong'");
//    user.execute("DELETE FROM TB_TEST WHERE user_nm = '\ud64d\uae38\ub3d9'"); // 28\ubc88\uc9f8\ub098 \ub458\uc911 \ud558\ub098 \uc0ac\uc6a9


p.setLayout("main");
p.setBody("main.index");
p.setVar(info);
p.setLoop("users", users);//users\ub77c\ub294 DataSet\uac1d\uccb4\uac00 \ubaa9\ub85d\uc774\ubbc0\ub85c setloop\uba54\uc18c\ub4dc\ub97c \uc774\uc6a9\ud574\uc11c \ub370\uc774\ud130\ub97c \ub118\uaca8\uc900\ub2e4. html\uc5d0\uc11c loop\uc9c0\uc2dc\uc790\ub97c \ud1b5\ud574 \ubaa9\ub85d\uc744 \ucd9c\ub825\ud558\ub294 html\uc744 \uc0dd\uc131
p.setVar("input_num", inputNum);
p.setVar("update_num", updateNum);
p.display();



      out.write(_jsp_string0, 0, _jsp_string0.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("testing/test.jsp"), 7842255150706392178L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("testing/init.jsp"), -605037953067765338L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\r\n".toCharArray();
  }
}
