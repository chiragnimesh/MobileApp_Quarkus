package com.company.repository;


import com.company.entity.Mobile;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MobileRepository implements PanacheRepository<Mobile> {
    // Custom queries can be added here if needed

    public List<Mobile> findByMobileName(String name){
        return list("mobileName", name);
    }
}
