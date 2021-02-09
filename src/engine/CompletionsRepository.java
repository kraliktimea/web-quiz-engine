package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionsRepository extends JpaRepository<Completions, Integer> {

    @Query("select c from Completions c where c.userId = :userId ORDER BY c.completedAt DESC")
    Page<Completions> findAllByUserId(@Param("userId") Integer userId, Pageable pageable);
}

