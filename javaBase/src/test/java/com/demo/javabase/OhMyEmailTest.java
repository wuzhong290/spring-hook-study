package com.demo.javabase;

import com.demo.javabase.mail.OhMyEmail;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.File;
import java.security.GeneralSecurityException;

/**
 * @author biezhi
 *         2017/5/30
 */
public class OhMyEmailTest {

    @Before
    public void before() throws GeneralSecurityException {
        // 配置，一次即可
        OhMyEmail.config(OhMyEmail.SMTP_ENT_QQ(false), "wuzhong@iqiguo.com", "4ZymKfoBzRdw2rFC");
    }

    @Test
    public void testSendText() throws MessagingException {
        OhMyEmail.subject("这是一封测试TEXT邮件")
                .from("王爵的QQ邮箱")
                .to("921293209@qq.com")
                .text("信件内容");
//                .send();
    }

    @Test
    public void testSendHtml() throws MessagingException {
        OhMyEmail.subject("这是一封测试HTML邮件")
                .from("王爵的QQ邮箱")
                .to("921293209@qq.com")
                .html("<h1 font=red>信件内容</h1>");
//                .send();
    }

    @Test
    public void testSendAttach() throws MessagingException {
        OhMyEmail.subject("这是一封测试附件邮件")
                .from("吴忠的QQ邮箱")
                .to("wuzhong@iqiguo.com")
                .cc("wuzhong@iqiguo.com")
                .html("<h1 font=red>信件内容</h1>")
                //.attach(new File("E:\\奇果\\大数据工作.zip"), "大数据工作.zip")
                //.attach(new File("E:\\奇果\\大数据工作.zip"), "江苏电信-订购转化率报表统计-2018-08-14.zip")
                .attach(new File("E:\\奇果\\教育频道运营日报-2018-08-27.zip"))
                .send();
    }

}