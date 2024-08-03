package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.authentication.AuthenticationFacade;
import com.containerdepot.metcon.data.RequestRepository;
import com.containerdepot.metcon.data.UserRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.model.entities.UserEntity;
import com.containerdepot.metcon.service.RequestService;
import com.containerdepot.metcon.service.dtos.imports.RequestAddDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {
    private final ModelMapper modelMapper;
    private final RequestRepository requestRepository;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public RequestServiceImpl(ModelMapper modelMapper, RequestRepository requestRepository, AuthenticationFacade authenticationFacade, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.requestRepository = requestRepository;
        this.authenticationFacade = authenticationFacade;
        this.userRepository = userRepository;
    }

    @Override
    public boolean add(RequestAddDto data) {
        boolean existingRequest = this.requestRepository.existsByTypeAndContainerNumber(data.getType(), data.getContainerNumber());
        if (existingRequest) {
            return false;
        }
        Authentication authentication = this.authenticationFacade.getAuthentication();
        String name = authentication.getName();
        Optional<UserEntity> byUsername = this.userRepository.findByUsername(name);
        if (byUsername.isEmpty()) {
            return false;
        }
        Company company = byUsername.get().getCompany();
        Request request = this.modelMapper.map(data, Request.class);
        request.setCompany(company);
        this.requestRepository.save(request);
        return true;
    }

    @Override
    public List<Request> findAllContainersByIdDesc() {
        return this.requestRepository.findByOrderByIdDesc();
    }

    @Override
    public Optional<Request> findRequestById(long id) {
        return this.requestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        if (this.requestRepository.existsById(id)) {
            this.requestRepository.deleteById(id);
        } /*TODO exception handling*/
    }

    @Override
    public boolean edit(Long id, RequestAddDto data) {
        Optional<Request> optionalRequest = this.requestRepository.findById(id);
        if (optionalRequest.isEmpty()) {
            return false;
        }
        Request request = optionalRequest.get();
//        request.setType(data.getType());
//        request.setContainerNumber(data.getContainerNumber());
//        request.setContainerType(data.getContainerType());
        request.setTruck(data.getTruck());
        /*
        Only the truck should be editable. New request would be required to change the rest of the fields
        and the old one should be deleted!
        */
        this.requestRepository.save(request);
        return true;
    }
}
