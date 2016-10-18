package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tool.ChangeResultSetToArray;
import allClasses.Ad;
import allClasses.Post;
import allClasses.PrivateAdType;
import allClasses.User;

//关于用户的操作
public class UserOperation {
	private ChangeResultSetToArray changeResultSetToArray = new ChangeResultSetToArray();
	
	//注册普通用户,向user表插入用户，普通用户的phone默认为空，userType默认为0
	public boolean register(User user) {
		ConnectDB connect = new ConnectDB();
		// System.out.println("执行src/jdbc/SearchFromDB/unitTypeNames()");
		//查找是否已经存在用户用户名、密码为给定值，如果都相同则表示已存在该用户，注册失败
		if(null!=user(user.getUserName(),user.getPassword())){
			System.out.println("jdbc/UserOperation/register，已存在该用户名、密码，注册失败 ");
			return false;
		}
		else{
			//插入用户信息
			String sql="insert into user(userName,password,email,phone,userType) values ('"+user.getUserName()+"','"+user.getPassword()+"','" +user.getEmail()+"','" +user.getPhone()+"','" +user.getUserType()+"')";
			connect.executeUpdate(sql);
			System.out.println("UserOperation/register 注册成功");
			connect.close();
			return true;			
		}	
	}
	//修改用户信息
	public boolean alterUserInformation(User user) {
		ConnectDB connect = new ConnectDB();
		// System.out.println("执行src/jdbc/SearchFromDB/unitTypeNames()");
		// 查找是否已经存在用户用户名、密码为给定值，如果都相同且不是该用户自身则表示已存在其他用户信息跟输入值一样，更新失败
		if (null != user(user.getUserName(), user.getPassword())&& user.getUserId()!=user(user.getUserName(), user.getPassword()).getUserId()) {
			System.out.println("jdbc/UserOperation/alterUserInformation，已存在该用户名、密码，更新失败 ");
			return false;
		} else {
			// 更新用户信息
			String sql ="update user set userName='"+user.getUserName()+"', password='"+user.getPassword()+"',email='" +user.getEmail()+"',phone='" +user.getPhone()+"',userType='" +user.getUserType()+"' where userId='"+user.getUserId()+"'";
			connect.executeUpdate(sql);
			System.out.println("UserOperation/alterMyInformation更新成功");
			connect.close();
			return true;
		}
	}
	
	//判断是否有用户的用户名和密码为输入值
	public User user(String userName,String Password){
		ConnectDB connect = new ConnectDB();
		//查找指定用户名密码的user
		String sql="select * from user where userName='"+userName+"'and password='"+Password+"'";
		ResultSet result=connect.executeQuery(sql);
		List<User> users=changeResultSetToArray.usersArray(result);
		User user=null;
		if(users.size()>1){//如果查到的不止一个用户则显示错误
			System.out.println("jdbc/UserOperation/user错误，不应该有多个用户名密码相同");
		}
		else if(users.size()==1){
			//System.out.println("查找成功");			
			user=users.get(0);
		}
		else{
			System.out.println("jdbc/UserOperation/user，没有该用户名和密码");
		}
		return user;
	}
	
	//返回用户所传的所有广告,包括为审核的、以及发在自己专栏的
	public List<Ad> myAds(int userId){
		ConnectDB connect = new ConnectDB();
		//查找指定用户名密码的user
		String sql="select * from ad where userId='"+userId+"' order by sortValue DESC";
		ResultSet result=connect.executeQuery(sql);
		List<Ad> myAds=changeResultSetToArray.adsArray(result);	
		System.out.println(myAds.size());
		return myAds;
	}
	
	//删除用户所有广告
	public boolean deleteMyAds(int userId) {
		ConnectDB connect = new ConnectDB();
		//删除用户对应所有广告
		String sql = "delete from ad where userId='"+userId+"'";
		boolean delete=connect.executeUpdate(sql); 
		return delete;
	}
	// 删除某个广告
	public boolean deleteAd(int adId) {
		ConnectDB connect = new ConnectDB();
		// 删除指定的广告
		String sql = "delete from ad where adId='"+adId+"'";
		boolean delete=connect.executeUpdate(sql); 
		return delete;
	}
 
	// 删除某个专栏内的广告
	public boolean deletePrivateAd(int adId) {
		ConnectDB connect = new ConnectDB();
		// 删除指定的广告
		String sql = "delete from privatead where adId='" + adId + "'";
		boolean delete = connect.executeUpdate(sql);
		return delete;
	}
	
	// 删除某个专栏内的广告
	public boolean deletePrivateAdsOfType(int adTypeId) {
		ConnectDB connect = new ConnectDB();
		// 删除指定的广告
		String sql = "delete from privatead where adTypeId='" + adTypeId+ "'";
		boolean delete = connect.executeUpdate(sql);
		return delete;
	}
	
 //指定用户关注特定粘贴栏	
	public boolean attention(int userId,int postId) {
		ConnectDB connect = new ConnectDB();
		// System.out.println("执行src/jdbc/SearchFromDB/unitTypeNames()");
		// 查找是否该用户已经关注过此粘贴栏
		String sql="select * from attention where userId='"+userId+"'and postId='"+postId+"'";
		ResultSet result=connect.executeQuery(sql);
		try {
			if(result.next()){//如果能够查找到，则已经关注
				System.out.println("已经关注");
			}
			else{
				sql="insert into attention(userId,postId) values('"+userId+"','"+postId+"') ";
				connect.executeUpdate(sql);
				System.out.println("成功");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connect.close();
		return true;		
	}
	
	// 删除某个用户的某个关注
	public boolean deleteMyAttention(int userId, int postId) {
		ConnectDB connect = new ConnectDB();
		// 删除指定的广告
		String sql = "delete from attention where userId='"+userId+"'and postId='"+postId+"'";
		boolean delete = connect.executeUpdate(sql);
		System.out.println("删除："+delete);
		return delete;
	}
	
	// 删除专栏内某个广告类别
	public boolean deleteAdType( int postId,int adTypeId) {
		ConnectDB connect = new ConnectDB();
		// 删除指定的广告
		String sql = "delete from privateAdType where typeId='"+adTypeId+"'and postId='"+postId+"'";
		boolean delete = connect.executeUpdate(sql);
		System.out.println("删除："+delete);
		return delete;
	}
	
	// 增加专栏内广告类别
	public boolean addAdType(int postId, String adTypeName) {
		ConnectDB connect = new ConnectDB();
		// 删除指定的广告
		String sql = "insert into privateAdType(typeName,postId) values('"+adTypeName+"','"+postId+"')";
		boolean delete = connect.executeUpdate(sql);
		System.out.println("添加：" + delete);
		return delete;
	}

	//查找某个用户所关注的所有粘贴栏
	public List<Post> myAttentions(int userId) {
		List<Post> posts=new ArrayList<Post>();
		ConnectDB connect = new ConnectDB(); 
		//查找用户的所有关注信息
		String sql="select * from attention where userId='"+userId+"'";
		ResultSet result=connect.executeQuery(sql);
		try {
			while(result.next()){
				int postId=result.getInt(3);//获取postId
				Post post=new SearchAboutPost().postOfId(postId);//根据id获取粘贴栏信息
				System.out.println(post.getPostName());
				posts.add(post);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("false in:UserOperation/myAttentions");
		}
		
		connect.close();
		return posts;		
	}
	
	// 查找某个用户的专栏
	public List<Post> privatePosts(int userId) {	 
		ConnectDB connect = new ConnectDB();
		// 查找用户的所有关注信息
		String sql = "select * from post where userId='" + userId + "'";
		ResultSet result = connect.executeQuery(sql);
		List<Post> posts=changeResultSetToArray.postsArray(result);	
		connect.close();
		return posts;
	}
	
	// 查找某个专栏类别ID对应的类别
	public PrivateAdType privateAdTypeOfId(int adTypeId) {	 
		ConnectDB connect = new ConnectDB();
		System.out.println("执行privateAdTypeOfId，adTypeId:"+adTypeId);
		// 查找用户的所有关注信息
		String sql = "select * from privateAdType where typeId='" +adTypeId + "'";
		ResultSet result = connect.executeQuery(sql);
		List<PrivateAdType> privateAdTypes=changeResultSetToArray.privateAdTypeArray(result);
		if(privateAdTypes.size()>1){
			System.out.println("一个类别id不可能对应多个类别");
			return null;
		}
		connect.close();
		return privateAdTypes.get(0);
	}
	
	public static void main(String[] args) {
		UserOperation user=new UserOperation();
		//user.alterUserInformation(new User(42,"zz","123456","23@qq.com","12332132451",0));
		//user.user("zly","123");
		//user.myAds(13);
		//user.deleteMyAds(1);
		//user.attention(1,2);
		//user.myAttentions(2);
		//user.privatePosts(13);
		//user.deleteMyAttention(2,1);
		//user.addAdType(21,"手表");
		user.privateAdTypeOfId(12);
	}
	
	//注册用户关注某个粘贴栏，插入attention表前先判断是否关注过，已关注则不用更新表
	
	
}
