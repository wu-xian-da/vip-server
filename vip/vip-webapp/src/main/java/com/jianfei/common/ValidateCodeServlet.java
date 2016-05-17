package com.jianfei.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 生成随机验证码
* @author ZhangBo   
* @date 2015年6月19日 下午1:47:56
 */
public class ValidateCodeServlet extends HttpServlet {
	
    private static final long serialVersionUID = -7903416276888617833L;

    public static final String VALIDATE_CODE = "validateCode";
	
	private int width;
	private int height;
	private int chars;
	
    @Override
    public void init(ServletConfig config) throws ServletException {
        String width = config.getInitParameter("width");
        String height = config.getInitParameter("height");
        String chars = config.getInitParameter("chars");
        
        this.setInitParams(width, height, chars);
        super.init(config);
    }
    
    private void setInitParams(String width, String height, String chars){
        if (StringUtils.isNumeric(width) && StringUtils.isNumeric(height) && 
                StringUtils.isNumeric(chars)) {
            this.width = NumberUtils.toInt(width);
            this.height = NumberUtils.toInt(height);
            this.chars = NumberUtils.toInt(chars);
        }
    }
	
	public static boolean validate(HttpServletRequest request, String validateCode){
		String code = (String)request.getSession().getAttribute(VALIDATE_CODE);
		return validateCode.toUpperCase().equals(code);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		createImage(request,response);
	}
	
	private void createImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		
		//得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		String chars = request.getParameter("chars");
		this.setInitParams(width, height, chars);
		
		BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		//生成背景
		createBackground(g);

		//生成字符
		String s = createCharacter(g);
		request.getSession().setAttribute(VALIDATE_CODE, s);

		g.dispose();
		OutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.close();
	}
	
	private Color getRandColor(int fc,int bc) { 
		int f = fc;
		int b = bc;
		Random random=new Random();
        if(f>255) {
        	f=255; 
        }
        if(b>255) {
        	b=255; 
        }
        return new Color(f+random.nextInt(b-f),f+random.nextInt(b-f),f+random.nextInt(b-f)); 
	}
	
	private void createBackground(Graphics g) {
		// 填充背景
		g.setColor(getRandColor(220,250)); 
		g.fillRect(0, 0, width, height);
		// 加入干扰线条
		for (int i = 0; i < 10; i++) {
			g.setColor(getRandColor(40,150));
			Random random = new Random();
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			g.drawLine(x, y, x1, y1);
		}
	}

	private String createCharacter(Graphics g) {
		char[] codeSeq = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
				'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
		String[] fontTypes = {"Arial","Arial Black","AvantGarde Bk BT","Calibri"};
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < this.chars; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);//random.nextInt(10));
			g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)],Font.BOLD, 26)); 
			g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
//			g.drawString(r, i*w/4, h-5);
			s.append(r);
		}
		return s.toString();
	}
	
	public ValidateCodeServlet() {
        super();
    }
    
    public void destroy() {
        super.destroy(); 
    }
	
}
