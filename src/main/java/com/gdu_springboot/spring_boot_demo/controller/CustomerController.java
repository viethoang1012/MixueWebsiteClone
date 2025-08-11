package com.gdu_springboot.spring_boot_demo.controller;

import com.gdu_springboot.spring_boot_demo.model.Customer;
import com.gdu_springboot.spring_boot_demo.model.Product;
import com.gdu_springboot.spring_boot_demo.model.Users;
import com.gdu_springboot.spring_boot_demo.service.CustomerService;
import com.gdu_springboot.spring_boot_demo.service.ProductService;
import com.gdu_springboot.spring_boot_demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
@RequestMapping("customers")
public class CustomerController {

    private  CustomerService customerService;
    private UserService userService;
    @Autowired
    public CustomerController(CustomerService customerService, UserService userService) {
        this.customerService = customerService;
        this.userService = userService;
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
//        binder.registerCustomEditor(String.class, stringTrimmerEditor);
//    }

@InitBinder
public void initBinder(WebDataBinder binder) {
    // Loại bỏ khoảng trắng trong các trường String
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    binder.registerCustomEditor(String.class, stringTrimmerEditor);

    // Đăng ký PropertyEditor cho Users
    binder.registerCustomEditor(Users.class, new PropertyEditorSupport() {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null || text.isEmpty()) {
                setValue(null);
                return;
            }
            try {
                Long id = Long.parseLong(text);
                Users user = userService.findById(id);
                if (user == null) {
                    throw new IllegalArgumentException("Không tìm thấy người dùng với ID: " + id);
                }
                setValue(user);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("ID người dùng không hợp lệ: " + text);
            }
        }
    });
}

    // Hiển thị danh sách khách hàng
    @GetMapping("/list-customers")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "admin/customers/list-customers";
    }

    // Hiển thị form thêm khách hàng
    @GetMapping("/customer-form")
    public String showCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        List<Users> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/customers/customer-form";
    }

    // Hiển thị form cập nhật khách hàng
    @GetMapping("/customer-form-update")
    public String showCustomerFormUpdate(@RequestParam("id")Long id , Model model) {
        Customer customer = customerService.findById(id);
        List<Users> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("customer", customer);
        return "admin/customers/customer-form-update";
    }

    // Lưu khách hàng (thêm hoặc cập nhật)
    @PostMapping("/save")
    public String save(@Valid Customer customer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("errorMessage", "Vui lòng kiểm tra lại thông tin khách hàng");
            return customer.getId() == null ? 
                   "admin/customers/customer-form" : 
                   "admin/customers/customer-form-update";
        }
        try {
            // Kiểm tra dữ liệu đầu vào
            if (customer.getFirst_name() == null || customer.getFirst_name().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Họ không được để trống");
                model.addAttribute("users", userService.findAll());
                return customer.getId() == null ? 
                       "admin/customers/customer-form" : 
                       "admin/customers/customer-form-update";
            }
            if (customer.getLast_name() == null || customer.getLast_name().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Tên không được để trống");
                model.addAttribute("users", userService.findAll());
                return customer.getId() == null ? 
                       "admin/customers/customer-form" : 
                       "admin/customers/customer-form-update";
            }
            if (customer.getUsers() == null) {
                model.addAttribute("errorMessage", "Vui lòng chọn người dùng");
                model.addAttribute("users", userService.findAll());
                return customer.getId() == null ? 
                       "admin/customers/customer-form" : 
                       "admin/customers/customer-form-update";
            }

            // Chuẩn hóa dữ liệu
            customer.setFirst_name(customer.getFirst_name().trim());
            customer.setLast_name(customer.getLast_name().trim());
            
            customerService.save(customer);
            model.addAttribute("successMessage", "Lưu khách hàng thành công");
            return "redirect:/customers/list-customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi lưu khách hàng: " + e.getMessage());
            model.addAttribute("users", userService.findAll());
            return customer.getId() == null ? 
                   "admin/customers/customer-form" : 
                   "admin/customers/customer-form-update";
        }
    }

    // Xóa khách hàng
    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("id") Long id, Model model) {
        try {
            customerService.deleteById(id);
            return "redirect:/customers/list-customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi xóa khách hàng: " + e.getMessage());
            return "redirect:/customers/list-customers";
        }
    }

}