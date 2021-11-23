package com.example.SpringMVC.Repository;

import com.example.SpringMVC.Model.News;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface NewsRepository extends CrudRepository<News, Integer> {
    News findByTitle(String title);
    List<News> findByIdBetween(int id1, int id2);
    List<News> findAllByOrderByIdDesc();
    List<News> findAllByOrderByTitleAsc();
    List<News> findAllByOrderByTitleDesc();


}