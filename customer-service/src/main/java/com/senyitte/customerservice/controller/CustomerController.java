package com.senyitte.customerservice.controller;

import com.senyitte.customerservice.dtos.CustomerRequest;
import com.senyitte.customerservice.dtos.CustomerResponse;
import com.senyitte.customerservice.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController = @Controller + @ResponseBody
// Indique que chaque méthode retourne directement du JSON (pas une vue HTML)
@RestController
// Préfixe commun pour toutes les routes de ce controller
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    // Injection du service par constructeur
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // ==================== CRÉER ====================
    // POST /api/customers
    // @RequestBody = Spring désérialise le JSON reçu en objet CustomerRequest
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        // 201 CREATED : code HTTP approprié pour une création réussie
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ==================== LIRE TOUS ====================
    // GET /api/customers
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> responses = customerService.getAllCustomers();
        // 200 OK par défaut
        return ResponseEntity.ok(responses);
    }

    // ==================== LIRE UN SEUL ====================
    // GET /api/customers/{id}
    // @PathVariable extrait la valeur {id} de l'URL
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id) {
        CustomerResponse response = customerService.getCustomer(id);
        return ResponseEntity.ok(response);
    }

    // ==================== MODIFIER ====================
    // PUT /api/customers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(response);
    }

    // ==================== SUPPRIMER ====================
    // DELETE /api/customers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        // 204 NO CONTENT : suppression réussie, pas de corps dans la réponse
        return ResponseEntity.noContent().build();
    }
}
