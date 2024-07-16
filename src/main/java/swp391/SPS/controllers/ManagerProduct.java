package swp391.SPS.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp391.SPS.entities.Brand;
//import swp391.SPS.entities.Category;
import swp391.SPS.entities.Phone;
import swp391.SPS.entities.Picture;
import swp391.SPS.exceptions.FileNotFoundException;
import swp391.SPS.services.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ManagerProduct {

    @Autowired
    BrandService brandService;
    @Autowired
    PhoneService phoneService;

    @Autowired
    PictureService pictureService;


    @GetMapping("/manageProduct")
    public String viewProduct(Model model, @RequestParam(name = "keyword", required = false) String name, @RequestParam(name = "pageNumber", defaultValue = "1") int page) {
        Page<Phone> list = phoneService.findPhonePage(page);
        model.addAttribute("listBrand", brandService.findAllBrand());
        if (name != null) {

            list = phoneService.searchPhone(name, page);
            model.addAttribute("keyword", name);
        }
        model.addAttribute("listPhone", list);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", page);
        return "products";
    }

    @GetMapping("/manageProduct/json")
    @ResponseBody
    public Map<String, Object> viewProductJson(@RequestParam(name = "keyword", required = false) String name, @RequestParam(name = "pageNumber", defaultValue = "1") int page) {
        Page<Phone> list;
        if (name != null && !name.isEmpty() ) {
            list = phoneService.searchPhone(name, page);
        } else {
            list = phoneService.findPhonePage(page);
        }

        Map<String, Object> response = new HashMap<>();
        // Kiểm tra nếu list là null hoặc không có sản phẩm nào
        if (list == null || list.getContent().isEmpty()) {
            response.put("htmlContent", ""); // Hoặc một thông báo HTML thích hợp
            response.put("totalPages", 0);
            response.put("currentPage", page);
            response.put("noProducts", true); // Thêm một trường để kiểm tra trên frontend
            return response;
        }
        response.put("htmlContent", generateHtmlContent(list.getContent()));
        response.put("totalPages", list.getTotalPages());
        response.put("currentPage", page);
        return response;
    }

    @GetMapping("/searchStatus/json")
    @ResponseBody
    public Map<String, Object> viewProductByStatus(@RequestParam(name = "selectedValue") int selectedValue, @RequestParam(name = "pageNumber", defaultValue = "1") int page) {
        Page<Phone> list = phoneService.findPhonePage(page);
        boolean status;
        if (selectedValue == 1 ) {
            status = true;
            list = phoneService.searchPhoneByStatus(status,page);
        }
        else if(selectedValue == 0) {
            status = false;
            list = phoneService.searchPhoneByStatus(status,page);
        }

        Map<String, Object> response = new HashMap<>();
        // Kiểm tra nếu list là null hoặc không có sản phẩm nào
        if (list == null || list.getContent().isEmpty()) {
            response.put("htmlContent", ""); // Hoặc một thông báo HTML thích hợp
            response.put("totalPages", 0);
            response.put("currentPage", page);
            response.put("noProducts", true); // Thêm một trường để kiểm tra trên frontend
            return response;
        }
        response.put("htmlContent", generateHtmlContent(list.getContent()));
        response.put("totalPages", list.getTotalPages());
        response.put("currentPage", page);
        return response;
    }


    private String generateHtmlContent(List<Phone> phones) {
        StringBuilder htmlContent = new StringBuilder();
        for (Phone phone : phones) {
            htmlContent.append("<tr>");
            htmlContent.append("<td class=\"tm-product-name\">").append(phone.getPhoneId()).append("</td>");
            htmlContent.append("<td>").append(phone.getProductName()).append("</td>");
            htmlContent.append("<td>").append(phone.getPrice()).append(" $</td>");
            htmlContent.append("<td><a href=\"/edit-product?id=").append(phone.getPhoneId()).append("\" class=\"btn btn-link\" style=\"color: white;\">Edit</a></td>");
            htmlContent.append("<td class=\"action-links\">");
            if (phone.getStatus()) {
                htmlContent.append("<a onclick=\"changeStatus(this)\" class=\"stock active\" style=\"color: green;\" data-id=\"").append(phone.getPhoneId()).append("\">");
                htmlContent.append("<i class=\"fas fa-check-circle\" data-toggle=\"tooltip\" title=\"In Stock\"></i></a>");
            } else {
                htmlContent.append("<a onclick=\"changeStatus(this)\" class=\"stock inactive\" style=\"color: gray;\" data-id=\"").append(phone.getPhoneId()).append("\">");
                htmlContent.append("<i class=\"fas fa-times-circle\" data-toggle=\"tooltip\" title=\"Out of Stock\"></i></a>");
            }
            htmlContent.append("</td>");
            htmlContent.append("</tr>");
        }
        return htmlContent.toString();
    }


    @GetMapping("/add-product")
    public String addP(Model model) {
        model.addAttribute("listBrand", brandService.findAllBrand());
        return "add-product";
    }

    @GetMapping("/edit-product")
    public String viewEditphone(@RequestParam("id") String idPhone, Model model) throws FileNotFoundException {
       if(idPhone.isEmpty() || idPhone.equals("")){
           model.addAttribute("check",true);
           return "Edit-product";
       }
        int id = Integer.parseInt(idPhone);
        Phone p = phoneService.getPhoneByIdForManager(id);
        if(p!=null) {
            model.addAttribute("phone", p);
        }
        else {
            model.addAttribute("check",true);
            return "Edit-product";
        }
        model.addAttribute("check",false);
        return "Edit-product";
    }

    @PostMapping("/editProduct")
    public String EditPhone(@RequestParam("pid") int pid,
                            @RequestParam("picid") int picid,
                            @RequestParam("name") String productName,
                            @RequestParam("price") double price,
                            @RequestParam("cpu") String cpu,
                            @RequestParam("memory") double memory,
//                             @RequestParam("category") int cate,
                            @RequestParam("sim") String sim,
                            @RequestParam("ram") int ram,
                            @RequestParam("dis") double dis,
                            @RequestParam("origin") String origin,
                            @RequestParam("brand") int brand,
                            @RequestParam("pm") String pm,
                            @RequestParam("pf") String pf,
                            @RequestParam("pb") String pb,
                            @RequestParam("ps") String ps,
                            @RequestParam("camera") double camera,
                            @RequestParam("status") Boolean status,
                            @RequestParam("date") Date date, Model model, RedirectAttributes redirectAttributes) throws FileNotFoundException {
        model.addAttribute("listBrand", brandService.findAllBrand());
        Picture picture = new Picture(picid, pm, pf, pb, ps);
        pictureService.editPicture(pictureService.getPictureById(picid));

        Brand b = brandService.getBrand(brand);

        Phone phone = new Phone();
        phone = Phone.builder().productName(productName).status(status).phoneId(pid).cpu(cpu).ram(ram).sim(sim).price(price).camera(camera).memory(memory).origin(origin).brand(b).picture(picture).releaseDate(date.toLocalDate()).display(dis).build();
        phoneService.editPhone(phone);
        redirectAttributes.addFlashAttribute("message", "Chỉnh sửa thành công sản phẩm "+phone.getProductName() +" !");
        return "redirect:/manageProduct";
    }

    @GetMapping("/add-brand")
    public String viewAddBrand() {
        return "add-brand";
    }

    @GetMapping("/edit-brand")
    public String editBrand(@RequestParam("id") String idBrand, Model model) throws FileNotFoundException {
        if(idBrand.isEmpty() || idBrand.equals("")){
            model.addAttribute("check",true);
            return "edit-brand";
        }
        Brand brand = brandService.getBrand(Integer.parseInt(idBrand));
       if(brand!=null) model.addAttribute("brand",brand );
       else{
           model.addAttribute("check",true);
           return "edit-brand";
       }
        model.addAttribute("check",false);

        return "edit-brand";
    }

    @PostMapping("/editbrand")
    public String editBrand(@RequestParam("id") int id,
                            @RequestParam("name") String name, Model model, RedirectAttributes redirectAttributes) {
        Brand brand = Brand.builder().brandId(id).brandName(name).build();
        brandService.editBrand(brand);
        redirectAttributes.addFlashAttribute("message", "Chỉnh sửa brand thành công!");
        return "redirect:/manageProduct";
    }


    @PostMapping("/addBrand")
    public String addBrand(@RequestParam("name") String name, Model model) {
        Brand brand = new Brand();
        brand = Brand.builder().brandName(name).build();
        brandService.addBrand(brand);
        List<Brand> lb = brandService.findAllBrand();
        for (Brand b : lb) {
            if (b.equals(brand)) {
                model.addAttribute("listBrand", brandService.findAllBrand());
                model.addAttribute("mess", "Thêm brand thành công!");
                return "add-brand";
            }
        }
        model.addAttribute("mess", "Thêm sản phẩm không thành công!");
        return "add-brand";
    }


    @PostMapping("/addProduct")
    public String addProduct(
            @RequestParam("name") String productName,
            @RequestParam("price") int price,
            @RequestParam("cpu") String cpu,
            @RequestParam("memory") int memory,
            @RequestParam("sim") String sim,
            @RequestParam("ram") int ram,
            @RequestParam("dis") int dis,
            @RequestParam("origin") String origin,
            @RequestParam("brand") int brand,
            @RequestParam("pm") String pm,
            @RequestParam("pf") String pf,
            @RequestParam("pb") String pb,
            @RequestParam("ps") String ps,
            @RequestParam("camera") int camera,
            @RequestParam("date") Date date,
            @RequestParam("status") Boolean status,
            Model model) throws FileNotFoundException {

        Picture picture = new Picture();
        picture = Picture.builder().site(ps).back(pb).front(pf).main(pm).build();
        pictureService.addPicture(picture);
        Brand b = brandService.getBrand(brand);
        picture = pictureService.getPictureById(picture.getPictureId());
        Phone phone = new Phone();
        phone = Phone.builder().productName(productName).status(status).cpu(cpu).ram(ram).sim(sim).price(price).camera(camera).memory(memory).origin(origin).brand(b).picture(picture).releaseDate(date.toLocalDate()).display(dis).build();
        phoneService.addPhone(phone);
        List<Phone> lphone = phoneService.findAllPhone();
        for (Phone p : lphone) {
            if (p.equals(phone)) {
                model.addAttribute("listBrand", brandService.findAllBrand());
                model.addAttribute("mess", "Đã thêm thành công "+ phone.getProductName()+" !");
                return "add-product";
            }
        }
        model.addAttribute("listBrand", brandService.findAllBrand());
        model.addAttribute("mess", "Thêm sản phẩm không thành công");
        return "add-product";
    }


}
