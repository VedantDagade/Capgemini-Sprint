package com.classicmodels.classicmodels.mapper;

import com.classicmodels.classicmodels.dto.*;
import com.classicmodels.classicmodels.entity.*;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    public CustomerDTO toCustomerDTO(Customer c) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerNumber(c.getCustomerNumber());
        dto.setCustomerName(c.getCustomerName());
        dto.setContactLastName(c.getContactLastName());
        dto.setContactFirstName(c.getContactFirstName());
        dto.setPhone(c.getPhone());
        dto.setAddressLine1(c.getAddressLine1());
        dto.setAddressLine2(c.getAddressLine2());
        dto.setCity(c.getCity());
        dto.setState(c.getState());
        dto.setPostalCode(c.getPostalCode());
        dto.setCountry(c.getCountry());
        dto.setCreditLimit(c.getCreditLimit());

        // safely extract only what we need from the related Employee
        if (c.getSalesRep() != null) {
            dto.setSalesRepEmployeeNumber(c.getSalesRep().getEmployeeNumber());
            dto.setSalesRepName(
                    c.getSalesRep().getFirstName() + " " + c.getSalesRep().getLastName()
            );
        }
        return dto;
    }

    public EmployeeDTO toEmployeeDTO(Employee e) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeNumber(e.getEmployeeNumber());
        dto.setLastName(e.getLastName());
        dto.setFirstName(e.getFirstName());
        dto.setExtension(e.getExtension());
        dto.setEmail(e.getEmail());
        dto.setJobTitle(e.getJobTitle());

        if (e.getOffice() != null) {
            dto.setOfficeCode(e.getOffice().getOfficeCode());
            dto.setOfficeCity(e.getOffice().getCity());
        }

        if (e.getManager() != null) {
            dto.setManagerEmployeeNumber(e.getManager().getEmployeeNumber());
            dto.setManagerName(
                    e.getManager().getFirstName() + " " + e.getManager().getLastName()
            );
        }
        return dto;
    }

    public OrderDTO toOrderDTO(Order o) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderNumber(o.getOrderNumber());
        dto.setOrderDate(o.getOrderDate());
        dto.setRequiredDate(o.getRequiredDate());
        dto.setShippedDate(o.getShippedDate());
        dto.setStatus(o.getStatus());
        dto.setComments(o.getComments());

        if (o.getCustomer() != null) {
            dto.setCustomerNumber(o.getCustomer().getCustomerNumber());
            dto.setCustomerName(o.getCustomer().getCustomerName());
        }
        return dto;
    }

    public OrderDetailDTO toOrderDetailDTO(OrderDetail od) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderNumber(od.getId().getOrderNumber());
        dto.setProductCode(od.getId().getProductCode());
        dto.setQuantityOrdered(od.getQuantityOrdered());
        dto.setPriceEach(od.getPriceEach());
        dto.setOrderLineNumber(od.getOrderLineNumber());

        if (od.getProduct() != null) {
            dto.setProductName(od.getProduct().getProductName());
        }
        return dto;
    }

    public PaymentDTO toPaymentDTO(Payment p) {
        PaymentDTO dto = new PaymentDTO();
        dto.setCustomerNumber(p.getId().getCustomerNumber());
        dto.setCheckNumber(p.getId().getCheckNumber());
        dto.setPaymentDate(p.getPaymentDate());
        dto.setAmount(p.getAmount());

        if (p.getCustomer() != null) {
            dto.setCustomerName(p.getCustomer().getCustomerName());
        }
        return dto;
    }

    public ProductDTO toProductDTO(Product p) {
        ProductDTO dto = new ProductDTO();
        dto.setProductCode(p.getProductCode());
        dto.setProductName(p.getProductName());
        dto.setProductScale(p.getProductScale());
        dto.setProductVendor(p.getProductVendor());
        dto.setProductDescription(p.getProductDescription());
        dto.setQuantityInStock(p.getQuantityInStock());
        dto.setBuyPrice(p.getBuyPrice());
        dto.setMsrp(p.getMsrp());

        if (p.getProductLineObj() != null) {
            dto.setProductLine(p.getProductLineObj().getProductLine());
        }
        return dto;
    }

    public OfficeDTO toOfficeDTO(Office o) {
        OfficeDTO dto = new OfficeDTO();
        dto.setOfficeCode(o.getOfficeCode());
        dto.setCity(o.getCity());
        dto.setPhone(o.getPhone());
        dto.setAddressLine1(o.getAddressLine1());
        dto.setAddressLine2(o.getAddressLine2());
        dto.setState(o.getState());
        dto.setCountry(o.getCountry());
        dto.setPostalCode(o.getPostalCode());
        dto.setTerritory(o.getTerritory());
        return dto;
    }

    public ProductLineDTO toProductLineDTO(ProductLine pl) {
        ProductLineDTO dto = new ProductLineDTO();
        dto.setProductLine(pl.getProductLine());
        dto.setTextDescription(pl.getTextDescription());
        dto.setHtmlDescription(pl.getHtmlDescription());
        return dto;
    }
}