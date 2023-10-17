package pl.mateusz_semklo.automationshoprest.services;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz_semklo.automationshoprest.repositories.CategoriesRepository;

@Transactional
@Service
public class CategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;
}
