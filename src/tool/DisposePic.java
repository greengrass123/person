package tool;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DisposePic {
	private static BufferedImage InputImage(String srcImgPath) {//读取图片文件
		BufferedImage srcImage = null;
		try {
			// 构造BufferedImage对象
			java.io.File file = new java.io.File(srcImgPath);
			FileInputStream in = new FileInputStream(file);
			byte[] b = new byte[5];
			in.read(b);
			srcImage = javax.imageio.ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("读取图片文件出错！" + e.getMessage());
			e.printStackTrace();
		}
		return srcImage;
	}
	 
   //压缩图片，w,h为要压缩成的大小，srcImgPath为图片所在路径，outImgPath为图片输出路径，per为图片质量，0~1
	public void compress(String srcImgPath, String outImgPath,
			int w, int h, float per) {
		// 得到图片
		BufferedImage src = InputImage(srcImgPath);
		int old_w=src.getWidth(); //得到源图宽 
	    int old_h=src.getHeight(); 
	    System.out.println("old_w:"+old_w+"old_h:"+old_h);
	    int new_w=0; 
	    int new_h=0; //得到源图长 

	    double w2=(old_w*1.00)/(w*1.00); 
	    double h2=(old_h*1.00)/(h*1.00); 

	    //图片跟据长宽留白，成一个正方形图。 
	    BufferedImage oldpic; 
	    if(old_w>old_h){ 
	        oldpic=new BufferedImage(old_w,old_w,BufferedImage.TYPE_INT_RGB);
	     }
	    else{if(old_w<old_h){ 
	        oldpic=new BufferedImage(old_h,old_h,BufferedImage.TYPE_INT_RGB);
	     }else{ 
	         oldpic=new BufferedImage(old_w,old_h,BufferedImage.TYPE_INT_RGB);
	     } 
	    } 
	     Graphics2D g = oldpic.createGraphics(); 
	     g.setColor(Color.white); //留白的颜色
	     if(old_w>old_h) 
	     { 
	         g.fillRect(0, 0, old_w, old_w); 
	         g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null);
	      }else{ 
	         if(old_w<old_h){ 
	         g.fillRect(0,0,old_h,old_h); 
	         g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null);
	          }else{ 
	             //g.fillRect(0,0,old_h,old_h); 
	             g.drawImage(src.getScaledInstance(old_w, old_h,  Image.SCALE_SMOOTH), 0,0,null);
	          } 
	     }              
	     g.dispose(); 
	     src = oldpic; 
	     //图片调整为方形结束 
	    if(old_w>w) 
	    new_w=(int)Math.round(old_w/w2); 
	    else 
	        new_w=old_w; 
	    if(old_h>h) 
	    new_h=(int)Math.round(old_h/h2);//计算新图长宽 
	    else 
	        new_h=old_h; 
		BufferedImage newImg = new BufferedImage(new_w, new_h,
				BufferedImage.TYPE_INT_RGB);
		newImg.getGraphics().drawImage(
				src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0,
				0, null);
		// 调用方法输出图片文件
		OutImage(outImgPath, newImg, per);
	}

	private static void OutImage(String outImgPath, BufferedImage newImg,
			float per) {
		// 判断输出的文件夹路径是否存在，不存在则创建
		java.io.File file = new java.io.File(outImgPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		// 输出到文件流
		try {
			FileOutputStream newimage = new FileOutputStream(outImgPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(newImg);
			// 压缩质量
			jep.setQuality(per, true);
			encoder.encode(newImg, jep);
			newimage.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch blocke.printStackTrace();
		} catch (ImageFormatException e) {
			// TODO Auto-generated catch blocke.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch blocke.printStackTrace();
		}
	}
	
	//将图片加上标题	
	public boolean createMark(String filePath, String printPath,
			String markContent) {
		System.out.println("执行createMark，给登录用户上传广告加标志");
		ImageIcon imgIcon = new ImageIcon(filePath);
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);
		BufferedImage bimage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.setColor(Color.red);
		g.drawImage(theImg, 0, 0, null);
		//Font font = new Font(markContent, Font.BOLD, 30);
		//g.setFont(font);
		g.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER, 0.5f));// 10%透明
		//g.rotate(0.3f); // 文字的旋转角度
		g.drawString(markContent, width / 20, height*9/10);// 绘制水印的位置
		g.dispose();
		try {
			FileOutputStream out = new FileOutputStream(printPath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(100f, true);
			encoder.encode(bimage, param);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
