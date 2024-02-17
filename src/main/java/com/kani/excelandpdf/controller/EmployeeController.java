package com.kani.excelandpdf.controller;

import com.kani.excelandpdf.download.EmployeeExcel;
import com.kani.excelandpdf.download.EmployeePDF;
import com.kani.excelandpdf.entity.Employee;
import com.kani.excelandpdf.service.IEmployeeService;
import com.kani.excelandpdf.util.PageRender;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
    private final IEmployeeService employeeService;

    @GetMapping("/list")
    public String listOfEmployee(@RequestParam(name = "page", defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Employee> employees = employeeService.findAllEmployees(pageRequest);
        PageRender<Employee> pageRender = new PageRender<>("/list", employees);
        model.addAttribute("employees", employees);
        model.addAttribute("page", pageRender);
        return "front-end/listOfEmployee";
    }

    @GetMapping("/add")
    public String addEmployee(Map<String,Object> model){
        Employee employee = new Employee();
        model.put("employee", employee);
        model.put("title", "Add Employee");
        return "front-end/employee-form";
    }

    @PostMapping("/add")
    public String createEmployee(@Valid
                                 Employee employee,
                                 Model model,
                                 BindingResult result,
                                 RedirectAttributes attributes,
                                 SessionStatus sessionStatus){
        if(result.hasErrors()){
            model.addAttribute("employee", employee);
            model.addAttribute("title", "Add Employee");
            return "front-end/employee-form";
        }
        String message = (employee.getEmployeeId() != null) ? "Register Successfully" : "Register Failed";
        employeeService.saveEmployee(employee);
        sessionStatus.setComplete();
        attributes.addFlashAttribute("success", message);
        return "redirect:/list";
    }

    @GetMapping("/info/{id}")
    public String employeeInfo(@PathVariable(value = "id") Long id, Map<String , Object> model, RedirectAttributes attributes){
        Employee employee = employeeService.findEmployeeById(id);
        if(employee == null){
            attributes.addFlashAttribute("error", "Not fount");
            return "redirect:/list";
        }
        model.put("employee", employee);
        model.put("title", "Details Of " + employee.getFirstName());
        return "front-end/info";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") Long id, Map<String , Object> model, RedirectAttributes attributes){
        Employee employee = employeeService.findEmployeeById(id);
        if(employee == null) {
            attributes.addFlashAttribute("error", "Not found");
            return "redirect:/list";
        }
        model.put("employee", employee);
        model.put("title", "Update Employee");
        return "front-end/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @PathVariable(value = "id") Long id,  Employee employee,
                         Model model,
                         BindingResult result,
                         SessionStatus sessionStatus){
        if(result.hasErrors()){
            model.addAttribute("error", "Register Errors");
            return "redirect:/add";
        }
        Employee update = employeeService.findEmployeeById(id);
        update.setFirstName(employee.getFirstName());
        update.setLastName(employee.getLastName());
        update.setEmail(employee.getEmail());
        update.setPhone(employee.getPhone());
        update.setGender(employee.getGender());
        update.setSalary(employee.getSalary());
        update.setDate(employee.getDate());
        employeeService.saveEmployee(employee);
        sessionStatus.setComplete();
        return "redirect:/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes attributes){
        if(id > 0){
            employeeService.deleteEmployeeById(id);
                attributes.addFlashAttribute("success", "Deleted Successfully");
        }
        return "redirect:/list";
    }

    @GetMapping("/pdf")
    public void downloadEmployeePdf(HttpServletResponse response) throws IOException {
        response.setContentType("/application/pdf");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String date = dateFormat.format(new Date());

        String report = "Content-Disposition";
        String value = "attachment; filename=Employee_" + date + ".pdf";

        response.setHeader(report, value);

        List<Employee> employeeList = employeeService.findAllEmployees();

        EmployeePDF employeePDF = new EmployeePDF(employeeList);
        employeePDF.exportPdf(response);
    }

    @GetMapping("/excel")
    public void exportEmployeeExcel(HttpServletResponse response) throws IOException {
        response.setContentType("/application/octet-stream");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String date = dateFormat.format(new Date());

        String report = "Content-Disposition";
        String value = "attachment; filename=Employee_" + date + ".xlsx";

        response.setHeader(report, value);

        List<Employee> employeeList = employeeService.findAllEmployees();

        EmployeeExcel employeeExcel = new EmployeeExcel(employeeList);
        employeeExcel.exportExcel(response);
    }
}
