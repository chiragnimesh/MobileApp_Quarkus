package com.company.service;

import com.company.entity.Mobile;

import java.util.List;

public interface MobileService {
    List<Mobile> getAllMobiles();
    Mobile getMobileById(Long id);
    Mobile addMobile(Mobile mobileInput);
    Mobile updateMobile(Long id, Mobile mobile);
    void deleteMobile(Long id);
    List<Mobile> findByMobileName(String name);
}
