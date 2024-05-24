package net.lahlalia.prevision.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.prevision.repositories.PrevisionRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrevisionService {

    private final PrevisionRepository previsionRepository;



}
