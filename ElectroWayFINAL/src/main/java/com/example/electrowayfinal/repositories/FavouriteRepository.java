package com.example.electrowayfinal.repositories;

import com.example.electrowayfinal.models.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository  extends JpaRepository<Favourite, Long> {

    List<Favourite> findAllByStationId(long station_id);

    @Override
    List<Favourite> findAll();

    Optional<Favourite> findFavouriteByStationIdAndId(long stationId, long favouriteId);

    Optional<Favourite> findFavouriteById(long stationId);

    @Override
    <F extends Favourite> F save(F entity);

    void deleteById(long id);

    @Override
    void delete(Favourite entity);
}
