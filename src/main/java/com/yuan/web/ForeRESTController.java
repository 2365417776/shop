package com.yuan.web;

import com.google.code.kaptcha.Producer;
import com.yuan.comparator.*;
import com.yuan.pojo.*;
import com.yuan.service.*;
import com.yuan.tools.CodeUtil;
import com.yuan.tools.ImageUtil;
import com.yuan.tools.PageNavigator;
import com.yuan.tools.Result;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ForeRESTController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartStatusService cartStatusService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountyService countyService;
    @Autowired
    private TownService townService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private Producer producer;

    @GetMapping("/forehome")
    public Object home(){
        List<Category> cs = categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        categoryService.removeCategoryFromProduct(cs);
        return cs;
    }
    @GetMapping("homePageList")
    public Object homePageList(@RequestParam(value = "start", defaultValue = "0")int start, @RequestParam(value = "size", defaultValue = "15")int size)throws Exception{
        start = start<0? 0:start;
        PageNavigator<Product> page = productService.list(start, size, 5);
        return page;
    }
    @PostMapping("/testUsername")
    public Object testUsername(@RequestBody User user){
        String name = user.getName();
        String password = user.getPassword();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        if(exist){
            String message = "????????????????????????";
            return Result.fail(message);
        }
        user.setPassword(password);
        return Result.success();
    }
    @PostMapping("/testPhone")
    public Object testPhone(@RequestBody User user){
        String phone = user.getPhone();
        String password = user.getPassword();
        phone = HtmlUtils.htmlEscape(phone);
        String name = RandomStringUtils.random(5);
        user.setPhone(phone);
        user.setName(name);
        boolean exist = userService.isExist2(phone);
        if(exist){
            String message = "????????????????????????";
            return Result.fail(message);
        }
        user.setPassword(password);
        return Result.success(user);
    }
    @PostMapping("editPasswordNoLog")
    public Object editPasswordNoLog(@RequestBody User user){
        String name = HtmlUtils.htmlEscape(user.getName());
        String phone = user.getPhone();
        String password = user.getPassword();
        User user1 = new User();
        if(name != null && !name.equals("")){
            user1 = userService.getByName(name);
        }
        if(phone != null && !phone.equals("")){
            user1 = userService.getByPhone(phone);
        }
        if(user1 == null){
            return Result.fail("???????????????");
        }
        if(password != null && !password.equals("")){
            user1.setPassword(password);
        }
        userService.update(user1);
        return Result.success(user1);
    }
    @PostMapping("/foreregister")
    public Object register(@RequestBody User user){
        String name = user.getName();
        String phone = user.getPhone();
        String password = user.getPassword();
        name = HtmlUtils.htmlEscape(name);
        if(name != null)
            user.setName(name);
        if(phone != null)
            user.setPhone(phone);
        boolean exist = userService.isExist(name);
        if(exist){
            String message = "????????????????????????";
            return Result.fail(message);
        }
        user.setPassword(password);
        userService.add(user);
        return Result.success();
    }
    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session){
        String name = userParam.getName();
        String phone = userParam.getPhone();
        String email = userParam.getEmail();
        name = HtmlUtils.htmlEscape(name);

        if(name != null && !name.equals("")){
            User user = userService.get(name, userParam.getPassword());
            if(user == null){
                String message = "?????????????????????";
                return Result.fail(message);
            }else{
                session.setAttribute("user", user);
                return Result.success();
            }
        }
        if(phone != null && !phone.equals("")){
            User user = userService.getByPhone(phone, userParam.getPassword());
            if(user == null){
                String message = "?????????????????????";
                return Result.fail(message);
            }else{
                session.setAttribute("user", user);
                return Result.success();
            }
        }
        if(email != null && !email.equals("")){
            User user = userService.getByEmail(email, userParam.getPassword());
            if(user == null){
                String message = "?????????????????????";
                return Result.fail(message);
            }else{
                session.setAttribute("user", user);
                return Result.success();
            }
        }
        return null;
    }
    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") int pid){
        Product product = productService.get(pid);

        List<ProductImage> productSingleImages = productImageService.listSingleProductImages(product);
        List<ProductImage> productDetailImages = productImageService.listDetailProductImages(product);
        product.setProductSingleImage(productSingleImages);
        product.setProductDetailImage(productDetailImages);

        List<PropertyValue> pvs = propertyValueService.list(product);
        List<Review> reviews = reviewService.list(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProductImage(product);

        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("pvs", pvs);
        map.put("reviews", reviews);

        return Result.success(map);
    }
    @GetMapping("/forecheckLogin")
    public Result checkLogin(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user != null){
            return Result.success(user);
        }
        return Result.fail("???????????????");
    }
    @GetMapping(value = "/forelogout")
    public void logout(HttpSession session){
        session.removeAttribute("user");
    }
    @GetMapping(value = "forecategory/{cid}")
    public Object category(@PathVariable int cid, String sort){
        Category c = categoryService.getOne(cid);
        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());
        categoryService.removeCategoryFromProduct(c);

        if(sort != null){
            switch(sort){
                case "review":
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(c.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;
                case "aa":
                    Collections.sort(c.getProducts(), new ProductAllComparator());
                    break;
            }
        }
        return c;
    }
    @PostMapping("foresearch")
    public Object search(@RequestParam String keyword){
        if(keyword == null)
            keyword = "";
        List<Product> ps = productService.search(keyword, 0, 20);
        productService.setSaleAndReviewNumber(ps);
        productImageService.setFirstProductImages(ps);
        return ps;
    }
    @GetMapping("forebuyone")
    public Object buyone(int pid, int num, HttpSession session){
        return buyoneAndAddCart(pid, num, session);
    }
    private int buyoneAndAddCart(int pid, int num, HttpSession session){
        Product product = productService.get(pid);
        int oiid = 0;

        User user = (User)session.getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUser(user);
        for(OrderItem oi : ois){
            if(oi.getProduct().getId() == product.getId()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setProduct(product);
            oi.setNumber(num);
            orderItemService.add(oi);
            oiid = oi.getId();
        }
        return oiid;
    }
    @GetMapping("forebuy")
    public Object buy(String[] oiid, HttpSession session){
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;

        for (String strid : oiid){
            int id = Integer.parseInt(strid);
            OrderItem oi = orderItemService.get(id);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
            orderItems.add(oi);
        }
        productImageService.setFirstProductImagesOnOrderItems(orderItems);
        session.setAttribute("ois", orderItems);
        Map<String, Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("total", total);
        return Result.success(map);
    }
    @GetMapping("foreaddCart")
    public Object addCart(int pid, int num, HttpSession session){
        buyoneAndAddCart(pid,num, session);
        return Result.success();
    }
    @GetMapping("forecart")
    public Object cart(HttpSession session){
        User user = (User)session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user);
        productImageService.setFirstProductImagesOnOrderItems(ois);
        return ois;
    }
    @DeleteMapping("foredeleteOrderItem")
    public Object deleteItemFromCart(int oiid, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null)
            return Result.fail("?????????");
//        ?????????????????????????????????
        OrderItem orderItem = orderItemService.get(oiid);
        Product product = orderItem.getProduct();
//        ??????????????????????????????????????????
        CartStatus cartStatus = cartStatusService.get(user, product);
//        ?????????????????????
        cartStatus.setStatus(0);
        cartStatusService.set(cartStatus);
        orderItemService.delete(oiid);
        return Result.success();
    }
    @GetMapping("forechangeOrderItem")
    public Object changeOrderItem(HttpSession session, int pid, int num){
        User user = (User)session.getAttribute("user");
        if(user == null){
            return Result.fail("?????????");
        }
        List<OrderItem> ois = orderItemService.listByUser(user);
        for (OrderItem oi : ois){
            if(oi.getProduct().getId() == pid){
                oi.setNumber(num);
                orderItemService.update(oi);
                break;
            }
        }
        return Result.success();
    }
    @PostMapping("forecreateOrder")
    public Object createOrder(@RequestBody Order order, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null)
            return Result.fail("?????????");
        String orderCode = new
                SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date())
                + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderService.waitPay);
        List<OrderItem> ois = (List<OrderItem>)session.getAttribute("ois");
        float total = orderService.add(order, ois);
//        ???????????????????????????????????????0
//        ??????????????????????????????
        for(OrderItem orderItem : ois){
            Product product = orderItem.getProduct();
//            ????????????????????????????????????????????????
            CartStatus cartStatus = cartStatusService.get(user, product);
            cartStatus.setStatus(0);
            cartStatusService.set(cartStatus);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);

        return Result.success(map);
    }
    @GetMapping("forepayed")
    public Object payed(int oid){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        return order;
    }
    @GetMapping("forebought")
    public Object bought(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null){
            return Result.fail("?????????");
        }
        List<Order> os = orderService.listByUserWithoutDelete(user);
        orderService.removeOrderFromOrderItem(os);
        return os;
    }
    @PutMapping("foredeleteOrder")
    public Object deleteOrder(int oid){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return Result.success();
    }
    @GetMapping("foreconfirmPay")
    public Object confirmPay(int oid){
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        orderService.cacl(o);
        orderService.removeOrderFromOrderItem(o);
        return o;
    }
    @GetMapping("foreorderConfirmed")
    public Object orderConfirmed(int oid){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());
        orderService.update(order);
        return Result.success();
    }
    @GetMapping("forereview")
    public Object review(int oid){
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        orderService.removeOrderFromOrderItem(order);
        Product product = order.getOrderItemList().get(0).getProduct();
        List<Review> reviews = reviewService.list(product);
        productService.setSaleAndReviewNumber(product);
        Map<String, Object> map = new HashMap<>();
        map.put("p", product);
        map.put("o", order);
        map.put("reviews", reviews);

        return Result.success(map);
    }
    @PostMapping("foredoreview")
    public Object doreview(HttpSession session, int oid, int pid, String content){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);

        Product p = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);

        User user = (User)session.getAttribute("user");
        if(user == null)
            return Result.fail("?????????");
        Review review = new Review();
        review.setContent(content);
        review.setProduct(p);
        review.setCreateDate(new Date());
        review.setUser(user);
        reviewService.add(review);
        return Result.success();
    }
    @GetMapping("setCartStatus")
    public Object setCartStatus(int pid, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null)
            return Result.fail("?????????");
        Product product = productService.get(pid);
        CartStatus cartStatus = cartStatusService.get(user, product);
        cartStatus.setStatus(1);
        cartStatusService.set(cartStatus);
        return Result.success(cartStatus);
    }
    @GetMapping("getCartStatus")
    public boolean getCartStatus(int pid, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null)
            return false;
        Product product = productService.get(pid);
        CartStatus cartStatus = cartStatusService.get(user, product);
        if(cartStatus == null){
            CartStatus cartStatus1 = new CartStatus();
            cartStatus1.setUser(user);
            cartStatus1.setProduct(product);
            cartStatusService.set(cartStatus1);
            cartStatus = cartStatus1;
        }
        if(cartStatus.getStatus() == 1)
            return true;
        return false;
    }
    @GetMapping("getProvinces")
    public Object getProvinces(){
        return provinceService.get();
    }
    @GetMapping("getCityByProvince")
    public Object getCityByProvince(String provinceName){
        Province province = provinceService.get(provinceName);
        List<City> cities = cityService.get(province);
        return cities;
    }
    @GetMapping("getCountyByCity")
    public Object getCountyByCity(String cityName){
        City city = cityService.get(cityName);
        List<County> counties = countyService.get(city);
        return counties;
    }
    @GetMapping("getTownByCounty")
    public Object getTownByCounty(String countyName){
        County county = countyService.get(countyName);
        List<Town> towns = townService.get(county);
        return towns;
    }
    @PostMapping("setAddress")
    public Object setAddress(@RequestBody Address address, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null)
            return Result.fail("?????????");
        List<Address> addresses = addressService.get(user);
        if(address.getStatus().equals("default")){
            for (Address addr : addresses){
                addr.setStatus("normal");
                addressService.save(addr);
            }
        }
        address.setUser(user);
        addressService.set(address);
        return Result.success();
    }
    @GetMapping("getAddress")
    public Object getAddress(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null)
            return Result.fail("?????????");
       List<Address> addresses = addressService.get(user);
       return Result.success(addresses);
    }
    @GetMapping("setDefaultAddress")
    public Object setDefaultAddress(int addressId, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null)
            return Result.fail("?????????");
        List<Address> addresses = addressService.get(user);
        for (Address address : addresses){
            address.setStatus("normal");
        }
        Address address = addressService.get(addressId);
        address.setStatus("default");
        addressService.save(address);
        return Result.success();
    }
    @GetMapping("deleteAddress")
    public void deleteAddress(int addressId){
        addressService.delete(addressId);
    }
    @GetMapping("getAddressById")
    public Object getAddressById(int addressId){
        return addressService.get(addressId);
    }
    @PostMapping("updateAddress")
    public void updateAddress(@RequestBody Address address,HttpSession session){
        User user = (User)session.getAttribute("user");
        List<Address> addresses = addressService.get(user);
        if(address.getStatus().equals("default")){
            for (Address addr : addresses){
                addr.setStatus("normal");
                addressService.save(addr);
            }
        }
        addressService.update(address);
    }

    @RequestMapping("/verty")
    public Object verty(HttpServletRequest request, @RequestBody String vertyCode) {
        if((vertyCode.indexOf("+") != -1) || (vertyCode.indexOf("=") != -1)){
            vertyCode = vertyCode.replace("=", "");
            vertyCode = vertyCode.replace("+", "");
        }
        if (!CodeUtil.checkVerifyCode(request, vertyCode)) {
            return Result.fail("???????????????");
        } else {
            return Result.success();
        }
    }

    @PostMapping("userInfoImage")
    public void userInfoImage(HttpSession session,HttpServletRequest request, MultipartFile image)throws Exception{
        if(image == null)
            return;
        User user = (User)session.getAttribute("user");
        File userFolder = new File(request.getServletContext().getRealPath("img/userImage"));
        File file = new File(userFolder, user.getName()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try{
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
            String imgPath = "img/" + userFolder.getName() + "/" + file.getName();
            user.setPhoto(imgPath);
            userService.update(user);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @PostMapping("userInfoMain")
    public Object userInfoMain(HttpSession session, @RequestBody User user2){
        User user = (User)session.getAttribute("user");
        if(user2.getName() != null && !user2.getName().equals(""))
            user.setName(user2.getName());
        if(user2.getPassword() != null && !user2.getPassword().equals(""))
            user.setPassword(user2.getPassword());
        if(user2.getPhone() != null && !user2.getPhone().equals(""))
            user.setPhone(user2.getPhone());
        if(user2.getEmail() != null && !user2.getEmail().equals(""))
            user.setEmail(user2.getEmail());
        if(user2.getSex() != null && !user2.getSex().equals(""))
            user.setSex(user2.getSex());
        if(user2.getNickname() != null && !user2.getNickname().equals(""))
            user.setNickname(user2.getNickname());
        if(user2.getRealname() != null && !user2.getRealname().equals(""))
            user.setRealname(user2.getRealname());
        if(user2.getAddress() != null && !user2.getAddress().equals(""))
            user.setAddress(user2.getAddress());
        if(user2.getDetail() != null && !user2.getDetail().equals(""))
            user.setDetail(user2.getDetail());
        userService.update(user);
        return user;
    }
    @GetMapping("getDefaultAddress")
    public Object getDefaultAddress(HttpSession session){
        User user = (User)session.getAttribute("user");
        List<Address> addresses = addressService.get(user);
        for (Address address : addresses){
            if(address.getStatus().equals("default")){
                return Result.success(address);
            }
        }
        return Result.fail("?????????????????????????????????");
    }
}
