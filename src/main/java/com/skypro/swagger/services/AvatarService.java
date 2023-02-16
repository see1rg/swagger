package com.skypro.swagger.services;

import com.skypro.swagger.models.Avatar;
import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.AvatarRepository;
import com.skypro.swagger.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${avatar.dir.path}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);


    public AvatarService(StudentService studentService, AvatarRepository avatarRepository,
                         StudentRepository studentRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }


    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.debug("Requesting to upload the avatar for the student with id: {}.", studentId);

        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)

        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatarByStudentId(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setPreview(generatedImagePreview(filePath));

        avatarRepository.save(avatar);
    }

    private byte[] generatedImagePreview(Path filePath) throws IOException {
        logger.info("Generating preview.");

        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    public Avatar findAvatarByStudentId(Long studentId) {
        logger.info("Requesting to find the avatar by student id: {}.", studentId);
        return avatarRepository.findAvatarByStudentId(studentId)
                .orElse(new Avatar());
    }

    private String getExtension(String fileName) {
        logger.info("Getting extension for the file with name: {}.", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getAllAvatar(Integer pageNumber, Integer pageSize) {
        logger.info("Requesting all avatars for page with number: {}.", pageNumber);
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
