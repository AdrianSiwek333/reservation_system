package com.example.reservation_system.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties) {

        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be empty");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        if(Files.notExists(rootLocation)){
            try{
                Files.createDirectories(rootLocation);
            }
            catch (IOException e){
                throw new StorageException("Could not initialize storage", e);
            }
        }
    }

    @Override
    public void store(MultipartFile file, String uploadDir) {
        try{
            if(file.isEmpty()){
                throw new StorageException("File upload can not be empty");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(uploadDir, file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if(!destinationFile.startsWith(this.rootLocation.toAbsolutePath())){
                throw new StorageException(
                        "Cannot store file outside current directory"
                );
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e){
            throw new StorageException("Failed to store file.");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e){
            throw new StorageException("Failed to read stored files.");
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try{
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else{
                throw new StorageException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e){
            throw new StorageFileNotFoundException("Could not read file: " + filename);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
    }
}