package com.example.rest_service.repository;

import com.example.rest_service.model.ClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientDetailRepository extends JpaRepository<ClientDetail, Long> {

    List<ClientDetail> findByIsmember(boolean ismember);

    List<ClientDetail> findByAlamatContaining(String alamat);
}