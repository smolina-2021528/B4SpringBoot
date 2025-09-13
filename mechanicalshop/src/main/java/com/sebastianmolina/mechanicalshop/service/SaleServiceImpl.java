package com.sebastianmolina.mechanicalshop.service;

import com.sebastianmolina.mechanicalshop.model.Customer;
import com.sebastianmolina.mechanicalshop.model.Employee;
import com.sebastianmolina.mechanicalshop.model.Product;
import com.sebastianmolina.mechanicalshop.model.Sale;
import com.sebastianmolina.mechanicalshop.repository.CustomerRepository;
import com.sebastianmolina.mechanicalshop.repository.EmployeeRepository;
import com.sebastianmolina.mechanicalshop.repository.ProductRepository;
import com.sebastianmolina.mechanicalshop.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;

    public SaleServiceImpl(SaleRepository saleRepository, CustomerRepository customerRepository,
                           EmployeeRepository employeeRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Sale getSaleById(Integer id) {
        return saleRepository.findById(id).orElse(null);
    }

    @Override
    public Sale saveSale(Sale sale) {
        // No se incluyen validaciones de unicidad ya que no hay restricciones unique en el modelo
        // Las validaciones de @NotNull para customer, employee y product se manejan en el modelo
        return saleRepository.save(sale);
    }

    public Sale saveSaleFromMap(Map<String, Object> saleData) {
        // Validar y obtener los IDs
        Integer customerId = parseId(saleData.get("customer"), "customer");
        Integer employeeId = parseId(saleData.get("employee"), "employee");
        Integer productId = parseId(saleData.get("product"), "product");
        Double total = parseDouble(saleData.get("total"), "total");

        // Buscar las entidades por sus IDs
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    String msg = "customer: No se encontró el cliente con ID " + customerId;
                    System.out.println("⚠️ ALERTA - " + msg);
                    return new IllegalArgumentException(msg);
                });

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> {
                    String msg = "employee: No se encontró el empleado con ID " + employeeId;
                    System.out.println("⚠️ ALERTA - " + msg);
                    return new IllegalArgumentException(msg);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    String msg = "product: No se encontró el producto con ID " + productId;
                    System.out.println("⚠️ ALERTA - " + msg);
                    return new IllegalArgumentException(msg);
                });

        // Crear la entidad Sale
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setEmployee(employee);
        sale.setProduct(product);
        sale.setTotal(total); // Total se calcula por trigger, pero se incluye por si se envía

        return saleRepository.save(sale);
    }

    @Override
    public Sale updateSale(Integer id, Sale sale) {
        Sale existingSale = saleRepository.findById(id).orElse(null);
        if (existingSale != null) {
            // No se incluyen validaciones de unicidad ya que no hay restricciones unique en el modelo
            // Las validaciones de @NotNull para customer, employee y product se manejan en el modelo
            existingSale.setCustomer(sale.getCustomer());
            existingSale.setEmployee(sale.getEmployee());
            existingSale.setProduct(sale.getProduct());
            existingSale.setTotal(sale.getTotal());
            return saleRepository.save(existingSale);
        }
        return null;
    }

    public Sale updateSaleFromMap(Integer id, Map<String, Object> saleData) {
        Sale existingSale = saleRepository.findById(id).orElse(null);
        if (existingSale != null) {
            // Validar y obtener los IDs
            Integer customerId = parseId(saleData.get("customer"), "customer");
            Integer employeeId = parseId(saleData.get("employee"), "employee");
            Integer productId = parseId(saleData.get("product"), "product");
            Double total = parseDouble(saleData.get("total"), "total");

            // Buscar las entidades por sus IDs
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> {
                        String msg = "customer: No se encontró el cliente con ID " + customerId;
                        System.out.println("⚠️ ALERTA - " + msg);
                        return new IllegalArgumentException(msg);
                    });

            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> {
                        String msg = "employee: No se encontró el empleado con ID " + employeeId;
                        System.out.println("⚠️ ALERTA - " + msg);
                        return new IllegalArgumentException(msg);
                    });

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> {
                        String msg = "product: No se encontró el producto con ID " + productId;
                        System.out.println("⚠️ ALERTA - " + msg);
                        return new IllegalArgumentException(msg);
                    });

            // Actualizar la entidad Sale
            existingSale.setCustomer(customer);
            existingSale.setEmployee(employee);
            existingSale.setProduct(product);
            existingSale.setTotal(total); // Total se calcula por trigger, pero se incluye por si se envía

            return saleRepository.save(existingSale);
        }
        return null;
    }

    @Override
    public void deleteSale(Integer id) {
        saleRepository.deleteById(id);
    }

    private Integer parseId(Object value, String fieldName) {
        if (value == null) {
            String msg = fieldName + ": El ID es obligatorio";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
        try {
            if (value instanceof Number) {
                return ((Number) value).intValue();
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else {
                String msg = fieldName + ": El ID debe ser un número";
                System.out.println("⚠️ ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }
        } catch (NumberFormatException e) {
            String msg = fieldName + ": El ID debe ser un número válido";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
    }

    private Double parseDouble(Object value, String fieldName) {
        if (value == null) {
            return null; // Total es opcional debido al trigger
        }
        try {
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            } else if (value instanceof String) {
                return Double.parseDouble((String) value);
            } else {
                String msg = fieldName + ": El total debe ser un número";
                System.out.println("⚠️ ALERTA - " + msg);
                throw new IllegalArgumentException(msg);
            }
        } catch (NumberFormatException e) {
            String msg = fieldName + ": El total debe ser un número válido";
            System.out.println("⚠️ ALERTA - " + msg);
            throw new IllegalArgumentException(msg);
        }
    }
}