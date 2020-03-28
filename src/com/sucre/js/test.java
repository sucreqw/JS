package com.sucre.js;

import com.sucre.utils.MyUtil;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder;


import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class test {
    private JPanel jpanle;
    private JButton runButton;
    private JTextArea source;
    private JComboBox mission;

    public test() {
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m = (String) mission.getSelectedItem();
                String sourceText = source.getText();
                String ret;
                switch (m) {

                    case "java":

                        String temp = "private byte[] getdata(String cookies, String uid) {\r\n"
                                + "    StringBuilder data = new StringBuilder(900);\r\n"
                                + "    String temp = \"\";\r\n"
                                + "    data.append(\"" + sourceText.replace("\n", "\\r\\n\");\r\n    data.append(\"")//")\r\ndata.append("

                                + "\")\r\nreturn data.toString().getBytes();\r\n"
                                + "}\r\n";
                        source.setText(temp);
                        break;
                    case "md5":


                        String hash = MyUtil.MD5(sourceText);
                        source.setText(sourceText + "\r\n" + hash);
                        break;

                    case "base64file":

                        ret = new String(Base64.getEncoder().encode(MyUtil.loadByte(sourceText)));
                        source.setText(sourceText + "\r\n" + ret);
                        break;

                    case "url":
                        ret = URLEncoder.encode(sourceText);
                        source.setText(sourceText + "\r\n" + ret);
                        break;
                    case "unicode":
                        ret = MyUtil.decodeUnicode(sourceText);
                        source.setText(sourceText + "\r\n" + ret);
                        break;

                    case "timestampToDate":
                        //Timestamp ts = new Timestamp(Long.valueOf(sourceText));
                        String tsStr = "";
                        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        try {
                            //方法一
                            Date date = new Date(Long.valueOf(sourceText));
                            //date=sourceText;
                            tsStr = sdf.format(date);
                            // System.out.println(tsStr);
                            source.setText(sourceText + "\r\n" + tsStr);
                            /*//方法二
                            tsStr = ts.toString();
                            System.out.println(tsStr);*/
                        } catch (Exception es) {
                            es.printStackTrace();
                            source.setText(es.getMessage());
                        }

                        break;

                    case "DateToTimestamp":
                        //String string = "2016-10-24 21:59:06";
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date=sdf2.parse(sourceText);

                            String stamp=String.valueOf(date.getTime());
                            source.setText(date.toString()+"\r\n" + stamp );
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "body"://okhttp body
                        String[] bodys=sourceText.split("&");
                        ret="";
                        for (String keys:bodys) {
                            String[] bo=keys.split("=");
                            String v="";
                            if(bo.length>1){
                                v=bo[1];
                            }
                            ret+="body.put(\""+ bo[0] +"\",\""+ v +"\");\r\n";
                        }
                        source.setText(ret);
                        break;
                    case "header" ://okhttp header
                        String[] header=sourceText.split("\n");
                        ret="";
                        for (String keys:header) {
                            String[] bo=keys.split(":");
                            String v="";
                            if(bo.length>1){
                                v=bo[1];
                            }
                            ret+="header.put(\""+ bo[0] +"\",\""+ v +"\");\r\n";
                        }
                        source.setText(ret);
                        break;
                    case "controller":
                        source.setText(controller(sourceText));
                        break;
                    case "service" :
                        source.setText(service(sourceText));
                        break;
                    case "impl" :
                        source.setText(impl(sourceText));
                        break;
                    default:

                        Map<String, String> hashmap = new HashMap();

                        hashmap.put("app_id", "2111350163");
                        hashmap.put("time_stamp", MyUtil.getTime());
                        hashmap.put("nonce_str", "fa577ce3");
                        //hashmap.put("app_key", "Rfhw1apxn3WQQAfh");
                        hashmap.put("image", "iVBORw0KGgoAAAANSUhEUgAAAGQAAAAoCAIAAACHGsgUAAAMT0lEQVR4nNxaeVhT17bfJ%2FMAhDAThjCFIRAGCSKCChS1YAEFBVu0aqtPrffdttb2ae31WW2vWnu99nod0M%2FW8akgijgAjjwVqIwCQpR5kMwQIAOZ8z45mi9lUiGAfb%2BPP1bWWeectX%2Bsvfbaax%2BUTqcDfyoI%2B0XNonaORNArF%2FcqxCJ53yuht1cu7lGI5Wo5AkIAALQ6HQKCIIBAIhDlq7MhAI3z1dDUkvWgoyzccRo8tmHRqxA3dLc1dLfCf43dbRAE0chUBzNbEtaUjCOZ48zMcWYvZLwZGUciYU0xSPQEeTuVZImV0rXXv%2BNLu5b5L1ziE0tE4wEAdcLGJ%2Fx6PUFqrdrDgupBptIsXOA%2FCzxpqhye4sgCAFTxnp6oyip8Xu5OduZLu1EIJNOeQbN8wYunhYs1wWJq3TMEampfL5B1V%2FLq2vvYaARKoVb2KvqiqDOWMRJ8rDym1rFhMTWRpdFpr9bfyX52myVsjHEN%2F4AWFeoQgIAQYqU0o%2B7G6erLVJLDyoDkSJfQ8WdlI2IKyGoStW%2B5%2BzMBjU9jJMxxnj40H6u16uuNBb89zlJpVR%2F7L1roNReLxEyyk8Nissk6VZOdXn7uqxmfJHnPf63xo86qE1VZ1fxnBDQuyXve%2BuC0SfFxREwqWVlP849VXDiRsMfOxPrN72ru6Ui7%2FJVEKVnoNXdlQLI72XkifRwNk0dWKbt6462%2Ffzdrw2NuXbhTcAjFf6TJJVcrsp7mN4nak7zn%2BVl76oAu7NclqwIXP2gvreKxSDjTCCcmDoUVykTd%2FT29CjEJa2pLtLIzsfIgUxd5zxulahsnJo%2BsNde3xtOi67tbjldmAgCsCOQHK84PsuFI%2BDebC8s5NRCEuNn0IMI5eDoloJRdXfy8EgBgQ7TkSPhENBGNQOFQmCjXGfPdZtsQLXvkvVyJkCsV5jXe97OhfRexYYKGMEmlg0jeV8Vl%2FWv%2BtpPVl2BNkB3d0EAHdJ9e3fJE0BBJDe1XK%2BoEjQAArkQokvfh0bgVAUlfhq7q6OPGn19zPmm%2FG9mp6HlFRl3u5%2Fk7Yz3mpNLj3nefDQCQKKRNPe0TNwqjkVXCrvYgU0cqr5tEbZ6Wrv0qOUvYBGtCKAG1ggaWsOlpVxNL2FTf1ULEEGRKGcXEOs5jjreV27yzK88s3EdE42LOrFgdmIKEkJdY%2BfPcZsE5K9wxONwxWCDrzmLlf5b739YECy9Lt%2FsdpecW%2FdNYIxoK5Pbt28f%2FFLVWnZix7mjl%2BZvNDzv6OACCbImWKARSbyBVya%2FU3xYrJGWcJ7CmhF1Vya1Ta1XuZGo8LfqrsE%2FXTvtQrJQ5keznu8%2B61VzYKGp3Izs1ito8yM4XWbl3WovvtBRtiVhruDgQ0XgmhfGx%2FyIEhEivOKfV6aQqmb2JjSXefPyDGgrj5KxSdvXHV7421GCQ6CA7Xx8rdxwKwxYLyjk1bAnfEm8ulIng7HN72Sk0YnBcC2Tdy7M3SVSyb8LWxHtGQwASyLrhHc%2F63G0FrY9WBiStmbbUAjdM%2FPKlXQACl1g3M1k3bIlWKfS4WI85xi3QjBNZXImAJ%2B3iy7o0Wg2s0ei0nWLuYx6rjPOkU8w9nrCrvqulu79HrlYAABzM7NL8EoY%2Bh4jGhzkGNXW3cST8%2BW6zYA0AoFPM%2B6Xk5K6oTRfqbhwp%2Fx%2BZqt%2Fbyg2Pwv3hXgyBiCYwKYzljIU2RKurDXf3FKVzJUKKqY2FkQLNCGRpdBqhrAeJQCAgSNgvwqOweDROrlHoDYLsfFcHpvSr5TebH8IamUq%2BNvjDYZ9GxODxaFxO%2FZ1nXc1sMd%2Ff1hsAsPPBv1PocYleMck%2B8%2FnSrlPVl3%2Btuniv9XeOREBA42yJVoZPgCCISnKI84hcQItq6enYXZR%2Bq6UQhUC6mjsaZoYxYIwJXqlRVfFYpZyaMnZNFe%2Bpg6ktk8JI8IzZEfmFDcESnpgrcr6B5%2FgMh0AAgGH506%2BW86TCQYNMrziXycozwRD2zd2i0KhS6HG%2B1jQAwMOOMpVWvdjnfQAAHoXbHL4uhOLf1tvJkQisCGQTDHEkJ%2B2IVhuYy9YHf3S%2FvfRC7fU9RUfjadFLfRe4mjuNbdRvQZZM1V%2FBrS1l15RxaljCJg8yNdjeN42RsG%2Fut%2BY4M0NLuVqx48G%2FYaZMMMRknxc7m6KOCkOb1p5OPVnPxdx%2FlZy81nAXzp8FbSVAp2PYeAEAeFJhTv2d3dEvEmKjqO1MzZWr9Xf71fK%2FMJf%2FEPnlm7iNgBCR1NBIaihXKshi5a%2B6utnJzD6FHjffbdbbtglfk%2BB7FeKygfApZdc093T4WtOY9gwmhRFo6wNnk2Hxt4J%2FXmTlwfLWiM%2BWMRI1Os2MXxdLlDK9zfY5f02lLxDIug%2BWncli5au1av0lUyzRHGt2delRjkRQ2FGOhJDVfJZYIfO0dJntHGJFsJh7dsX77rP3zf32rYYKQ6PTFrT9nlF3o5bfEO8ZnUJf4Gru%2BIb3DhNZfGlXKbu6nFtbxn6xhAXa%2BjDtGZvD1zFsPN9kcbnWcE%2FPlI%2BVx0d%2B8QCAGv4zQ6YAABWc2s4%2B3umabDjlw4AgKMDG%2B%2FvIL765vedA6ak1QalpfgkHSk%2FVCRqzU44Y3j7TcdobjnAQkBDiPZeZ77nMZIt5may8lTnfuJAcUuhxc90jMIjXBNrLyGrvY5cNzK9Sdo1YKYXDJ9jOl27tgYTeIim297GTMjdIB3iBIOh80n5%2FmxcZ%2BlD52QMlpwAAOBTW3tS2RTS4zsajsEvocWgkuqijXK3TbAz9JJIaCl%2BSKGXzzq64kpoO1xAandbvSOy5pP2Btj5vz9VgwIF2vvZ6naAx0Ssmlb6ASqKMZIz6z7wd1fynOp0uhOLPpDBWBSzxsHAeW8tNqVF9efNH6asIWh%2F8EcwUAADe3AEAmBQGzcLFkCxTLDHNL3GGY%2BCxigvFzyvDHIP2xmwmGyRBEwxhCT0ut%2FF%2FKaY291ofVfFY0S5hXIkAGIMsfaB1inkXWXnLr3zlZu58OG4HHoUdagzl1N8JsPV2NhuRzjeETCXfWvCPvMb78M8PaFF7Yza%2FvKSWhx5PhrPSf4WvpVm4rL66BQBAwOAhAB2O3RFCYYiV0ousPDQSleaXMPRfxZUIPr22ZYV%2FUrC9nxvZeeKap2qt5hG7KnyEOW6cCp4lbNp46%2B%2BtPc%2Fhn0F29BMJP%2BnXmoK2R%2BtvbIPlK6lHqCSH6ceTlBpVvGf0AlrU1rv%2F2BW9aZZzyOivGNj3UMfv6nhghNbPmZorSy99rmeKau5wMHa74apc%2BKposCSQPS1csUhMkJ0vACC38b63pdvB2O%2B%2Fvbfv8rNbo79lypkaL1m9CvGGvO0%2FPjyk1KhgzXQH%2FwtJv5D%2FuHcrfv6SrLCB6nRgLQuCt9%2Bna7IDbL1PL%2Fz5UNmZn4qPKjTK8fgz0Rg7WeXc2oUZ6%2B%2B2FOs1yT7vH%2F9gFwlramjGl3U1vUrnYQMcGQoZtTekqn4XkkPm4gMCafeizM%2BqeKwxuzTRGDtZ%2Bx%2F99mJJGgAKgdwUtvqHyC9RQxoJ%2BnVwgKOXidPXmmaGNYEPpTNZuQAAc6zZ3pjNG0M%2F%2BWv%2Bzj1F6e9miI2drAgnJiwwKYzLKYc%2FDVwyrJmeLBdzB%2FtXrSgEhHjPdSYsn6zKkqnlsBzjOjMn9ahI3peYsa78Vefr3cF4yAq2wJvvfu%2Fr04k%2Fj5J99VvCcKdgQ%2F0XoSuJGALcO95TmK7Xk7Amu6O%2F3jxz7abbu394eLD%2FFY%2FvAsZOFt3aI%2FfD44meMaPYlLKrBbJuWA53%2FANZNgTLv4Qsh%2BWMuht3W4sNr0ZSQ3NS0xVqZWLGuhJ29ZidNC6MebpzrPJCYUfFTMegcKdgWxMruVqx7vrf4OyORCB%2BX5VlMhBKemh02uTMDc%2B6mgEAFnhSTmq6JZ486JkPO8q2FfwymxryddiaUbbukwNjkvVT8bHfHl8c9lKEM%2FPYgh%2BH6iu5dWnZG2EfZlOnp8ftHGojUcn2Fh172FGWtfjgoF7QJMOY55GG%2FYNBSB7hsD7Ijr7Iax4s328rOfskZ6iNCZrw%2FZzPD8XumFqmjEzWMr%2BErRGffUCLMvyoCoKg%2F5i2FD7XGxabwlbDZQQBjXMxdxjJzMvS1Yiujg0TdSLd3NPBFvP6FFImxQ9uNI%2BCc7XXDpScPBy3I8AYjYSJw9R%2F%2BQcfR3PEAoqpzVQ78hq8E2T9WTBRH5z8v8T%2FBQAA%2F%2F8GjVenKJquWQAAAABJRU5ErkJggg%3D%3D");
                        Ksort(hashmap);
                        //System.out.println(hashmap.toString());

                        ret = "app_id=2111350163&image=iVBORw0KGgoAAAANSUhEUgAAAGQAAAAoCAIAAACHGsgUAAAMT0lEQVR4nNxaeVhT17bfJ%2FMAhDAThjCFIRAGCSKCChS1YAEFBVu0aqtPrffdttb2ae31WW2vWnu99nod0M%2FW8akgijgAjjwVqIwCQpR5kMwQIAOZ8z45mi9lUiGAfb%2BPP1bWWeectX%2Bsvfbaax%2BUTqcDfyoI%2B0XNonaORNArF%2FcqxCJ53yuht1cu7lGI5Wo5AkIAALQ6HQKCIIBAIhDlq7MhAI3z1dDUkvWgoyzccRo8tmHRqxA3dLc1dLfCf43dbRAE0chUBzNbEtaUjCOZ48zMcWYvZLwZGUciYU0xSPQEeTuVZImV0rXXv%2BNLu5b5L1ziE0tE4wEAdcLGJ%2Fx6PUFqrdrDgupBptIsXOA%2FCzxpqhye4sgCAFTxnp6oyip8Xu5OduZLu1EIJNOeQbN8wYunhYs1wWJq3TMEampfL5B1V%2FLq2vvYaARKoVb2KvqiqDOWMRJ8rDym1rFhMTWRpdFpr9bfyX52myVsjHEN%2F4AWFeoQgIAQYqU0o%2B7G6erLVJLDyoDkSJfQ8WdlI2IKyGoStW%2B5%2BzMBjU9jJMxxnj40H6u16uuNBb89zlJpVR%2F7L1roNReLxEyyk8Nissk6VZOdXn7uqxmfJHnPf63xo86qE1VZ1fxnBDQuyXve%2BuC0SfFxREwqWVlP849VXDiRsMfOxPrN72ru6Ui7%2FJVEKVnoNXdlQLI72XkifRwNk0dWKbt6462%2Ffzdrw2NuXbhTcAjFf6TJJVcrsp7mN4nak7zn%2BVl76oAu7NclqwIXP2gvreKxSDjTCCcmDoUVykTd%2FT29CjEJa2pLtLIzsfIgUxd5zxulahsnJo%2BsNde3xtOi67tbjldmAgCsCOQHK84PsuFI%2BDebC8s5NRCEuNn0IMI5eDoloJRdXfy8EgBgQ7TkSPhENBGNQOFQmCjXGfPdZtsQLXvkvVyJkCsV5jXe97OhfRexYYKGMEmlg0jeV8Vl%2FWv%2BtpPVl2BNkB3d0EAHdJ9e3fJE0BBJDe1XK%2BoEjQAArkQokvfh0bgVAUlfhq7q6OPGn19zPmm%2FG9mp6HlFRl3u5%2Fk7Yz3mpNLj3nefDQCQKKRNPe0TNwqjkVXCrvYgU0cqr5tEbZ6Wrv0qOUvYBGtCKAG1ggaWsOlpVxNL2FTf1ULEEGRKGcXEOs5jjreV27yzK88s3EdE42LOrFgdmIKEkJdY%2BfPcZsE5K9wxONwxWCDrzmLlf5b739YECy9Lt%2FsdpecW%2FdNYIxoK5Pbt28f%2FFLVWnZix7mjl%2BZvNDzv6OACCbImWKARSbyBVya%2FU3xYrJGWcJ7CmhF1Vya1Ta1XuZGo8LfqrsE%2FXTvtQrJQ5keznu8%2B61VzYKGp3Izs1ito8yM4XWbl3WovvtBRtiVhruDgQ0XgmhfGx%2FyIEhEivOKfV6aQqmb2JjSXefPyDGgrj5KxSdvXHV7421GCQ6CA7Xx8rdxwKwxYLyjk1bAnfEm8ulIng7HN72Sk0YnBcC2Tdy7M3SVSyb8LWxHtGQwASyLrhHc%2F63G0FrY9WBiStmbbUAjdM%2FPKlXQACl1g3M1k3bIlWKfS4WI85xi3QjBNZXImAJ%2B3iy7o0Wg2s0ei0nWLuYx6rjPOkU8w9nrCrvqulu79HrlYAABzM7NL8EoY%2Bh4jGhzkGNXW3cST8%2BW6zYA0AoFPM%2B6Xk5K6oTRfqbhwp%2Fx%2BZqt%2Fbyg2Pwv3hXgyBiCYwKYzljIU2RKurDXf3FKVzJUKKqY2FkQLNCGRpdBqhrAeJQCAgSNgvwqOweDROrlHoDYLsfFcHpvSr5TebH8IamUq%2BNvjDYZ9GxODxaFxO%2FZ1nXc1sMd%2Ff1hsAsPPBv1PocYleMck%2B8%2FnSrlPVl3%2Btuniv9XeOREBA42yJVoZPgCCISnKI84hcQItq6enYXZR%2Bq6UQhUC6mjsaZoYxYIwJXqlRVfFYpZyaMnZNFe%2Bpg6ktk8JI8IzZEfmFDcESnpgrcr6B5%2FgMh0AAgGH506%2BW86TCQYNMrziXycozwRD2zd2i0KhS6HG%2B1jQAwMOOMpVWvdjnfQAAHoXbHL4uhOLf1tvJkQisCGQTDHEkJ%2B2IVhuYy9YHf3S%2FvfRC7fU9RUfjadFLfRe4mjuNbdRvQZZM1V%2FBrS1l15RxaljCJg8yNdjeN42RsG%2Fut%2BY4M0NLuVqx48G%2FYaZMMMRknxc7m6KOCkOb1p5OPVnPxdx%2FlZy81nAXzp8FbSVAp2PYeAEAeFJhTv2d3dEvEmKjqO1MzZWr9Xf71fK%2FMJf%2FEPnlm7iNgBCR1NBIaihXKshi5a%2B6utnJzD6FHjffbdbbtglfk%2BB7FeKygfApZdc093T4WtOY9gwmhRFo6wNnk2Hxt4J%2FXmTlwfLWiM%2BWMRI1Os2MXxdLlDK9zfY5f02lLxDIug%2BWncli5au1av0lUyzRHGt2delRjkRQ2FGOhJDVfJZYIfO0dJntHGJFsJh7dsX77rP3zf32rYYKQ6PTFrT9nlF3o5bfEO8ZnUJf4Gru%2BIb3DhNZfGlXKbu6nFtbxn6xhAXa%2BjDtGZvD1zFsPN9kcbnWcE%2FPlI%2BVx0d%2B8QCAGv4zQ6YAABWc2s4%2B3umabDjlw4AgKMDG%2B%2FvIL765vedA6ak1QalpfgkHSk%2FVCRqzU44Y3j7TcdobjnAQkBDiPZeZ77nMZIt5may8lTnfuJAcUuhxc90jMIjXBNrLyGrvY5cNzK9Sdo1YKYXDJ9jOl27tgYTeIim297GTMjdIB3iBIOh80n5%2FmxcZ%2BlD52QMlpwAAOBTW3tS2RTS4zsajsEvocWgkuqijXK3TbAz9JJIaCl%2BSKGXzzq64kpoO1xAandbvSOy5pP2Btj5vz9VgwIF2vvZ6naAx0Ssmlb6ASqKMZIz6z7wd1fynOp0uhOLPpDBWBSzxsHAeW8tNqVF9efNH6asIWh%2F8EcwUAADe3AEAmBQGzcLFkCxTLDHNL3GGY%2BCxigvFzyvDHIP2xmwmGyRBEwxhCT0ut%2FF%2FKaY291ofVfFY0S5hXIkAGIMsfaB1inkXWXnLr3zlZu58OG4HHoUdagzl1N8JsPV2NhuRzjeETCXfWvCPvMb78M8PaFF7Yza%2FvKSWhx5PhrPSf4WvpVm4rL66BQBAwOAhAB2O3RFCYYiV0ousPDQSleaXMPRfxZUIPr22ZYV%2FUrC9nxvZeeKap2qt5hG7KnyEOW6cCp4lbNp46%2B%2BtPc%2Fhn0F29BMJP%2BnXmoK2R%2BtvbIPlK6lHqCSH6ceTlBpVvGf0AlrU1rv%2F2BW9aZZzyOivGNj3UMfv6nhghNbPmZorSy99rmeKau5wMHa74apc%2BKposCSQPS1csUhMkJ0vACC38b63pdvB2O%2B%2Fvbfv8rNbo79lypkaL1m9CvGGvO0%2FPjyk1KhgzXQH%2FwtJv5D%2FuHcrfv6SrLCB6nRgLQuCt9%2Bna7IDbL1PL%2Fz5UNmZn4qPKjTK8fgz0Rg7WeXc2oUZ6%2B%2B2FOs1yT7vH%2F9gFwlramjGl3U1vUrnYQMcGQoZtTekqn4XkkPm4gMCafeizM%2BqeKwxuzTRGDtZ%2Bx%2F99mJJGgAKgdwUtvqHyC9RQxoJ%2BnVwgKOXidPXmmaGNYEPpTNZuQAAc6zZ3pjNG0M%2F%2BWv%2Bzj1F6e9miI2drAgnJiwwKYzLKYc%2FDVwyrJmeLBdzB%2FtXrSgEhHjPdSYsn6zKkqnlsBzjOjMn9ahI3peYsa78Vefr3cF4yAq2wJvvfu%2Fr04k%2Fj5J99VvCcKdgQ%2F0XoSuJGALcO95TmK7Xk7Amu6O%2F3jxz7abbu394eLD%2FFY%2FvAsZOFt3aI%2FfD44meMaPYlLKrBbJuWA53%2FANZNgTLv4Qsh%2BWMuht3W4sNr0ZSQ3NS0xVqZWLGuhJ29ZidNC6MebpzrPJCYUfFTMegcKdgWxMruVqx7vrf4OyORCB%2BX5VlMhBKemh02uTMDc%2B6mgEAFnhSTmq6JZ486JkPO8q2FfwymxryddiaUbbukwNjkvVT8bHfHl8c9lKEM%2FPYgh%2BH6iu5dWnZG2EfZlOnp8ftHGojUcn2Fh172FGWtfjgoF7QJMOY55GG%2FYNBSB7hsD7Ijr7Iax4s328rOfskZ6iNCZrw%2FZzPD8XumFqmjEzWMr%2BErRGffUCLMvyoCoKg%2F5i2FD7XGxabwlbDZQQBjXMxdxjJzMvS1Yiujg0TdSLd3NPBFvP6FFImxQ9uNI%2BCc7XXDpScPBy3I8AYjYSJw9R%2F%2BQcfR3PEAoqpzVQ78hq8E2T9WTBRH5z8v8T%2FBQAA%2F%2F8GjVenKJquWQAAAABJRU5ErkJggg%3D%3D&nonce_str=" + MyUtil.makeNonce(5) + "&time_stamp=" + MyUtil.getTimeB() + "&app_key=Rfhw1apxn3WQQAfh";

                        System.out.println(ret);
                        System.out.println(ret + "&sign=" + MyUtil.MD5(ret).toUpperCase());

                }
            }
        });
    }

    /**
     * controller restful风格代码生成
     * @param data
     * @return
     */
    private String controller(String data){
        //@RequestMapping("/adAccountcollectDO")
        String Do=MyUtil.midWord("@RequestMapping(\"/","\")",data).replace("DO","");

       String place="@Autowired\n" +
               "    private I"+ fristBig(Do)+"Service "+ Do +"Service;\n" +
               "\n" +
               "    /**\n" +
               "     * 对接前端接口，新增一条数据\n" +
               "     * @param "+ Do +"DTO\n" +
               "     */\n" +
               "    @PostMapping(\"/\")\n" +
               "    public CommonResult<Integer> create(@RequestBody "+ fristBig(Do)+"DTO "+ Do +"DTO) {\n" +
               "        CommonResult<Integer> result = new CommonResult<>();\n" +
               "        Integer id = "+ Do+"Service.create("+ Do+"DTO);\n" +
               "        result.setData(id);\n" +
               "        return result;\n" +
               "    }\n" +
               "/**\n" +
               "     * 对接前端接口，更新一条数据\n" +
               "     * @param id\n" +
               "     * @param "+Do+"DTO\n" +
               "     */\n"+
               "    @PutMapping(\"/{id}\")\n" +
               "    public void update(@PathVariable Integer id, @RequestBody "+ fristBig(Do)+"DTO "+ Do+"DTO) {\n" +
               "        "+ Do+"Service.update(id, "+ Do+"DTO);\n" +
               "    }\n" +
               "/**\n" +
               "     * 对接前端接口，删除一条数据\n" +
               "     * @param id\n" +
               "     */\n"+
               "    @DeleteMapping(\"/{id}\")\n" +
               "    public void delete(@PathVariable Integer id) {\n" +
               "        "+ Do +"Service.delete(id);\n" +
               "    }\n" +
               "\n" +
               "/**\n" +
               "     * 取指定id的数据\n" +
               "     * @param id\n" +
               "     * @return\n" +
               "     */\n"+
               "    @GetMapping(\"/{id}\")\n" +
               "    public CommonResult<"+ fristBig(Do)+"Info> get(@PathVariable Integer id) {\n" +
               "        CommonResult<"+ fristBig(Do) +"Info> result = new CommonResult<>();\n" +
               "        "+ fristBig(Do)+"Info "+ Do+"Info = "+ Do +"Service.get(id);\n" +
               "        result.setData("+ Do +"Info);\n" +
               "        return result;\n" +
               "    }\n" +
               "/**\n" +
               "     * 取分页面数据。\n" +
               "     * @param page\n" +
               "     * @param pageSize\n" +
               "     * @param query\n" +
               "     * @return\n" +
               "     */\n"+
               "    @GetMapping(\"/page/{page}/{pageSize}\")\n" +
               "    public CommonResult<Page<"+ fristBig(Do)+"Info>> listPage(@PathVariable Integer page, @PathVariable Integer pageSize, String query) {\n" +
               "        CommonResult<Page<"+ fristBig(Do)+"Info>> result = new CommonResult<>();\n" +
               "        Page<"+ fristBig(Do)+"Info> list = "+ Do+"Service.listPage(page, pageSize, query);\n" +
               "        result.setData(list);\n" +
               "        return result;\n" +
               "    }";


        return place;
    }

    /**
     * impl 服务接口 实现类代码生成
     * @param data
     * @return
     */
    private String impl(String data){
        //@RequestMapping("/adAccountcollectDO")
        String Do=MyUtil.midWord("implements I","Service",data);

        String ret="    @Override\n" +
                "    public Integer create("+ Do +"DTO "+ fristLow(Do)+"DTO) {\n" +
                "        "+ Do+"DO "+ fristLow(Do)+"DO = new "+ Do +"DO();\n" +
                "        BeanUtils.copyProperties("+ fristLow(Do)+"DTO, "+ fristLow(Do)+"DO);\n" +
                "        baseMapper.insert("+ fristLow(Do)+"DO);\n" +
                "        return "+ fristLow(Do)+"DO.getId();\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void delete(Integer id) {\n" +
                "        baseMapper.deleteById(id);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void update(Integer id, "+Do+"DTO "+ fristLow(Do)+"DTO) {\n" +
                "        "+ Do +"DO "+ fristLow(Do)+"DO = baseMapper.selectById(id);\n" +
                "        if ("+ fristLow(Do)+"DO == null) {\n" +
                "            return;\n" +
                "        }\n" +
                "        BeanUtils.copyProperties("+ fristLow(Do)+"DTO, "+ fristLow(Do)+"DO);\n" +
                "        baseMapper.updateById("+ fristLow(Do)+"DO);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public Page<"+ Do+"Info> listPage(Integer page, Integer pageSize, String query) {\n" +
                "        QueryWrapper<"+ Do+"DO> wrapper = new QueryWrapper<>();\n" +
                "        if (StringUtils.isNotBlank(query)) {\n" +
                "            //wrapper.like(\"card_name\", query);\n" +
                "        }\n" +
                "        wrapper.orderByDesc(\"id\");\n" +
                "        Page<"+ Do+"DO> "+ fristLow(Do)+"DOPage = new Page<>(page, pageSize);\n" +
                "        return PageUtil.buildPage(baseMapper.selectPage("+ fristLow(Do)+"DOPage, wrapper), "+ Do+"Info.class);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public "+ Do+"Info get(Integer id) {\n" +
                "        "+ Do+"DO "+ fristLow(Do)+"DO = baseMapper.selectById(id);\n" +
                "        if ("+ fristLow(Do)+"DO == null) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        "+ Do + "Info "+ fristLow(Do)+"Info = new "+ Do+"Info();\n" +
                "        BeanUtils.copyProperties("+ fristLow(Do)+"DO, "+ fristLow(Do)+"Info);\n" +
                "        return "+ fristLow(Do)+"Info;\n" +
                "    }";

        return ret;
    }

    /**
     * service服接口代码生成
     * @param data
     * @return
     */
    private String service(String data){
        //
        String Do=MyUtil.midWord("<",">",data).replace("DO","");

        String ret="//新增数据\n"+
                "Integer create("+fristBig(Do)+"DTO "+ fristLow(Do) +"DTO);\n" +
                "//删除数据\n" +
                "    void delete(Integer id);\n" +
                "//更新数据\n" +
                "    void update(Integer id, "+ fristBig(Do) +"DTO "+ fristLow(Do)+"DTO);\n" +
                "//分页数据\n" +
                "    Page<"+ fristBig(Do)+"Info> listPage(Integer page, Integer pageSize, String query);\n" +
                "//取指定id数据\n" +
                "    "+ fristBig(Do) + "Info get(Integer id);";
        return ret;
    }
    /**
     * 腾讯云api排序规则。
     *
     * @param map
     */
    public static void Ksort(Map<String, String> map) {

        String sb = "";
        String[] key = new String[map.size()];
        int index = 0;
        for (String k : map.keySet()) {
            key[index] = k;
            index++;
        }
        Arrays.sort(key);
        for (String s : key) {
            sb += s + "=" + map.get(s) + "&";
        }
        sb = sb.substring(0, sb.length() - 1);
// 将得到的字符串进行处理得到目标格式的字符串
        try {
            //sb = URLEncoder.encode(sb, "UTF-8");
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }// 使用常见的UTF-8编码
        //sb = sb.replace("%3D", "=").replace("%26", "&");

        System.out.println("ksort:" + sb);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setContentPane(new test().jpanle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200, 100, 807, 499);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * 首字母大写
     * @param data
     * @return
     */
    private String fristBig(String data){
        String ret=data.substring(0,1).toUpperCase()+ data.substring(1);
        return ret;
    }
    /**
     * 首字母小写
     * @param data
     * @return
     */
    private String fristLow(String data){
        String ret=data.substring(0,1).toLowerCase()+ data.substring(1);
        return ret;
    }
}
