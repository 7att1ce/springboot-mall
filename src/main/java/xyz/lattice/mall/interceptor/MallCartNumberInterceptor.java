package xyz.lattice.mall.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.lattice.mall.common.Constants;
import xyz.lattice.mall.controller.vo.MallUserVO;
import xyz.lattice.mall.dao.MallShoppingCartItemMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MallCartNumberInterceptor implements HandlerInterceptor {
    private final MallShoppingCartItemMapper mallShoppingCartItemMapper;

    public MallCartNumberInterceptor(MallShoppingCartItemMapper mallShoppingCartItemMapper) {
        this.mallShoppingCartItemMapper = mallShoppingCartItemMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //购物车中的数量会更改，但是在这些接口中并没有对session中的数据做修改，这里统一处理一下
        if (null != request.getSession() && null != request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY)) {
            //如果当前为登陆状态，就查询数据库并设置购物车中的数量值
            MallUserVO mallUserVO = (MallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
            //设置购物车中的数量
            mallUserVO.setShopCartItemCount(mallShoppingCartItemMapper.selectCountByUserId(mallUserVO.getUserId()));
            request.getSession().setAttribute(Constants.MALL_USER_SESSION_KEY, mallUserVO);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
