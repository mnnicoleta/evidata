package evidata.core.service;

import evidata.core.model.AppUser;
import evidata.core.model.Attachment;
import evidata.core.model.Expertise;
import evidata.core.repository.AttachmentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * Created by Nicolle on 10.09.2018
 */

public class AttachmentServiceTest {
    private AttachmentRepository attachmentRepositoryMock;

    private AttachmentServiceImpl attachmentService;

    @Before
    public void setUp() throws Exception {
        attachmentService = new AttachmentServiceImpl();

        attachmentRepositoryMock = Mockito.mock(AttachmentRepository.class);
        attachmentService.setAttachmentRepository(attachmentRepositoryMock);
    }
    
    @Test
    public void testGetAllAttachments() {
        Attachment attachment =new Attachment();

        List<Attachment> mockList = Arrays.asList(attachment);
        Mockito.doReturn(mockList).when(attachmentRepositoryMock).findAll();

        List<Attachment> attachmentList = attachmentService.getAllAttachments();
        Assert.assertTrue(attachmentList.size() == 1);

        Assert.assertSame(attachment, attachmentList.get(0));

    }

    @Test
    public void testSaveAttachments() {
        String fileName = "document.png";
        Attachment attachment= new Attachment();
        attachment.setFileName(fileName);

        Attachment savedAttachmentMock= new Attachment();
        Mockito.doReturn(savedAttachmentMock).when(attachmentRepositoryMock).save(attachment);

        Attachment savedAttachment = attachmentService.saveAttachment(attachment);
        Assert.assertSame(savedAttachmentMock,savedAttachment);

    }

    @Test
    public void testDeleteAttachments() {
        String fileName = "document.png";
        Attachment attachment= new Attachment();
        attachment.setFileName(fileName);

        Long id = 1L;

        Attachment attachmentMock = new Attachment();
        attachmentMock.setAppUser(new AppUser());
        attachmentMock.setExpertise(new Expertise());

        Mockito.doReturn(attachmentMock).when(attachmentRepositoryMock).findOne(id);

        attachmentService.deleteAttachment(id);

        verify(attachmentRepositoryMock, times(1)).save(attachmentMock);
        Assert.assertNull(attachmentMock.getAppUser());
        Assert.assertNull(attachmentMock.getExpertise());

    }
}
