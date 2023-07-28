package com.quickstartdev.librarymanagementsystem.Contoller;

import com.knf.dev.librarymanagementsystem.Application;
import com.knf.dev.librarymanagementsystem.controller.AuthorController;
import com.knf.dev.librarymanagementsystem.dto.AuthorDTO;
import com.knf.dev.librarymanagementsystem.entity.Author;
import com.knf.dev.librarymanagementsystem.service.AuthorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** 
* AuthorController Tester. 
* 
* @author Navdeep
* @since <pre>Jul. 25, 2023</pre> 
* @version 1.0 
*/

@SpringBootTest(classes= Application.class)
class AuthorControllerTest {

    @Autowired
    static
    AuthorService authorService;
    static Model model;
    static AuthorController controller;
    static BindingResult bindingResult;

    @BeforeAll
    private static void init()
    {
        authorService = mock(AuthorService.class);
        model = new ConcurrentModel();
        controller= new AuthorController(authorService);
        bindingResult = mock(BindingResult.class);
    }

@Test
 void testFindAllAuthors() {

    int expectedPage = 1;
    int expectedSize = 10;
    Page<Author> mockPage = mock(Page.class);

    when(authorService.findPaginated(any())).thenReturn(mockPage);
    when(mockPage.getTotalPages()).thenReturn(2);

    String result = controller.findAllAuthors(model, Optional.of(expectedPage), Optional.of(expectedSize));
    assertEquals("list-authors", result);
    assertEquals(List.of(1,2),model.getAttribute("pageNumbers"));
} 

@Test
 void testFindAuthorById() {
    Author author = new Author();
    when(authorService.findAuthorById(10L)).thenReturn(author);

    String result = controller.findAuthorById(10L,model);
    assertEquals("list-author",result);
    assertEquals(author,model.getAttribute("author"));
} 

@Test
 void testShowCreateForm() {
    AuthorDTO author = new AuthorDTO("testname");
    String result = controller.showCreateForm(author);
    assertEquals("add-author-testname",result);
} 

@Test
 void testCreateAuthor() {
    AuthorDTO author = new AuthorDTO("test-name");

    when(bindingResult.hasErrors()).thenReturn(true);

    String result = controller.createAuthor(author,bindingResult,model);
    assertEquals("add-author",result);
}

@Test
 void testCreateAuthor2() {
    AuthorDTO author = new AuthorDTO("test-name");

    when(authorService.findAllAuthors()).thenReturn(null);
    when(bindingResult.hasErrors()).thenReturn(false);

    String result = controller.createAuthor(author,bindingResult,model);
    assertEquals("redirect:/authors",result);
    assertNull(model.getAttribute("author"));
}

@Test
 void testShowUpdateForm() {

    Author author = new Author();
    when(authorService.findAuthorById(10L)).thenReturn(author);
    String result = controller.showUpdateForm(10L,model);
    assertEquals("update-author",result);
    assertEquals(author,model.getAttribute("author"));
} 


@Test
 void testUpdateAuthor() {
    AuthorDTO author = new AuthorDTO("test-name");

    when(bindingResult.hasErrors()).thenReturn(true);

    String result = controller.updateAuthor(10L,author,bindingResult,model);
    assertEquals("update-author",result);
    assertEquals(10L,author.getId());
}

@Test
 void testUpdateAuthorEmpty() {
    AuthorDTO author = new AuthorDTO("test-name");

    when(authorService.findAllAuthors()).thenReturn(null);
    when(bindingResult.hasErrors()).thenReturn(false);

    String result = controller.updateAuthor(10L,author,bindingResult,model);
    assertEquals("redirect:/authors",result);
    assertNull(model.getAttribute("author"));
}

@Test
 void testDeleteAuthor() {
    when(authorService.findAllAuthors()).thenReturn(null);

    String result = controller.deleteAuthor(10L,model);
    assertEquals("redirect:/authors",result);


    assertNull(model.getAttribute("author"));

}
} 
