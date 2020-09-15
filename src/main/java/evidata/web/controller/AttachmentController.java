package evidata.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import evidata.core.model.Attachment;
import evidata.core.service.AppSettingsService;
import evidata.core.service.AppUserService;
import evidata.core.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on aug. in 2018
 */
@Controller
public class AttachmentController {
    private static final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AppSettingsService appSettingsService;

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(value = "attachmentList", method = RequestMethod.GET)
    public String getAttachments(Model model) {
        log.trace("getAttachments");
        List<Attachment> attachments = attachmentService.getAllAttachments();

        log.trace("getAttachments: attachments={}", attachments);

        model.addAttribute("attachments", attachments);
        model.addAttribute("message", "Success");
        return "attachmentList";

    }

    @RequestMapping(value = {"attachmentEdit", "attachmentEdit/{id}"}, method = RequestMethod.GET)
    public String attachmentEdit(Model model, @PathVariable(required = false, name = "id") Long id) {
        log.trace("getAttachment: id={}", id);
        Attachment attachment;
        if (id != null) {
            Optional<Attachment> attachmentOptional = attachmentService.findAttachment(id);
            if (attachmentOptional.isPresent()) {
                attachment = attachmentOptional.get();
            } else {
                //attachment not found!
                return "attachmentList";
            }
        } else {
            attachment = new Attachment();
        }

        model.addAttribute("attachment", attachment);
        return "attachmentEdit";

    }


    @RequestMapping(value = "attachmentEdit", method = RequestMethod.POST)
    public RedirectView attachmentSave(@Valid Attachment attachment, BindingResult bindingResult) {
        log.trace("saveAttachment: {}", attachment);

        attachmentService.saveAttachment(attachment);

        return new RedirectView("/attachmentList");
    }


    @RequestMapping(value = "attachmentDelete/{id}", method = RequestMethod.GET)
    public RedirectView deleteAttachment(Model model, Authentication authentication, @PathVariable final Long id, @RequestParam("expertiseId") Optional<Integer> expertiseId) {
        log.trace("deleteAttachment: id={}", id);
        if (id != null) {
            Optional<Attachment> attachmentOptional = attachmentService.findAttachment(id);
            if (attachmentOptional.isPresent()) {
                attachmentService.deleteAttachment(id);
            }
        }
        if (expertiseId.isPresent()) {
            return new RedirectView("/expertiseDetail/" + expertiseId.get());
        }
        return new RedirectView("/attachmentList");
    }

    @RequestMapping(value = "/downloadAttachment/{id}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, Authentication authentication, @PathVariable final Long id) throws IOException {

        File file = null;

        if (id != null) {
            Optional<Attachment> attachmentOptional = attachmentService.findAttachment(id);
            Attachment attachment = attachmentOptional.get();
            if (attachment != null) {
                String fullFileName = getFullFileName(attachment);
                log.info("Download file:" + fullFileName);
                file = new File(fullFileName);
            }
        }


        if (!file.exists()) {
            String errorMessage = "Sorry. The file you are looking for does not exist";
            createErrorMessage(response, errorMessage);
            return;
        }

        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            log.warn("Mime type not found for file {}", file.getName());
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    void createErrorMessage(HttpServletResponse response, String errorMessage) throws IOException {
        log.warn("Error message:" + errorMessage);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
        outputStream.close();
    }

    protected String getFullFileName(Attachment attachment) {
        String dir = appSettingsService.getAppSettings().getAttachmentDir();
        String fileName = attachment.getExpertise().getId() + File.separator + attachment.getId() + "_" + attachment.getFileName();
        return dir + File.separator + fileName;
    }
}
