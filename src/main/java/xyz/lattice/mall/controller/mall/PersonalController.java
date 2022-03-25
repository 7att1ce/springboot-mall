package xyz.lattice.mall.controller.mall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.lattice.mall.common.Constants;
import xyz.lattice.mall.common.ServiceResultEnum;
import xyz.lattice.mall.controller.vo.MallUserVO;
import xyz.lattice.mall.entity.MallUser;
import xyz.lattice.mall.service.MallUserService;
import xyz.lattice.mall.util.MD5Util;
import xyz.lattice.mall.util.Result;
import xyz.lattice.mall.util.ResultGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PersonalController {
    @Resource
    private MallUserService mallUserService;

    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request,
                               HttpSession httpSession) {
        request.setAttribute("path", "personal");
        return "mall/personal";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        return "mall/login";
    }

    @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "mall/login";
    }

    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("verifyCode") String verifyCode,
                        @RequestParam("password") String password,
                        HttpSession httpSession) {
        if (loginName.isEmpty()) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (password.isEmpty()) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (verifyCode.isEmpty()) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String captcha = (String)httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY);

        if (captcha.isEmpty() || !captcha.equals(verifyCode.toLowerCase())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        String loginResult = mallUserService.login(loginName, MD5Util.MD5Encode(password, "UTF-8"), httpSession);
        //登录成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {
            //删除session中的verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return ResultGenerator.genSuccessResult();
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("password") String password,
                           HttpSession httpSession) {
        if (loginName.isEmpty()) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (password.isEmpty()) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (verifyCode.isEmpty()) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String captcha = (String)httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY);
        if (captcha.isEmpty() || !captcha.equals(verifyCode.toLowerCase())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        String registerResult = mallUserService.register(loginName, password);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            //删除session中的verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
        MallUserVO mallUserTemp = mallUserService.updateUserInfo(mallUser, httpSession);
        Result result;
        if (mallUserTemp == null) {
            result = ResultGenerator.genFailResult("修改失败");
        } else {
            //返回成功
            result = ResultGenerator.genSuccessResult();
        }
        return result;
    }
}
