package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.PaypalDetail;
import com.example.electrowayfinal.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaypalDetailRepository extends JpaRepository<PaypalDetail, Long> {
    Optional<PaypalDetail> findByUser_Id(long user_id);

    <S extends Role> S save(S entity);

    void delete(PaypalDetail paypalDetail);
}
