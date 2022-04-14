# 面试n问

## 介绍项目背景

该项目是一套电商系统，其中包含面对用户的商城系统和商城的后台管理系统。前台商城系统包含首页门户、商品分类、新品上线、首页轮播、商品推荐、商品搜索、商品展示、购物车、订单结算、订单流程、个人订单管理等。后台管理系统包含数据面板、轮播图管理、商品管理、订单管理、会员管理、分类管理、设置等模块。

该商城系统属于B2C模式，这种模式属于企业与消费者之间的电子商务，企业在电子商务平台上进行销售，消费者选择并购买，是一种很常见的电子商务模式，天猫商城、京东商城就是这种模式。

## 为什么要做这个项目

## 是为了解决什么问题

## 多少个人完成

两个人完成

## 你负责了哪些工作

后端部分

## 有什么亮点？难点？

## 运用了哪些技术

前端：bootstrap，thymeleaf
后端：springboot，mybatis，mysql

## 有什么收获

## 项目详解

### 总揽

1. 问：简单说说项目结构？<br>答：项目采用了MVC设计模式，主要包括商城首页、商品展示、商品搜索、会员模块、购物车模块、订单模块和支付模块。项目中分了几个包，分别为common（一些常量枚举），config（配置，主要包含拦截器和文件上传路径的绑定），controller包（MVC中控制器），DAO包（数据访问层），entity实体类包，service包（业务逻辑层）（dao，entity，service为MVC中业务模型），interceptor包（拦截器具体实现），util包（工具类）。resources文件夹李主要有mapper文件，还有thymeleaf写的template（MVC中视图），一些静态文件（css，js）。
2. 问：service为什么要先写接口然后写实现？<br>答：可以在尚未实现具体Service情况下编写上层改代码,如Controller对Service的调用。
3. 问：Spring Boot整合数据库是怎么实现的？<br>答：主要使用Mybatis这个ORM框架，在application.yml中配置好xml配置文件的路径，然后在包含main方法的模块加上MapperScan注解，参数为dao包，如com.example.mall.dao，根据数据库表的属性来创建entity实体类，再在DAO包根据实际需求写mapper接口，然后最重要的是写对应的xml配置文件，简单来说就是将mapper接口的方法与sql语句关联起来。
4. 问：controller包的vo包是干什么用的？<br>答：vo用于表示一个与前端进行交互的java对象，对于前端不需要的数据，比如数据创建和修改的时间等字段，出于减少传输数据量大小和保护数据库结构不外泄的目的，不应该在VO中体现出来。通常遵守Java Bean的规范，拥有 getter/setter 方法。
5. 问：分页功能怎么实现的？<br>答：

### 后台管理系统

1. 问：你的后台管理系统登录系统是如何制作的？<br>答：先设置了一个拦截器，未登录状态下访问/admin目录下的所有页面都会被拦截并重定位到登录页面，具体的登录页面是要输入用户名，密码和验证码，其中验证码单独分离出一个模块，为controller类，使用了easy-captcha开源项目，先生成一个验证码，并把验证码存入到session中，最后将图片流输出到httpServletResponse.getOutputStream()中，验证码验证用小写处理。而登录验证则先验证输入的合法性（在AdminController的controller类实现，post方法），再调用AdminUserService类中的login方法，先对密码进行md5处理，再调用AdminUserMapper的login方法来进行数据库查询操作，登录成功就将loginUser和loginUserID写入到session中。这里的话我想了想应该先前端就进行md5处理密码比较好。
2. 问：登录页面怎么制作的？<br>答：用AdminLite3模版制作的
3. 问：AdminController类的功能？<br>答：用户登录，修改用户昵称和密码，退出登录
4. 问：用户修改昵称和密码功能实现？<br>答：简单来说就是数据库的update操作，先检查该用户是否在数据库中，然后再执行相应修改（修改密码成功后清空Session中的数据，前端控制跳转至登录页）。前端用ajax异步请求。
5. 问：登录拦截器怎么实现的？<br>答：通过实现HandlerInterceptor接口实现拦截器逻辑，然后实现WebMvcConfigurer接口来配置拦截器（主要是拦截器拦截的路径）。主要通过检查session中有无loginUser字段。如果session中无loginUser字段会强制跳转到登陆界面。
