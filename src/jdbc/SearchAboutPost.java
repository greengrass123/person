package jdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 

import tool.ChangeResultSetToArray;
import allClasses.*;
//粘贴栏相关的查找工作，ResultSet为空不影响查找结果，但在对结果应用的地方应该判断
public class SearchAboutPost {
	
	private ChangeResultSetToArray changeResultSetToArray = new ChangeResultSetToArray();
	// 返回所有单位类别信息，包括类别名以及名称
	public List<UnitType> unitTypes() {
		ConnectDB connect = new ConnectDB();
		// System.out.println("执行src/jdbc/SearchFromDB/unitTypeNames()");
		String sql = "select * from unitType";
		ResultSet result = connect.executeQuery(sql);
		List<UnitType> unitTypes = changeResultSetToArray.unitTypeArrays(result);
		connect.close();
		return unitTypes;

	}
    
	
	
	// 查找传入类别下的所有单位
	public List<Unit> unitsOfType(int unitTypeId) {
		// System.out.println("执行src/jdbc/SearchFromDB/unitsOfType(),传入的unitTypeId为："+unitTypeId);
		ConnectDB connect = new ConnectDB();
		String sql = "select * from unit where unitTypeId=" + unitTypeId;
		ResultSet result = connect.executeQuery(sql);
		List<Unit> units = changeResultSetToArray.unitsArray(result);
		connect.close();
		System.out.println("unitTypeId:"+unitTypeId+" unitsOfType:"+units.size());
		return units;
	}
	
	// 查找传入类别下的所有包含非专栏的单位
	public List<Unit> unitsWithPublicPost(int unitTypeId) {
		System.out.println("执行src/jdbc/SearchFromDB/unitsOfType(),传入的unitTypeId为："+unitTypeId);
		ConnectDB connect = new ConnectDB();
		String sql = "select * from unit where unitTypeId=" + unitTypeId;
		ResultSet result = connect.executeQuery(sql);
		List<Unit> units = new ArrayList<Unit>();
		try {
			while(result.next()){				 
				int unitId=result.getInt(1);//获取unitId
				List<Post> publicPosts=publicPostsOfUnit(unitId);//获取对应单位下所有非专栏
				System.out.println("unitId:"+unitId);
				if(publicPosts.size()>0){//如果单位下有非专栏，则添加到列表中
					Unit unit=new Unit(result.getInt(1),result.getString(2),result.getInt(3)); 	
					units.add(unit);
				}
			}
		} catch (SQLException e) {
			 System.out.println("false in:src/jdbc/ChangeResultSetToArray/postsArray");
			 System.out.println(e);
		} 
		System.out.println("unitTypeId:"+unitTypeId+" unitsWithPublicPost:"+units.size());
		connect.close();
		return units;
	}
	
	// 查找传入单位下所有粘贴栏
	public List<Post> postsOfUnit(int unitId) {
		// System.out.println("执行src/jdbc/SearchFromDB/postsOfUnit(),传入的unitId为："+unitId);
		ConnectDB connect = new ConnectDB();
		String sql = "select * from post where unitId=" + unitId;
		ResultSet result = connect.executeQuery(sql);		
		List<Post> posts = changeResultSetToArray.postsArray(result);
		connect.close();
		//System.out.println("unitId:"+unitId+"postsOfUnit:"+posts.size());
		return posts;

	}
	
	// 查找传入单位下所有非专栏粘贴栏
	public List<Post> publicPostsOfUnit(int unitId) {
		// System.out.println("执行src/jdbc/SearchFromDB/postsOfUnit(),传入的unitId为："+unitId);
		ConnectDB connect = new ConnectDB();
		String sql = "select * from post where userId<=0 and unitId=" + unitId;
		ResultSet result = connect.executeQuery(sql);
		List<Post> posts = changeResultSetToArray.postsArray(result);
		System.out.println("unitId:"+unitId+"publicPostsOfUnit:"+posts.size());
		connect.close();
		return posts;
	}
	
	// 返回普通粘贴栏下的所有广告类别
	public List<AdType> adTypes() {
		ConnectDB connect = new ConnectDB();
		String sql = "select * from adType";
		ResultSet result = connect.executeQuery(sql);
		List<AdType> adTypes = changeResultSetToArray.adTypesArray(result);
		connect.close();
		return adTypes;
	}
	
	// 返回某个专栏下的所有广告类别
	public List<PrivateAdType> privateAdTypes(int postId) {
		ConnectDB connect = new ConnectDB();
		String sql = "select * from privateAdType where postId='"+postId+"'";
		ResultSet result = connect.executeQuery(sql);
		List<PrivateAdType> adTypes = changeResultSetToArray.privateAdTypesArray(result);
		connect.close();
		return adTypes;
	}
	
	

	//select * from table order by id limit m, n; 该语句的意思为，查询m+n条记录，去掉前m条，返回后n条记录
//当m更大的时候，较为高效的方法：select * from table where id > (select id from table order by id limit m, 1) limit n;
	// 查找指定非专栏粘贴栏下第m~n条广告
	public List<Ad> adsOfPost(int postId,int m,int n) {
		// System.out.println("执行src/jdbc/SearchFromDB/adsOfPost(),传入的postId为："+postId);
		ConnectDB connect = new ConnectDB();
		//返回通过审核的广告并且按时间排序
		String sql = "select * from ad where exist=1 and postId='" + postId+"' and checked=1 order by sortValue DESC limit "+m+","+n;		 
		ResultSet result = connect.executeQuery(sql);
		List<Ad> ads = changeResultSetToArray.adsArray(result);
		connect.close();
		System.out.println(ads.size());
		return ads;
	}
	
	// 查找指定非专栏粘贴栏指定类别下m~n条广告
	public List<Ad> adsOfPost(int postId,int adTypeId,int m,int n) {
		// System.out.println("执行src/jdbc/SearchFromDB/adsOfPost(),传入的postId为："+postId);	
		if(adTypeId>0){
			ConnectDB connect = new ConnectDB();
			//返回通过审核的广告并且按时间排序
		 	String sql = "select * from ad where exist=1 and postId='" + postId+"' and adTypeId='"+adTypeId+"'and checked=1 order by sortValue  DESC limit "+m+","+n;
			ResultSet result = connect.executeQuery(sql);
			System.out.println("result:"+result);
			List<Ad> ads = changeResultSetToArray.adsArray(result);
			connect.close();
			System.out.println(ads.size());
			return ads;
		}
		else{
			System.out.println("src/jdbc/SearchFromDB/adsOfPost(),传入的postId为："+postId);			
			return null;
		}
	}
	
	// 查找指定非专栏广告下所有图片       
	public List<Pic> picsOfAd(int adId) {
		// System.out.println("执行src/jdbc/SearchFromDB/adsOfPost(),传入的postId为："+postId);

		ConnectDB connect = new ConnectDB();
		// 返回通过审核的广告并且按时间排序
		String sql = "select * from pic where  pic.adId in(select ad.adId from ad where exist=1) and pic.adId='" + adId
				+ "'and checked=1 ";
		ResultSet result = connect.executeQuery(sql);
		// System.out.println("result:" + result);
		List<Pic> pics = changeResultSetToArray.picsArray(result);
		connect.close();
		// System.out.println(pics.size());
		return pics;

	}
	public void updateClick(String sql ){
	    ConnectDB connectDB=new ConnectDB();
	    boolean flag=connectDB.executeUpdate(sql);
	    connectDB.close();
	}
	 
		
	// 查找指定专栏下第m~n条广告
	public List<PrivateAd> adsOfPrivatePost(int postId,int m,int n) {
		// System.out.println("执行src/jdbc/SearchFromDB/adsOfPost(),传入的postId为："+postId);
		ConnectDB connect = new ConnectDB();
		//返回通过审核的广告并且按时间排序
		String sql = "select * from privateAd where exist=1and postId='" + postId+"' order by sortValue DESC limit "+m+","+n;		 
		ResultSet result = connect.executeQuery(sql);
		List<PrivateAd> ads = changeResultSetToArray.privateAdsArray(result);
		connect.close();
		System.out.println(ads.size());
		return ads;
	}
	
	// 查找指定专栏粘贴栏指定类别下m~n条广告
	public List<PrivateAd> adsOfPrivatePost(int postId,int adTypeId,int m,int n) {
		// System.out.println("执行src/jdbc/SearchFromDB/adsOfPost(),传入的postId为："+postId);	
		if(adTypeId>0){
			ConnectDB connect = new ConnectDB();
			//返回通过审核的广告并且按时间排序
		 	String sql = "select * from privateAd where  exist=1and postId='" + postId+"' and adTypeId='"+adTypeId+"' order by sortValue  DESC limit "+m+","+n;
			ResultSet result = connect.executeQuery(sql);
			System.out.println("result:"+result);
			List<PrivateAd> ads = changeResultSetToArray.privateAdsArray(result);
			connect.close();
			System.out.println(ads.size());
			return ads;
		}
		else{
			System.out.println("src/jdbc/SearchFromDB/PrivateadsOfPost(),传入的postId为："+postId);			
			return null;
		}
	}
	
	// 查找指定专栏广告下所有图片       
	public List<PrivatePic> picsOfPrivateAd(int adId) {
		// System.out.println("执行src/jdbc/SearchFromDB/adsOfPost(),传入的postId为："+postId);

		ConnectDB connect = new ConnectDB();
		// 返回通过审核的广告并且按时间排序
		String sql = "select * from privatePic where  privatePic.adId in(select privatead.adId from ad where exist=1) and privatePic.adId='" + adId
				+ "'";
		ResultSet result = connect.executeQuery(sql);
		// System.out.println("result:" + result);
		List<PrivatePic> pics = changeResultSetToArray.privatePicsArray(result);
		connect.close();
		// System.out.println(pics.size());
		return pics;

	}
	// 查找包含指定字符串的单位和粘贴栏，如果是单位中包含，则同时返回单位下所有粘贴栏
	public List<Map<String, List<Post>>> postsContaintText(String text) {
		// System.out.println("执行src/jdbc/SearchFromDB/adsOfPost(),传入的postId为："+postId);
		ConnectDB connect = new ConnectDB();
		List<Map<String, List<Post>>> posts=new ArrayList<Map<String, List<Post>>>();
		String sql="select * from unit";//查找所有的单位
		ResultSet result=connect.executeQuery(sql);
		try {
			while(result.next()){
				int unitId=result.getInt(1);//获取单位Id
				String unitName=result.getString(2);//获取单位名		
				String unit=unitId+"_"+unitName;
				if(unitName.contains(text)){//如果单位中包含该字符串，则返回此单位和单位下所有粘贴栏
					System.out.println("unit"+unit);				
					List<Post> post=postsOfUnit(unitId);//返回单位下所有粘贴栏
					Map<String, List<Post>> unitsAndPost=new HashMap<String, List<Post>>();
					unitsAndPost.put(unit,post);//将单位跟粘贴栏放在Map中
					posts.add(unitsAndPost);//将map添加到list
				}
				else{//如果单位中不包含该字符串，则判断单位下是否有粘贴栏名包含该自段
					List<Post> postsOfUnit=postsOfUnit(unitId);//返回单位下所有粘贴栏				
					if(postsOfUnit.size()>0){
						List<Post> postsContainText=new ArrayList<Post>();//用来存储包含指定字段的粘贴栏名
						for(int i=0;i<postsOfUnit.size();i++){
							String postName=postsOfUnit.get(i).getPostName();//获取粘贴栏名
							if(postName.contains(text)){//如果粘贴栏中包含字段则加入List
								postsContainText.add(postsOfUnit.get(i));
								System.out.println(unitName+"postName:"+postName);
							}						
						}	
						if(postsContainText.size()>0){//如果有粘贴栏包含该字段，则将单位跟包含字段的粘贴栏放在map
							Map<String, List<Post>> unitsAndPost=new HashMap<String, List<Post>>();
							unitsAndPost.put(unit,postsContainText);//将单位跟粘贴栏放在Map中
							posts.add(unitsAndPost);//将map添加到list
						}
					}					
				}
			}			
		} 
		catch (SQLException e) {
			System.out.println("false in:src/jdbc/SearchFromDB/postsContaintText");
			e.printStackTrace();
		} 
		System.out.println("posts.size()"+posts.size());
		 return posts;
	}

	//查找用户所拥有的的粘贴栏
    public List<Post> postsOfUser(int userId){
    	ConnectDB connect = new ConnectDB(); 
    	String sql="select * from post where userId='"+userId+"'";
    	ResultSet result=connect.executeQuery(sql);
    	List<Post> posts=changeResultSetToArray.postsArray(result);
    	connect.close();
    	return posts;
    	
    }
    
    //返回指定id的粘贴栏信息
    public Post postOfId(int postId){
    	ConnectDB connect = new ConnectDB(); 
    	String sql="select * from post where postId='"+postId+"'";
    	ResultSet result=connect.executeQuery(sql);
    	List<Post> posts=changeResultSetToArray.postsArray(result);
    	Post post=new Post();
    	if(posts.size()>1){//如果根据postId查出的单位栏超过一个，则出现错误
    		System.out.println("false in:SearchAboutPost/postId,一个postId对应的粘贴栏不可能为多个");
    	}
    	else{
    		post=posts.get(0);//只有一个则获取第一个
    		connect.close();
    	}    
    	return post;
    }
    
    //将post对应的访问量增加
    public boolean updateVisitors(int postId){
    	ConnectDB connect = new ConnectDB(); 
    	String sql="update post set visitorsOfToday=visitorsOfToday+1,allVisitors=allVisitors+1 where postId='"+postId+"'";
    	boolean isUpdate=connect.executeUpdate(sql);
    	System.out.println("isUpdate:"+isUpdate);
    	return isUpdate; 
     
    }
   
    //返回指定id的单位信息
    public Unit unitOfId(int unitId){
    	ConnectDB connect = new ConnectDB(); 
    	String sql="select * from unit where unitId='"+unitId+"'";
    	ResultSet result=connect.executeQuery(sql);
    	List<Unit> units=changeResultSetToArray.unitsArray(result);
    	Unit unit=new Unit();
    	if(units.size()>1){//如果根据postId查出的单位栏超过一个，则出现错误
    		System.out.println("false in:SearchAboutPost/postId,一个postId对应的粘贴栏不可能为多个");
    	}
    	else{
    		unit=units.get(0);//只有一个则获取第一个
    		connect.close();
    	}    
    	return unit;
    }
    //返回当前的最大广告id
    public int maxAdId(){
    	ConnectDB connect = new ConnectDB(); 
    	String sql="select max(adId) from ad where exist=1 ";
    	ResultSet result=connect.executeQuery(sql);
    	int maxAdId=0;
    	try {
			while(result.next()){
				maxAdId=result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("maxAdId:"+maxAdId);
    	return maxAdId;
    }    
    //返回当前的最大专帖栏广告id
    public int maxPrivateAdId(){
    	ConnectDB connect = new ConnectDB(); 
    	String sql="select max(adId) from privateAd where exist=1 ";
    	ResultSet result=connect.executeQuery(sql);
    	int maxAdId=0;
    	try {
			while(result.next()){
				maxAdId=result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("maxAdId:"+maxAdId);
    	return maxAdId;
    }
	
    //存储广告
    public boolean saveAd(Ad ad){
    	ConnectDB connect = new ConnectDB(); 
    	System.out.println("ad.getFirstPicAddr():"+ad.getFirstPicAddr());
    	String sql="insert into ad(adId,adTypeId,upLoadTime,userId,postId,firstPicAddr,money,sortValue,checked,remark,height,width) " +
    			"values('"+ad.getAdId()+"','"+ad.getAdTypeId()+"','"+ad.getUpLoadTime()+"','"+ad.getUserId()
    			+"','"+ad.getPostId()+"','"+ad.getFirstPicAddr()+"','"+ad.getMoney()+"','"+ad.getSortValue()+"','"+ad.getChecked()+"','"+ad.getRemark()+"','"+ad.getHeight()+"','"+ad.getWidth()+"')";
    	
    	boolean isSave=connect.executeUpdate(sql);
    	System.out.println("isSave:"+isSave);
    	return isSave;
    }
    //存储图片
    public boolean savePic(Pic pic){
    	ConnectDB connect = new ConnectDB(); 
    	System.out.println("pic.getPicAddr():"+pic.getPicAddr());
    	String sql="insert into pic(picAddr,width,height,checked,adId) " +
    			"values('"+pic.getPicAddr()+"','"+pic.getWidth()+"','"+pic.getHeight()+"','"+pic.getChecked()
    			+"','"+pic.getAdId()+"')";
    	boolean isSave=connect.executeUpdate(sql);
    	System.out.println("isSave:"+isSave);
    	return isSave;
    }
    //存储专栏图片
    public boolean savePrivatePic(PrivatePic pic){
    	ConnectDB connect = new ConnectDB(); 
    	System.out.println("pic.getPicAddr():"+pic.getPicAddr());
    	String sql="insert into privatepic(picAddr,width,height,adId) " +
    			"values('"+pic.getPicAddr()+"','"+pic.getWidth()+"','"+pic.getHeight()+"','"+pic.getAdId()+"')";
    	boolean isSave=connect.executeUpdate(sql);
    	System.out.println("isSave:"+isSave);
    	return isSave;
    }
   
    //存储专栏广告
    public boolean savePrivateAd(PrivateAd ad){
    	ConnectDB connect = new ConnectDB(); 
    	System.out.println("ad.getRemark():"+ad.getRemark());
    	String sql="insert into privatead(adId,adTypeId,upLoadTime,userId,postId,firstPicAddr,money,sortValue,remark,height,width) " +
    			"values('"+ad.getAdId()+"','"+ad.getAdTypeId()+"','"+ad.getUpLoadTime()+"','"+ad.getUserId()
    			+"','"+ad.getPostId()+"','"+ad.getFirstPicAddr()+"','"+ad.getMoney()+"','"+ad.getSortValue()+"','"+ad.getRemark()+"','"+ad.getHeight()+"','"+ad.getWidth()+"')";
    	
    	boolean isSave=connect.executeUpdate(sql);
    	System.out.println("isSave:"+isSave);
    	return isSave;
    }
  

    //返回指定专栏下所有广告
	public List<PrivateAd> adsOfPrivatePost(int postId) {
		// System.out.println("执行src/jdbc/SearchFromDB/adsOfPost(),传入的postId为："+postId);
		ConnectDB connect = new ConnectDB();
		//返回通过审核的广告并且按时间排序
		String sql = "select * from privatead where postId='" + postId+"' order by sortValue  DESC";		 
		ResultSet result = connect.executeQuery(sql);
		List<PrivateAd> ads = changeResultSetToArray.privateAdsArray(result);
		connect.close();
		System.out.println(ads.size());
		return ads;
	}
	
	  //返回指定id的广告信息
    public Ad adOfId(int adId){
    	ConnectDB connect = new ConnectDB(); 
    	String sql="select * from ad where adId='"+adId+"'";
    	ResultSet result=connect.executeQuery(sql);
    	List<Ad> ads=changeResultSetToArray.adsArray(result);
    	Ad ad=new Ad();
    	if(ads.size()>1){//如果根据postId查出的单位栏超过一个，则出现错误
    		System.out.println("false in:SearchAboutPost/adOfId,一个adId对应的粘贴栏不可能为多个");
    	}
    	else{
    		ad=ads.get(0);//只有一个则获取第一个
    		connect.close();
    	}    
    	return ad;
    }
    //返回指定id的单位类别
    public AdType adTypeOfId(int adTypeId){
    	ConnectDB connect = new ConnectDB(); 
    	System.out.println("adTypeId:"+adTypeId);
    	String sql="select * from adType where adTypeId='"+adTypeId+"'";
    	ResultSet result=connect.executeQuery(sql);
    	List<AdType> adTypes=changeResultSetToArray.adTypesArray(result);
    	AdType adType=new AdType();
    	if(adTypes.size()>1){//如果根据postId查出的单位栏超过一个，则出现错误
    		System.out.println("false in:SearchAboutPost/adTypeId,一个adTypeId对应的粘贴栏不可能为多个");
    	}
    	else{
    		adType=adTypes.get(0);//只有一个则获取第一个
    		connect.close();
    	}   
    	System.out.println("adTypeName:"+adType.getAdTypeName());
    	return adType;
    }
    //返回指定id的专栏单位类别
    public PrivateAdType privateAdTypeOfId(int adTypeId){
    	ConnectDB connect = new ConnectDB(); 
    	System.out.println("adTypeId:"+adTypeId);
    	String sql="select * from privateAdType where typeId='"+adTypeId+"'";
    	ResultSet result=connect.executeQuery(sql);
    	List<PrivateAdType> adTypes=changeResultSetToArray.privateAdTypesArray(result);
    	PrivateAdType adType=new PrivateAdType();
    	if(adTypes.size()>1){//如果根据postId查出的单位栏超过一个，则出现错误
    		System.out.println("false in:SearchAboutPost/adTypeId,一个adTypeId对应的粘贴栏不可能为多个");
    	}
    	else if(adTypes.size()==0){
    		System.out.println("false in:SearchAboutPost/adTypeId,没有对应的类别");
    	}
    	else{
    		adType=adTypes.get(0);//只有一个则获取第一个
    		connect.close();
    	}   
    	System.out.println("adTypeName:"+adType.getAdTypeName());
    	return adType;
    }
    
    //更新指定专栏广告的排序值
    public boolean updateSortValueOfPrivateAds(int adId,long sortValue){
    	ConnectDB connect = new ConnectDB(); 
    	String sql="update privatead set sortValue='"+sortValue+"' where adId='"+adId+"'";
    	boolean isUpdate=connect.executeUpdate(sql);
    	System.out.println("isUpdate:"+isUpdate);
    	return isUpdate; 
     
    }
 public static void main(String[] args) throws SQLException {
		SearchAboutPost search=new SearchAboutPost();
		//search.unitsOfType(1);
		// new SearchAboutPost().unitsWithPublicPost(1);
		// new SearchAboutPost().unitTypes();
		//new SearchAboutPost().postsOfUnit(11);
		//new SearchAboutPost().publicPostsOfUnit(11);
		//new SearchAboutPost().adsOfPost(11,1,0,10);
		//new SearchAboutPost().adsOfPost(11,10,20);
		//new SearchAboutPost().picsOfAd(430);
		// search.postsContaintText("12");
		//search.postsOfUser(12);
		//search.privateAdTypes(21);
		//search.maxAdId();
		//search.updateVisitors(4);
		//search.adOfId(4);
		//search.updateSortValue(4,001234);
		//search.adsOfPrivatePost(1,1,0,2);
		//search.picsOfPrivateAd(1);
		//search.adsOfPrivatePost(21);
		//search.adTypeOfId(1);
		search.privateAdTypeOfId(1);
	}


}
