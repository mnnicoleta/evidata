package evidata.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import evidata.core.model.Attachment;
import evidata.core.repository.AttachmentRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private static final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);


    private AttachmentRepository attachmentRepository;

    @Override
    public List<Attachment> getAllAttachments() {
        log.trace("getAllAttachments --- method entered");

        List<Attachment> attachments = attachmentRepository.findAll();

        log.trace("getAllAttachments: attachments={}", attachments);

        return attachments;
    }

    @Transactional
    @Override
    public Attachment saveAttachment(Attachment attachment) {
        if (attachment.getId() == null) {
            log.trace("create new attachment type={} ", attachment.getFileName());
        } else {
            log.trace("updateAttachment: id={}, type={} ", attachment.getId(), attachment.getFileName());
        }
        Attachment savedAttachment = attachmentRepository.save(attachment);
        log.trace("saveAttachment: result={}", savedAttachment);
        return savedAttachment;
    }

    @Override
    @Transactional
    public void deleteAttachment(Long id) {
        log.trace("deleteAttachment: id={}", id);
        Attachment attachment = attachmentRepository.findOne(id);
        attachment.setAppUser(null);
        attachment.setExpertise(null);

        attachmentRepository.save(attachment);

        attachmentRepository.delete(id);
        log.trace("deleteAttachment --- method finished");
    }


    @Override
    public Optional<Attachment> findAttachment(Long id) {
        log.trace("findAttachment: id={}", id);

        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);

        log.trace("findAttachment: attachmentOptional={}", attachmentOptional);

        return attachmentOptional;
    }

    public AttachmentRepository getAttachmentRepository() {
        return attachmentRepository;
    }

    @Autowired
    public void setAttachmentRepository(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }
}
