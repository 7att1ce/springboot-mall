package xyz.lattice.mall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.lattice.mall.common.Constants;
import xyz.lattice.mall.interceptor.AdminLoginInterceptor;
import xyz.lattice.mall.interceptor.MallCartNumberInterceptor;
import xyz.lattice.mall.interceptor.MallLoginInterceptor;

@Configuration
public class MallWebMvcConfigurer implements WebMvcConfigurer {

    private final AdminLoginInterceptor adminLoginInterceptor;
    private final MallLoginInterceptor mallLoginInterceptor;
    private final MallCartNumberInterceptor mallCartNumberInterceptor;

    public MallWebMvcConfigurer(AdminLoginInterceptor adminLoginInterceptor, MallLoginInterceptor mallLoginInterceptor, MallCartNumberInterceptor mallCartNumberInterceptor) {
        this.adminLoginInterceptor = adminLoginInterceptor;
        this.mallLoginInterceptor = mallLoginInterceptor;
        this.mallCartNumberInterceptor = mallCartNumberInterceptor;
    }

    // 拦截器配置
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器，拦截以/admin为前缀的url路径（后台登陆拦截）
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**") // 拦截路径
                .excludePathPatterns("/admin/login") // 忽略路径
                .excludePathPatterns("/admin/dist/**")
                .excludePathPatterns("/admin/plugins/**");
        // 购物车中的数量统一处理
        registry.addInterceptor(mallCartNumberInterceptor)
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/register")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout");
        // 商城页面登陆拦截
        registry.addInterceptor(mallLoginInterceptor)
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/register")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .addPathPatterns("/goods/detail/**")
                .addPathPatterns("/shop-cart")
                .addPathPatterns("/shop-cart/**")
                .addPathPatterns("/saveOrder")
                .addPathPatterns("/orders")
                .addPathPatterns("/orders/**")
                .addPathPatterns("/personal")
                .addPathPatterns("/personal/updateInfo")
                .addPathPatterns("/selectPayType")
                .addPathPatterns("/payPage");
    }

    // 上传文件路径绑定
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
        registry.addResourceHandler("/goods-img/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
    }
}
