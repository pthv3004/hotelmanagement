package swp.server.hotelmanagement.services;

import swp.server.hotelmanagement.dtos.ServiceDTO;

import java.util.List;

public interface ServiceSer {
    List<ServiceDTO> getAllService();
    ServiceDTO getServiceById(int serviceId);
    ServiceDTO createNewService(ServiceDTO serviceDTO);
    ServiceDTO updateService(ServiceDTO serviceDTO);
    Boolean deleteService(int serviceId);
}
