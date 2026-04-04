package com.beutyglam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beutyglam.dto.order.OrderRequestDTO;
import com.beutyglam.dto.order.OrderResponseDTO;
import com.beutyglam.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> makeOrder(
        @RequestBody OrderRequestDTO request,
        HttpServletRequest httpRequest) {
    try {

        String role = (String) httpRequest.getAttribute("role");
        if (!"CLIENT".equals(role)) {
            return ResponseEntity.status(401).body("No autorizado");
        } 

        Integer userId = (Integer) httpRequest.getAttribute("userId"); 
        OrderResponseDTO response = orderService.makeOrder(request, userId);
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    @GetMapping("/history")
    public ResponseEntity<?> getOrderHistory(HttpServletRequest httpRequest) {
    try {

        String role = (String) httpRequest.getAttribute("role");
        if (!"CLIENT".equals(role)) {
            return ResponseEntity.status(401).body("No autorizado");
        } 

        Integer userId = (Integer) httpRequest.getAttribute("userId"); 
        return ResponseEntity.ok(orderService.getOrderHistory(userId));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

}