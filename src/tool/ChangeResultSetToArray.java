package tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import allClasses.Ad;
import allClasses.AdType;
import allClasses.Pic;
import allClasses.Post;
import allClasses.PrivateAd;
import allClasses.PrivateAdType;
import allClasses.PrivatePic;
import allClasses.Unit;
import allClasses.UnitType;
import allClasses.User;

//将数据库搜寻的结果转换成包含对应类的列表
public class ChangeResultSetToArray {
	//result中unitType数据库中所有信息
	public List<UnitType> unitTypeArrays(ResultSet result){
		List<UnitType> unitTypes=new ArrayList<UnitType>();
		try {
			while(result.next()){						
				//将获取的数据赋值给对应的unitType对象，并将其加入要返回的列表
				UnitType unitType=new UnitType(result.getInt(1),result.getString(2));
				unitTypes.add(unitType);
			}
		} catch (SQLException e) {
			 System.out.println("false in:src/jdbc/ChangeResultSetToArray/unitTypesArray");
			 System.out.println(e);
		}
		return unitTypes;
	}
	
	//result为根据unitTypeId查找的unit
	public List<Unit> unitsArray(ResultSet result){
		List<Unit> units=new ArrayList<Unit>();		 
		try {
			while(result.next()){
				//将搜到的Unit相关信息都存到units列表中
				Unit unit=new Unit(result.getInt(1),result.getString(2),result.getInt(3)); 				 
				units.add(unit); 	
			}
		} catch (SQLException e) {
			 System.out.println("false in:src/jdbc/ChangeResultSetToArray/unitsArray");
			 System.out.println(e);
		}
		return units;
	}
	
	//result为post信息，一定要保证顺序一致！！！！！
	public List<Post> postsArray(ResultSet result){
		List<Post> posts=new ArrayList<Post>();		 
		try {
			while(result.next()){
				//将搜到的Unit相关信息都存到units列表中
				Post post=new Post(result.getInt(1),result.getString(2),result.getInt(3),result.getInt(4),result.getString(5),result.getInt(6),result.getInt(7));
				System.out.println(post.getPostName());
				posts.add(post);
			}
		} catch (SQLException e) {
			 System.out.println("false in:src/jdbc/ChangeResultSetToArray/postsArray");
			 System.out.println(e);
		}
		return posts;
	}
	//result为专栏类别信息
	public List<PrivateAdType> privateAdTypeArray(ResultSet result){
		List<PrivateAdType> privateAdTypes=new ArrayList<PrivateAdType>();		 
		try {
			while(result.next()){
				//将搜到的Unit相关信息都存到units列表中
				PrivateAdType privateAdType=new PrivateAdType(result.getInt(1),result.getString(2),result.getInt(3));
				System.out.println("adTypeName:"+privateAdType.getAdTypeName());
				privateAdTypes.add(privateAdType);
			}
		} catch (SQLException e) {
			 System.out.println("false in:src/jdbc/ChangeResultSetToArray/privateAdTypes");
			 System.out.println(e);
		}
		return privateAdTypes;
	}
	//result为普通粘贴栏的广告类别
	public List<AdType> adTypesArray(ResultSet result){
		List<AdType> adTypes=new ArrayList<AdType>();		 
		try {
			while(result.next()){
				//将搜到的adType相关信息都存到units列表中
				AdType adType=new AdType(result.getInt(1),result.getString(2));
				//System.out.println(adType.getAdTypeName());
				adTypes.add(adType);
			}
		} catch (SQLException e) {
			 System.out.println("false in:src/jdbc/ChangeResultSetToArray/adTypesArray");
			 System.out.println(e);
		}
		return adTypes;
	}
	//result为专栏的广告类别
	public List<PrivateAdType> privateAdTypesArray(ResultSet result){
		List<PrivateAdType> adTypes=new ArrayList<PrivateAdType>();		 
		try {
			while(result.next()){
				//将搜到的Unit相关信息都存到units列表中
				PrivateAdType adType=new PrivateAdType(result.getInt(1),result.getString(2),result.getInt(3));
				System.out.println("adTypeName:"+result.getString(2));
				adTypes.add(adType);
			}
		} catch (SQLException e) {
			 System.out.println("false in:src/jdbc/ChangeResultSetToArray/privateAdTypesArray");
			 System.out.println(e);
		}
		return adTypes;
	}
	// result为根据ad信息 
	public List<Ad> adsArray(ResultSet result) {
		List<Ad> ads = new ArrayList<Ad>();
		try {
			while (result.next()) { 				 
				Ad ad = new Ad(result.getInt(1), result.getInt(2),
						result.getString(3), result.getInt(4),result.getInt(5), result.getString(6),result.getInt(7),result.getLong(8),result.getInt(9),result.getString(10),result.getInt(11),result.getInt(12),result.getInt(13),result.getInt(14));//通过审核的广告checked属性必为1
				//System.out.println(ad.getAdId());
				ads.add(ad);
			}
		} catch (SQLException e) {
			System.out.println("false in:src/jdbc/ChangeResultSetToArray/adsArray");
			System.out.println(e);
		}
		return ads;
	}
	// result为 privateAd信息 
	public List<PrivateAd> privateAdsArray(ResultSet result) {
		List<PrivateAd> ads = new ArrayList<PrivateAd>();
		try {
			while (result.next()) {
				PrivateAd ad = new PrivateAd(result.getInt(1), result.getInt(2),
						result.getString(3), result.getInt(4),
						result.getInt(5), result.getString(6),
						result.getInt(7), result.getLong(8),
						result.getString(9), result.getInt(10),
						result.getInt(11),result.getInt(12),result.getInt(13));// 通过审核的广告checked属性必为1
				// System.out.println(ad.getAdId());
				ads.add(ad);
			}
		} catch (SQLException e) {
			System.out
					.println("false in:src/jdbc/ChangeResultSetToArray/adsArray");
			System.out.println(e);
		}
		return ads;
	}
	// result为根据adId查找的privateAd
	public List<Pic> picsArray(ResultSet result) {
		List<Pic> pics = new ArrayList<Pic>();
		try {
			while (result.next()) {
				Pic pic = new Pic(result.getInt(1), result.getString(2),
						result.getInt(3), result.getInt(4), result.getInt(5),
						result.getInt(6));// 通过审核的广告checked属性必为1
				// System.out.println(ad.getAdId());
				pics.add(pic);
			}
		}
		catch (SQLException e) {
			System.out.println("false in:src/jdbc/ChangeResultSetToArray/picsArray");
			System.out.println(e);
		}
		return pics;
	}
	// result为根据adId查找的pics
	public List<PrivatePic> privatePicsArray(ResultSet result) {
		List<PrivatePic> pics = new ArrayList<PrivatePic>();
		try {
			while (result.next()) {
				PrivatePic pic = new PrivatePic(result.getInt(1), result.getString(2),
						result.getInt(3), result.getInt(4), result.getInt(5));// 通过审核的广告checked属性必为1
				// System.out.println(ad.getAdId());
				pics.add(pic);
			}
		}
		catch (SQLException e) {
			System.out.println("false in:src/jdbc/ChangeResultSetToArray/picsArray");
			System.out.println(e);
		}
		return pics;
	}
	// result为根据用户信息
	public List<User> usersArray(ResultSet result) {
		List<User> users = new ArrayList<User>();
		try {
			while (result.next()) {
				User user = new User(result.getInt(1), result.getString(2),
						result.getString(3), result.getString(4),
						result.getString(5), result.getInt(6));// 通过审核的广告checked属性必为1
				// System.out.println(ad.getAdId());
				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println("false in:src/jdbc/ChangeResultSetToArray/usersArray");
			System.out.println(e);
		}
		return users;
	}
}
