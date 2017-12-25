package ru.velkomfood.mrp3.reports.model.md;

import org.springframework.data.repository.CrudRepository;
import ru.velkomfood.mrp3.reports.model.md.Material;

import java.util.List;

public interface IMaterial extends CrudRepository<Material, Long> {

    List<Material> findAll();
    @Override
    Material findOne(Long aLong);
    List<Material> findMaterialByIdBetween(long low, long high);

}
