# API Endpoints Documentation

This document provides a comprehensive list of all API endpoints available in the ClassicModels application, along with their expected HTTP status codes and a description of their purpose.

All routes are prefixed with `/api/v1/`.

## 🏭 ProductLines
| Method | Endpoint | Status Code | Purpose |
|---|---|---|---|
| GET | `/api/v1/productlines` | 200 OK | Retrieve a list of all product lines. |
| GET | `/api/v1/productlines/{name}` | 200 OK | Retrieve details of a specific product line by its name. |
| POST | `/api/v1/productlines` | 201 Created | Create a new product line. |
| PUT | `/api/v1/productlines/{name}` | 200 OK | Update an existing product line. |
| DELETE | `/api/v1/productlines/{name}` | 200 OK | Delete a specific product line. |

## 📦 Products
| Method | Endpoint | Status Code | Purpose |
|---|---|---|---|
| GET | `/api/v1/products` | 200 OK | Retrieve a list of all products. |
| GET | `/api/v1/products/{code}` | 200 OK | Retrieve details of a specific product by its code. |
| POST | `/api/v1/products` | 201 Created | Create a new product. |
| PUT | `/api/v1/products/{code}` | 200 OK | Update an existing product. |
| DELETE | `/api/v1/products/{code}` | 200 OK | Delete a specific product. |
| GET | `/api/v1/products/search?line={line}` | 200 OK | Search and retrieve products belonging to a specific product line (category). |
| GET | `/api/v1/products/low-stock?threshold={val}` | 200 OK | Retrieve products with stock quantity below a given threshold. |

## 💳 Payments
| Method | Endpoint | Status Code | Purpose |
|---|---|---|---|
| GET | `/api/v1/payments` | 200 OK | Retrieve all payments in the system. |
| GET | `/api/v1/payments/customer/{id}` | 200 OK | Retrieve all payments made by a specific customer. |
| POST | `/api/v1/payments` | 201 Created | Record a new payment. |
| PUT | `/api/v1/payments/{cNo}/{checkNo}` | 200 OK | Update an existing payment's details (e.g. date, amount). |
| DELETE | `/api/v1/payments/{cNo}/{checkNo}` | 200 OK | Delete a specific payment record. |

## 🛒 Orders
| Method | Endpoint | Status Code | Purpose |
|---|---|---|---|
| GET | `/api/v1/orders` | 200 OK | Retrieve all orders. |
| GET | `/api/v1/orders/{id}` | 200 OK | Retrieve a specific order by ID. |
| POST | `/api/v1/orders` | 201 Created | Create a new order. |
| PUT | `/api/v1/orders/{id}` | 200 OK | Update an existing order (e.g. status updates). |
| DELETE | `/api/v1/orders/{id}` | 200 OK | Delete a specific order. |
| GET | `/api/v1/orders/search?status={status}` | 200 OK | Search for orders by their current status. |
| GET | `/api/v1/orders/customer/{customerId}` | 200 OK | Retrieve all orders placed by a specific customer. |
| GET | `/api/v1/orders/{id}/details` | 200 OK | Retrieve all line items (details) for a specific order. |
| POST | `/api/v1/orders/{id}/details` | 201 Created | Add a new line item to an existing order. |
| GET | `/api/v1/orders/{id}/total` | 200 OK | Get the total monetary value breakdown of an order. |

## 🏢 Offices
| Method | Endpoint | Status Code | Purpose |
|---|---|---|---|
| GET | `/api/v1/offices` | 200 OK | Retrieve all company offices. |
| GET | `/api/v1/offices/{code}` | 200 OK | Retrieve details of a specific office by code. |
| POST | `/api/v1/offices` | 201 Created | Add a new office. |
| PUT | `/api/v1/offices/{code}` | 200 OK | Update an existing office location or details. |
| DELETE | `/api/v1/offices/{code}` | 200 OK | Delete an office record. |

## 👥 Employees
| Method | Endpoint | Status Code | Purpose |
|---|---|---|---|
| GET | `/api/v1/employees` | 200 OK | Retrieve all employees. | ✅
| GET | `/api/v1/employees/{id}` | 200 OK | Retrieve an employee by their ID. |  ✅
| POST | `/api/v1/employees` | 201 Created | Add a new employee to the system. | ✅
| PUT | `/api/v1/employees/{id}` | 200 OK | Update an employee's details. | ✅
| DELETE | `/api/v1/employees/{id}` | 200 OK | Remove an employee record. | ✅
| GET | `/api/v1/employees/search?name={name}` | 200 OK | Search for employees by name. |
| GET | `/api/v1/employees/filter/jobtitle?title={val}` | 200 OK | Retrieve employees filtered by job title. | ✅
| GET | `/api/v1/employees/filter/office?code={val}` | 200 OK | Retrieve employees filtered by office code. | ✅
| GET | `/api/v1/employees/filter/manager?id={val}` | 200 OK | Retrieve employees managed by a specific manager. | ✅
| GET | `/api/v1/employees/{id}/customers` | 200 OK | Retrieve all customers managed by a specific sales representative. | ✅


## 🤝 Customers
| Method | Endpoint | Status Code | Purpose |
|---|---|---|---|
| GET | `/api/v1/customers` | 200 OK | Retrieve all customers. | ✅
| GET | `/api/v1/customers/{id}` | 200 OK | Retrieve a customer by their ID. | ✅
| POST | `/api/v1/customers` | 201 Created | Register a new customer. | ✅
| PUT | `/api/v1/customers/{id}` | 200 OK | Update details for an existing customer. | ✅
| DELETE | `/api/v1/customers/{id}` | 200 OK | Delete a customer record. | ✅
| GET | `/api/v1/customers/search?name={name}` | 200 OK | Search for customers by their name. |  ✅
| GET | `/api/v1/customers/country?country={name}` | 200 OK | Retrieve customers located in ✅specific country. | 
| GET | `/api/v1/customers/city?city={name}`| 200 OK | Retrieve customers located in a specific city. | ✅
| GET | `/api/v1/customers/{id}/orders` | 200 OK | Retrieve all orders placed by the specified customer. | ✅
| GET | `/api/v1/customers/{id}/payments` | 200 OK | Retrieve all payments made by the specified customer. | ✅
 
## 📊 Dashboard
| Method | Endpoint | Status Code | Purpose |
|---|---|---|---|
| GET | `/api/v1/dashboard/stats` | 200 OK | Retrieve high-level statistics and aggregations for the admin dashboard. |
