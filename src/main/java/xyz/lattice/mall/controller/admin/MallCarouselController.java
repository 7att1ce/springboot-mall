package xyz.lattice.mall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.lattice.mall.common.ServiceResultEnum;
import xyz.lattice.mall.entity.Carousel;
import xyz.lattice.mall.service.MallCarouselService;
import xyz.lattice.mall.util.PageQueryUtil;
import xyz.lattice.mall.util.Result;
import xyz.lattice.mall.util.ResultGenerator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 轮播图控制器
 */

@Controller
@RequestMapping("/admin")
public class MallCarouselController {

    @Resource
    MallCarouselService mallCarouselService;

    @GetMapping("/carousels")
    public String carousels(HttpServletRequest request) {
        request.setAttribute("path", "mall_carousel");
        return "admin/mall_carousel";
    }

    // 列举所有轮播图
    @GetMapping("/carousels/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (params.get("page") == null || params.get("limit") == null) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(mallCarouselService.getCarouselPage(pageUtil));
    }

    // 添加轮播图
    @PostMapping("/carousels/save")
    @ResponseBody
    public Result save(@RequestBody Carousel carousel) {
        if (carousel.getCarouselUrl().isEmpty() || Objects.isNull(carousel.getCarouselRank())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallCarouselService.saveCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    // 更新轮播图
    @PostMapping("/carousels/update")
    @ResponseBody
    public Result update(@RequestBody Carousel carousel) {
        if (Objects.isNull(carousel.getCarouselId()) || carousel.getCarouselUrl().isEmpty() || Objects.isNull(carousel.getCarouselRank())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = mallCarouselService.updateCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    // 查看轮播图详情
    @GetMapping("/carousels/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        Carousel carousel = mallCarouselService.getCarouselById(id);
        if (carousel == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(carousel);
    }

    // 删除轮播图
    @PostMapping("/carousels/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (mallCarouselService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
