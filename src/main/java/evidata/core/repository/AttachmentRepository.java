package evidata.core.repository;

import org.springframework.stereotype.Repository;
import evidata.core.model.Attachment;

import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Repository
public interface AttachmentRepository extends IRepository<Attachment, Long> {
    Optional<Attachment> findById(Long id);
}
