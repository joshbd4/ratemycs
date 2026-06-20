This project uses slice tests with @WebMvcTest. The tests focus on the controller layer only, while the repository is mocked using @MockitoBean.
This approach was chosen because it provides fast and isolated tests for request handling, validation, model attributes, and redirects without requiring 
a database or the full Spring application context.
