package com.senyitte.customerservice.services;

import com.senyitte.customerservice.dtos.CustomerRequest;
import com.senyitte.customerservice.dtos.CustomerResponse;
import com.senyitte.customerservice.mappers.CustomerMapper;
import com.senyitte.customerservice.models.Customer;
import com.senyitte.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service indique à Spring que cette classe est un bean de type "service"
// Spring la détecte automatiquement et l'injecte là où on en a besoin
@Service
public class CustomerServiceImpl implements CustomerService {

    // "final" = ces dépendances ne peuvent pas changer après la construction
    // C'est une bonne pratique pour l'injection par constructeur
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    // Injection par constructeur : Spring injecte automatiquement les beans
    // Pas besoin de @Autowired quand il n'y a qu'un seul constructeur
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    // ==================== CRÉER ====================
    @Override
    public CustomerResponse createCustomer(CustomerRequest dto) {
        // 1. Convertir le DTO reçu en entité JPA
        Customer customer = customerMapper.toEntity(dto);
        // 2. Sauvegarder en BDD (JPA génère l'id et les dates automatiquement)
        Customer savedCustomer = customerRepository.save(customer);
        // 3. Reconvertir l'entité sauvegardée en DTO pour la réponse
        return customerMapper.toDto(savedCustomer);
    }

    // ==================== MODIFIER ====================
    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest dto) {
        // 1. Chercher le customer existant, sinon lever une exception
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // 2. Mettre à jour les champs manuellement
        //    Note : on ne crée PAS une nouvelle entité, on modifie l'existante
        //    pour conserver l'id et le createdAt
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());

        // 3. Sauvegarder les modifications
        Customer updatedCustomer = customerRepository.save(customer);

        // 4. Retourner le DTO mis à jour
        return customerMapper.toDto(updatedCustomer);
    }

    // ==================== LIRE UN SEUL ====================
    @Override
    public CustomerResponse getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerMapper.toDto(customer);
    }

    // ==================== LIRE TOUS ====================
    @Override
    public List<CustomerResponse> getAllCustomers() {
        // stream() + map() : transforme chaque Customer en CustomerResponse
        // customerMapper::toDto est une "method reference" (raccourci pour c -> customerMapper.toDto(c))
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    // ==================== SUPPRIMER ====================
    // ⚠️ CORRECTION : "void" minuscule au lieu de "Void" (la classe wrapper)
    @Override
    public void deleteCustomer(Long id) {
        // ⚠️ CORRECTION : vérifier que le customer existe avant de supprimer
        // sinon deleteById échoue silencieusement si l'id n'existe pas
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        customerRepository.deleteById(id);
    }
}