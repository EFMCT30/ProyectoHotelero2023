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

    public Cliente obtenerClienteUserId(Long id) {return clienteRepository.findByUser_Id(id);}

    public Cliente crearCliente(Cliente cliente) {
        // Agrega lógica para crear un nuevo cliente en la base de datos
        return clienteRepository.save(cliente);
    }

    // Otros métodos relacionados con los clientes
}

