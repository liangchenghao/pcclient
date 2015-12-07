package com.chenlian.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	//byte[]转Int
    public static int bytesToInt(byte[] bytes) {  
        int addr = bytes[0] & 0xFF;  
        addr |= ((bytes[1] << 8) & 0xFF00);  
        addr |= ((bytes[2] << 16) & 0xFF0000);  
        addr |= ((bytes[3] << 24) & 0xFF000000);  
        return addr;  
    }  
  
    //Int转byte[] 
    public static byte[] intToByte(int i) {  
        byte[] abyte0 = new byte[4];  
        abyte0[0] = (byte) (0xff & i);  
        abyte0[1] = (byte) ((0xff00 & i) >> 8);  
        abyte0[2] = (byte) ((0xff0000 & i) >> 16);  
        abyte0[3] = (byte) ((0xff000000 & i) >> 24);  
        return abyte0;  
    }
    
    //是否为手机号
    public static boolean isMobileNO(String mobiles){
    	System.out.println(mobiles);
    	Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
    	Matcher m = p.matcher(mobiles);
    	System.out.println("isNumber:" + m.matches());  
    	return m.matches(); 
    }
    
  //是否为网站
    public static boolean isUrl(String url){
    	System.out.println(url);
    	Pattern p = Pattern.compile(
    			"^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");  
    	Matcher m = p.matcher(url);
    	System.out.println("isUrl:" + m.matches());  
    	return m.matches(); 
    }
}
