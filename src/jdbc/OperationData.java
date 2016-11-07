package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import tool.ChangeResultSetToArray;

import allClasses.Ad;
import allClasses.AdType;
import allClasses.Pic;
import allClasses.Post;
import allClasses.PrivateAd;
import allClasses.Unit;
import allClasses.UnitType;
import allClasses.VisitorLog;

public class OperationData {

    private ConnectDB connection = null;
    private List list = null;
    String sql = "";

    // 根据图片Id返回图片所属的类别名称
    public String query_adTypeBypicId(int picId) {
        System.out.println("执行query_adById,adId=" + picId);
        sql = "select  adTypeName  from ad ,adtype ,pic where picId='" + picId
                + "' and ad.adId=pic.adId and adtype.adTypeId=ad.adTypeId";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        String typeName = null;
        try {
            // advertise ad;
            while (rs.next()) {
                typeName = rs.getString("adTypeName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return typeName;

    }

    // /根据广告Id返回该广告的所属粘贴栏
    public String query_adPostBypicId(int picId) {
        System.out.println("执行query_adById,adId=" + picId);
        sql = "select  postName  from ad ,post,pic where picId='" + picId
                + "' and ad.adId=pic.adId and ad.postId =post.postId";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        String typeName = null;
        try {
            // advertise ad;
            while (rs.next()) {
                typeName = rs.getString("postName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return typeName;

    }

    // 根据用户交钱金额返回他的等级
    public int userType(int money) {
        int userType = 0;
        sql = "select * from usertype where money='" + money + "'";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                userType = rs.getInt("userTypeId");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            connection.close();
        }
        return userType;
    }

    // 根据给出的单位名返回其id
    public int unitId(String unitName) {
        System.out.println("执行unitId,unitName为：" + unitName);
        int unitId = 0;
        sql = "select unitId from unit where unitName='" + unitName + "'";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                unitId = rs.getInt("unitId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return unitId;
    }

    // 根据粘贴栏名查询张贴栏ID
    public int query_pasteId(String pasteName) {

        int pasteId = 0;
        String sql = "select postId from post where postName='" + pasteName
                + "'";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                pasteId = rs.getInt("postId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("执行query_pasteId" + pasteName + "对应的ID为：" + pasteId);
        return pasteId;
    }

    // 判断是否已存在某个单位名
    public boolean is_exist_unit(String unitName) {
        System.out.println("执行is_exist_unit，typeName：" + unitName);
        sql = "select * from unit where unitName='" + unitName + "'";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return false;
    }

    // 根据给出的粘贴栏类别名返回粘贴栏类别ID
    public int pasteType_id(String typeName) {
        System.out.println("执行pasteType_id,typeName为：" + typeName);
        int pasteType = 0;
        sql = "select unitTypeId from unittype where unitTypeName='" + typeName
                + "'";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                pasteType = rs.getInt("unitTypeId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return pasteType;
    }

    // 根据用户名查询用户Id
    public int userId(String username) {
        int userId = 0;
        connection = new ConnectDB();
        sql = "SELECT * FROM user WHERE userName='" + username + "'";
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {

                userId = rs.getInt("userId");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return userId;
    }

    // 查询广告的所有类别
    public List query_adType() {
        AdType type;
        sql = "select *from adtype";
        connection = new ConnectDB();
        ResultSet rs = null;
        List adList = new ArrayList();

        rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                type = new AdType();
                type.setAdTypeId(rs.getInt("adTypeId"));
                type.setAdTypeName(rs.getString("adTypeName"));
                adList.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return adList;

    }

    // 返回所有包含非专栏粘贴栏的类别
    public List publicPasteType() {
        String name = "";
        UnitType type;
        sql = "select *from unittype";
        connection = new ConnectDB();
        ResultSet rs = null;
        List pasteType = new ArrayList();

        rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                name = rs.getString("unitTypeName");// 获取类别名
                if (typeUnit(name) != "") {// typeUnit(String
                                           // typeName)是返回该类别下所有有非专栏的单位名，如果不是空，则代表该类别有非专栏粘贴栏
                    type = new UnitType();
                    type.setUnitTypeId(rs.getInt("unitTypeId"));
                    type.setUnitTypeName(rs.getString("unitTypeName"));
                    pasteType.add(type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return pasteType;

    }

    // 返回某类别下所有有非专栏粘贴栏的单位名 用-连成的字符串
    public String typeUnit(String typeName) {
        String unitName = "", name;
        sql = "select * from unittype,unit where unitTypeName= '" + typeName
                + "' and unit.unitTypeId=unittype.unitTypeId order by unitId";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);

        try {
            while (rs.next()) {
                name = rs.getString("unitName");
                if (!is_unit_special(name)) {// 如果某单位下有非专栏的粘贴栏，则加入
                    if (unitName != "")
                        unitName = unitName + "-" + name;
                    else
                        unitName = name;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("执行typeUnit" + typeName + "下单位为：" + unitName);
        return unitName;

    }

    // 返回所有单位类别信息，包括类别名以及名称
    public List<String> postTypes() throws SQLException {
        // /ConnectDB connect = new ConnectDB();
        // System.out.println("执行src/jdbc/SearchFromDB/unitTypeNames()");
        String sql = "select * from post";
        connection = new ConnectDB();
        ResultSet result = connection.executeQuery(sql);
        // List<UnitType> unitTypes =
        // changeResultSetToArray.unitTypeArrays(result);
        List<String> postList = new ArrayList();
        while (result.next()) {
            String p = result.getString("postName");
            postList.add(p);
        }

        connection.close();
        return postList;

    }

    // 判断某个单位下是不是所有的粘贴栏都是专栏，即看是否有非专栏的粘贴栏
    public boolean is_unit_special(String unitName) {

        // 查询该单位下所有的粘贴栏
        String pasteName = "";
        sql = "select * from post,unit where unitName='" + unitName
                + "' and unit.unitId=post.unitId";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                pasteName = rs.getString("postName");// 获取粘贴栏名
                if (!is_paste_special(pasteName)) {// 该单位下有任何一个粘贴栏不为专栏，都返回false
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            connection.close();
        }

        return true;
    }

    // 根据给出的粘贴栏名，判断是不是专栏
    public boolean is_paste_special(String pasteName) {
        int userId = 0;
        sql = "select * from post where postName='" + pasteName + "' ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                userId = rs.getInt("userId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        if (userId <= 0) {// 不是专栏，返回false
            return false;
        } else {
            return true;
        }
    }

    // 根据选取的中学、小学等类别查询所有该类别的单位名称
    public String[] query_unitName(String typeName) {

        String unitName[] = null;
        Unit un;
        int i = 0, len = 0;
        System.out.println("typeName：" + typeName);
        sql = "select * from unittype where unitTypeName='" + typeName + "'";
        connection = new ConnectDB();
        ResultSet rs1 = connection.executeQuery(sql);
        System.out.println(rs1);
        try {
            rs1.last();
            len = rs1.getRow();
            rs1.beforeFirst();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println("len：" + len);

        sql = "select unitName from unit,unittype where unitTypeName='"
                + typeName
                + "' and unit.unitTypeId=unittype.unitTypeId order by unitId";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        System.out.println(rs);
        try {
            rs.last();
            len = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println("len：" + len);
        unitName = new String[len];

        try {
            while (rs.next()) {
                unitName[i++] = rs.getString("unitName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        System.out.println("执行query_unitName" + typeName + "下的单位个数为："
                + unitName.length);
        return unitName;

    }

    // 查询某个单位下所有张贴栏名称
    public String[] query_pasteName(String unitName) {
        System.out.println("执行query_pasteName，查询单位：" + unitName + "下的所有粘贴栏");
        // paste pa=null;
        String pasteName[] = null;
        int i = 0, len = 1;
        sql = "select postName from post,unit where unitName= '" + unitName
                + "' and unit.unitId=post.unitId ";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);

        try {
            rs.last();
            len = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        pasteName = new String[len];
        try {
            while (rs.next()) {

                pasteName[i++] = rs.getString("postName");
                System.out.println("在数据库中查询：" + pasteName[i - 1]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return pasteName;
    }

    // 查询给定广告类别和粘贴栏名的广告
    public List queryByAdType_paste(String typeName, String pasteName) {
        List adList = new ArrayList();
        Ad ad;
        if (typeName.equals("所有广告")) {// 如果是点击所有广告这一类别，则显示所有的图片
            sql = "select *from post,ad where  postName='" + pasteName
                    + "'  and ad.postId=post.postId and checked='" + 1
                    + "' order by sortValue DESC";
        } else {
            System.out.println("typeName:" + typeName);
            sql = "select *from post,ad,adtype where adTypeName='"
                    + typeName
                    + "' and postName='"
                    + pasteName
                    + "' and adtype.adTypeId=ad.adTypeId and ad.postId=post.postId and checked='"
                    + 1 + "' order by sortValue DESC";
        }
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        try {
            while (rs.next()) {
                ad = new Ad();
                ad.setUserId(rs.getInt("userId"));
                // ad.setAdFirstPic(rs.getString("adFirstPic"));
                ad.setFirstPicAddr(rs.getString("firstPicAddr"));
                ad.setPostId(rs.getInt("postId"));
                ad.setSortValue(rs.getLong("sortValue"));
                ad.setMoney((int) (rs.getDouble("money")));

                // ad.setPicWidth(rs.getInt("picWidth"));
                ad.setUpLoadTime(rs.getString("upLoadTime"));
                ad.setAdTypeId(rs.getInt("adTypeId"));
                ad.setAdId(rs.getInt("adId"));
                adList.add(ad);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            connection.close();
        }
        System.out.println("广告个数" + adList.size());
        return adList;
    }

    // 根据广告Id 查询该广告下的所有图片
    public List query_pic_byId(int adId) {
        System.out.println("执行query_pic_byId,广告Id为：" + adId);
        List list_pic = new ArrayList();
        sql = "select *from pic where adId='" + adId + "' and checked='" + 1
                + "'";
        connection = new ConnectDB();
        Pic p;
        ResultSet rs = connection.executeQuery(sql);
        try {
            if (rs == null) {
                System.out.println("rs为空");
            }
            while (rs.next()) {
                p = new Pic();
                p.setPicAddr(rs.getString("picAddr"));
                p.setWidth(rs.getInt("width"));
                p.setHeight(rs.getInt("height"));
                list_pic.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return list_pic;

    }

    // 根据广告Id返回广告所属的类别名称
    public String query_adTypeByadId(int adId) {
        System.out.println("执行query_adById,adId=" + adId);
        sql = "select  adTypeName  from ad ,adtype where adId='" + adId
                + "' and  adtype.adTypeId=ad.adTypeId";
        connection = new ConnectDB();
        ResultSet rs = connection.executeQuery(sql);
        String typeName = null;
        try {
            while (rs.next()) {
                typeName = rs.getString("adTypeName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return typeName;

    }

    // 根据图片Id返回该图片上传时间
    public String getUpTimeByPicId(int picId) {
        connection = new ConnectDB();
        sql = "select upLoadTime  from ad,pic where pic.picId=" + picId
                + " and pic.adId=ad.adId";
        ResultSet rs = connection.executeQuery(sql);
        String uploadtime = null;
        try {
            while (rs.next()) {
                uploadtime = rs.getString("upLoadTime");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return uploadtime;

    }

    public int addVisitorLog(VisitorLog vl) {
        /*
         * sql=" select count(*) from visitorlog"; connection=new ConnectDB();
         * ResultSet rs=connection.executeQuery(sql); int visitorid=0; try {
         * while(rs.next()) { visitorid=rs.getInt(1)+1;
         * System.out.println("visitorid="+visitorid); } } catch (SQLException
         * e) { // TODO Auto-generated catch block e.printStackTrace(); }
         */
        System.out.println(vl.getVisitorip() + "," + vl.getVisitorpostname());
        sql = "insert into visitorlog(visitorIP,visitorPostName)" + " values('"
                + vl.getVisitorip() + "','" + vl.getVisitorpostname() + "') ";

        // sql="insert into tb_name values('"+0+"','"+name+"','"+password+"')";
        System.out.println("addVisitorLog.0000.....");
        connection = new ConnectDB();
        System.out.println("addVisitorLog.11111....");
        boolean rs2 = connection.executeUpdate(sql);
        System.out.println("addVisitorLog.2222.....");
        connection.close();
        return 1;
    }

    // 获得所有广告
    public List<Ad> getAdList() {
        connection = new ConnectDB();
        ChangeResultSetToArray changeResultSetToArray = new ChangeResultSetToArray();
        sql = "select * from ad order by sortValue  DESC";
        ResultSet result = connection.executeQuery(sql);
        List<Ad> ads = changeResultSetToArray.adsArray(result);
        connection.close();
        return ads;
    }

    // 获得所有的时间
    public Map<Integer, Double> getSortUploadTime() {
        List<Ad> ads = getAdList();
        List<String> timeList = new ArrayList<String>();
        Map<Integer, Double> tMap = new HashMap<Integer, Double>();
        for (Iterator iterator = ads.iterator(); iterator.hasNext();) {
            Ad ad = (Ad) iterator.next();
            tMap.put(ad.getAdId(), timeTransformDouble(ad.getUpLoadTime()));

        }

        return tMap;
    }

    // 获得时间转化后的double值，转化到小时
    public Double timeTransformDouble(String times) {
        double year, month, day, hour;

        year = (Double.parseDouble(times.substring(0, 4)) - 2014) * 8760;
        month = Double.parseDouble(times.substring(4, 6)) * 720;
        day = Double.parseDouble(times.substring(6, 8)) * 24;
        hour = Double.parseDouble(times.substring(8, 10));

        return year + month + day + hour;
    }
}
