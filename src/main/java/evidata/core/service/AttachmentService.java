package evidata.core.service;

import evidata.core.model.Attachment;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
public interface AttachmentService {
    /**
     * Get a list will all available attachments
     *
     * @return the list of attachments
     */
    List<Attachment> getAllAttachments();

    /**
     * Create or update a attachment
     *
     * @param attachment saved entity
     * @return the new created attachment or updated attachment
     */
    Attachment saveAttachment(Attachment attachment);

    /**
     * Delete the attachment by given ID
     *
     * @param id identifier for deleted attachment
     */
    void deleteAttachment(Long id);

    /**
     * Get the attachment with given id
     *
     * @param id identifier of the attachment
     * @return the optional attachment
     */
    Optional<Attachment> findAttachment(Long id);
}
