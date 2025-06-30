package com.nomnom.backend.service;

import com.google.cloud.firestore.Firestore;
import com.nomnom.backend.dto.CreateFolderRequest;
import com.nomnom.backend.dto.FolderDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "FIREBASE_PROJECT_ID=nomnom-f3371"
})
public class FolderServiceTest {
    
    @MockBean
    private Firestore firestore;
    
    @Test
    public void testCreateFolderRequest() {
        CreateFolderRequest request = new CreateFolderRequest("Winter Gerechten", "Warme maaltijden voor de winter");
        
        assertNotNull(request);
        assertEquals("Winter Gerechten", request.getName());
        assertEquals("Warme maaltijden voor de winter", request.getDescription());
    }
    
    @Test
    public void testFolderDto() {
        FolderDto folder = new FolderDto();
        folder.setName("Test Folder");
        folder.setDescription("Test Description");
        folder.setUserId("test-user-123");
        
        assertEquals("Test Folder", folder.getName());
        assertEquals("Test Description", folder.getDescription());
        assertEquals("test-user-123", folder.getUserId());
        assertNotNull(folder.getMealIds());
    }
} 