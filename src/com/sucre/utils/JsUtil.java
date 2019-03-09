package com.sucre.utils;


import java.io.FileInputStream;
import java.io.InputStream;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JsUtil {
    private static String JS = "";

    public static void loadJs(String fileName) {

        try {
            // 鎵撳紑鏂囦欢璇诲彇鏁版嵁,濡傚嚭鐜板紓甯歌嚜鍔ㄩ噴鏀�.
            InputStream in = new FileInputStream(fileName);
            int len = 0;

            byte[] buffer = new byte[in.available()];
            while ((len = in.read(buffer)) != -1) {

                String temp = new String(buffer);
                JS = JS + MyUtil.trimNull(temp);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //鍔ㄦ�佹坊鍔爅s浠ｇ爜銆�
    public static void AddJs(String js) {
        JS += js;
    }

    //娓呴櫎鎵�鏈塲s浠ｇ爜
    public static void SetJs(String js) {

        JS = js;
    }

    /**
     * 杩愯js鐨勬柟娉�
     *
     * @return
     */
    public static String runJS(String function, Object... arg) throws Exception {
        String ret = "";
        ScriptEngineManager sem = new ScriptEngineManager();
        /*
         * sem.getEngineByExtension(String extension)鍙傛暟涓簀s
         * sem.getEngineByMimeType(String mimeType) 鍙傛暟涓篴pplication/javascript
         * 鎴栬�卼ext/javascript sem.getEngineByName(String
         * shortName)鍙傛暟涓簀s鎴杍avascript鎴朖avaScript
         */
        ScriptEngine se = sem.getEngineByName("js");
        try {

            //String script = "function say(t){ return 'hello,'+t; }";
            se.eval(JS);
            Invocable inv2 = (Invocable) se;
            ret = (String) inv2.invokeFunction(function, arg);
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return ret;
    }
}
