/**
 * Copyright 2014. www.sinovatech.com Inc. All rights reserved.
 * qrcode
 * 2014年12月16日 下午6:31:41
 */
package org.springrain.sinova.util;

/** 
 * @作者   Relieved
 * @创建日期   2015年1月15日
 * @描述  （利用QrCode生成二维码） 
 * @版本 V 1.0
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.common.BaseLogger;



/**
 * 二维码生成器工具类
 */
public class QrcodeUtils {
    /**
     * 生成二维码(QRCode)图片
     * 
     * @param content
     * @param imgPath
     */
   /* public static void encoderQRCode(String content, String imgPath,String ccbPath) {
        try {

            Qrcode qrcodeHandler = new Qrcode();
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
            qrcodeHandler.setQrcodeErrorCorrect('M');
            // N代表数字,A代表字符a-Z,B代表其他字符
            qrcodeHandler.setQrcodeEncodeMode('B');
            // N代表数字,A代表字符a-Z,B代表其他字符
            qrcodeHandler.setQrcodeVersion(8);

            System.out.println(content);
            byte[] contentBytes = content.getBytes("gb2312");

            BufferedImage bufImg = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
            Graphics2D gs = bufImg.createGraphics();

            gs.setBackground(Color.WHITE);
            gs.clearRect(0, 0, 150, 150);

            // 设定图像颜色 > BLACK
            gs.setColor(Color.BLACK);

            // 设置偏移量 不设置可能导致解析出错
            int pixoff = 2;
            // 输出内容 > 二维码
            if (contentBytes.length > 0 && contentBytes.length < 130) {
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
                for (int i = 0; i < codeOut.length; i++) {
                    for (int j = 0; j < codeOut.length; j++) {
                        if (codeOut[j][i]) {
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                        }
                    }
                }
            } else {
                System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,130 ]. ");
            }
            Image logo = ImageIO.read(new File(ccbPath));//实例化一个Image对象。  
            int widthLogo = logo.getWidth(null)>bufImg.getWidth()*2/10?(bufImg.getWidth()*2/10):logo.getWidth(null),   
                heightLogo = logo.getHeight(null)>bufImg.getHeight()*2/10?(bufImg.getHeight()*2/10):logo.getWidth(null);  
            int widthLogo =35,heightLogo=35;
            		
             
             *//** 
               * logo放在中心   
              *//*  
            int x = (bufImg.getWidth() - widthLogo) / 2;  
            int y = (bufImg.getHeight() - heightLogo) / 2;  
            gs.drawImage(logo, x, y, widthLogo, heightLogo, null);  
            gs.dispose();    
            bufImg.flush();    
    
            // 生成二维码QRCode图片    
            File imgFile = new File(imgPath);    
            ImageIO.write(bufImg, "png", imgFile);    

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void main(String[] args) {
        String imgPath = "D:/workspace/122.png";

        String content = "www.baidu.com";
        String aa="D:/workspace/logo.png";
        QrcodeUtils handler = new QrcodeUtils();
        handler.encoderQRCode(content, imgPath,aa);

        System.out.println("encoder QRcode success");
    }*/
}
