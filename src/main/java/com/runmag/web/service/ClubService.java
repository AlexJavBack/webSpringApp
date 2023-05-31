package com.runmag.web.service;

import com.runmag.web.dto.ClubDto;
import com.runmag.web.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();

    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(long clubId);

    void updateClub(ClubDto club);

    void delete(Long id);
    List<ClubDto> searchClubs(String query);
}
