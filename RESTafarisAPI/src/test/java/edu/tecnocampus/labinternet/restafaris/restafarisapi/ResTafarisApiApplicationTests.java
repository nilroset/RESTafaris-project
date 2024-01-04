package edu.tecnocampus.labinternet.restafaris.restafarisapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.dto.*;
import edu.tecnocampus.labinternet.restafaris.restafarisapi.application.exception.EntityNotFound;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootTest
@AutoConfigureMockMvc
class ResTafarisApiApplicationTests {
	@Autowired
	private  MockMvc mockMvc;

	private static ObjectMapper objectMapper;
	private static String authToken;

	@BeforeAll
	static void setUp() throws Exception {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	@BeforeEach
	void forEach() throws Exception {
		//Authorization header is required for all requests except login
		if(authToken != null) return;
		String authenticationPayload = """
                {
                  "username": "gbusquets",
                  "password": "password123"
                }
                """;

		MvcResult authResult = mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
						.content(authenticationPayload)
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		authToken = authResult.getResponse().getHeader("Authorization");
	}

	@Test
	void badLogin() throws Exception {
		String authenticationPayload = """
				{
				  "username": "gbusquets",
				  "password": "password1234"
				}
				""";

		mockMvc.perform(MockMvcRequestBuilders.post("/authenticate")
						.content(authenticationPayload)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	void getCourses() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<ReturningDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<ReturningDTO>>() {});

		ReturningDTO primerDto = listaDto.get(0);

		assertEquals("7291a930-6c4d-11ee-b962-0242ac120002", primerDto.getId());
	}

	@Test
	void createCourse() throws Exception {

		String course = """
				{
				  "title": "Bases de dades 90",
				  "description" : "gestio de dades",
				  "imageUrl" : "url",
				  "creator_id" : "6074b4c2-717f-11ee-b962-0242ac120002",
				  "category_id": "e4014b24-7432-11ee-b962-0242ac120002",
				  "language_id": "e4014ed0-7432-11ee-b962-0242ac120003"
				}
				""";

		mockMvc.perform(post("/courses").header("Authorization", authToken)
						.contentType("application/json").content(course))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("Bases de dades 90"))
				.andExpect(jsonPath("$.description").value("gestio de dades"))
				.andExpect(jsonPath("$.availability").value(false));
	}

	@Test
	void badCreateCourse() throws Exception {

		String course = """
				{
				  "title": "Bases de dades 90",
				  "description" : "gestio de dades",
				  "imageUrl" : "url",
				  "creator_id" : "6074b4c2-717f-11ee-b962-0242ac1200029",
				  "category_id": "e4014b24-7432-11ee-b962-0242ac120002",
				  "language_id": "e4014ed0-7432-11ee-b962-0242ac120003"
				}
				""";
		assertThrows(Exception.class, () -> {
			mockMvc.perform(post("/courses").header("Authorization", authToken)
							.contentType("application/json").content(course))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.title").value("Bases de dades 90"))
					.andExpect(jsonPath("$.description").value("gestio de dades"))
					.andExpect(jsonPath("$.availability").value(false));
		});
	}

	@Test
	void getCoursesCreatedByUser() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/6074b4c2-717f-11ee-b962-0242ac120002/courses").header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<CourseDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<CourseDTO>>() {});

		CourseDTO primerDto = listaDto.get(0);

		assertEquals("7291a930-6c4d-11ee-b962-0242ac120002", primerDto.getId());
	}

	@Test
	void badGetCoursesCreatedByUser() throws Exception {
		assertThrows(AssertionError.class, () -> {
			mockMvc.perform(MockMvcRequestBuilders.get("/users/6074b4c2-717f-11ee-b962-0242ac1200/courses").header("Authorization", authToken))
					.andExpect(MockMvcResultMatchers.status().isNotFound())
					.andReturn();
		});
	}

	@Test
	void getCourseById() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/72919b8e-6c4d-11ee-b962-0242ac120002").header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		ReturningDTO dto = objectMapper.readValue(content, ReturningDTO.class);

		assertEquals("72919b8e-6c4d-11ee-b962-0242ac120002", dto.getId());
	}

	@Test
	void badGetCourseById() throws Exception {
		assertThrows(AssertionError.class, () -> {
			mockMvc.perform(MockMvcRequestBuilders.get("/courses/72919b8e-6c4d-11ee-b962-0242ac120002#/courses").header("Authorization", authToken))
					.andExpect(MockMvcResultMatchers.status().isNotFound())
					.andReturn();
		});
	}

	@Test
	void updateAvailability() throws Exception{
		String id = "72919f4e-6c4d-11ee-b962-0242ac120002";

		String course = """
				{
				  "title": "Bases de dades 21",
				  "description" : "hols",
				  "imageUrl" : "url3",
				  "availability" : false,
				  "creator_id" : "6074b4c2-717f-11ee-b962-0242ac120002",
				  "category_id": "e4014b24-7432-11ee-b962-0242ac120002",
				  "language_id": "e4014b24-7432-11ee-b962-0242ac120002"
				}
				""";

		mockMvc.perform(patch("/courses/{id}/availability", id).header("Authorization", authToken)
						.contentType("application/json").content(course))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.availability").value(false));
	}

	@Test
	void badUpdateAvailability() throws Exception{
		String id = "72919f4e-6c4d-11ee-b962-0242ac120023";

		String course = """
				{
				  "availability" : false,
				}
				""";

		mockMvc.perform(patch("/courses/{id}/availability", id).header("Authorization", authToken)
						.contentType("application/json").content(course))
				.andExpect(status().isBadRequest());
	}

	@Test
	void updateCourse() throws Exception {
		String id = "72919f4e-6c4d-11ee-b962-0242ac120002";

		String course = """
				{
				  "title": "Bases de dades 21",
				  "description" : "hols",
				  "imageUrl" : "url3",
				  "availability" : false,
				  "creator_id" : "6074b4c2-717f-11ee-b962-0242ac120002",
				  "category_id": "e4014b24-7432-11ee-b962-0242ac120002",
				  "language_id": "e4014b24-7432-11ee-b962-0242ac120002"
				}
				""";
		mockMvc.perform(patch("/courses/{id}/availability", id).header("Authorization", authToken)
						.contentType("application/json").content(course))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.availability").value(false));


		mockMvc.perform(put("/courses/{id}", id).header("Authorization", authToken)
						.contentType("application/json").content(course))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Bases de dades 21"))
				.andExpect(jsonPath("$.creator_id").value("6074bfbc-717f-11ee-b962-0242ac120004"));
	}

	@Test
	void badUpdateCourse() throws Exception {
		String id = "72919f4e-6c4d-11ee-b962-0242ac120123";

		String course = """
				{
				  "title": "Bases de dades 21",
				  "description" : "hols",
				  "imageUrl" : "url3",
				  "availability" : false,
				  "creator_id" : "6074b4c2-717f-11ee-b962-0242ac120002",
				  "category_id": "e4014b24-7432-11ee-b962-0242ac120002",
				  "language_id": "e4014b24-7432-11ee-b962-0242ac120002"
				}
				""";

		mockMvc.perform(put("/courses/{id}", id).header("Authorization", authToken)
						.contentType("application/json").content(course)).andExpect(status().isNotFound());
	}

	@Test
	void updatePrice() throws Exception {
		String id = "72919f4e-6c4d-11ee-b962-0242ac120002";

		String course = """
				{
				 "currentPrice" : 30000
				}
				""";

		mockMvc.perform(patch("/courses/{id}/price", id).header("Authorization", authToken)
						.contentType("application/json").content(course))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.currentPrice").value(30000));
	}

	@Test
	void badUpdatePrice() throws Exception {
		String id = "72919f4e-6c4d-11ee-b962-0242ac120002";

		String course = """
				{
				 "currentPrice" : -300
				}
				""";

		mockMvc.perform(patch("/courses/{id}/price", id).header("Authorization", authToken)
						.contentType("application/json").content(course))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createCategory() throws Exception {
		String category = """
				{
				  "name": "economia"
				}
				""";

		mockMvc.perform(post("/categories").header("Authorization", authToken)
						.contentType("application/json").content(category))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("economia"));
	}

	@Test
	void createLanguage() throws Exception {
		String language = """
				{
				   "name": "chino"
				 }
				""";

		mockMvc.perform(post("/languages").header("Authorization", authToken)
						.contentType("application/json").content(language))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("chino"));
	}

	@Test
	void getUsers() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users").header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<UserDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<UserDTO>>() {});

		UserDTO primerDto = listaDto.get(0);

		assertEquals("6074b4c2-717f-11ee-b962-0242ac120002", primerDto.getId());
	}

	@Test
	void deleteCategory() throws Exception{
		String id = "e4014ed0-7432-11ee-b962-0242ac120003";

		mockMvc.perform(delete("/categories/{id}", id).header("Authorization", authToken))
				.andExpect(status().isOk());
	}

	@Test
	void badDeleteCategory() throws Exception{
		//Non existing category
		String id = "e4014ed0-7432-11ee-b962-0242ac120123";

		assertThrows(Exception.class, () -> { mockMvc.perform(delete("/categories/{id}", id).header("Authorization", authToken))
				.andExpect(status().isOk());
		});
	}

	@Test
	void getMatchedTextAndDescription() throws Exception {
		String text = "Xarxes i serveis";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/search?text=" + text).header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<CourseProjectionDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<CourseProjectionDTO>>() {});

		CourseProjectionDTO primerDto = listaDto.get(0);

		assertEquals("Xarxes i serveis", primerDto.getTitle());
	}

	@Test
	void getCoursesByCategoryOrLanguage() throws Exception{
		String id = "e4014ed0-7432-11ee-b962-0242ac120003";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/categoriesLanguages?language=" + id).header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<CourseDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<CourseDTO>>() {});

		CourseDTO primerDto = listaDto.get(0);

		assertEquals("Xarxes i serveis", primerDto.getTitle());
	}

	@Test
	void badGetCoursesByCategoryOrLanguage() throws Exception{
		//No category or language was given
		assertThrows(Exception.class, () -> { mockMvc.perform(MockMvcRequestBuilders.get("/courses/categoriesLanguages").header("Authorization", authToken));
		});

	}

	@Test
	void createLesson() throws Exception{
		String lesson = """
				{
				  "title": "lesson 2",
				  "description": "introducción 1",
				  "duration": 1.5,
				  "videoUrl": "hols",
				  "courseId": "72919b8e-6c4d-11ee-b962-0242ac120002"
				}
				""";

		mockMvc.perform(post("/lessons").header("Authorization", authToken)
						.contentType("application/json").content(lesson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("lesson 2"))
				.andExpect(jsonPath("$.courseId").value("72919b8e-6c4d-11ee-b962-0242ac120002"));
	}

	@Test
	void badCreateLesson() throws Exception{
		String lesson = """
				{
				  "title": "lesson 2",
				  "description": "introducción 1",
				  "duration": 1.5,
				  "videoUrl": "hols",
				  "courseId": "72919b8e-6c4d-11ee-b962-0242ac120123"
				}
				""";
		assertThrows(Exception.class, () -> {mockMvc.perform(post("/lessons").header("Authorization", authToken)
				.contentType("application/json").content(lesson));
		});
	}
	@Test
	void purchaseCourse() throws Exception{
		String idCourse = "72919b8e-6c4d-11ee-b962-0242ac120002";

		mockMvc.perform(post("/course/{idCourse}/purchase", idCourse).header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.course_id").value("72919b8e-6c4d-11ee-b962-0242ac120002"));
	}

	@Test
	void badPurchaseCourse() throws Exception{
		String idCourse = "72919b8e-6c4d-11ee-b962-0242ac120123";

		assertThrows(Exception.class, () -> {
			mockMvc.perform(post("/course/{idCourse}/purchase", idCourse).header("Authorization", authToken));
		});
	}

	@Test
	void getLessonsByCourseId() throws Exception{

		String idCourse = "72919b8e-6c4d-11ee-b962-0242ac120002";

		mockMvc.perform(MockMvcRequestBuilders.post("/course/{idCourse}/purchase", idCourse).header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk());

		String id = "72919b8e-6c4d-11ee-b962-0242ac120002";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/lessons/{id}", id).header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<LessonDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<LessonDTO>>() {});

		LessonDTO primerDto = listaDto.get(0);

		assertEquals("Introduccio", primerDto.getTitle());
	}

	@Test
	void badGetLessonsByCourseId() throws Exception{
		//Not bought course
		String id = "72919b8e-6c4d-11ee-b962-0242ac120123";
		assertThrows(Exception.class, () -> {
			mockMvc.perform(MockMvcRequestBuilders.get("/lessons/{id}", id).header("Authorization", authToken));
		});
	}

	@Test
	void getLessonsOrdered() throws Exception{
		String id = "72919b8e-6c4d-11ee-b962-0242ac120002";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/lessons/{id}/ordered", id).header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<LessonDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<LessonDTO>>() {});

		LessonDTO primerDto = listaDto.get(0);

		assertEquals("Introduccio", primerDto.getTitle());
	}

	@Test
	void badGetLessonsOrdered() throws Exception{
		String id = "72919b8e-6c4d-11ee-b962-0242ac120123";
		assertThrows(Exception.class, () -> {
			mockMvc.perform(MockMvcRequestBuilders.get("/lessons/{id}/ordered", id).header("Authorization", authToken));
		});
	}

	@Test
	void reorderLessons() throws Exception{
		String id = "72919b8e-6c4d-11ee-b962-0242ac120002";
		String idList = """
				[
				  "7291d9dc-6c4d-11ee-b962-0242ac120006",
				  "7291d7c0-6c4d-11ee-b962-0242ac120004",
				  "7291d8ce-6c4d-11ee-b962-0242ac120005",
				  "7291d6b2-6c4d-11ee-b962-0242ac120003",
				  "7291d5a4-6c4d-11ee-b962-0242ac120002"
				]
				""";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/lessons/{id}/ordered", id)
						.header("Authorization", authToken).content(idList).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<LessonDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<LessonDTO>>() {});


		assertEquals("7291d5a4-6c4d-11ee-b962-0242ac120002", listaDto.get(0).getId());
		assertEquals("7291d6b2-6c4d-11ee-b962-0242ac120003", listaDto.get(1).getId());
	}

	@Test
	void badReorderLessons() throws Exception{
		String id = "72919b8e-6c4d-11ee-b962-0242ac120123";
		String idList = """
				[
				  "7291d9dc-6c4d-11ee-b962-0242ac120006",
				  "7291d7c0-6c4d-11ee-b962-0242ac120004",
				  "7291d8ce-6c4d-11ee-b962-0242ac120005",
				  "7291d6b2-6c4d-11ee-b962-0242ac120003",
				  "7291d5a4-6c4d-11ee-b962-0242ac120002"
				]
				""";
		assertThrows(Exception.class, () -> {
			mockMvc.perform(MockMvcRequestBuilders.get("/lessons/{id}/ordered", id)
							.header("Authorization", authToken).content(idList).contentType("application/json"));
		});
	}

	@Test
	void addReview() throws Exception{
		String idCourse = "72919e18-6c4d-11ee-b962-0242ac120002";

		mockMvc.perform(post("/course/{idCourse}/purchase", idCourse).header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk());

		String review = """
				{
				   "title": "xx",
				   "content": "content",
				   "satisfaction": 1
				 }
				""";
		String id = "72919e18-6c4d-11ee-b962-0242ac120002";
		String lessonId = "7291d5a4-6c4d-11ee-b962-0242ac120007";
		String lessonId2 = "7291d6b2-6c4d-11ee-b962-0242ac120008";
		mockMvc.perform(patch("/lessons/{lessonId}/markAsDone", lessonId).header("Authorization", authToken))
				.andExpect(status().isOk());
		mockMvc.perform(patch("/lessons/{lessonId}/markAsDone", lessonId2).header("Authorization", authToken))
				.andExpect(status().isOk());

		mockMvc.perform(put("/course/{id}/review", id).header("Authorization", authToken)
						.contentType("application/json").content(review))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("content"));
	}

	@Test
	void badAddReview() throws Exception{
		String review = """
				{
				   "title": "xx",
				   "content": "content",
				   "satisfaction": 1
				 }
				""";
		String id = "72919e18-6c4d-11ee-b962-0242ac120002";

		assertThrows(Exception.class, () -> {
			mockMvc.perform(put("/course/{id}/review", id).header("Authorization", authToken)
					.contentType("application/json").content(review));
		});
	}

	@Test
	void editReview() throws Exception{
		String idCourse = "72919e18-6c4d-11ee-b962-0242ac120002";

		String reviewDTO = """
				{
				  "id": "71f2f50c-16b4-4431-862d-6f520821bdc1",
				  "title": "xarxes2",
				  "content": "content2",
				  "satisfaction": 1
				}   
				""";

		mockMvc.perform(patch("/course/{idCourse}/review", idCourse).header("Authorization", authToken)
						.content(reviewDTO).contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.title").value("xarxes2"));
	}

	@Test
	void badEditReview() throws Exception{
		String idCourse = "72919e18-6c4d-11ee-b962-0242ac1212502";

		String reviewDTO = """
				{
				  "id": "71f2f50c-16b4-4431-862d-6f235821b123",
				  "title": "xarxes2",
				  "content": "content2",
				  "satisfaction": 1
				}   
				""";

		assertThrows(Exception.class, () -> {
		mockMvc.perform(patch("/course/{idCourse}/review", idCourse).header("Authorization", authToken)
						.content(reviewDTO).contentType("application/json"));
		});
	}

	@Test
	void markLessonAsDone() throws Exception{
		String idCourse = "7291a930-6c4d-11ee-b962-0242ac120002";

		mockMvc.perform(post("/course/{idCourse}/purchase", idCourse).header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.course_id").value("7291a930-6c4d-11ee-b962-0242ac120002"));


		String lessonId = "7291d5a4-6c4d-11ee-b962-0242ac120009";

		mockMvc.perform(patch("/lessons/{lessonId}/markAsDone", lessonId).header("Authorization", authToken))
				.andExpect(status().isOk());
	}

	@Test
	void badMarkLessonAsDone() throws Exception{
		String lessonId = "7291d5a4-6c4d-11ee-k862-0242ac120123";

		assertThrows(Exception.class, () -> {
			mockMvc.perform(patch("/lessons/{lessonId}/markAsDone", lessonId).header("Authorization", authToken))
					.andExpect(status().isOk());
		});

	}

	@Test
	void getMyCourses() throws Exception{
		MvcResult result = mockMvc.perform(get("/courses/myCourses").header("Authorization", authToken))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<ReturningDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<ReturningDTO>>() {});
		List<String> idS = new ArrayList<>();

		for(ReturningDTO r : listaDto) {
			idS.add(r.getId());
		}
		assertTrue(idS.contains("72919b8e-6c4d-11ee-b962-0242ac120002"));
	}

	@Test
	void getMyCreatedCourses() throws Exception{
		MvcResult result = mockMvc.perform(get("/courses/getMyCreatedCourses").header("Authorization", authToken))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<CourseDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<CourseDTO>>() {});

		assertEquals("72919b8e-6c4d-11ee-b962-0242ac120002", listaDto.get(0).getId());
	}

	@Test
	void getMyNotDonePurchasedCourses() throws Exception{
		MvcResult result = mockMvc.perform(get("/courses/myCourses/notFinished").header("Authorization", authToken))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<CourseDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<CourseDTO>>() {});
		List<String> ids = new ArrayList<>();
		for(CourseDTO c : listaDto){
			ids.add(c.getId());
		}
		assertTrue(ids.contains("7291a930-6c4d-11ee-b962-0242ac120002"));
	}

	@Test
	void getCoursesUnregistered() throws Exception{
		MvcResult result = mockMvc.perform(get("/courses/unregistered").header("Authorization", authToken))
				.andExpect(status().isOk())
				.andReturn();

		String content = result.getResponse().getContentAsString();

		List<UnregisteredDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<UnregisteredDTO>>() {});
		List<String> names = new ArrayList<>();
		for(UnregisteredDTO c : listaDto){
			names.add(c.getTitle());
		}
		assertTrue(names.contains("Bases de dades") && names.contains("Programacio orientada a objectes"));
	}

	@Test
	void getReviews() throws Exception{
		MvcResult result = mockMvc.perform(get("/reviews?OrderByDate=false").header("Authorization", authToken))
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();

		List<ReviewCoursesDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<ReviewCoursesDTO>>() {});

		assertTrue(listaDto.size() > 0);
	}

	@Test
	void getUsersTakenACourse() throws Exception{
		String idCourse = "72919b8e-6c4d-11ee-b962-0242ac120002";
		MvcResult result = mockMvc.perform(get("/courses/{idCourse}/students", idCourse).header("Authorization", authToken))
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();

		List<UserDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<UserDTO>>() {});

		assertEquals("6074bc56-717f-11ee-b962-0242ac120002", listaDto.get(0).getId());
	}

	@Test
	void badGetUsersTakenACourse() throws Exception{
		//Non existing course
		String idCourse = "72919b8e-6c4d-11ee-b962-0242ac120123";
		assertThrows(Exception.class, () -> { mockMvc.perform(get("/courses/{idCourse}/students", idCourse).header("Authorization", authToken));
		});

	}

	@Test
	void getBestRatedTeachers() throws Exception{
		String idCourse = "7291ac3c-6c4d-11ee-b962-0242ac120002";

		mockMvc.perform(post("/course/{idCourse}/purchase", idCourse).header("Authorization", authToken))
				.andExpect(MockMvcResultMatchers.status().isOk());

		String review = """
				{
				   "title": "xx",
				   "content": "content",
				   "satisfaction": 1
				 }
				""";
		String id = "7291ac3c-6c4d-11ee-b962-0242ac120002";
		String lessonId = "3491d5a4-6c4d-11ee-b962-0242ac120012";
		mockMvc.perform(patch("/lessons/{lessonId}/markAsDone", lessonId).header("Authorization", authToken))
				.andExpect(status().isOk());

		mockMvc.perform(put("/course/{id}/review", id).header("Authorization", authToken)
						.contentType("application/json").content(review))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("content"));


		MvcResult result = mockMvc.perform(get("/teachers?x=1&year=2023").header("Authorization", authToken))
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();

		List<TeacherDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<TeacherDTO>>() {});

		assertEquals("Aleix", listaDto.get(0).getTeacherName());
	}

	@Test
	void badGetBestRatedTeachers() throws Exception{
		mockMvc.perform(get("/teachers").header("Authorization", authToken)).andExpect(status().isBadRequest());
	}

	@Test
	void getTopStudents() throws Exception{
		MvcResult result = mockMvc.perform(get("/students?x=1").header("Authorization", authToken))
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();

		List<StudentDTO> listaDto = objectMapper.readValue(content, new TypeReference<List<StudentDTO>>() {});

		assertEquals("Nil", listaDto.get(0).getName());
	}
}
