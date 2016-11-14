package logicalConduct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.inject.New;

import allClasses.AdType;
import allClasses.Administrator;
import allClasses.Pic;
import allClasses.Post;
import allClasses.Unit;
import allClasses.UnitType;
import allClasses.User;

import jdbc.ConnectDB;
import jdbc.SearchAboutPost;

public class AdminLogic {
    private ConnectDB connection = null;
    private List list = null;
    String sql = "";
    String sqlSelectadId = "select adId from ad where userId=?";

    // private SearchAboutPost searchFromDB=new SearchAboutPost();
    // 返回所有管理员信息

    public Administrator get_admin() {
        Administrator a = new Administrator();
        sql = "select * from administrator";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        System.out.println("rs=" + rs);
        try {
            while (rs.next()) {
                a.setAdministratorName(rs.getString("administratorName"));
                a.setPassword(rs.getString("password"));

            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            connection.close();
        }

        return a;

    }

    // 返回所有未通过审核的图片的信息
    public List getAuditInfo(int checked) {
        List list = new ArrayList();
        Pic p;
        sql = "select *from pic where pic.adId in(select ad.adId from ad where exist=1) and checked='" + checked + "'";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);

        try {
            while (rs.next()) {
                p = new Pic();
                p.setPicId(rs.getInt("picId"));
                p.setAdId(rs.getInt("adId"));
                p.setHeight(rs.getInt("height"));
                p.setWidth(rs.getInt("width"));
                // p.setPicName(rs.getString("picName"));
                p.setPicAddr(rs.getString("picAddr"));
                p.setChecked(rs.getInt("checked"));
                // p.setState(rs.getInt("checked"));
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return list;
    }

    // 将特定ID的图片状态改为通过
    public boolean changeState_pic(int adId) {
        connection = new ConnectDB();
        sql = "update pic set checked='" + 1 + "' where adId='" + adId + "'";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        return flag;
    }

    // 批量将特定ID的图片状态改为通过
    public boolean BatchchangeState_pic(List<Integer> List) {
        connection = new ConnectDB();
        sql = "update pic set checked='" + 1 + "' where adId=?";
        boolean flag = connection
                .executeBatch(sql, listTransformationInt(List));
        System.out.println(flag);
        connection.close();
        return flag;
    }

    // 将特定ID的广告状态改为通过
    public boolean changeState_ad(int adId) {
        connection = new ConnectDB();
        sql = "update ad set checked='" + 1 + "' where adId='" + adId + "'";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        return flag;
    }

    // 批量将特定ID的广告状态改为通过
    public boolean BatchchangeState_ad(List<Integer> List) {
        connection = new ConnectDB();
        sql = "update ad set checked='" + 1 + "' where adId=?";
        boolean flag = connection
                .executeBatch(sql, listTransformationInt(List));
        System.out.println(flag);
        connection.close();
        return flag;
    }

    // 将所有图片的状态改为1
    public boolean changeAllState_pic() {
        connection = new ConnectDB();
        sql = "update pic set checked='" + 1 + "' ";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        return flag;
    }

    // 将所有广告状态改为1
    public boolean changeAllState_ad() {
        connection = new ConnectDB();
        sql = "update ad set checked='" + 1 + "' ";
        boolean flag = connection.executeUpdate(sql);

        connection.close();
        return flag;
    }

    // 删除某个id广告下所有的图片
    public void del_pic_ad(int adId) {
        connection = new ConnectDB();
        sql = "delete from pic where adId='" + adId + "'";
        boolean flag = connection.executeUpdate(sql);
        sql = "delete from ad where adId='" + adId + "'";
        flag = connection.executeUpdate(sql);
        connection.close();

    }
    //通过改变ad的状态，来代表删除
    public void newDel_pic_ad(int adId){
        connection=new ConnectDB();
        sql="update ad set exist=0 where adId="+adId;
        connection.executeUpdate(sql);
        connection.close();
    }
    
    public void delBatch_pic_ad(List<Integer> AdList) {
        connection = new ConnectDB();
        sql = "delete from pic where adId=?";
        boolean flag = connection.executeBatch(sql,
                listTransformationInt(AdList));
        sql = "delete from ad where adId=?";
        flag = connection.executeBatch(sql, listTransformationInt(AdList));
        connection.close();
    }
    
    public void newDelBatch_pic_ad(List<Integer> AdList){
        connection=new ConnectDB();
        sql="update ad set exist=0 where adId=?";
        boolean flag=connection.executeBatch(sql, listTransformationInt(AdList));
        connection.close();
    }
    // 看用户名是否在在数据库中存在，存在返回false,不存在返回true
    public boolean isRepeat(String userName) {

        sql = "select *from user  where userName = '" + userName + "'";
        connection = new ConnectDB();
        // System.out.println(sql);
        ResultSet rs = connection.executeQuery(sql);
        System.out.println(rs);
        try {
            if (rs.next()) {
                System.out.println(sql);
                return false;

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    // 注册用户
    public boolean register_user(User user) {
        sql = "insert into user_info values('" + user.getUserId() + "','"
                + user.getUserName() + "','" + user.getPassword() + "','"
                + user.getUserType() + "" + "','" + user.getEmail() + "','"
                + user.getPhone() + "')";
        connection = new ConnectDB();
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        if (flag == true) {
            System.out.println("注册成功!");
        }
        return flag;
    }

    // 更新用户
    public boolean updateUser(int userId, String userName, String userPasswd,
            String email, String phone) {
        connection = new ConnectDB();
        System.out.println("执行updateUser，userName=" + userName + "userPasswd"
                + userPasswd + "email" + email + "phone" + phone);
        sql = "update user set  userName='" + userName + "' ,password='"
                + userPasswd + "' ,email='" + email + "' ,phone='" + phone
                + "' where userId='" + userId + "'";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        System.out.println("执行updateUser，flag=" + flag);
        return flag;
    }

    // 更改用户等级
    public boolean changeUser(int userId, int userType) {
        connection = new ConnectDB();
        sql = "update user set  userType='" + userType + "' where userId='"
                + userId + "'";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        System.out.println("执行changeUser，flag=" + flag);
        return flag;
    }

    // 获取所有用户信息
    public List getUser() {
        List list = new ArrayList();
        User user;
        sql = "select *from user ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        System.out.println("rs=" + rs);
        try {
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                // user.setUserPasswd(rs.getString("userPasswd"));
                user.setUserType(rs.getInt("userType"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                list.add(user);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return list;
    }

    // 获取所有用户姓名,用-连接
    public String userName() {
        String userName = null, name = null;
        User user;
        sql = "select * from user ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);

        try {

            while (rs.next()) {

                name = rs.getString("userName");
                if (userName == null) {
                    userName = name;
                } else {
                    userName = userName + "-" + name;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            connection.close();
        }

        return userName;
    }

    // 删除用户
    public boolean del_user(int userId) {
        connection = new ConnectDB();
        sql = "delete from user where userId='" + userId + "'";

        boolean flag = connection.executeUpdate(sql);

        connection.close();
        return flag;
    }

    public List<Integer> selectAdID(String sql, List<Integer> IDList) {
        List<ResultSet> resultSets = new ArrayList<ResultSet>();
        List<Integer> IDLists = new ArrayList<Integer>();
        connection = new ConnectDB();
        resultSets = connection.executeQueryBatch(sql,
                listTransformationInt(IDList));
        if(resultSets.size()!=0){
            for (Iterator iterator = resultSets.iterator(); iterator.hasNext();) {
                ResultSet resultSet = (ResultSet) iterator.next();
                try {
                    while (resultSet.next()) {
                        IDLists.add(resultSet.getInt("adId"));
                    }
                } catch (SQLException e1) {
                   
                    e1.printStackTrace();
                }
                finally {
                    connection.close();
                }
            }
        }
        
        return IDLists;
    }

    // 通过用户ID 查找adId
    public List<Integer> selectAdID(int userId) {
        ResultSet resultSet = null;
        List<Integer> IDLists = new ArrayList<Integer>();
        connection = new ConnectDB();
        sql = "select adId from ad where userId='" + userId + "'";
        resultSet = connection.executeQuery(sql);
        try {
            while (resultSet.next()) {
                IDLists.add(resultSet.getInt("adId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            connection.close();
        }
        return IDLists;
    }

    // 批量删除用户
    public boolean delBatch_user(List<Integer> userIDList) {
        ConnectDB  connection1 = new ConnectDB();
        int[] param = new int[userIDList.size()];
        List<String> strings = new ArrayList<String>();
        int i = 0;
        for (Iterator iterator = userIDList.iterator(); iterator.hasNext();) {
            Integer integer = (Integer) iterator.next();
            param[i] = integer;
            i++;

        }
        sql = "delete from user where userId=?";// 删除用户之前需要删除ad、pic、privatead、privatepic。
        String sqlDeleteFather = "delete from ad where userId=?";
        String sqlSelectFather="select adId from ad where userId=?";
        String sqlDeleteSon = "delete from pic where adId=?";
        connection1.executeBatch(sqlDeleteSon,
                listTransformationInt(selectAdID(sqlSelectFather, userIDList)));// 删除pic中与这个用户相关联的pic，可以使用批量删除广告
        connection1.executeBatch(sqlDeleteFather,
                listTransformationInt(userIDList));// 删除ad中与这个用户相关联的ad
        sqlDeleteFather = "delete from privatead where userId=?";
        sqlSelectFather="select adId from privatead where userId=?";
        sqlDeleteSon = "delete from privatepic where adId=?";
        connection1.executeBatch(sqlDeleteSon,
                listTransformationInt(selectAdID( sqlSelectFather, userIDList)));// 删除pic中与这个用户相关联的privatepic
        connection1.executeBatch(sqlDeleteFather,
                listTransformationInt(userIDList));// 删除ad中与这个用户相关联的privatead
        sqlDeleteSon = "delete from attention where userId=?";
        connection1
                .executeBatch(sqlDeleteSon, listTransformationInt(userIDList));
        boolean flag = connection1.executeBatch(sql, param);// 此地用的param可以用listTransformationInt(userIDList)获得，是一样的
        System.out.println(flag);
        connection1.close();
        return flag;
    }
    public void delBatch_ad(String sql,List<Integer> adList){
        connection = new ConnectDB();
       
        String sqlDeleteSon = "delete from pic where adId=?";
        connection.executeBatch(sqlDeleteSon,
                listTransformationInt(adList));// 删除pic中与这个用户相关联的pic
        connection.executeBatch(sql,
                listTransformationInt(adList));// 删除ad中与这个用户相关联的ad
    }
    //把list 转化为数组。
    public int[] listTransformationInt(List<Integer> List) {
        int[] param = new int[List.size()];
        int i = 0;
        for (Iterator iterator = List.iterator(); iterator.hasNext();) {
            Integer integer = (Integer) iterator.next();
            param[i] = integer;
            i++;

        }
        return param;
    }

    // 返回当前最大的用户id
    public int maxUserId() {
        int max = 0;
        sql = "select max(userId) as userId from user";

        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                max = rs.getInt("userId");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("执行maxUserId，当前最大的userId为：" + max);
        return max;
    }

    // 获取所有粘贴栏信息
    public List get_paste() {
        List list = new ArrayList();
        Post p;
        sql = "select *from post order by unitId ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        System.out.println("rs=" + rs);
        try {
            while (rs.next()) {
                p = new Post();
                p.setPostId(rs.getInt("postId"));
                p.setPostName(rs.getString("postName"));
                p.setUnitId(rs.getInt("unitId"));
                p.setUserId(rs.getInt("userId"));
                p.setCreateTime(rs.getString("createtime"));
                list.add(p);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return list;
    }

    // 保存粘贴栏
    public boolean savePaste(Post p) {
        connection = new ConnectDB();
        sql = "insert into post(postId, unitId,userId,postName,createTime) values('"
                + p.getPostId()
                + "','"
                + p.getUnitId()
                + "','"
                + p.getUserId()
                + "','" + p.getPostName() + "','" + p.getCreateTime() + "')";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        return flag;
    }

    // 更新粘贴栏信息
    public boolean updatePaste(int id, String paste_name) {
        connection = new ConnectDB();
        sql = "update post set  postName='" + paste_name + "' where postId='"
                + id + "'";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        System.out.println("执行updatePaste，flag=" + flag);
        return flag;
    }

    // 返回当前最大的粘贴栏id
    public int maxPasteId() {
        int max = 0;
        sql = "select max(postId) as pasteId from post";

        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                max = rs.getInt("pasteId");// postId

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("执行maxPostId，当前最大的postId为：" + max);
        return max;
    }

    // 返回当前最大的单位id
    public int maxUnitId() {
        int max = 0;
        sql = "select max(unitId) as unitId from unit";

        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                max = rs.getInt("unitId");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("执行maxUnitId，当前最大的unitId为：" + max);
        return max;
    }

    // 删除特定id粘贴栏信息
    public boolean delPaste(int pasteId) {
        connection = new ConnectDB();
        sql = "delete from post where postId='" + pasteId + "'";
        boolean flag = connection.executeUpdate(sql);

        connection.close();
        return flag;
    }

    // 获取所有单元信息
    public List get_unit() {
        List list = new ArrayList();
        Unit u;
        sql = "select * from unit order by unitId";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                u = new Unit();
                u.setUnitId(rs.getInt("unitId"));
                u.setUnitName(rs.getString("unitName"));
                u.setUnitTypeId(rs.getInt("unitTypeId"));
                // u.setPasteType(rs.getInt("pasteType"));
                list.add(u);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return list;
    }

    // 保存单元信息
    public boolean saveUnit(Unit u) {
        connection = new ConnectDB();
        sql = "insert into unit  values('" + u.getUnitId() + "','"
                + u.getUnitName() + "','" + u.getUnitTypeId() + "')";
        boolean flag = connection.executeUpdate(sql);

        connection.close();
        return flag;
    }

    // 更新单元信息
    public boolean updateUnit(Unit u) {
        connection = new ConnectDB();
        sql = "update unit set unitName='" + u.getUnitName()
                + "', unitTypeId='" + u.getUnitTypeId() + "' where unitId='"
                + u.getUnitId() + "'";
        boolean flag = connection.executeUpdate(sql);

        connection.close();
        return flag;
    }

    // 删除特定id单元信息
    public boolean delUnit(int unitId) {
        connection = new ConnectDB();
        sql = "delete from unit where unitId='" + unitId + "'";
        boolean flag = connection.executeUpdate(sql);

        connection.close();
        return flag;
    }

    // 获取所有广告类别信息
    public List get_type() {
        List list = new ArrayList();
        AdType p;
        sql = "select *from adtype  ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        System.out.println("rs=" + rs);
        try {
            while (rs.next()) {
                p = new AdType();
                p.setAdTypeId(rs.getInt(1));
                // p.setAdType(rs.getInt(1));
                p.setAdTypeName(rs.getString(2));
                list.add(p);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return list;
    }

    // 保存广告类别
    public boolean saveType(AdType p) {
        connection = new ConnectDB();
        sql = "insert into adtype values('" + p.getAdTypeId() + "','"
                + p.getAdTypeName() + "')";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        return flag;
    }

    // 更新广告类别信息
    public boolean updateType(int id, String typeName) {
        connection = new ConnectDB();
        sql = "update adtype set adTypeName='" + typeName
                + "' where adTypeId='" + id + "'";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        System.out.println("执行updateType，flag=" + flag);
        return flag;
    }

    // 返回当前最大的广告类别id
    public int maxTypeId() {
        int max = 0;
        sql = "select max(adTypeId) as adTypeId from adtype";

        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                max = rs.getInt(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("执行maxTypeId，当前最大的typeId为：" + max);
        return max;
    }

    public boolean delType(int id) {
        connection = new ConnectDB();
        sql = "delete from adtype where adTypeId='" + id + "'";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        return flag;
    }

    // 获取所有单位类别信息
    public List get_pasteType() {
        List list = new ArrayList();
        UnitType p;
        sql = "select *from unittype  ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        System.out.println("rs=" + rs);
        try {
            while (rs.next()) {
                p = new UnitType();
                p.setUnitTypeId(rs.getInt(1));
                // p.setPasteType(rs.getInt(1));
                // p.setTypeName(rs.getString(2));
                p.setUnitTypeName(rs.getString(2));
                list.add(p);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return list;
    }

    // 保存单位类别
    public boolean savePasteType(UnitType p) {
        connection = new ConnectDB();
        sql = "insert into unittype values('" + p.getUnitTypeId() + "','"
                + p.getUnitTypeName() + "')";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        return flag;
    }

    // 更新单位类别信息
    public boolean updatePasteType(int id, String typeName) {
        connection = new ConnectDB();
        sql = "update unittype set  unitTypeName='" + typeName
                + "' where unitTypeId='" + id + "'";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        return flag;
    }

    // 返回当前最大的单位类别id
    public int maxPasteTypeId() {
        int max = 0;
        sql = "select max(unitTypeId) as unitTypeId from unittype";

        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                max = rs.getInt(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return max;
    }

    public boolean delPasteType(int id) {
        connection = new ConnectDB();
        sql = "delete from unittype where unitTypeId='" + id + "'";
        boolean flag = connection.executeUpdate(sql);
        connection.close();
        return flag;
    }

    // 根据单位类别id返回单位所属类别
    public String typeName(int id) {
        String name = null;
        sql = "select *from unittype where unitTypeId='" + id + "' ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                name = rs.getString(2);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return name;
    }

    // 根据单位id返回单位名
    public String unitName(int id) {
        String name = null;
        sql = "select *from unit where unitId='" + id + "' ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                name = rs.getString("unitName");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return name;
    }

    // 根据用户id返回用户名
    public String userName(int id) {
        String name = null;
        sql = "select *from user where userId='" + id + "' ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                name = rs.getString(2);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return name;
    }

    // 返回在数据库中所有用户id
    public int[] userIds() {
        int[] userIds;
        int len = 0;
        sql = "select distinct userId from user ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next())// id个数
            {
                len++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        userIds = new int[len];
        int i = 0;
        sql = "select distinct userId from user ";
        connection = new ConnectDB();
        ResultSet rs1 = connection.executeQuery(sql);
        try {
            while (rs1.next())// id个数
            {
                // userIds[i++]=rs1.getInt(1);
                userIds[i++] = rs1.getInt("userId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return userIds;
    }

    // 返回在数据库中所有单位id
    public int[] unitIds() {
        int[] unitIds;
        int len = 0;
        sql = "select distinct unitId from unit ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next())// id个数
            {
                len++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        unitIds = new int[len];
        int i = 0;
        sql = "select distinct unitId from unit ";
        connection = new ConnectDB();
        ResultSet rs1 = connection.executeQuery(sql);
        try {
            while (rs1.next())// id个数
            {
                unitIds[i++] = rs1.getInt("unitId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return unitIds;
    }

    // 返回所有粘贴栏类别id
    public int[] pasteTypes() {
        int[] pasteTypes;
        int len = 0;
        sql = "select distinct unitTypeId  from unittype";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        System.out.println("返回所有粘贴栏类别id,rs:" + rs);
        try {
            while (rs.next())// id个数
            {
                len++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        pasteTypes = new int[len];
        int i = 0;
        sql = "select distinct unitTypeId  from unittype";
        connection = new ConnectDB();
        ResultSet rs1 = connection.executeQuery(sql);
        try {
            while (rs1.next())// id个数
            {
                pasteTypes[i++] = rs1.getInt("unitTypeId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return pasteTypes;
    }

}
