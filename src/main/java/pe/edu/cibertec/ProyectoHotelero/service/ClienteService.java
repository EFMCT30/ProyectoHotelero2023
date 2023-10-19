package pe.edu.cibertec.ProyectoHotelero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.ProyectoHotelero.entity.Cliente;
import pe.edu.cibertec.ProyectoHotelero.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente obtenerClientePorNombre(String nombre) {
        return clienteRepository.findByNombre(nombre);
    }

    public Cliente crearCliente(Cliente cliente) {
        // Agrega lógica para crear un nuevo cliente en la base de datos
        return clienteRepository.save(cliente);
    }

    // Otros métodos relacionados con los clientes
}

