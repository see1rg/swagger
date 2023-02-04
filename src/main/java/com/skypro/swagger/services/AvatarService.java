package com.skypro.swagger.services;

import com.skypro.swagger.models.Avatar;
import com.skypro.swagger.models.Student;
import com.skypro.swagger.repository.AvatarRepository;
import com.skypro.swagger.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.awt.Image.SCALE_SMOOTH;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${avatar.dir.path}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository,
                         StudentRepository studentRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }


    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + ", " + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
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
        avatar.setPreview(generatePreview(filePath));

        avatarRepository.save(avatar);
    }


    public static byte[] generatePreview(Path filePath) throws IOException  {
        BufferedImage originalImage = ImageIO.read(filePath.toFile());

        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        int targetWidth;
        int targetHeight;

        if (originalWidth > originalHeight) {
            targetWidth = 120;
            targetHeight = (int) ((double) originalHeight / originalWidth * targetWidth);
        } else {
            targetHeight = 120;
            targetWidth = (int) ((double) originalWidth / originalHeight * targetHeight);
        }

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(originalImage.getScaledInstance(targetWidth,
                targetHeight, SCALE_SMOOTH), 0, 0, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", outputStream);
        outputStream.flush();

        return outputStream.toByteArray();
    }
    public Avatar findAvatarByStudentId(Long studentId) {
        return avatarRepository.findAvatarByStudentId(studentId)
                .orElse(new Avatar());
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getAllAvatar(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
