package pl.tarkiewicz.springsecuritysimplefactorauth.tire.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tarkiewicz.springsecuritysimplefactorauth.exceptions.NotFoundException;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.Season;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tire.TireRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetailRepo;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.tireDetails.TireDetails;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.dto.TireDto;
import pl.tarkiewicz.springsecuritysimplefactorauth.tire.converter.TireWithDetailsWebCommandConverter;

@Service
@AllArgsConstructor
public class TireGetService {

    private final TireRepo tireRepo;
    private final TireDetailRepo tireDetailRepo;
    private final TireWithDetailsWebCommandConverter tireWithDetailsWebCommandConverter;

    public List<TireDto> getAllTire() {
        return tireRepo.findAll()
                .stream()
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<TireDto> getAllBySeason(Season season) {
        return tireDetailRepo.findBySeason(season)
                .stream()
                .map(TireDetails::getTireLists)
                .flatMap(Collection::stream)
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<TireDto> getAllNotBoughtTires() {
        return tireDetailRepo.findAll()
                .stream()
                .map(TireDetails::getTireLists)
                .flatMap(Collection::stream)
                .filter(tire -> !tire.isBought())
                .map(tireWithDetailsWebCommandConverter::toDto)
                .collect(Collectors.toList());
    }

    public TireDetails getTireDetailsById(Long tireDetailsId) {
        return tireDetailRepo.findById(tireDetailsId).orElseThrow(() -> new NotFoundException(String.format("TireDetail with following id %s not found", tireDetailsId)));
    }

}