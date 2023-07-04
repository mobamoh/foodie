package com.mohamedbamoh.foodie.order.service.domain;

import com.mohamedbamoh.foodie.domain.valueobject.*;
import com.mohamedbamoh.foodie.order.core.domain.entity.Customer;
import com.mohamedbamoh.foodie.order.core.domain.entity.Order;
import com.mohamedbamoh.foodie.order.core.domain.entity.Product;
import com.mohamedbamoh.foodie.order.core.domain.entity.Restaurant;
import com.mohamedbamoh.foodie.order.core.domain.exception.OrderDomainException;
import com.mohamedbamoh.foodie.order.service.domain.dto.create.CreateOrderCommand;
import com.mohamedbamoh.foodie.order.service.domain.dto.create.OrderAddress;
import com.mohamedbamoh.foodie.order.service.domain.dto.create.OrderItem;
import com.mohamedbamoh.foodie.order.service.domain.mapper.OrderDataMapper;
import com.mohamedbamoh.foodie.order.service.domain.port.input.service.OrderApplicationService;
import com.mohamedbamoh.foodie.order.service.domain.port.output.repository.CustomerRepository;
import com.mohamedbamoh.foodie.order.service.domain.port.output.repository.OrderRepository;
import com.mohamedbamoh.foodie.order.service.domain.port.output.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;
    private final UUID CUSTOMER_ID = UUID.fromString("9ecb5338-8bce-48fa-8938-1b2605deb68f");
    private final UUID RESTAURANT_ID = UUID.fromString("830cfb97-aa31-4013-98d2-e8cf91ce99c2");
    private final UUID PRODUCT_ID = UUID.fromString("382a8ec2-6263-49ae-866e-85981d824c24");
    private final UUID ORDER_ID = UUID.fromString("73d5627e-5979-4e7a-8585-95b374c035fc");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    public void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("einstein str.")
                        .postalCode("10111")
                        .city("Berlin")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                )).build();

        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("einstein str.")
                        .postalCode("10111")
                        .city("Berlin")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                )).build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("einstein str.")
                        .postalCode("10111")
                        .city("Berlin")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                )).build();

        var customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));
        var restaurant = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID),
                                "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID),
                                "product-2", new Money(new BigDecimal("50.00")))
                ))
                .active(true)
                .build();
        var order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand))).
                thenReturn(Optional.of(restaurant));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testCreateOrder() {
        var createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order created successfully", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getTrackingId());
    }

    @Test
    public void testCreateOrderWithWrongTotalPrice() {
        var orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongPrice));
        assertEquals(String.format("Order total price 250,00 is not equal to items total price 200,00!"),
                orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithWrongProductPrice() {
        var orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongProductPrice));
        assertEquals(String.format("Order item price 60,00 is not valid for product %s!", PRODUCT_ID),
                orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithInactiveRestaurant() {
        var restaurant = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID),
                                "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID),
                                "product-2", new Money(new BigDecimal("50.50")))
                ))
                .active(false)
                .build();

        when(restaurantRepository
                .findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurant));

        var orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommand));

        assertEquals(String.format("Restaurant with id %s is currently not active!", RESTAURANT_ID.toString()),
                orderDomainException.getMessage());
    }
}
