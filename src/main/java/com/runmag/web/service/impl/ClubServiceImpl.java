package com.runmag.web.service.impl;

import com.runmag.web.dto.ClubDto;
import com.runmag.web.models.Club;
import com.runmag.web.models.User;
import com.runmag.web.repository.ClubRepository;
import com.runmag.web.repository.UserRepository;
import com.runmag.web.security.SecurityUtil;
import com.runmag.web.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.runmag.web.mapper.ClubMapper.mapToClub;
import static com.runmag.web.mapper.ClubMapper.mapToClubDto;

@Service
public class ClubServiceImpl implements ClubService {
    private ClubRepository clubRepository;
    private UserRepository userRepository;
    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ClubDto> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String userName = SecurityUtil.getSessionUser();
        User user = userRepository.findByUserName(userName);
        Club club = mapToClub(clubDto);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }
    @Override
    public ClubDto findClubById(long clubId) {
       Club club = clubRepository.findById(clubId).get();
       return mapToClubDto(club);
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        Club club = mapToClub(clubDto);
        String userName = SecurityUtil.getSessionUser();
        User user = userRepository.findByUserName(userName);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void delete(Long id) {
        clubRepository.deleteById(id);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
        List<Club> clubs = clubRepository.searchClubs(query);
        return clubs.stream().map(club -> mapToClubDto(club)).collect(Collectors.toList());
    }

}
