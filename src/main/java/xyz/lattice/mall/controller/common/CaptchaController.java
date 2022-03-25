package xyz.lattice.mall.controller.common;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.lattice.mall.common.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码功能实现
 */

@Controller
public class CaptchaController {

    // 生成管理员登录界面的验证码
    @GetMapping("/common/admin/captcha")
    public void adminCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");
        // 生成验证码对象, 三个参数分别为宽, 高, 验证码位数
        SpecCaptcha captcha = new SpecCaptcha(75, 30, 4);
        // 设置类型为数字和字母混合
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        // 设置字体
        captcha.setCharType(Captcha.FONT_9);
        // 验证码存入session
        httpServletRequest.getSession().setAttribute("verifyCode", captcha.text().toLowerCase());
        // 输出图片流
        captcha.out(httpServletResponse.getOutputStream());
    }

    // 生成用户登录界面的验证码
    @GetMapping("/common/mall/captcha")
    public void mallCaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/png");
        // 生成验证码对象, 三个参数分别为宽, 高, 验证码位数
        SpecCaptcha captcha = new SpecCaptcha(75, 40, 4);
        // 设置类型为数字和字母混合
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        // 设置字体
        captcha.setCharType(Captcha.FONT_9);
        // 验证码存入session
        httpServletRequest.getSession().setAttribute(Constants.MALL_VERIFY_CODE_KEY, captcha.text().toLowerCase());
        // 输出图片流
        captcha.out(httpServletResponse.getOutputStream());
    }
}
