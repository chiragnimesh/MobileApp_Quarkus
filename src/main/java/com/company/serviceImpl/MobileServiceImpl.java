package com.company.serviceImpl;

import com.company.entity.Mobile;
import com.company.exception.ResourceNotFoundException;
import com.company.repository.MobileRepository;
import com.company.service.MobileService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;


@ApplicationScoped
public class MobileServiceImpl implements MobileService {

    @Inject
    private MobileRepository mobileRepository;


    @Override
    public List<Mobile> getAllMobiles() {
        return mobileRepository.listAll();
    }


    @Override
    public Mobile getMobileById(Long id) {
        return mobileRepository.findById(id);
    }

    @Override
    public List<Mobile> findByMobileName(String name) {
        return mobileRepository.findByMobileName(name);
    }

    @Override
    @Transactional
    public Mobile addMobile(Mobile mobileInput) {
        Mobile mobile = new Mobile();
        mobile.setMobileName(mobileInput.getMobileName());
        mobile.setBrand(mobileInput.getBrand());
        mobile.setRam(mobileInput.getRam());
        mobile.setPrice(mobileInput.getPrice());
        mobile.setExternalStorage(mobileInput.getExternalStorage());
        mobile.setImage(mobileInput.getImage());
        mobileRepository.persist(mobile);
        return mobile;
    }

    @Override
    @Transactional
    public Mobile updateMobile(Long id, Mobile updatedMobile) {
        Mobile existingMobile = getMobileById(id);
        existingMobile.mobileName = updatedMobile.mobileName;
        existingMobile.brand = updatedMobile.brand;
        existingMobile.ram = updatedMobile.ram;
        existingMobile.price = updatedMobile.price;
        existingMobile.externalStorage = updatedMobile.externalStorage;
        existingMobile.image = updatedMobile.image;
        return existingMobile;
    }


    @Override
    @Transactional
    public void deleteMobile(Long id) {
        Mobile mobile = getMobileById(id);
        mobileRepository.delete(mobile);
    }



}
