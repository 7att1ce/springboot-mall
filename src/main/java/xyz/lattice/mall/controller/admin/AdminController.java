package xyz.lattice.mall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.lattice.mall.entity.AdminUser;
import xyz.lattice.mall.service.AdminUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理员功能实现
 * 功能包括: 登录
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;

    // 管理员登录界面
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    // 管理员主界面
    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("path", "index");
        return "admin/index";
    }

    // 用户设置界面
    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    // 更改用户密码
    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (originalPassword.isEmpty() || newPassword.isEmpty()) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空Session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        } else {
            return "修改失败";
        }
    }

    // 更改用户昵称
    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("nickName") String nickName) {
        if (nickName.isEmpty()) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updateName(loginUserId, nickName)) {
            return "success";
        } else {
            return "修改失败";
        }
    }

    // 登录验证
    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName, // 账号
                        @RequestParam("password") String password, // 密码
                        @RequestParam("verifyCode") String verifyCode, // 验证码
                        HttpSession session) {
        if (verifyCode.isEmpty()) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }

        if (userName.isEmpty() || password.isEmpty()) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }

        String captchaCode = session.getAttribute("verifyCode") + "";
        if (captchaCode.isEmpty() || !verifyCode.toLowerCase().equals(captchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }

        AdminUser adminUser = adminUserService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "登录失败");
            return "admin/login";
        }
    }

    // 退出登录
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }
}
