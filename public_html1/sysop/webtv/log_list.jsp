<%@ page contentType="text/html; charset=utf-8" %><%@ include file="../init.jsp" %><%

//접근권한
if(!(Menu.accessible(138, userId, userKind))) { m.jsError("접근 권한이 없습니다."); return; }

//객체
WebtvDao webtv = new WebtvDao();
WebtvLogDao webtvLog = new WebtvLogDao();
UserDao user = new UserDao(isBlindUser);
UserDeptDao userDept = new UserDeptDao();
LmCategoryDao category = new LmCategoryDao("webtv");

//카테고리
DataSet categories = category.getList(siteId);

//정보
DataSet info;
info = webtv.find("status != -1 AND site_id = " + siteId);
if(!info.next()) { m.jsAlert("해당 정보가 없습니다."); return; }
info.put("open_date_conv", m.time("yyyy-MM-dd", info.s("open_date")));
info.put("open_hour", m.time("HH", info.s("open_date")));
info.put("open_min", m.time("mm", info.s("open_date")));

//폼체크
f.addElement("s_user_kind", null, null);
f.addElement("s_dept", null, null);
f.addElement("s_start_date", null, null);
f.addElement("s_end_date", null, null);
f.addElement("s_field", null, null);
f.addElement("s_keyword", null, null);
f.addElement("s_listnum", null, null);
f.addElement("s_list_mode", null, null);

//목록
ListManager lm = new ListManager();
//lm.d(out);
lm.setRequest(request);
lm.setListNum("excel".equals(m.rs("mode")) ? 20000 : 20);
lm.setTable(
    webtvLog.table + " a "
        + " LEFT JOIN " + webtv.table + " w ON a.webtv_id = w.id AND w.site_id = " + siteId + " AND w.status != -1"
        + " INNER JOIN " + user.table + " u ON a.user_id = u.id AND u.site_id = " + siteId + " AND u.status != -1 "
        + " LEFT JOIN " + userDept.table + " d ON u.dept_id = d.id "
);
lm.setFields(
    " w.webtv_nm, w.subtitle, w.category_id, u.*, u.id user_id, a.reg_date log_reg_date, d.dept_nm"
);
lm.addWhere("a.site_id = " + siteId);
lm.addWhere("u.dept_id IN (" + userDept.getSubIdx(siteId, userDeptId) + ")");
if(0 < f.getInt("s_dept")) lm.addWhere("u.dept_id IN (" + userDept.getSubIdx(siteId, f.getInt("s_dept")) + ")");
if(!"".equals(f.get("s_start_date"))) lm.addWhere("a.reg_date >= '" + m.time("yyyyMMdd000000", f.get("s_start_date")) + "'");
if(!"".equals(f.get("s_end_date"))) lm.addWhere("a.reg_date <= '" + m.time("yyyyMMdd235959", f.get("s_end_date")) + "'");
lm.addSearch("u.user_kind", f.get("s_user_kind"));
if(!"".equals(f.get("s_field"))) lm.addSearch(f.get("s_field"), f.get("s_keyword"), "LIKE");
else lm.addSearch("w.webtv_nm, u.login_id, u.user_nm, u.email, u.etc1, u.etc2, u.etc3, u.etc4, u.etc5", f.get("s_keyword"), "LIKE");
lm.setOrderBy(!"".equals(m.rs("ord")) ? m.rs("ord") : "a.reg_date DESC");

//포멧팅
DataSet list = lm.getDataSet();
while(list.next()) {
    if(0 < list.i("dept_id")) {
        list.put("dept_nm_conv", userDept.getNames(list.i("dept_id")));
    } else {
        list.put("dept_nm", "[미소속]");
        list.put("dept_nm_conv", "[미소속]");
    }
    list.put("reg_date_conv", m.time("yyyy.MM.dd HH:mm", list.s("log_reg_date")));
    list.put("category_nm", category.getTreeNames(list.i("category_id")));
    list.put("webtv_nm_conv", m.cutString(list.s("webtv_nm"), 50));
    list.put("subtitle_conv", m.stripTags(list.s("subtitle")));
    user.maskInfo(list);
}

//기록-개인정보조회
if("".equals(m.rs("mode")) && list.size() > 0 && !isBlindUser) _log.add("L", Menu.menuNm, list.size(), "이러닝 운영", list);

//엑셀
if("excel".equals(m.rs("mode"))) {
    if(list.size() > 0 && !isBlindUser) _log.add("E", Menu.menuNm, list.size(), "이러닝 운영", list);

    //if(1 > list.size()) { m.jsError("출력할 데이터가 없습니다."); return; }
    //ExcelWriter ex = new ExcelWriter(response, "방송시청이력-" + wid + "-" + list.size() + "건-" + info.s("webtv_nm") + "(" + m.time("yyyy-MM-dd") + ").xls");
    ExcelWriter ex = new ExcelWriter(response, "방송시청이력-" + info.s("webtv_nm") + "(" + m.time("yyyy-MM-dd") + ").xls");
    ex.setData(list, new String[] { "__ord=>No", "dept_nm_conv=>소속", "login_id=>로그인아이디", "user_nm=>회원명", "reg_date_conv=>시청일자" }, "방송시청이력-" + info.s("webtv_nm") + "(" + m.time("yyyy-MM-dd") + ")");
    ex.write();
    return;
}

//출력
p.setBody("webtv.log_list");
p.setVar("form_script", f.getScript());

p.setVar(info);
p.setLoop("list", list);
p.setVar("list_total", lm.getTotalString());
p.setVar("pagebar", lm.getPaging());

p.setVar("SITE_CONFIG", SiteConfig.getArr("user_etc_"));
p.setLoop("kinds", m.arr2loop(user.kinds));
p.setLoop("dept_list", userDept.getList(siteId, userKind, userDeptId));
p.setLoop("status_list", m.arr2loop(user.statusList));
p.display();
%>