package com.befaruq.service;

import com.befaruq.model.response.Meta;
import com.befaruq.repository.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MetaService {
    @Autowired
    MetaRepository metaRepository;

    public Meta save(com.befaruq.model.request.Meta metaReq) {
        Meta meta = Meta.builder().created(metaReq.created)
                .dataVersion(metaReq.data_version)
                .revision(metaReq.revision).build();
        return metaRepository.save(meta);
    }
    
    public Optional<Meta> findById(Long metaId) {
        return metaRepository.findById(metaId);
    }
}
