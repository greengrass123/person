package logicalConduct;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import tool.GetCurrentTime;
import tool.judgeTime;

import jdbc.OperationData;
import jdbc.UserOperation;

import allClasses.AdType;
import allClasses.Administrator;
import allClasses.Pic;
import allClasses.Post;
import allClasses.Unit;
import allClasses.UnitType;
import allClasses.User;

import logicalConduct.TimeProcessThread;

public class AdminManagerLogical extends HttpServlet {
    private String info = "";
    AdminLogic data = null;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        info = request.getParameter("info");
        data = new AdminLogic();
        System.out.println("执行adminManager，传入的信息为：" + info);
        if (info.equals("adminlogin")) {
            this.adminLogin(request, response);// 管理员登陆
            System.out.println("管理员登陆");
        } else if (info.equals("adminloginout")) {
            this.adminLoginOut(request, response);// 管理员退出
        } else if (info.equals("auditInfo")) {
            this.aduitInfo(request, response);// 审核信息
        } else if (info.equals("auditBy")) {
            this.auditBy(request, response);// 信息审核通过
        } else if (info.equals("allBy")) {
            this.allBy(request, response);// 信息审核通过
        } else if (info.equals("auditnoBy")) {
            this.auditnoBy(request, response);// 信息审核不通过
        } else if (info.equals("registerUser")) {
            this.registerUser(request, response);// 注册用户
        } else if (info.equals("showUser")) {
            this.showUser(request, response);// 展示所有用户
        } else if (info.equals("delUser")) {
            this.delUser(request, response);// 删除用户
        } else if (info.equals("delInfo")) {
            this.delInfo(request, response);// 删除信息，就是删除图片
        } else if (info.equals("pasteShow")) {
            this.pasteShow(request, response);// 展示所有张贴栏
        } else if (info.equals("insertPaste")) {
            this.insertPaste(request, response);// 添加张贴栏
        } else if (info.equals("updatePaste")) {
            this.updatePaste(request, response);// 更改张贴栏
        } else if (info.equals("delPaste")) {
            this.delPaste(request, response);// 删除张贴栏
        } else if (info.equals("unitShow")) {
            this.unitShow(request, response);// 展示单位
        } else if (info.equals("insertUnit")) {
            this.insertUnit(request, response);// 添加单位
        } else if (info.equals("updateUnit")) {
            this.updateUnit(request, response);// 更改单位
        } else if (info.equals("delUnit")) {
            this.delUnit(request, response);// 删除单位
        } else if (info.equals("typeShow")) {
            this.typeShow(request, response);
        } else if (info.equals("insertType")) {
            this.insertType(request, response);
        } else if (info.equals("updateType")) {
            this.updateType(request, response);
        } else if (info.equals("delType")) {
            this.delType(request, response);
        } else if (info.equals("pasteTypeShow")) {
            this.pasteTypeShow(request, response);
        } else if (info.equals("insertPasteType")) {
            this.insertPasteType(request, response);
        } else if (info.equals("updatePasteType")) {
            this.updatePasteType(request, response);
        } else if (info.equals("delPasteType")) {
            this.delPasteType(request, response);
        } else if (info.equals("updateUser")) {
            this.updateUser(request, response);
        } else if (info.equals("changeUser")) {
            this.changeUser(request, response);
        } else if (info.equals("delBatchUser")) {
            this.delBatchUser(request, response);
        } else if (info.equals("delBatchInfo")) {
            this.delBatchInfo(request, response);
        } else if (info.equals("batchAuditBy")) {
            this.batchAuditBy(request, response);
        } else if (info.equals(" TimingProcess")) {
            this.TimingProcess(request, response);
        }else if (info.equals("delAuditBatchInfo")) {
            this.delAuditBatchInfo(request, response);
        }

    }

    // 管理员登陆

    public void adminLogin(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String infomation = "";
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        System.out.println("name=" + name);
        data = new AdminLogic();
        Administrator a = data.get_admin();// 此处只默认有一个管理员

        if (name.equals("") || password.equals("")) {
            infomation = "请输入信息";
        }
        if (a.getName().equals(name)) {
            if (a.getPassword().equals(password)) {
                System.out.println("right...");
                request.getRequestDispatcher("adminManager.jsp").forward(
                        request, response);
                request.getSession().setAttribute("adminInfo", a);
                return;
            } else {
                infomation = "登录失败";
            }
        } else {
            infomation = "登录失败";
        }
        request.setAttribute("infomation", infomation);
        request.getRequestDispatcher("adminLogin.jsp").forward(request,
                response);

    }

    // 管理员退出
    public void adminLoginOut(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        System.out.println("loginout");
        if (request.getSession().getAttribute("adminInfo") != null) {
            request.getSession().invalidate();
        }
        request.getRequestDispatcher("adminLogin.jsp").forward(request,
                response);
    }

    // 点击审核、未审核显示对应状态广告
    public void aduitInfo(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String audit = request.getParameter("audit");
        String adType = request.getParameter("adType");

        String pasteName = request.getParameter("pasteType");
        String adTime = request.getParameter("adTime");

        pasteName = new String(pasteName.getBytes("iso-8859-1"), "UTF-8");
        adTime = new String(adTime.getBytes("iso-8859-1"), "UTF-8");
        // pasteName=URLDecoder.decode(pasteName, "utf-8");
        // adTime=URLDecoder.decode(adTime, "utf-8");
        System.out.println("pasteName----->" + pasteName);
        System.out.println("adTime----->" + adTime);
        data = new AdminLogic();
        List<Pic> noauditlist = new ArrayList(), auditlist = new ArrayList();
        List<Pic> noauditlistOriginal = null;
        List<Pic> auditlistOriginal = null;
        int state = 0;
        System.out.println("000000----->");
        if (audit != null)
            audit = new String(audit.getBytes("iso-8859-1"), "UTF-8");
        // audit=URLDecoder.decode(audit, "utf-8");
        System.out.println("audit----->" + audit);
        adType = new String(adType.getBytes("iso-8859-1"), "UTF-8");
        // adType=URLDecoder.decode(adType, "utf-8");
        System.out.println("adType----->" + adType);
        System.out.println("执行aduitInfo，要显示的图片状态为：" + audit);
        OperationData od = new OperationData();
        if (audit.equals("未审核")) {
          
            state = 0;
            noauditlistOriginal = data.getAuditInfo(state);
            if (adType.equals("所有广告")) {
                noauditlist = noauditlistOriginal;
            } else {
                for (int i = 0; i < noauditlistOriginal.size(); i++)// 按类别展示
                {
                    Pic p = (Pic) noauditlistOriginal.get(i);
                    int picId = p.getPicId();
                    String adTypeName = od.query_adTypeBypicId(picId);
                    System.out.println("adTypeName=" + adTypeName);
                    if (adTypeName.equals(adType)) {
                        System.out.println("----------------->");
                        noauditlist.add(p);
                    }
                }

            }
            if (!pasteName.equals("所有粘贴栏")) // 按粘贴栏展示
            {
                for (int i = 0; i < noauditlist.size(); i++) {
                    String pasteStr = od.query_adPostBypicId(noauditlist.get(i)
                            .getPicId());
                    if (!pasteName.equals(pasteStr)) {
                        System.out.println("删除一个数据----->");
                        noauditlist.remove(i);
                    }
                }
            }
            // 按时间展示
            noauditlist = new judgeTime().adjustTime(adTime, noauditlist);
            System.out.println("size : ----->" + noauditlist.size());

        } else if (audit.equals("已审核")) {
            
            System.out.println("----------------->111");
            state = 1;
            auditlistOriginal = data.getAuditInfo(state);
            System.out.println("----------------->222");
            if (adType.equals("所有广告")) {
                auditlist = auditlistOriginal;
                System.out.println("----------------->333");
            } else {
                for (int i = 0; i < auditlistOriginal.size(); i++) {
                    Pic p = (Pic) auditlistOriginal.get(i);
                    int picId = p.getPicId();
                    String adTypeName = od.query_adTypeBypicId(picId);
                    System.out.println("adTypeName--------------->"
                            + adTypeName);
                    if (adTypeName != null && adTypeName.equals(adType)) {
                        auditlist.add(p);
                    }

                }
            }

            if (!pasteName.equals("所有粘贴栏")) // 按粘贴栏展示
            {
                for (int i = 0; i < auditlist.size(); i++) {
                    String pasteStr = od.query_adPostBypicId(auditlist.get(i)
                            .getPicId());
                    if (!pasteName.equals(pasteStr)) {
                        System.out.println("删除一个数据----->");
                        auditlist.remove(i);
                    }
                }
            }
            // 按时间展示
            auditlist = new judgeTime().adjustTime(adTime, auditlist);

        }
        request.setAttribute("audit", audit);
        request.setAttribute("noauditlist", noauditlist);
        request.setAttribute("auditlist", auditlist);
        request.getRequestDispatcher("adminManager.jsp").forward(request,
                response);

    }

    // 通过审核的图片
    public void auditBy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int adId = Integer.parseInt(request.getParameter("adId"));
        data = new AdminLogic();
        System.out.println("通过审核的广告id为：" + adId);
        boolean flag1 = data.changeState_pic(adId);
        boolean flag2 = data.changeState_ad(adId);
        int state = 0;
        List noauditlist = data.getAuditInfo(state);
        request.setAttribute("noauditlist", noauditlist);
        request.getRequestDispatcher("adminManager.jsp").forward(request,
                response);
    }

    // 批量审查
    public void batchAuditBy(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();

        boolean flag1 = data.BatchchangeState_pic(getIDJsonArray(request,
                response));
        boolean flag2 = data.BatchchangeState_ad(getIDJsonArray(request,
                response));
        int state = 0;
        List noauditlist = data.getAuditInfo(state);
        request.setAttribute("noauditlist", noauditlist);
        request.getRequestDispatcher("adminManager.jsp").forward(request,
                response);
    }

    // 一个公共的获取Jsp页面json数组的方法
    public List<Integer> getIDJsonArray(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("appliction/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String jsonADCheckedID = request.getParameter("jsonADCheckedID");
        List<Integer> AdList = new ArrayList<Integer>();
        JSONArray jsonArray = JSONArray.fromObject(jsonADCheckedID);
        for (int i = 0; i < jsonArray.size(); i++) {
            AdList.add(i, Integer.parseInt((String) jsonArray.get(i)));
            System.out.println(" 广告ID" + (String) jsonArray.get(i));
        }
        return AdList;
    }

    // 将所有的待审核照片通过审核
    public void allBy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        data = new AdminLogic();
        if (data.changeAllState_pic()) {
            System.out.println("改变所有图片状态成功");
        }
        if (data.changeAllState_ad()) {
            System.out.println("改变所有广告状态成功");
            // out.print("<script language="JavaScript"> alert('审核成功') </script>");
        }

        int state = 0;
        List noauditlist = data.getAuditInfo(state);
        request.setAttribute("noauditlist", noauditlist);
        request.getRequestDispatcher("adminManager.jsp").forward(request,
                response);
    }

    public void auditnoBy(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        int adId = Integer.parseInt(request.getParameter("adId"));
        data = new AdminLogic();
        System.out.println("adId=" + adId);
        data.del_pic_ad(adId);
        int state = 0;
        List noauditlist = data.getAuditInfo(state);
        request.setAttribute("noauditlist", noauditlist);
        request.getRequestDispatcher("adminManager.jsp").forward(request,
                response);
    }

    public void delInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int adId = Integer.parseInt(request.getParameter("adId"));
        data = new AdminLogic();
        System.out.println("adId=" + adId);
        data.newDel_pic_ad(adId);
        int state = 1;

        List auditlist = data.getAuditInfo(state);
        request.setAttribute("auditlist", auditlist);
        request.getRequestDispatcher("adminManager.jsp").forward(request,
                response);
    }

    public void delBatchInfo(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("appliction/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String jsonADCheckedID = request.getParameter("jsonADCheckedID");
        List<Integer> AdList = new ArrayList<Integer>();
        JSONArray jsonArray = JSONArray.fromObject(jsonADCheckedID);
        for (int i = 0; i < jsonArray.size(); i++) {
            AdList.add(i, Integer.parseInt((String) jsonArray.get(i)));
            // System.out.println(" 广告ID" + (String) jsonArray.get(i));
        }
        data.delBatch_pic_ad(AdList);
        int state = 1;

        List auditlist = data.getAuditInfo(state);
        request.setAttribute("auditlist", auditlist);
        request.getRequestDispatcher("adminManager.jsp").forward(request,
                response);
    }
    public void delAuditBatchInfo(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("appliction/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String jsonADCheckedID = request.getParameter("jsonADCheckedID");
        List<Integer> AdList = new ArrayList<Integer>();
        JSONArray jsonArray = JSONArray.fromObject(jsonADCheckedID);
        for (int i = 0; i < jsonArray.size(); i++) {
            AdList.add(i, Integer.parseInt((String) jsonArray.get(i)));
            // System.out.println(" 广告ID" + (String) jsonArray.get(i));
        }
        data.newDelBatch_pic_ad(AdList);
        int state = 1;

        List auditlist = data.getAuditInfo(state);
        request.setAttribute("auditlist", auditlist);
        request.getRequestDispatcher("adminManager.jsp").forward(request,
                response);
    }
    // 注册新用户

    public void registerUser(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        data = new AdminLogic();
        String information = "注册失败";
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String userName = request.getParameter("name");
        userName = new String(userName.getBytes("iso-8859-1"), "UTF-8");

        System.out.println("username=" + userName);
        String userPasswd = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String money = request.getParameter("money");
        money = new String(money.getBytes("iso-8859-1"), "UTF-8");// 获取交付的金额
        int m = 0;
        if (!money.equals(""))
            m = Integer.parseInt(money);
        int userType = new OperationData().userType(m);

        int userId = data.maxUserId() + 1;

        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        // user.setPassword(password)
        user.setPassword(userPasswd);
        user.setUserType(userType);
        user.setEmail(email);
        user.setPhone(phone);
        boolean f = false;
        if (userName.equals("") || userPasswd.equals("")) {
            information = "注册失败，有选项未填";

        } else {
            if (userPasswd.equals(passwordConfirm)) {// 判断两次输入的密码是否相同
                f = data.isRepeat(userName);// 判断是否已存在此用户名
                if (f == true) {
                    boolean flag = data.register_user(user);
                    if (flag == true) {
                        information = "注册成功,专栏申请成功";
                    }

                } else {
                    information = "已存在此用户名";
                }

            } else {
                information = "两次输入的密码不同";
            }

        }

        String paste_name = request.getParameter("pasteName");
        paste_name = new String(paste_name.getBytes("iso-8859-1"), "UTF-8");
        String unitName = request.getParameter("unitName");
        unitName = new String(unitName.getBytes("iso-8859-1"), "UTF-8");
        int unitId = new OperationData().unitId(unitName);
        if (paste_name.equals(null) || paste_name.equals("")) {// 如果没填粘贴栏名
            information = "用户已经注册成功，未申请专栏";
        } else {// 粘贴栏名不为空
            int paste_Id = new OperationData().query_pasteId(paste_name);// 根据给定的粘贴栏名查看其Id，如果ID>0，表示该粘贴栏已经存在
            if (paste_Id > 0) {
                information = "用户已经注册成功，专栏申请中专栏名已存在";
            } else {// 粘贴栏名不存在

                if (unitId != 0 && userId != 0) {// 单位名和用户名都存在时才记录
                    int pasteId = data.maxPasteId() + 1;// 生成的新粘贴栏id为当前最大的粘贴栏Id基础上加1
                    String time = new GetCurrentTime().currentTime();// 粘贴栏开设时间为插入的时间
                    System.out.println("time:" + time);

                    Post p = new Post();
                    p.setPostId(pasteId);
                    p.setPostName(paste_name);
                    p.setUnitId(unitId);
                    p.setUserId(userId);
                    p.setCreateTime(time);
                    data.savePaste(p);

                } else {
                    information = "用户注册成功，专栏申请中请保证专栏单位名存在";
                }
            }
        }

        request.setAttribute("information", information);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    // 显示用户信息
    public void showUser(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        data = new AdminLogic();
        List list = data.getUser();
        System.out.println("执行showUser，len:" + list.size());
        request.setAttribute("list", list);
        request.getRequestDispatcher("registerManager.jsp").forward(request,
                response);

    }

    // 删除用户
    public void delUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("userId");
        int userId = Integer.parseInt(id);
        boolean f = data.del_user(userId);
        showUser(request, response);
    }

    // 批量删除用户
    public void delBatchUser(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("appliction/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String userCheckedID = request.getParameter("jsonCheckedID");
        List<Integer> userList = new ArrayList<Integer>();
        JSONArray jsonArray = JSONArray.fromObject(userCheckedID);
        for (int i = 0; i < jsonArray.size(); i++) {
            userList.add(i, Integer.parseInt((String) jsonArray.get(i)));
        }
        // System.out.println(userCheckedID);
        // userCheckedID.split(",");
        // for(int i=0;i<userCheckedID.length;i++){
        // System.out.println(userCheckedID[i]+"||");
        // }
        boolean flag = data.delBatch_user(userList);
        showUser(request, response);
    }

    // 修改用户等级
    public void changeUser(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String user = request.getParameter("userId");
        int userId = Integer.parseInt(user);
        String type = request.getParameter("userType");
        int userType = Integer.parseInt(type);
        data.changeUser(userId, userType);
        showUser(request, response);
    }

    // 修改用户信息
    public void updateUser(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        String information = "";
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        User user = new User();
        user = (User) request.getSession().getAttribute("User");
        int userId = user.getUserId();// 获取用户id
        String oldname = user.getUserName();// 获取原来的用户名
        String userName = request.getParameter("name");
        userName = URLDecoder.decode(userName, "utf-8");
        // userName=new String(userName.getBytes("iso-8859-1"),"UTF-8");
        System.out.println("username=" + userName);
        String userPasswd = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");

        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        boolean f = false;
        if (userName.equals("") || userPasswd.equals("")) {
            information = "更新失败，有选项未填";

        } else {
            if (userPasswd.equals(passwordConfirm)) {
                if (!userName.equals(oldname)) {// 如果更改了用户名
                    System.out.println("更改了用户名");
                    f = data.isRepeat(userName);
                    if (f == true) {
                        boolean flag = data.updateUser(userId, userName,
                                userPasswd, email, phone);
                        if (flag == true) {
                            user.setUserName(userName);
                            user.setPassword(userPasswd);
                            user.setEmail(email);
                            user.setPhone(phone);
                            information = "更新成功";
                        }
                    } else {
                        information = "更新失败,用户名已存在";
                    }
                } else {// 用户名没有更改
                    System.out.println("没有更改用户名");
                    boolean flag = data.updateUser(userId, userName,
                            userPasswd, email, phone);
                    if (flag == true) {
                        user.setUserName(userName);
                        user.setPassword(userPasswd);
                        user.setEmail(email);
                        user.setPhone(phone);
                        information = "更新成功";
                    }
                }

            } else {
                information = "更新失败，两次输入的密码不同";
            }

        }

        request.setAttribute("information", information);
        request.getRequestDispatcher("updateUser.jsp").forward(request,
                response);
    }

    // 显示粘贴栏信息
    public void pasteShow(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        List list = data.get_paste();
        request.setAttribute("list", list);
        System.out.println("t4");
        request.getRequestDispatcher("pasteManage.jsp").forward(request,
                response);
    }

    // 插入一个粘贴栏
    public void insertPaste(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String paste_name = request.getParameter("paste_name");
        // paste_name=new String(paste_name.getBytes("iso-8859-1"),"UTF-8");
        paste_name = URLDecoder.decode(paste_name, "utf-8");
        if (paste_name.equals(null) || paste_name.equals("")) {// 如果没填粘贴栏名
            out.print("<script language=javascript>alert('粘贴栏名不能为空')</script>");
            out.print("<script language=javascript>history.go(-1);</script>");
        } else {// 粘贴栏名不为空
            int paste_Id = new OperationData().query_pasteId(paste_name);// 根据给定的粘贴栏名查看其Id，如果ID>0，表示该粘贴栏已经存在
            if (paste_Id > 0) {
                out.print("<script language=javascript>alert('该粘贴栏名已存在，请重新取名')</script>");
                out.print("<script language=javascript>history.go(-1);</script>");

            } else {// 粘贴栏名不存在
                String userName = request.getParameter("userName");
                // userName=new String(userName.getBytes("iso-8859-1"),"UTF-8");
                userName = URLDecoder.decode(userName, "utf-8");
                if (userName == null) {
                    userName = "xjp";
                }

                userName = URLDecoder.decode(userName, "utf-8");
                int userId = new OperationData().userId(userName);// 由用户名获取用户ID

                System.out.println("userName" + userName + "userId" + userId);
                String unitName = request.getParameter("unitName");
                // unitName=new String(unitName.getBytes("iso-8859-1"),"UTF-8");
                unitName = URLDecoder.decode(unitName, "utf-8");
                int unitId = new OperationData().unitId(unitName);
                System.out.println("unitName:" + unitName + "unitId:" + unitId);
                if (unitId != 0) {// 单位名和用户名都存在时才记录&& userId!=0
                    int pasteId = data.maxPasteId() + 1;// 生成的新粘贴栏id为当前最大的粘贴栏Id基础上加1
                    String time = new GetCurrentTime().currentTime();// 粘贴栏开设时间为插入的时间
                    System.out.println("time:" + time);

                    Post p = new Post();
                    p.setPostId(pasteId);
                    p.setPostName(paste_name);
                    p.setUnitId(unitId);
                    p.setUserId(userId);
                    p.setCreateTime(time);
                    boolean f = data.savePaste(p);
                    pasteShow(request, response);
                } else {
                    out.print("<script language=javascript>alert('请核对填写的信息，保证用户名和单位名存在')</script>");
                    out.print("<script language=javascript>history.go(-1);</script>");
                }
            }
        }
    }

    // 修改粘贴栏信息
    public void updatePaste(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String Id = request.getParameter("id");
        int id = Integer.parseInt(Id);// 获取传入粘贴栏id
        String paste_name = request.getParameter("newName");// 获取传入的粘贴栏名
        // paste_name=new String(paste_name.getBytes("iso-8859-1"),"UTF-8");
        paste_name = URLDecoder.decode(paste_name, "utf-8");
        System.out.println("paste_name=" + paste_name);
        data.updatePaste(id, paste_name);
        pasteShow(request, response);
    }

    // 删除粘贴栏
    public void delPaste(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        String pasteId = request.getParameter("pasteId");
        System.out.println("pasteId=" + pasteId);
        int id = Integer.parseInt(pasteId);
        boolean f = data.delPaste(id);
        pasteShow(request, response);

    }

    // 后台显示单位
    public void unitShow(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        List list = data.get_unit();
        List type = data.get_type();// 返回所有的粘贴栏类别信息
        request.setAttribute("list", list);
        request.setAttribute("type", type);
        request.getRequestDispatcher("unitManager.jsp").forward(request,
                response);
    }

    // 插入一个单位
    public void insertUnit(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String unit_name = request.getParameter("unit_name");
        // unit_name=new String(unit_name.getBytes("iso-8859-1"),"UTF-8");
        unit_name = URLDecoder.decode(unit_name, "utf-8");
        System.out.println("执行insertUnit,unit_name:" + unit_name);
        String paste_type = request.getParameter("paste_type");
        // paste_type=new String(paste_type.getBytes("iso-8859-1"),"UTF-8");
        paste_type = URLDecoder.decode(paste_type, "utf-8");
        int pasteType = new OperationData().pasteType_id(paste_type);// 类别id
        if (unit_name.equals(null) || unit_name.equals("")) {// 如果没填单位名
            out.print("<script language=javascript>alert('单位名不能为空')</script>");
            out.print("<script language=javascript>history.go(-1);</script>");
        } else {
            // 如果该单位名在该类中已经存在，就不添加，显示已存在
            if (new OperationData().is_exist_unit(unit_name)) {

                out.print("<script language=javascript>alert('已存在此单位名，请重新取名!')</script>");
                out.print("<script language=javascript>history.go(-1);</script>");
            } else {
                int unitId = data.maxUnitId() + 1;
                Unit u = new Unit();
                u.setUnitId(unitId);
                u.setUnitName(unit_name);
                u.setUnitTypeId(pasteType);
                // u.setPostType(pasteType);
                data.saveUnit(u);
                unitShow(request, response);
            }
        }
    }

    // 修改单位信息
    public void updateUnit(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String unit_name = request.getParameter("newName");
        // unit_name=new String(unit_name.getBytes("iso-8859-1"),"UTF-8");
        unit_name = URLDecoder.decode(unit_name, "utf-8");
        String unit_id = request.getParameter("id");
        String newType = request.getParameter("newType");
        int unitId = Integer.parseInt(unit_id);
        int pasteType = Integer.parseInt(newType);
        Unit u = new Unit();
        u.setUnitId(unitId);
        u.setUnitName(unit_name);
        u.setUnitTypeId(pasteType);
        data.updateUnit(u);
        unitShow(request, response);
    }

    // 删除某个单位
    public void delUnit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        data = new AdminLogic();
        String unitId = request.getParameter("unitId");
        System.out.println("unitId=" + unitId);
        int id = Integer.parseInt(unitId);
        data.delUnit(id);
        unitShow(request, response);

    }

    // 显示类别信息
    public void typeShow(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        List list = data.get_type();
        request.setAttribute("list", list);
        request.getRequestDispatcher("typeManager.jsp").forward(request,
                response);
    }

    // 插入一个广告类别
    public void insertType(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String paste_type = request.getParameter("typeName");
        // paste_type=new String(paste_type.getBytes("iso-8859-1"),"UTF-8");
        paste_type = URLDecoder.decode(paste_type, "utf-8");
        System.out.println("paste_type:" + paste_type);

        int typeId = data.maxTypeId() + 1;
        AdType t = new AdType();
        t.setAdTypeId(typeId);
        t.setAdTypeName(paste_type);
        data.saveType(t);
        typeShow(request, response);
    }

    // 修改广告类别信息
    public void updateType(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String typeid = request.getParameter("typeId");
        int typeId = Integer.parseInt(typeid);
        String typeName = request.getParameter("typeName");
        // typeName=new String(typeName.getBytes("iso-8859-1"),"UTF-8");
        data.updateType(typeId, typeName);
        typeShow(request, response);
    }

    // 删除某个类别
    public void delType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        data = new AdminLogic();
        String typeId = request.getParameter("typeId");
        System.out.println("typeId=" + typeId);
        int id = Integer.parseInt(typeId);
        data.delType(id);
        typeShow(request, response);

    }

    // 显示粘贴栏类别信息
    public void pasteTypeShow(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        List list = data.get_pasteType();
        request.setAttribute("list", list);
        request.getRequestDispatcher("pasteTypeManager.jsp").forward(request,
                response);
    }

    // 插入一个粘贴栏类别
    public void insertPasteType(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String paste_type = request.getParameter("typeName");
        // paste_type=new String(paste_type.getBytes("iso-8859-1"),"UTF-8");
        paste_type = URLDecoder.decode(paste_type, "utf-8");
        System.out.println("paste_type:" + paste_type);

        int typeId = data.maxPasteTypeId() + 1;
        UnitType t = new UnitType();

        t.setUnitTypeId(typeId);
        t.setUnitTypeName(paste_type);
        data.savePasteType(t);
        pasteTypeShow(request, response);
    }

    // 修改粘贴栏类别
    public void updatePasteType(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String typeid = request.getParameter("typeId");
        int typeId = Integer.parseInt(typeid);
        String typeName = request.getParameter("typeName");
        // typeName=new String(typeName.getBytes("iso-8859-1"),"UTF-8");
        typeName = URLDecoder.decode(typeName, "utf-8");
        data.updatePasteType(typeId, typeName);
        pasteTypeShow(request, response);
    }

    // 删除粘贴栏类别
    public void delPasteType(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        data = new AdminLogic();
        String typeId = request.getParameter("typeId");
        System.out.println("typeId=" + typeId);
        int id = Integer.parseInt(typeId);
        data.delPasteType(id);
        pasteTypeShow(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);

    }

    public void TimingProcess(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String flag = request.getParameter("flag");
        int on_off=Integer.parseInt(flag);
        TimeProcessThread thread = TimeProcessThread.getTimeProcessThread();
        if (on_off == 1) {
            thread.run();
        }
        if (on_off == 0)
            thread.interrupt();
    }

}
