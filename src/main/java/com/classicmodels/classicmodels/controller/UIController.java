package com.classicmodels.classicmodels.controller;

import com.classicmodels.classicmodels.dto.CustomerDTO;
import com.classicmodels.classicmodels.dto.EmployeeDTO;
import com.classicmodels.classicmodels.dto.OfficeDTO;
import com.classicmodels.classicmodels.entity.Customer;
import com.classicmodels.classicmodels.entity.Employee;
import com.classicmodels.classicmodels.entity.Office;
import com.classicmodels.classicmodels.service.CustomerService;
import com.classicmodels.classicmodels.service.EmployeeService;
import com.classicmodels.classicmodels.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class UIController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final OfficeService officeService;

    // ==========================================
    // PUBLIC ROUTES (Phase 2)
    // ==========================================
    @GetMapping("/")
    public String home() {
        return "public/home";
    }

    @GetMapping("/team/{memberName}")
    public String teamMember(@PathVariable String memberName, Model model) {
        String name = "";
        String role = "";
        String service = "";
        String image = "";
        String bio = "";
        java.util.List<java.util.Map<String, String>> endpoints = new java.util.ArrayList<>();

        switch (memberName.toLowerCase()) {
            case "sumit":
                name = "Sumit Sonawane";
                role = "Lead Backend Developer";
                service = "Customer Service";
                image = "/images/team/sumit.jpg";
                bio = "Architected the customer relationship management system, ensuring secure and efficient handle of client data archives.";
                addEndpoint(endpoints, "GET", "/api/v1/customers", "Retrieve a list of all customers.");
                addEndpoint(endpoints, "GET", "/api/v1/customers/{id}", "Retrieve a customer by their ID.");
                addEndpoint(endpoints, "POST", "/api/v1/customers", "Register a new customer.");
                addEndpoint(endpoints, "PUT", "/api/v1/customers/{id}", "Update details for an existing customer.");
                addEndpoint(endpoints, "DELETE", "/api/v1/customers/{id}", "Delete a customer record.");
                addEndpoint(endpoints, "GET", "/api/v1/customers/search?name={name}", "Search for customers by their name.");
                addEndpoint(endpoints, "GET", "/api/v1/customers/country?country={name}", "Retrieve customers located in a specific country.");
                addEndpoint(endpoints, "GET", "/api/v1/customers/city?city={name}", "Retrieve customers located in a specific city.");
                addEndpoint(endpoints, "GET", "/api/v1/customers/{id}/orders", "Retrieve all orders placed by the specified customer.");
                addEndpoint(endpoints, "GET", "/api/v1/customers/{id}/payments", "Retrieve all payments made by the specified customer.");
                addEndpoint(endpoints, "GET", "/api/v1/dashboard/stats", "Retrieve high-level statistics for the admin dashboard.");
                break;
            case "vikas":
                name = "Vikas Gadekar";
                role = "Senior Systems Engineer";
                service = "Order Service";
                image = "/images/team/vikas.jpg";
                bio = "Engineered the transaction processing engine, specializing in complex order lifecycle management and state transitions.";
                addEndpoint(endpoints, "GET", "/api/v1/orders", "Retrieve all orders.");
                addEndpoint(endpoints, "GET", "/api/v1/orders/{id}", "Retrieve a specific order by ID.");
                addEndpoint(endpoints, "POST", "/api/v1/orders", "Create a new order.");
                addEndpoint(endpoints, "PUT", "/api/v1/orders/{id}", "Update an existing order.");
                addEndpoint(endpoints, "DELETE", "/api/v1/orders/{id}", "Delete a specific order.");
                addEndpoint(endpoints, "GET", "/api/v1/orders/search?status={status}", "Search for orders by their current status.");
                addEndpoint(endpoints, "GET", "/api/v1/orders/customer/{customerId}", "Retrieve all orders placed by a specific customer.");
                addEndpoint(endpoints, "GET", "/api/v1/orders/{id}/details", "Retrieve all line items (details) for a specific order.");
                addEndpoint(endpoints, "POST", "/api/v1/orders/{id}/details", "Add a new line item to an existing order.");
                addEndpoint(endpoints, "GET", "/api/v1/orders/{id}/total", "Get the total monetary value breakdown of an order.");
                break;
            case "vedant":
                name = "Vedant Dagade";
                role = "Inventory Solutions Architect";
                service = "Product Service";
                image = "/images/team/vedant.jpg";
                bio = "Designed the archival inventory system, focusing on SKU optimization and multi-category product line structures.";
                addEndpoint(endpoints, "GET", "/api/v1/productlines", "Retrieve a list of all product lines.");
                addEndpoint(endpoints, "GET", "/api/v1/productlines/{name}", "Retrieve details of a specific product line.");
                addEndpoint(endpoints, "POST", "/api/v1/productlines", "Create a new product line.");
                addEndpoint(endpoints, "PUT", "/api/v1/productlines/{name}", "Update an existing product line.");
                addEndpoint(endpoints, "DELETE", "/api/v1/productlines/{name}", "Delete a specific product line.");
                addEndpoint(endpoints, "GET", "/api/v1/products", "Retrieve a list of all products.");
                addEndpoint(endpoints, "GET", "/api/v1/products/{code}", "Retrieve details of a specific product.");
                addEndpoint(endpoints, "POST", "/api/v1/products", "Create a new product.");
                addEndpoint(endpoints, "PUT", "/api/v1/products/{code}", "Update an existing product.");
                addEndpoint(endpoints, "DELETE", "/api/v1/products/{code}", "Delete a specific product.");
                addEndpoint(endpoints, "GET", "/api/v1/products/search?line={line}", "Search products belonging to a specific product line.");
                addEndpoint(endpoints, "GET", "/api/v1/products/low-stock?threshold={val}", "Retrieve products with stock quantity below threshold.");
                break;
            case "vrushab":
                name = "Vrushab Salunke";
                role = "Finance & Logistics Specialist";
                service = "Payment & Office";
                image = "/images/team/vrushab.jpg";
                bio = "Directed the global logistics and financial reconciliation modules, ensuring cross-border office synchronization.";
                addEndpoint(endpoints, "GET", "/api/v1/payments", "Retrieve all payments in the system.");
                addEndpoint(endpoints, "GET", "/api/v1/payments/customer/{id}", "Retrieve all payments made by a specific customer.");
                addEndpoint(endpoints, "POST", "/api/v1/payments", "Record a new payment.");
                addEndpoint(endpoints, "PUT", "/api/v1/payments/{cNo}/{checkNo}", "Update an existing payment's details.");
                addEndpoint(endpoints, "DELETE", "/api/v1/payments/{cNo}/{checkNo}", "Delete a specific payment record.");
                addEndpoint(endpoints, "GET", "/api/v1/offices", "Retrieve all company offices.");
                addEndpoint(endpoints, "GET", "/api/v1/offices/{code}", "Retrieve details of a specific office.");
                addEndpoint(endpoints, "POST", "/api/v1/offices", "Add a new office.");
                addEndpoint(endpoints, "PUT", "/api/v1/offices/{code}", "Update an existing office.");
                addEndpoint(endpoints, "DELETE", "/api/v1/offices/{code}", "Delete an office record.");
                break;
            case "anshul":
                name = "Anshul Bhaisare";
                role = "Human Capital & Security lead";
                service = "Employee Service";
                image = "/images/team/anshul.jpg";
                bio = "Specializes in organizational security and internal data visualization, managing employee hierarchies and reporting.";
                addEndpoint(endpoints, "GET", "/api/v1/employees", "Retrieve all employees.");
                addEndpoint(endpoints, "GET", "/api/v1/employees/{id}", "Retrieve an employee by their ID.");
                addEndpoint(endpoints, "POST", "/api/v1/employees", "Add a new employee to the system.");
                addEndpoint(endpoints, "PUT", "/api/v1/employees/{id}", "Update an employee's details.");
                addEndpoint(endpoints, "DELETE", "/api/v1/employees/{id}", "Remove an employee record.");
                addEndpoint(endpoints, "GET", "/api/v1/employees/search?name={name}", "Search for employees by name.");
                addEndpoint(endpoints, "GET", "/api/v1/employees/filter/jobtitle?title={val}", "Retrieve employees filtered by job title.");
                addEndpoint(endpoints, "GET", "/api/v1/employees/filter/office?code={val}", "Retrieve employees filtered by office code.");
                addEndpoint(endpoints, "GET", "/api/v1/employees/filter/manager?id={val}", "Retrieve employees managed by a specific manager.");
                addEndpoint(endpoints, "GET", "/api/v1/employees/{id}/customers", "Retrieve customers managed by a specific sales representative.");
                break;
            default:
                return "redirect:/";
        }

        model.addAttribute("name", name);
        model.addAttribute("role", role);
        model.addAttribute("service", service);
        model.addAttribute("image", image);
        model.addAttribute("bio", bio);
        model.addAttribute("endpoints", endpoints);
        
        return "public/team-member";
    }

    private void addEndpoint(java.util.List<java.util.Map<String, String>> list, String method, String path, String desc) {
        java.util.Map<String, String> endpoint = new java.util.HashMap<>();
        endpoint.put("method", method);
        endpoint.put("path", path);
        endpoint.put("desc", desc);
        list.add(endpoint);
    }

    @GetMapping("/api-explorer/{endpointId}")
    public String apiExplorer(@PathVariable String endpointId, Model model) {
        model.addAttribute("endpointId", endpointId);
        return "public/api-explorer";
    }

    @GetMapping("/login")
    public String login() {
        return "public/login";
    }

    @GetMapping("/register")
    public String register() {
        return "public/register";
    }


    // ==========================================
    // USER ROUTES (Phase 3)
    // ==========================================
    @GetMapping("/user/home")
    public String userDashboard() {
        return "user/dashboard";
    }

    @GetMapping("/user/categories")
    public String userCategories() {
        return "user/categories";
    }

    @GetMapping("/user/products")
    public String userProducts(@RequestParam(required = false) String line, Model model) {
        model.addAttribute("line", line);
        return "user/products";
    }

    @GetMapping("/user/products/{code}")
    public String userProductDetail(@PathVariable String code, Model model) {
        model.addAttribute("code", code);
        return "user/product-detail";
    }

    @GetMapping("/user/orders/new")
    public String userOrderNew() {
        return "user/order-new";
    }

    @GetMapping("/user/orders")
    public String userOrders() {
        return "user/orders-list";
    }

    @GetMapping("/user/orders/{id}")
    public String userOrderDetail(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "user/order-detail";
    }

    @GetMapping("/user/payments/new")
    public String userPaymentNew() {
        return "user/payment-new";
    }

    @GetMapping("/user/payments")
    public String userPayments() {
        return "user/payments-list";
    }


    // ==========================================
    // ADMIN ROUTES (Phase 4)
    // ==========================================
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }


    @GetMapping("/admin/customers")
    public String adminCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) Integer editId,
            Model model) {
        
        if (city != null && !city.isEmpty()) {
            // City is more specific, prioritize it
            model.addAttribute("customers", customerService.getByCity(city));
        } else if (country != null && !country.isEmpty()) {
            model.addAttribute("customers", customerService.getByCountry(country));
        } else if (name != null && !name.isEmpty()) {
            model.addAttribute("customers", customerService.searchByName(name));
        } else {
            model.addAttribute("customers", customerService.getAllCustomers());
        }

        // For the dropdowns and forms
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("countries", customerService.getAllCountries());
        model.addAttribute("cities", customerService.getAllCities());
        
        // Handle drawer state
        model.addAttribute("action", action); // 'add' or 'edit'
        if ("edit".equals(action) && editId != null) {
            model.addAttribute("customerForm", customerService.getCustomerById(editId));
        } else {
            model.addAttribute("customerForm", new CustomerDTO());
        }

        return "admin/customers";
    }

    @PostMapping("/admin/customers/save")
    public String saveCustomer(@ModelAttribute("customerForm") CustomerDTO dto, RedirectAttributes ra) {
        try {
            // Build entity from DTO
            Customer customer = new Customer();
            customer.setCustomerNumber(dto.getCustomerNumber());
            customer.setCustomerName(dto.getCustomerName());
            customer.setContactFirstName(dto.getContactFirstName());
            customer.setContactLastName(dto.getContactLastName());
            customer.setPhone(dto.getPhone());
            customer.setAddressLine1(dto.getAddressLine1());
            customer.setAddressLine2(dto.getAddressLine2());
            customer.setCity(dto.getCity());
            customer.setState(dto.getState());
            customer.setPostalCode(dto.getPostalCode());
            customer.setCountry(dto.getCountry());
            customer.setCreditLimit(dto.getCreditLimit());

            // Set sales rep relationship if provided
            if (dto.getSalesRepEmployeeNumber() != null) {
                Employee salesRep = new Employee();
                salesRep.setEmployeeNumber(dto.getSalesRepEmployeeNumber());
                customer.setSalesRep(salesRep);
            }

            // Determine create vs update
            try {
                customerService.getCustomerById(dto.getCustomerNumber());
                // Exists, so update
                customerService.updateCustomer(dto.getCustomerNumber(), customer);
                ra.addFlashAttribute("message", "Customer updated successfully!");
            } catch (Exception e) {
                // Does not exist, so create
                customerService.createCustomer(customer);
                ra.addFlashAttribute("message", "Customer created successfully!");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error saving customer: " + e.getMessage());
        }
        return "redirect:/admin/customers";
    }

    @PostMapping("/admin/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id, RedirectAttributes ra) {
        customerService.deleteCustomer(id);
        ra.addFlashAttribute("message", "Customer deleted successfully!");
        return "redirect:/admin/customers";
    }

    @GetMapping("/admin/customers/{id}")
    public String adminCustomerDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("customer", customerService.getCustomerById(id));
        model.addAttribute("orders", customerService.getOrdersByCustomer(id));
        model.addAttribute("payments", customerService.getPaymentsByCustomer(id));
        return "admin/customer-detail";
    }

    @GetMapping("/admin/employees")
    public String adminEmployees(
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String jobTitle,
            @RequestParam(required = false) String officeCode,
            @RequestParam(required = false) Integer managerId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) Integer editId,
            Model model) {
        
        if (jobTitle != null && !jobTitle.isEmpty()) {
            model.addAttribute("employees", employeeService.getByJobTitle(jobTitle));
        } else if (officeCode != null && !officeCode.isEmpty()) {
            model.addAttribute("employees", employeeService.getByOfficeCode(officeCode));
        } else if (managerId != null) {
            model.addAttribute("employees", employeeService.getByManager(managerId));
        } else if (lastName != null && !lastName.isEmpty()) {
            model.addAttribute("employees", employeeService.searchByLastName(lastName));
        } else {
            model.addAttribute("employees", employeeService.getAllEmployees());
        }

        model.addAttribute("offices", officeService.getAllOffices());
        model.addAttribute("jobTitles", employeeService.getAllJobTitles());
        model.addAttribute("managers", employeeService.getAllEmployees()); // For manager selection
        
        model.addAttribute("action", action);
        if ("edit".equals(action) && editId != null) {
            model.addAttribute("employeeForm", employeeService.getEmployeeById(editId));
        } else {
            model.addAttribute("employeeForm", new EmployeeDTO());
        }

        return "admin/employees";
    }

    @PostMapping("/admin/employees/save")
    public String saveEmployee(@ModelAttribute("employeeForm") EmployeeDTO dto, RedirectAttributes ra) {
        try {
            // Build entity from DTO
            Employee employee = new Employee();
            employee.setEmployeeNumber(dto.getEmployeeNumber());
            employee.setLastName(dto.getLastName());
            employee.setFirstName(dto.getFirstName());
            employee.setExtension(dto.getExtension());
            employee.setEmail(dto.getEmail());
            employee.setJobTitle(dto.getJobTitle());

            // Set office relationship if provided
            if (dto.getOfficeCode() != null && !dto.getOfficeCode().isEmpty()) {
                Office office = new Office();
                office.setOfficeCode(dto.getOfficeCode());
                employee.setOffice(office);
            }

            // Set manager relationship if provided
            if (dto.getManagerEmployeeNumber() != null) {
                Employee manager = new Employee();
                manager.setEmployeeNumber(dto.getManagerEmployeeNumber());
                employee.setManager(manager);
            }

            // Determine create vs update
            try {
                employeeService.getEmployeeById(dto.getEmployeeNumber());
                // Exists, so update
                employeeService.updateEmployee(dto.getEmployeeNumber(), employee);
                ra.addFlashAttribute("message", "Employee updated successfully!");
            } catch (Exception e) {
                // Does not exist, so create
                employeeService.createEmployee(employee);
                ra.addFlashAttribute("message", "Employee created successfully!");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error saving employee: " + e.getMessage());
        }
        return "redirect:/admin/employees";
    }

    @PostMapping("/admin/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id, RedirectAttributes ra) {
        employeeService.deleteEmployee(id);
        ra.addFlashAttribute("message", "Employee removed successfully!");
        return "redirect:/admin/employees";
    }

    @GetMapping("/admin/employees/{id}")
    public String adminEmployeeDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        model.addAttribute("customers", employeeService.getCustomersBySalesRep(id));
        // Also might want subordinates?
        model.addAttribute("subordinates", employeeService.getByManager(id));
        return "admin/employee-detail";
    }

    @GetMapping("/admin/offices")
    public String adminOffices(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String territory,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String editCode,
            Model model) {
        
        if (territory != null && !territory.isEmpty()) {
            model.addAttribute("offices", officeService.getByTerritory(territory));
        } else if (country != null && !country.isEmpty()) {
            model.addAttribute("offices", officeService.getByCountry(country));
        } else if (city != null && !city.isEmpty()) {
            model.addAttribute("offices", officeService.searchByCity(city));
        } else {
            model.addAttribute("offices", officeService.getAllOffices());
        }

        model.addAttribute("countries", officeService.getAllCountries());
        model.addAttribute("territories", officeService.getAllTerritories());
        
        model.addAttribute("action", action);
        if ("edit".equals(action) && editCode != null && !editCode.isEmpty()) {
            model.addAttribute("officeForm", officeService.getOfficeByCode(editCode));
        } else {
            model.addAttribute("officeForm", new OfficeDTO());
        }

        return "admin/offices";
    }

    @PostMapping("/admin/offices/save")
    public String saveOffice(@ModelAttribute("officeForm") OfficeDTO dto, RedirectAttributes ra) {
        try {
            // Build entity from DTO
            Office office = new Office();
            office.setOfficeCode(dto.getOfficeCode());
            office.setCity(dto.getCity());
            office.setPhone(dto.getPhone());
            office.setAddressLine1(dto.getAddressLine1());
            office.setAddressLine2(dto.getAddressLine2());
            office.setState(dto.getState());
            office.setCountry(dto.getCountry());
            office.setPostalCode(dto.getPostalCode());
            office.setTerritory(dto.getTerritory());

            // Determine create vs update
            try {
                officeService.getOfficeByCode(dto.getOfficeCode());
                // Exists, so update
                officeService.updateOffice(dto.getOfficeCode(), office);
                ra.addFlashAttribute("message", "Office updated successfully!");
            } catch (Exception e) {
                // Does not exist, so create
                officeService.createOffice(office);
                ra.addFlashAttribute("message", "Office created successfully!");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error saving office: " + e.getMessage());
        }
        return "redirect:/admin/offices";
    }

    @PostMapping("/admin/offices/delete/{code}")
    public String deleteOffice(@PathVariable String code, RedirectAttributes ra) {
        officeService.deleteOffice(code);
        ra.addFlashAttribute("message", "Office removed successfully!");
        return "redirect:/admin/offices";
    }

    @GetMapping("/admin/offices/{code}")
    public String adminOfficeDetail(@PathVariable String code, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("office", officeService.getOfficeByCode(code));
        model.addAttribute("employees", employeeService.getByOfficeCode(code));
        return "admin/office-detail";
    }

    @GetMapping("/admin/orders")
    public String adminOrders() {
        return "admin/orders";
    }

    @GetMapping("/admin/payments")
    public String adminPayments() {
        return "admin/payments";
    }

    @GetMapping("/admin/products")
    public String adminProducts() {
        return "admin/products";
    }

    @GetMapping("/admin/productlines")
    public String adminProductlines() {
        return "admin/productlines";
    }

}
