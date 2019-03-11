package Test;

import com.jrwp.core.utils.JacksonUtil;
import com.jrwp.wx.entity.ModelJson;
import com.jrwp.wx.entity.TestGhInfo;
import com.jrwp.wx.until.DesUtil;
import com.jrwp.wx.until.Jpush;
import com.jrwp.wx.until.Utils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class Demo {
	//private static final String  SKEY    = "12345678";
    //private static final Charset CHARSET = Charset.forName("gb2312");
    public static void main(String[] args) {

        //设置推送参数
        //这里同学们就可以自定义推送参数了
       /* Map<String, String> parm = new HashMap<String, String>();
        //设备id,指定设备推送id
        parm.put("id", "13165ffa4e594a31ed1");
        //设置提示信息,内容是文章标题
        parm.put("msg", "{\"stateType\":0,\"stateValue\":\"[{\\\"state\\\":\\\"0\\\",\\\"name\\\":\\\"刘浩\\\"," +
                "\\\"queueNumber\\\":\\\"B022\\\",\\\"windowNumber\\\":null,\\\"deptid\\\":2060},{\\\"state\\\":\\\"0\\\"," +
                "\\\"name\\\":\\\"晓风\\\",\\\"queueNumber\\\":\\\"B023\\\",\\\"windowNumber\\\":null,\\\"deptid\\\":2060}," +
                "{\\\"state\\\":\\\"0\\\",\\\"name\\\":\\\"彭国钦\\\",\\\"queueNumber\\\":\\\"B024\\\",\\\"windowNumber\\\":null," +
                "\\\"deptid\\\":2060},{\\\"state\\\":\\\"0\\\",\\\"name\\\":\\\"李林\\\",\\\"queueNumber\\\":\\\"B025\\\"," +
                "\\\"windowNumber\\\":null,\\\"deptid\\\":2060},{\\\"state\\\":\\\"0\\\",\\\"name\\\":\\\"刘俊峰\\\"," +
                "\\\"queueNumber\\\":\\\"B026\\\",\\\"windowNumber\\\":null,\\\"deptid\\\":2060},{\\\"state\\\":\\\"3\\\"," +
                "\\\"name\\\":\\\"张洋\\\",\\\"queueNumber\\\":\\\"B011\\\",\\\"windowNumber\\\":\\\"1\\\",\\\"deptid\\\":2060}," +
                "{\\\"state\\\":\\\"3\\\",\\\"name\\\":\\\"张洋\\\",\\\"queueNumber\\\":\\\"B010\\\",\\\"windowNumber\\\":\\\"3\\\"," +
                "\\\"deptid\\\":2060},{\\\"state\\\":\\\"3\\\",\\\"name\\\":\\\"张建国\\\",\\\"queueNumber\\\":\\\"B009\\\"," +
                "\\\"windowNumber\\\":\\\"3\\\",\\\"deptid\\\":2060},{\\\"state\\\":\\\"3\\\",\\\"name\\\":\\\"彭国钦\\\"," +
                "\\\"queueNumber\\\":\\\"B001\\\",\\\"windowNumber\\\":\\\"4\\\",\\\"deptid\\\":2060}]\",\"stateMsg\":\"获取成功\"," +
                "\"url\":null,\"validationResults\":null}");
        Jpush.jpushAndroid(parm);*/
    	/*Map<String,String> param = new HashMap<String,String>();
    	TestGhInfo testGhInfo = new TestGhInfo();
    	testGhInfo.setQhxxxlh("1734451234567890B00005");
    	param.put("opType", "TMRI_SKIP");
		param.put("reqdata", Utils.getBase64(JacksonUtil.toJson(testGhInfo)));
		param.put("charset", "utf-8");
	    String backResult = Utils.sendPost1("http://127.0.0.1:8080//paymentOut/serverAction/callSystem", param);
	    System.out.println("返回的结果="+backResult);*/
    	
    	/*ModelJson modelJson = new ModelJson();
		modelJson.setCardNumber("360122199309154232");
		modelJson.setThanType(1);
		Map<String,String> param = new HashMap<String,String>();
		param.put("apiauthtoken", "E7C5EFFD9806F85141188C47986B6FC5");
		param.put("modeljson", JSONObject.fromObject(modelJson).toString());
	    String backResult = Utils.sendPost1("http://localhost:8080/paymentOut/queuing/queuingController/wxappointInfo",param );
	    JSONObject backResultJson =JSONObject.fromObject(backResult);
	    System.out.println("现场预警比对返回的结果："+param);
	    System.out.println("现场预警比对返回的结果："+backResultJson);*/
    	String a = "B002";
    	a = a.replaceFirst(".", "D");
    	System.out.println(a);
    }

}
