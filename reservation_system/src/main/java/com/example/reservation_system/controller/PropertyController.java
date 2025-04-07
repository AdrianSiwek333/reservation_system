package com.example.reservation_system.controller;

import com.example.reservation_system.entity.HostProfile;
import com.example.reservation_system.entity.Property;
import com.example.reservation_system.entity.PropertyImages;
import com.example.reservation_system.entity.Users;
import com.example.reservation_system.service.*;
import com.example.reservation_system.storage.StorageException;
import com.example.reservation_system.storage.StorageProperties;
import com.example.reservation_system.storage.StorageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/properties")
public class PropertyController {

    private final UsersService usersService;
    private final PropertyService propertyService;
    private final HostProfileService hostProfileService;
    private final PropertyTypeService propertyTypeService;
    private final CountryService countryService;
    private final StorageService storageService;
    private final Path rootLocation;
    private final PropertyImagesService propertyImagesService;

    public PropertyController(UsersService usersService, PropertyService propertyService,
                              HostProfileService hostProfileService, PropertyTypeService propertyTypeService,
                              CountryService countryService,
                              StorageService storageService,
                              StorageProperties storageProperties, PropertyImagesService propertyImagesService) {
        this.usersService = usersService;
        this.propertyService = propertyService;
        this.hostProfileService = hostProfileService;
        this.propertyTypeService = propertyTypeService;
        this.countryService = countryService;
        this.storageService = storageService;
        this.rootLocation = Paths.get(storageProperties.getLocation());
        this.propertyImagesService = propertyImagesService;
    }

    protected HostProfile getHostProfileIfHost(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        boolean isHost = authentication.getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals("HOST")
        );

        if(!isHost){
            return null;
        }

        String username = authentication.getName();
        Users user = usersService.findUserByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return hostProfileService.findById(user.getUserId()).orElseThrow(
                () -> new UsernameNotFoundException("Host profile not found")
        );
    }

    @GetMapping("/yourProperties")
    public String yourProperties(Model model) {

        HostProfile hostProfile = getHostProfileIfHost();
        if(hostProfile == null){
            return "redirect:/accessDenied";
        }

        List<Property> properties = propertyService.findByHostProfile(hostProfile);
        model.addAttribute("properties", properties);

        return "hostProperties";
    }

    @GetMapping("/add")
    public String addProperty(Model model) {

        HostProfile hostProfile = getHostProfileIfHost();

        if(hostProfile == null){
            return "redirect:/accessDenied";
        }

        model.addAttribute("propertyType", propertyTypeService.findAll());
        model.addAttribute("property", new Property());
        model.addAttribute("countries", countryService.findAll());
        return "property";
    }

    @GetMapping("/edit/{id}")
    public String editProperty(@PathVariable("id") int id, Model model) {

        HostProfile hostProfile = getHostProfileIfHost();

        if(hostProfile == null){
            return "redirect:/accessDenied";
        }

        prepareFormModelByPropertyId(model, id);
        return "property";
    }

    @PostMapping("/update")
    public String updateProperty(@Valid @ModelAttribute("property") Property property,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "imageFiles", required = false) MultipartFile[] imageFiles,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        HostProfile hostProfile = getHostProfileIfHost();
        if (hostProfile == null) {
            return "redirect:/accessDenied";
        }

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            prepareFormModel(model, property);
            return "property";
        }

        Path propertyDirectory = prepareImageDirectory(property.getPropertyId());
        property.setImages(fetchExistingImages(property.getPropertyId()));

        if (imageFiles != null) {
            Arrays.stream(imageFiles)
                    .filter(file -> !file.isEmpty())
                    .forEach(file -> {
                        storageService.store(file, "photos/property/" + property.getPropertyId());

                        PropertyImages newImage = new PropertyImages();
                        newImage.setImageUrl(buildImageUrl(propertyDirectory, file.getOriginalFilename()));
                        property.addImage(newImage);
                    });
        }

        property.setHostId(hostProfile);
        propertyService.update(property);

        return "redirect:/properties/yourProperties";
    }

    @GetMapping("/view/{id}")
    public String showProperty(@PathVariable("id") int id, Model model) {

        Optional<Property> property = propertyService.findById(id);
        if(property.isEmpty()){
            return "redirect:/siteDoesntExist";
        }
        model.addAttribute("property", property.get());
        return "propertyDetails";
    }

    @GetMapping("/view/browse")
    public String getProperties(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "query", required = false) String query,
                                @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                Model model) {
        Pageable pageable = PageRequest.of(page - 1, 9, Sort.by("propertyName").ascending());

        Page<Property> propertiesPage = propertyService.getProperties(query, minPrice, maxPrice, pageable);

        model.addAttribute("properties", propertiesPage.getContent());
        model.addAttribute("totalPages", propertiesPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "propertySearchPage";
    }

    @PostMapping("/removeImage")
    public String removeImage(@RequestParam("propertyId") int propertyId,
                              @RequestParam("imageId") int imageId,
                              Model model) {

        HostProfile hostProfile = getHostProfileIfHost();

        if(hostProfile == null){
            return "redirect:/accessDenied";
        }

        Property property = propertyService.findById(propertyId).orElseThrow(
                () -> new UsernameNotFoundException("Property not found")
        );

        property.removeImage(propertyImagesService.findById(imageId).orElseThrow(
                () -> new UsernameNotFoundException("image not found")
        ));

        prepareFormModelByPropertyId(model, propertyId);


        return "property";
    }

    private void prepareFormModel(Model model, Property property) {
        model.addAttribute("property", property);
        model.addAttribute("propertyType", propertyTypeService.findAll());
        model.addAttribute("countries", countryService.findAll());
    }

    private void prepareFormModelByPropertyId(Model model, int propertyId) {

        Property property = propertyService.findById(propertyId).orElseThrow(
                () -> new UsernameNotFoundException("Property not found")
        );

        model.addAttribute("propertyType", propertyTypeService.findAll());
        model.addAttribute("property", property);
        model.addAttribute("countries", countryService.findAll());
    }

    private Path prepareImageDirectory(int propertyId) {
        Path dir = rootLocation.resolve("photos/property/" + propertyId);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new StorageException("Failed to create directory for property", e);
        }
        return dir;
    }

    private List<PropertyImages> fetchExistingImages(int propertyId) {
        return propertyService.findById(propertyId)
                .map(Property::getImages)
                .orElseGet(ArrayList::new);
    }

    private String buildImageUrl(Path directory, String fileName) {
        return "/" + directory.toString().replace('\\', '/') + "/" + fileName;
    }



}
