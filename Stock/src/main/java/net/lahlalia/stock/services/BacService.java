package net.lahlalia.stock.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lahlalia.stock.dtos.BacDto;
import net.lahlalia.stock.dtos.Product;
import net.lahlalia.stock.entities.Bac;
import net.lahlalia.stock.entities.Depot;
import net.lahlalia.stock.entities.EntreSortie;
import net.lahlalia.stock.mappers.BacMapper;
import net.lahlalia.stock.repositories.BacRepository;
import net.lahlalia.stock.repositories.DepotRepository;
import net.lahlalia.stock.repositories.EntreSortieRepository;
import net.lahlalia.stock.restClients.ProductRestClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BacService {
    private final BacRepository bacRepository;
    private final BacMapper bacMapper;
    private final ProductRestClient productRestClient;
    private final EntreSortieRepository entreSortieRepository;
    private final DepotRepository depotRepository;

    public BacDto getBacById(String idBac ) throws EntityNotFoundException {
        if(idBac == null){
            log.error("id Bac is null");
            return null;
        }
        Bac bac = bacRepository.findById(idBac).get();
        BacDto bacDto =  bacMapper.toModel(bac);
        bacDto.setIdProduct(bac.getIdProduct());
        bacDto.setIdDepot(bac.getDepot().getIdDepot());
        return bacDto;
    }
    public List<BacDto> getAllBacs(){
//        return bacRepository.findAll().stream().map(bacMapper::toModel).toList();
        List<Bac> bacList = bacRepository.findAll();
        List<BacDto> bacsDto = new ArrayList<>();
        for (Bac bac : bacList) {
            BacDto bacDto = bacMapper.toModel(bac);
            try {
                bacDto.setIdProduct(bac.getIdProduct());
                bacDto.setIdDepot(bac.getDepot().getIdDepot());
            } catch (EntityNotFoundException e) {
                // Gérer l'exception si le produit n'est pas trouvé
                // Vous pouvez choisir de ne pas ajouter le produit au BacDto dans ce cas
                log.error("Product not found for Bac ID: " + bac.getIdProduct());
            }
            bacsDto.add(bacDto);
        }
        return bacsDto;
    }
    public double calculerCreux(String idBac)throws EntityNotFoundException{
        Bac bac = bacRepository.findById(idBac).get();
        BacDto dto = bacMapper.toModel(bac);
        double creux = dto.getCapacity() - dto.getCapacityUsed() - dto.getTotalImpom();
        return creux;
    }
    public boolean deleteBacById(String idBac)throws EntityNotFoundException{
        BacDto dto = getBacById(idBac);
        if( dto != null){
            bacRepository.deleteById(idBac);
            return true;
        } else {
            return false;
        }

    }
    public BacDto updateBac(String idBac, BacDto bacDto) throws EntityNotFoundException {
        try {
            Bac existingBac = bacRepository.findById(idBac)
                    .orElseThrow(() -> new EntityNotFoundException("Bac with ID " + idBac + " not found"));

            existingBac.setCapacity(bacDto.getCapacity());
            existingBac.setTotalImpom(bacDto.getTotalImpom());
            existingBac.setStatus(bacDto.isStatus());
            existingBac.setDateOuverture(bacDto.getDateOuverture());
            existingBac.setCapacityUsed(bacDto.getCapacityUsed());

            Bac updatedBac = bacRepository.save(existingBac);

            return bacMapper.toModel(updatedBac);
        } catch (EntityNotFoundException ex) {
            // Log the error or handle it appropriately
            ex.printStackTrace(); // Print the stack trace to console for debugging
            throw ex; // Re-throw the exception to be handled at a higher level
        } catch (Exception ex) {
            // Log the error or handle it appropriately
            ex.printStackTrace(); // Print the stack trace to console for debugging
            throw new RuntimeException("Error updating Bac: " + ex.getMessage()); // Wrap and throw a new exception
        }
    }

    public BacDto saveBac(BacDto dto)throws EntityNotFoundException {
        if(dto == null){
            log.error("Bac is null");
            return null;
        }
        Bac bac = bacMapper.toEntity(dto);
        if(dto.getIdProduct() != null || dto.getIdDepot() != null){
            bac.setIdProduct(dto.getIdProduct());
            Depot depot = depotRepository.findById(dto.getIdDepot()).get();
            bac.setDepot(depot);
        }

        Bac savedBac = bacRepository.save(bac);
        return bacMapper.toModel(savedBac);


    }
    public List<BacDto> getAllBacsForDepot(String idDepot){
        List<Bac> bacs = bacRepository.findAllByDepotId(idDepot);
        List<BacDto> bacDtos = bacs.stream().map(bacMapper::toModel).toList();
        return bacDtos;
    }
    public BacDto ESrProduit(EntreSortie es,String idBac)throws EntityNotFoundException{
        if(es == null || idBac == null || es.getBac().getIdBac()== null  ){
            log.error("value is null");
            return null;
        }

        Bac bac = bacRepository.findById(idBac).get();
        if(es.getTypeES()){
            double quantity = bac.getCapacityUsed() + es.getQuantite();
            bac.setCapacityUsed(quantity);
            Bac savedBac = bacRepository.save(bac);
            entreSortieRepository.save(es);
            return bacMapper.toModel(savedBac);
        }else if (!es.getTypeES()){
            double quantity = bac.getCapacityUsed() - es.getQuantite();
            bac.setCapacityUsed(quantity);
            Bac savedBac = bacRepository.save(bac);
            entreSortieRepository.save(es);
            return bacMapper.toModel(savedBac);
        }else{
            log.error("invalid type of EntreeSortie" + es.getTypeES());
            return null;

        }
    }
//    public BacDto soustraireProduit(EntreSortie es,String idBac)throws EntityNotFoundException{
//        if(es == null || idBac == null || es.getBac().getIdBac()== null ){
//            log.error("value is null");
//            return null;
//        }
//        Bac bac = bacRepository.findById(idBac).get();
//        if(!es.getTypeES()){
//            double quantity = bac.getCapacityUsed() - es.getQuantite();
//            bac.setCapacityUsed(quantity);
//            Bac savedBac = bacRepository.save(bac);
//            entreSortieRepository.save(es);
//            return bacMapper.toModel(savedBac);
//        }else{
//            log.error("invalid type of EntreeSortie" + es.getTypeES());
//            return null;
//        }
//    }
}
