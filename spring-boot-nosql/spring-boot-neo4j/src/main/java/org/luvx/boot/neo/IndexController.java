package org.luvx.boot.neo;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.neo.entity.Movie;
import org.luvx.boot.neo.entity.Person;
import org.luvx.boot.neo.entity.dept.Dept;
import org.luvx.boot.neo.repository.DeptRepository;
import org.luvx.boot.neo.repository.MovieRepository;
import org.luvx.boot.neo.repository.PersonRepository;
import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class IndexController {
    @Resource
    private PersonRepository personRepository;
    @Resource
    private MovieRepository  movieRepository;
    @Resource
    private DeptRepository   deptRepository;

    @GetMapping("movie/create")
    public R<Object> create() {
        Person d1 = new Person(1L, "导演1");
        Person d2 = new Person(2L, "导演2");
        Person a1 = new Person(3L, "演员1");
        Person a2 = new Person(4L, "演员2");

        Movie m1 = new Movie("电影1", "描述1");
        m1.addDirectors(List.of(d1, d2));
        m1.addActors(List.of(a1, a2));

        Movie m2 = new Movie("电影2", "描述2");
        m2.addActors(List.of(a1));

        List<Movie> movies = movieRepository.saveAll(List.of(m1, m2));
        return R.success(movies);
    }

    @GetMapping("movie/deleteAll")
    public void deleteAll() {
        movieRepository.deleteAll();
    }

    @GetMapping("movie/find/{title}")
    public R<Object> find(@PathVariable("title") String title) {
        Movie movie = movieRepository.findByTitle(title);
        return R.success(movie);
    }

    @GetMapping("person/create")
    public R<Object> create0() {
        Movie m3 = new Movie("电影3", "描述3");

        Person d1 = new Person(101L, "导演101");
        Person a1 = new Person(103L, "演员103");
        // 写库结果有问题
        d1.addDirectors(List.of(m3));
        a1.addActors(List.of(m3));

        List<Person> ps = personRepository.saveAll(List.of(d1, a1));
        return R.success(ps);
    }

    @GetMapping("person/findMovieByActor/{name}")
    public R<Object> findMovieByActor(@PathVariable("name") String name) {
        log.info("参数: {}", name);
        // 有问题
        List<Movie> list = personRepository.findMovieByActor(name);
        log.info("结果:{}", list);
        return R.success(list);
    }

    @GetMapping("person/deleteAll")
    public void deleteAll0() {
        personRepository.deleteAll();
    }

    @GetMapping("dept/create")
    public R<Object> create1() {
        Dept root = Dept.builder().id(0L).deptName("软件部").build();
        Dept dept1 = Dept.builder().id(11L).deptName("架构组").parent(root).build();
        Dept dept2 = Dept.builder().id(12L).deptName("开发组").parent(root).build();
        Dept dept3 = Dept.builder().id(13L).deptName("实施组").parent(root).build();
        Dept dept21 = Dept.builder().id(21L).deptName("前端技术部").parent(dept2).build();
        Dept dept22 = Dept.builder().id(22L).deptName("后端技术部").parent(dept2).build();
        Dept dept23 = Dept.builder().id(23L).deptName("测试技术部").parent(dept2).build();
        List<Dept> deptList = List.of(root, dept1, dept2, dept3, dept21, dept22, dept23);
        deptList = deptRepository.saveAll(deptList);
        return R.success(deptList);
    }
}
