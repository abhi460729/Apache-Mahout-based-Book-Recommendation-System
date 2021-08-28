# Apache-Mahout-based-Book-Recommendation-System

An Online Book Shopping Platform build using Spring Boot, Spring Data JPA, Spring MVC, Thymeleaf and MySQL Database (Business Layer and CRUD Layer APIs) with capabilities of Caching Data and Recommending Books to Users (using Apache Mahout).

Features -
- Add Books to the Website (for Sale)
- Remove Books from the Website (if unavailable for Sale)
- User Registration/ Login/ Logout
- Search Books (by Author/ Name/ Category)
- Add Books to the Cart
- Remove Books from the Cart
- Review Order Details (Total Amount)
- Place Order and Checkout
- Rate Book
- Delete User
- Get Book Recommendations

Implemented a User based Collaborative Filtering Algorithm for Recommending Books.
- Used Euclidean Distance Similarity, Nearest N User Neighborhood and Generic User Based Recommended to get best quality recommendations.
- Achieved the lowest evaluation score of 0.5 using Euclidean Distance Similarity as compared to other similarity measures to provide the best quality recommendations.
