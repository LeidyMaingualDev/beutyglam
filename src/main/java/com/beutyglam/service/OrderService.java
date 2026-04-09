package com.beutyglam.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.beutyglam.dto.order.OrderHistoryResponseDTO;
import com.beutyglam.dto.order.OrderRequestDTO;
import com.beutyglam.dto.order.OrderResponseDTO;
import com.beutyglam.entity.Order;
import com.beutyglam.entity.OrderDetails;
import com.beutyglam.entity.Product;
import com.beutyglam.entity.User;
import com.beutyglam.repository.OrderRepository;
import com.beutyglam.repository.ProductRepository;
import com.beutyglam.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public OrderResponseDTO makeOrder(OrderRequestDTO request, Integer userId) {
    User user = userRepository.findById(Long.valueOf(userId))
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Order order = new Order();
    order.setUser(user);
    order.setOrderDate(LocalDate.now());

    List<OrderDetails> details = new ArrayList<>();
    double total = 0;

    for(OrderRequestDTO.OrderItemDTO item:request.getItems()){

    Product product = productRepository.findById(item.getProductId())
	.orElseThrow(()-> new RuntimeException("Producto no encontrado"));
    

    if(product.getStock() < item.getQuantity()){
     throw new RuntimeException("Stock insuficiente para el producto: " + product.getProduct_name());
}

    product.setStock(product.getStock() - item.getQuantity());
    productRepository.save(product);

    OrderDetails detail = new OrderDetails();
    detail.setOrder(order);
    detail.setProduct(product);
    detail.setQuantity(item.getQuantity());

    details.add(detail);

    total += product.getPrice() * item.getQuantity();
}

    order.setDetails(details);
    order.setTotal(total);
    Order savedOrder = orderRepository.save(order);

    OrderResponseDTO response = new OrderResponseDTO();
    response.setOrderId(savedOrder.getOrderId());
    response.setUsername(user.getUsername());
    response.setOrderDate(savedOrder.getOrderDate());
    response.setTotal(savedOrder.getTotal());
    response.setMessage("¡Felicidades por su compra!");

    List<OrderResponseDTO.OrderItemResponseDTO> itemResponse = new ArrayList<>();
    for(OrderDetails detail: details){
        OrderResponseDTO.OrderItemResponseDTO itemDTO = new OrderResponseDTO.OrderItemResponseDTO();
        itemDTO.setProductName(detail.getProduct().getProduct_name());
        itemDTO.setQuantity(detail.getQuantity());
        itemDTO.setPrice(detail.getProduct().getPrice());
        itemResponse.add(itemDTO);
    }

    response.setItems(itemResponse);
    return response;
}

    public List<OrderHistoryResponseDTO> getOrderHistory(Integer userId) {
    List<Order> orders = orderRepository.findByUserId(Long.valueOf(userId));
    List<OrderHistoryResponseDTO> listOrders = new ArrayList<>();

    for(Order o: orders){
        OrderHistoryResponseDTO response = new OrderHistoryResponseDTO();
        response.setOrderId(o.getOrderId());
        response.setOrderDate(o.getOrderDate());
        response.setTotal(o.getTotal());

        List<OrderHistoryResponseDTO.OrderItemDTO> itemsResponse = new ArrayList<>();

        for(OrderDetails detail: o.getDetails()){
            OrderHistoryResponseDTO.OrderItemDTO itemDTO = new OrderHistoryResponseDTO.OrderItemDTO();
            itemDTO.setProductName(detail.getProduct().getProduct_name());
            itemDTO.setQuantity(detail.getQuantity());
            itemDTO.setPrice(detail.getProduct().getPrice());
            itemsResponse.add(itemDTO);
        }

        response.setItems(itemsResponse);
        listOrders.add(response);
    }

    return listOrders;
}

}