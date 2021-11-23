package com.example.SpringMVC.Controller;

import com.example.SpringMVC.Model.News;
import com.example.SpringMVC.Model.User;
import com.example.SpringMVC.Repository.NewsRepository;
import com.example.SpringMVC.Service.EmilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/newsPage") // This means URL's start with /demo (after Application path)
public class DBController {
    @Value("${upload.path}")
    private String path;
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private NewsRepository newsRepository;


    @GetMapping("/getAll")
    public String getAll(Model model) {
        Iterable<News> news = newsRepository.findAll();

        model.addAttribute("news", news);

        return "newsPage";
    }

   @PostMapping("/getAll")
   public String addNews(@RequestParam(name = "title", required = false, defaultValue = " ") String title,
                         @RequestParam(name = "text", required = false, defaultValue = " ") String news, Model model,
                         @RequestParam("file") MultipartFile file,
                         @AuthenticationPrincipal User user) throws IOException {
     String pathOfFile = path;
     String filename =  file.getOriginalFilename();
     String test = pathOfFile+"/"+filename;
     file.transferTo(new File(test));
    //   if (!file.isEmpty()) {
    //       byte[] bytes = file.getBytes();
    //       BufferedOutputStream stream =
    //               new BufferedOutputStream(new FileOutputStream(new File(path)));
    //       stream.write(bytes);
    //       stream.close();
//
           newsRepository.save(new News(title, news, user, file.getOriginalFilename()));
           Iterable<News> newsList = newsRepository.findAll();
           model.addAttribute("news", newsList);
           return "newsPage";
   }

   @RequestMapping(method = RequestMethod.POST,value = MediaType.ALL_VALUE)
       public String addNews (@RequestBody News news,Model model) {
           newsRepository.save(news);
           Iterable<News> newsList = newsRepository.findAll();
           model.addAttribute("news",newsList);
           return "newsPage";
       }


    @PostMapping("search")
    public String searchElement(@RequestParam String title, Model model) {
        News x = newsRepository.findByTitle(title);
        model.addAttribute("news", x);
        return "newsPage";
    }

    @PostMapping("search2")
    public String searchBtw2id(@RequestParam(name = "titleId", required = false, defaultValue = " ") String id,
                               @RequestParam(name = "titleSecondId", required = false, defaultValue = " ") String id2, Model model) {
        List<News> a = newsRepository.findByIdBetween(Integer.parseInt(id), Integer.parseInt(id2));
        model.addAttribute("news", a);
        return "newsPage";
    }

    @PostMapping("search3")
    public String order(@RequestParam(name = "order", required = false, defaultValue = "") String name, Model model) {

        switch (name) {
            case ("asc"):
                List<News> a = newsRepository.findAllByOrderByTitleAsc();
                model.addAttribute("news", a);
                break;
            case ("desc"):
                List<News> b = newsRepository.findAllByOrderByTitleDesc();
                model.addAttribute("news", b);
                break;
            case ("des"):
                List<News> c = newsRepository.findAllByOrderByIdDesc();
                model.addAttribute("news", c);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return "newsPage";
    }


    @PostMapping("changed")
    public String updateNews(@RequestParam(name = "new", required = false, defaultValue = "") String name, Model model) {
        List<News> list = new ArrayList<>();
        News x = newsRepository.findById(Integer.parseInt(name)).orElse(null);
        list.add(x);
        model.addAttribute("marker", list);
        return "change";
    }
   // @PostMapping("abcd")
   // public String update(@RequestParam(name = "id",required = false,defaultValue = "") String id,
   //                      @RequestParam(name = "title", required = false, defaultValue = "") String title,
   //                      @RequestParam (name = "text",required = false, defaultValue = "") String text, Model model) {
   //     News x = new News(Integer.parseInt(id),title,text);
   //     newsRepository.save(x);
   //     model.addAttribute("news",newsRepository.findAll());
   //     return "newsPage";
   // }
}