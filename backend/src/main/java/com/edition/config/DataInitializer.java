package com.edition.config;

import com.edition.model.Book;
import com.edition.model.BookCategory;
import com.edition.model.Role;
import com.edition.model.User;
import com.edition.repository.BookRepository;
import com.edition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedUsers();
        seedBooks();
    }

    private void seedUsers() {
        // Admin
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println(">>> Utilisateur admin créé: admin / admin123");
        } else {
            System.out.println(">>> Admin déjà présent (seed ignoré)");
        }

        // User
        if (!userRepository.existsByUsername("user")) {
            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .role(Role.ROLE_USER)
                    .build();
            userRepository.save(user);
            System.out.println(">>> Utilisateur demo créé: user / user123");
        }
    }

    private void seedBooks() {
        List<Book> demo = List.of(
                Book.builder()
                        .title("Les Misérables")
                        .author("Victor Hugo")
                        .isbn("978-1234567890")
                        .price(19.90)
                        .description("Un classique de la littérature française.")
                        .category(BookCategory.ROMAN)
                        .publicationYear(1862)
                        .coverUrl(null)
                        .build(),

                Book.builder()
                        .title("Les Fleurs du mal")
                        .author("Charles Baudelaire")
                        .isbn("978-1234567891")
                        .price(12.50)
                        .description("Recueil majeur de poésie.")
                        .category(BookCategory.POESIE)
                        .publicationYear(1857)
                        .coverUrl(null)
                        .build(),

                Book.builder()
                        .title("Cyrano de Bergerac")
                        .author("Edmond Rostand")
                        .isbn("978-1234567892")
                        .price(14.00)
                        .description("Pièce de théâtre en vers.")
                        .category(BookCategory.THEATRE)
                        .publicationYear(1897)
                        .coverUrl(null)
                        .build(),

                Book.builder()
                        .title("Discours de la méthode")
                        .author("René Descartes")
                        .isbn("978-1234567893")
                        .price(9.90)
                        .description("Texte fondateur de la philosophie moderne.")
                        .category(BookCategory.ESSAI)
                        .publicationYear(1637)
                        .coverUrl(null)
                        .build(),

                Book.builder()
                        .title("Mémoires d’outre-tombe")
                        .author("Chateaubriand")
                        .isbn("978-1234567894")
                        .price(18.00)
                        .description("Autobiographie monumentale.")
                        .category(BookCategory.BIOGRAPHIE)
                        .publicationYear(1848)
                        .coverUrl(null)
                        .build(),

                Book.builder()
                        .title("Germinal")
                        .author("Émile Zola")
                        .isbn("978-1234567895")
                        .price(11.90)
                        .description("Roman naturaliste sur le monde ouvrier.")
                        .category(BookCategory.ROMAN)
                        .publicationYear(1885)
                        .coverUrl(null)
                        .build(),

                Book.builder()
                        .title("Le Cid")
                        .author("Pierre Corneille")
                        .isbn("978-1234567896")
                        .price(10.00)
                        .description("Tragi-comédie classique.")
                        .category(BookCategory.THEATRE)
                        .publicationYear(1637)
                        .coverUrl(null)
                        .build(),

                Book.builder()
                        .title("Essais")
                        .author("Montaigne")
                        .isbn("978-1234567897")
                        .price(16.50)
                        .description("Réflexions sur l’humanité et soi-même.")
                        .category(BookCategory.ESSAI)
                        .publicationYear(1580)
                        .coverUrl(null)
                        .build()
        );

        int inserted = 0;
        for (Book b : demo) {
            // Important: ne pas re-insérer si ISBN déjà présent (évite crash UNIQUE)
            if (!bookRepository.existsByIsbn(b.getIsbn())) {
                bookRepository.save(b);
                inserted++;
            }
        }

        System.out.println(">>> Seed livres vérifié (insert si manquant). Ajoutés=" + inserted);
    }
}
