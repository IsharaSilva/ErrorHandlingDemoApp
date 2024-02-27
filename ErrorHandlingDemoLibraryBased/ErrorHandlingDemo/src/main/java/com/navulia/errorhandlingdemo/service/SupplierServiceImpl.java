package com.navulia.errorhandlingdemo.service;

import com.navulia.errorhandlingdemo.dto.SupplierInputDTO;
import com.navulia.errorhandlingdemo.dto.SupplierOutputDTO;
import com.navulia.errorhandlingdemo.entity.Supplier;
import com.navulia.errorhandlingdemo.exeption.SupplierAlreadyExistsException;
import com.navulia.errorhandlingdemo.repository.SupplierRepository;
import com.navulia.errorhandlingdemo.utility.SupplierErrorMessages;
import com.navulia.genericerrorhandler.exception.ResourceNotFoundException;
import com.navulia.genericerrorhandler.utility.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public List<SupplierOutputDTO> getAllSuppliers(){
        return supplierRepository.findAll().stream().map(Supplier::viewAsOutputDto).toList();
    }

    public SupplierOutputDTO getSupplierById(Long id){
        return supplierRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_RESOURCE_NOT_FOUND+" "+ id)).viewAsOutputDto();
    }

    public SupplierOutputDTO createSupplier(SupplierInputDTO supplierDTO){
       if(supplierRepository.findByRegistrationNo(supplierDTO.getRegistrationNo()).isPresent()) {
           throw new SupplierAlreadyExistsException(SupplierErrorMessages.ERROR_SUPPLIER_EXISTS +" "+ supplierDTO.getRegistrationNo());
      }
        Supplier supplier = Supplier.builder().name(supplierDTO.getName()).address(supplierDTO.getAddress()).registrationNo(supplierDTO.getRegistrationNo()).build();
        return supplierRepository.save(supplier).viewAsOutputDto();
    }

    public SupplierOutputDTO updateSupplier(long id, SupplierInputDTO supplierDTO){
        SupplierOutputDTO existingSupplier = getSupplierById(id);
        Supplier supplier = Supplier.builder().name(supplierDTO.getName()).address(supplierDTO.getAddress()).registrationNo(supplierDTO.getRegistrationNo()).id(existingSupplier.getId()).build();
        return supplierRepository.save(supplier).viewAsOutputDto();
    }

}
