🍽️ Restaurant Management System
This is a Spring Boot REST API for a Restaurant Management System that supports:

✅ User Registration with Email Verification

✅ JWT Authentication

✅ Restaurant Management (CRUD)

✅ Menu Management (CRUD)

✅ Order Management (CRUD)

✅ Secure Endpoints with Token Validation

🚀 Tech Stack
Backend: Spring Boot

Database: MySQL

Security: Spring Security, JWT

Email: Mailtrap (for testing)

ORM: Hibernate (JPA)

Build Tool: Maven

✅ Features
User registration with email verification link.

Secure JWT authentication and authorization.

Role-based access control.

CRUD for Restaurants, Menus, and Orders.

Full token validation using request headers.

Scalable token strategy compatible with Postman, frontend, and mobile.

⚙️ Setup Instructions
1. Clone the Repository
bash
Copy
Edit
git clone https://github.com/your-username/restaurant-management-system.git
cd restaurant-management-system
2. Configure Database
Update application.properties:

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_db
spring.datasource.username=root
spring.datasource.password=your_password
3. Configure Mailtrap (Email Testing)
properties
Copy
Edit
spring.mail.host=smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=your_mailtrap_username
spring.mail.password=your_mailtrap_password
4. Run the Application
bash
Copy
Edit
./mvnw spring-boot:run
🛠️ API Endpoints
🔑 Authentication
POST /api/auth/register – Register a new user

GET /api/auth/verify?token=xxx – Verify email

POST /api/auth/login – Login and receive JWT token

🍴 Restaurant
POST /api/restaurant/add – Add restaurant

GET /api/restaurant – Get all restaurants

PUT /api/restaurant/update/{id} – Update restaurant

DELETE /api/restaurant/delete/{id} – Delete restaurant

🍽️ Menu
POST /api/menu/add – Add menu

GET /api/menu – Get all menus

PUT /api/menu/update/{id} – Update menu

DELETE /api/menu/delete/{id} – Delete menu

🛒 Order
POST /api/orders/add – Place an order (JWT token required)

GET /api/orders – Get all orders

PUT /api/orders/update/{id} – Update order

DELETE /api/orders/delete/{id} – Delete order

🔐 Authentication Strategy
Token: Sent in the Authorization header as:
Bearer <your-jwt-token>

Postman: Add the token in Authorization header.

Frontend: Store token in localStorage and send it in headers.

🧪 Postman Testing Example
Headers:
text
Copy
Edit
Authorization: Bearer <your-jwt-token>
Content-Type: application/json
Body Example for Order:
json
Copy
Edit
{
    "menuId": 1,
    "quantity": 2
}
🚀 Future Improvements
Add unit and integration tests.

Implement user roles (ADMIN, USER) with access restrictions.

Add Swagger for API documentation.

Add email templates for verification messages.
