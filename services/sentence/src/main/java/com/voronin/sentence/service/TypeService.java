package com.voronin.sentence.service;

import com.voronin.sentence.domain.Type;
import com.voronin.sentence.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TypeService.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
@Service
public class TypeService {

    /**
     * TypeRepository.
     */
    private final TypeRepository typeRepository;

    /**
     * Constructor.
     *
     * @param typeRepository TypeRepository.
     */
    @Autowired
    public TypeService(final TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    /**
     * Save Type to db.
     *
     * @param type Type.
     * @return saved Type.
     */
    public Type save(final Type type) {
        return this.typeRepository.save(type);
    }

    /**
     * Find all Type.
     *
     * @return list of Type.
     */
    public List<Type> findAll() {
        return this.typeRepository.findAll();
    }
}
