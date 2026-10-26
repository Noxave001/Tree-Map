/**
 * A record containing thorough identification of a book
 *
 * @author Ace Breaux
 * @version 5-21-2025
 *
 * @param isbn the ISBN of the book
 * @param authors the author(s) of the book
 * @param publicationYear the year the book was published
 * @param origTitle the original title of the book
 * @param title the current publication title of the book
 * @param averageRating the average rating (out of 5 points) for the book
 */
public record Book(String isbn,
                   String authors,
                   int publicationYear,
                   String origTitle,
                   String title,
                   double averageRating) {
}
