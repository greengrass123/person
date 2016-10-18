package configurations;

public class Configuration {
	//数据库相关配置
	public final static String DBDriver = "com.mysql.jdbc.Driver";	
	public final static String USERName = "root";
	public final static String PASSWORD = "root";
	public final static String URL = "jdbc:mysql://localhost:3306/post?useUnicode=true&characterEncoding=UTF-8";
	public static int PICNumOFEVERYLOADING=10;//每次加载图片个数
	public static int[] MONEY=new int[]{10,50,100};//交钱档次 	
	
}
